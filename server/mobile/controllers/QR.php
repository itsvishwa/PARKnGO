<?php
class QR extends Controller
{
    private $driver_qr_model;
    private $officer_model;
    private $parking_space_model;
    private $session_model;
    private $parking_space_status_model;

    public function __construct()
    {
        $this->driver_qr_model = $this->model("DriverQRModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->parking_space_model = $this->model("ParkingSpaceModel");
        $this->session_model = $this->model("SessionModel");
        $this->parking_space_status_model = $this->model("ParkingSpaceStatusModel");
    }

    // driver mobile - used to get encrypted code for the QRs
    public function get_qr_code($selected_vehicle_number)
    {
        $token_data = $this->verify_token_for_drivers();
        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            $qr_id = $this->driver_qr_model->get_qr_id($token_data["user_id"], $selected_vehicle_number);
            if ($qr_id == false) // no records for a giver driver id and selected vehicle number
            {
                $this->send_json_400("QR_IDD"); //invalid driver data
            } else {
                $time_stamp = time();

                //update timestamp
                $this->driver_qr_model->update_time_stamp($time_stamp, $qr_id);

                //encode qr_id
                $qr_id = $this->encrypt_id($qr_id);

                $this->send_json_200($qr_id);
            }
        }
    }


    public function read_qr($encoded_qr_id)
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $encoded_parking_id = $_SERVER['HTTP_ENCODED_PARKING_ID'];
            $parking_id = $this->decrypt_id($encoded_parking_id);

            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            if ($assigned_parking == $parking_id) { //parking_id is similar to the assigned parking
                $qr_id = $this->decrypt_id($encoded_qr_id);

                //check whether the qr_id exists
                $is_qr_exists = $this->driver_qr_model->is_qr_exists($qr_id);

                if (!$is_qr_exists) {
                    $result = [
                        "response_code" => "801",
                        "message" => "Invalid QR"
                    ];

                    $this->send_json_404($result);
                } else {
                    $qr_data = $this->driver_qr_model->get_vehicle_info($qr_id);

                    if ((time() - $qr_data["auth_time_stamp"]) < 300) {
                        $result = [
                            "response_code" => "800",
                            "driver_id" => $qr_data["driver_id"],
                            "vehicle_number" => $qr_data["vehicle_number"],
                            "vehicle_type" => $qr_data["vehicle_type"]
                        ];

                        $this->send_json_200($result);
                    } else    // qr expired
                    {
                        $result = [
                            "response_code" => "802",
                            "message" => "QR Expired"
                        ];

                        $this->send_json_404($result);
                    }
                }
            } else {    //parking_id is not similar to the assigned parking

                $assigned_parking_details = $this->parking_space_model->get_parking_space_details($assigned_parking);

                if ($assigned_parking_details) {
                    $assigned_parking_name = $assigned_parking_details->name;

                    $result = [
                        "response_code" => "101",
                        "updated parking_id" => $assigned_parking,
                        "updated parking_name" => $assigned_parking_name,
                    ];

                    $this->send_json_200($result);
                } else {
                    $result = [
                        "response_code" => "204",
                        "message" => "parking details not found"
                    ];

                    $this->send_json_404($result);
                }
            }
        }
    }
}
