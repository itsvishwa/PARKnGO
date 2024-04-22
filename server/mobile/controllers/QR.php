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
                            "driver_id" => $qr_data["driver_id"],
                            "vehicle_number" =>$qr_data["vehicle_number"],
                            "vehicle_type" => $qr_data["vehicle_type"]
                        ];

                        $this->send_json_200($result);
                    }
                    else    // qr expired
                    {
                        $result = [
                            "response_code" => "204",
                            "message" => "QR Expired"
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


    public function end_session_details($encoded_session_id) {
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
                $session_id = $this->decrypt_id($encoded_session_id);

                //check whether the session_id exists
                $session_exists = $this->session_model->is_session_exists($session_id);

                if (!$session_exists) {
                    // No such session found
                    $result = [
                        "response_code" => "204",
                        "message" => "No such session found"
                    ];

                    $this->send_json_404($result);
                }
                else {
                    // Session Found
                    $ended_session = $this->session_model->is_session_already_ended($session_id);

                    if($ended_session) {
                        // Session already ended
                        $result = [
                            "response_code" => "409",
                            "message" => "This session is already ended"
                        ];
    
                        $this->send_json_404($result);

                    }
                    else {
                        // Open session exists
                        // get the parking session details from the parking_session table
                        $parking_session_data = $this->session_model->get_open_session_data($session_id);

                        //The parking that the vehicle is assigned currently
                        $current_vehicle_assigned_parking = $parking_session_data->parking_id;

                        if($current_vehicle_assigned_parking  == $parking_id) { // Check whether the founded open session is belong to this parking

                            $encrypted_session_id = $this->encrypt_id($parking_session_data->_id);

                            $start_timestamp = $parking_session_data->start_time;
                            
                            $end_timestamp = time();
                            
                            $duration = $end_timestamp - $start_timestamp;
                            
                            // Convert duration to hours and minutes
                            $hours = floor($duration / 3600);
                            $minutes = floor(($duration % 3600) / 60);

                            // Format the duration
                            $formatted_duration = sprintf('%02d H %02d Min', $hours, $minutes);


                            // calculate amount

                            if (isset($parking_session_data->vehicle_type) && isset($parking_session_data->parking_id)) {
                                $vehicle_num = $parking_session_data->vehicle_number;

                                $vehicle_type = $parking_session_data->vehicle_type;
                                $vehicle_type_letter = $this->convert_to_vehicle_category($vehicle_type);
                                
                                $parking_id = $parking_session_data->parking_id;

                                $hourly_rate_value = $this->parking_space_status_model->get_rate($vehicle_type_letter, $parking_id);
                                $hourly_rate = $hourly_rate_value->rate;

                                $amount = $this->calculate_amount($start_timestamp, $end_timestamp, $hourly_rate);

                                $formatted_amount = 'Rs. ' . number_format($amount, 0) . '.00';


                                $result = [
                                    "response_code" => "800",
                                    "message" => "QR end session details successfull!",
                                    "session_id" => $encrypted_session_id,
                                    "end_Time_Stamp" => $end_timestamp,
                                    "start_Time_Stamp" => $start_timestamp,
                                    "duration" => $formatted_duration,
                                    "amount" => $formatted_amount,
                                    "vehicle_number" => $vehicle_num,
                                    "vehicle_type" => $vehicle_type
                                ];

                                $this->send_json_200($result);
                            }

                        } else {
                            $result = [
                                "response_code" => "204",
                                "message" => "Searched Parking Session is not belongs to your parking"
                            ];
                            $this->send_json_404($result);
                        }
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

    //calculate Amount
    public function calculate_amount($start_timestamp, $end_timestamp, $hourly_rate)
    {
        // Validate if $hourly_rate is a valid number
        if (is_numeric($hourly_rate)) {
            $duration = $end_timestamp - $start_timestamp;

            // Convert duration to hours and minutes
            $hours = floor($duration / 3600);
            $minutes = floor(($duration % 3600) / 60);

            $total_parked_time = $hours + ($minutes / 60);
            $amount = $hourly_rate * $total_parked_time;

            return $amount;
        } else {
            return 'Invalid rate';
        }
    }

}
