<?php
class QR extends Controller
{
    public $driver_qr_model;
    private $officer_model;
    private $parking_space_model;

    public function __construct()
    {
        $this->driver_qr_model = $this->model("DriverQRModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->parking_space_model = $this->model("ParkingSpaceModel");
    }

    public function get_qr_code($selected_vehicle_number)
    {
        $token_data = $this->verify_token_for_drivers();
        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else {
            $qr_id = $this->driver_qr_model->get_qr_id($token_data["user_id"], $selected_vehicle_number);
            if ($qr_id === false) // no records for a giver driver id and selected vehicle number
            {
                $this->send_json_400("E_QR_5023");
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

    public function read_qr($encoded_qr_id) {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $encoded_parking_id = $_SERVER['HTTP_ENCODED_PARKING_ID'];
            $parking_id = $this->decrypt_id($encoded_parking_id);

            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            if ($assigned_parking === $parking_id) { //parking_id is similar to the assigned parking
                $qr_id = $this->decrypt_id($encoded_qr_id);

                //check whether the qr_id exists
                $is_qr_exists = $this->driver_qr_model->is_qr_exists($qr_id);
                
                if(!$is_qr_exists) {
                    $result = [
                        "response_code" => "204",
                        "message" => "No qr_id found"
                    ];

                    $this->send_json_404($result);
                } else {
                    $qr_data = $this->driver_qr_model->get_vehicle_info($qr_id);

                    if((time() - $qr_data["auth_time_stamp"]) > 300) {
                        $result = [
                            "response_code" => "800",
                            "Driver_id" => $qr_data["driver_id"],
                            "Vehicle_Number" => $qr_data["vehicle_number"],
                            "Vehicle_Type" => $qr_data["vehicle_type"]
                        ];

                        $this->send_json_200($result);
                    }
                    else    // qr expired
                    {
                        $result = [
                            "response_code" => "204",
                            "message" => "qr expired"
                        ];

                        $this->send_json_404($result);
                    }

                }

                    
            } 
            else {    //parking_id is not similar to the assigned parking

                $assigned_parking_details = $this->parking_space_model->get_parking_space_details($assigned_parking);
                if ($assigned_parking_details) // new parking has been assigned to the officer
                {
                    // You have been reassigned to a new parking space
                    $this->send_json_400("101");
                } else // no parking has been assigned to the officer
                {
                    // parking details not found
                    $this->send_json_404("204");
                }
            }
        }
    }
}