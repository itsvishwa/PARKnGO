<?php
class Session extends Controller
{
    private $session_model;
    private $officer_model;
    private $officer_activity_model;
    private $parking_space_status_model;
    private $payment_model;
    private $parking_space_model;


    public function __construct()
    {
        $this->session_model = $this->model("SessionModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->officer_activity_model = $this->model("OfficerActivityModel");
        $this->parking_space_status_model = $this->model("ParkingSpaceStatusModel");
        $this->payment_model = $this->model("PaymentModel");
        $this->parking_space_model = $this->model("ParkingSpaceModel");
    }


    public function start()
    {

        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $session_data = [
                "vehicle_number" => trim($_POST["vehicle_number"]),
                "vehicle_type" => trim($_POST["vehicle_type"]),
                "start_time" => time(),
                "officer_id" => $token_data["user_id"],
                "parking_id" => trim($_POST["parking_id"]),
                "driver_id" => trim($_POST["driver_id"])
            ];

            // Get the assigned parking_id
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            // Decrypt the parking_id that comes with the url
            $decrypted_parking_id = $this->decrypt_id($session_data["parking_id"]);

            // Replace the encrypted parking_id with the decrypted value
            $session_data["parking_id"] = $decrypted_parking_id;


            if ($assigned_parking == $session_data["parking_id"]) { //parking_id is similar to the assigned parking 

                $open_session = $this->session_model->is_open_session_exists($session_data["vehicle_number"]);

                // check whether the given vehicle number has an open session
                if ($open_session) {
                    $result = [
                        "response_code" => "409",
                        "message" => "This vehicle is already having a ongoing parking session"
                    ];

                    $this->send_json_404($result);

                    // No open session exists for the vehicle number
                } else {    // No open session exists for the vehicle number
                    if ($session_data["driver_id"] == "-1") {
                        // If driver_id is -1, set it to null before adding session data
                        $session_data["driver_id"] = null;
                    }

                    // add new session data to the parking_session table
                    $this->session_model->add_session($session_data);

                    // fetch the session id
                    $session_data["session_id"] = $this->session_model->get_session_id($session_data["vehicle_number"], $session_data["start_time"]);

                    // add new officer activity to the officer_activity table
                    $this->officer_activity_model->add_officer_activity($session_data);

                    // update the parking_space_status table
                    $vehicle_type = $session_data["vehicle_type"];
                    $vehicle_type_letter = $this->convert_to_vehicle_category($vehicle_type);

                    $this->parking_space_status_model->decrease_free_slots($vehicle_type_letter, $session_data["parking_id"]);


                    $result = [
                        "response_code" => "800",
                        "message" => "New parking session is created successfully!"
                    ];

                    $this->send_json_200($result);
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
                        "message" => "Parking details not found"
                    ];

                    $this->send_json_404($result);
                }
            }
        }
    }


    // Search parking session
    public function search($url_safe_vehicle_number)
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

            // Convert "~" to "#" in the vehicle number
            $vehicle_number = str_replace('~', '#', $url_safe_vehicle_number);

            if ($assigned_parking == $parking_id) { //parking_id is similar to the assigned parking

                $open_session = $this->session_model->is_open_session_exists($vehicle_number);
                $formatted_vehicle_number = $this->format_vehicle_number($vehicle_number);

                // check whether the given vehicle number has an open session
                if (!$open_session) {
                    $result = [
                        "response_code" => "801",
                        "message" => "No open session found for the vehicle number $formatted_vehicle_number"
                    ];

                    $this->send_json_404($result);

                    // An open session exists for the given vehicle number
                } else {

                    // get the parking session details from the parking_session table
                    $parking_session_data = $this->session_model->search_session($vehicle_number);

                    if ($parking_session_data == false) // no parking session for a given vehicle number
                    {
                        $result = [
                            "response_code" => "802",
                            "message" => "Parking Session Not Found"
                        ];

                        $this->send_json_404($result);
                    } else // parking session found
                    {

                        //The parking that the vehicle is assigned currently
                        $current_vehicle_assigned_parking = $parking_session_data->parking_id;

                        if ($current_vehicle_assigned_parking  == $parking_id) { // Check whether the founded open session is belong to this parking

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
                                $vehicle_type = $parking_session_data->vehicle_type;
                                $vehicle_type_letter = $this->convert_to_vehicle_category($vehicle_type);

                                $parking_id = $parking_session_data->parking_id;

                                $hourly_rate_value = $this->parking_space_status_model->get_rate($vehicle_type_letter, $parking_id);
                                $hourly_rate = $hourly_rate_value->rate;

                                $amount = $this->calculate_amount($start_timestamp, $end_timestamp, $hourly_rate);

                                $formatted_amount = 'Rs. ' . number_format($amount, 0) . '.00';

                                $formattedSDateTime = $this->format_time($start_timestamp);
                                $formatted_sTime = $formattedSDateTime[0];
                                $formatted_sDate = $formattedSDateTime[1];


                                $result = [
                                    "response_code" => "800",
                                    "message" => "Search session is successfull!",
                                    "session_id" => $encrypted_session_id,
                                    "end_Time_Stamp" => $end_timestamp,
                                    "start_Time_Stamp" => $start_timestamp,
                                    "formatted_SDate" => $formatted_sDate,
                                    "formatted_STime" => $formatted_sTime,
                                    "duration" => $formatted_duration,
                                    "amount" => $formatted_amount,
                                    "vehicle_Number" => $vehicle_number,
                                    "vehicle_Type" => $vehicle_type
                                ];

                                $this->send_json_200($result);
                            }
                        } else {
                            $result = [
                                "response_code" => "803",
                                "message" => "Searched Parking Session is not belongs to your parking"
                            ];
                            $this->send_json_404($result);
                        }
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


    // Search parking session
    public function get_in_progress_session_details($encrypted_session_id)
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


            if ($assigned_parking == $parking_id) {
                //parking_id is similar to the assigned parking
                $session_id = $this->decrypt_id($encrypted_session_id);

                $open_session_data = $this->session_model->get_open_session_data($session_id);

                if ($open_session_data == false) {
                    $result = [
                        "response_code" => "801",
                        "message" => "No open Session Not Found"
                    ];

                    $this->send_json_404($result);
                } else {
                    //The parking that the vehicle is assigned currently
                    $current_vehicle_assigned_parking = $open_session_data->parking_id;

                    if ($current_vehicle_assigned_parking  == $parking_id) {
                        // Check whether the founded open session is belong to this parking

                        $start_timestamp = $open_session_data->start_time;

                        $end_timestamp = time();

                        $duration = $end_timestamp - $start_timestamp;

                        // Convert duration to hours and minutes
                        $hours = floor($duration / 3600);
                        $minutes = floor(($duration % 3600) / 60);

                        // Format the duration
                        $formatted_duration = sprintf('%02d H %02d Min', $hours, $minutes);


                        // calculate amount

                        if (isset($open_session_data->vehicle_type) && isset($open_session_data->parking_id)) {
                            $vehicle_type = $open_session_data->vehicle_type;
                            $vehicle_type_letter = $this->convert_to_vehicle_category($vehicle_type);

                            $parking_id = $open_session_data->parking_id;

                            $hourly_rate_value = $this->parking_space_status_model->get_rate($vehicle_type_letter, $parking_id);
                            $hourly_rate = $hourly_rate_value->rate;

                            $amount = $this->calculate_amount($start_timestamp, $end_timestamp, $hourly_rate);

                            $formatted_amount = 'Rs. ' . number_format($amount, 0) . '.00';

                            $formattedSDateTime = $this->format_time($start_timestamp);
                            $formatted_sTime = $formattedSDateTime[0];
                            $formatted_sDate = $formattedSDateTime[1];


                            $result = [
                                "response_code" => "800",
                                "message" => "In progress session is details",
                                "session_id" => $encrypted_session_id,
                                "end_Time_Stamp" => $end_timestamp,
                                "start_Time_Stamp" => $start_timestamp,
                                "formatted_SDate" => $formatted_sDate,
                                "formatted_STime" => $formatted_sTime,
                                "duration" => $formatted_duration,
                                "amount" => $formatted_amount,
                                "vehicle_Number" => $open_session_data->vehicle_number,
                                "vehicle_Type" => $vehicle_type
                            ];

                            $this->send_json_200($result);
                        }
                    } else {
                        $result = [
                            "response_code" => "802",
                            "message" => "This Parking Session is not belongs to your parking"
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


    public function end()
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $encrypted_parking_id = trim($_POST["parking_id"]);

            // Decrypt the parking_id
            $parking_id = $this->decrypt_id($encrypted_parking_id);

            if ($assigned_parking == $parking_id) { //parking_id is similar to the assigned parking of the officer

                $encrypted_session_id = trim($_POST["session_id"]);

                $session_id = $this->decrypt_id($encrypted_session_id);

                $session_exists = $this->session_model->is_session_exists($session_id);

                if (!$session_exists) {
                    $result = [
                        "response_code" => "204",
                        "message" => "This parking session does not exist"
                    ];

                    $this->send_json_404($result);
                } else {
                    // Fetch session data using session_id from parking_session table
                    $session_details = $this->session_model->get_session_data($session_id);

                    //The parking that the vehicle is assigned currently
                    $current_vehicle_assigned_parking = $session_details["parking_id"];

                    if ($current_vehicle_assigned_parking  == $assigned_parking) {

                        $already_ended_session = $this->session_model->is_session_already_ended($session_id);

                        // check whether the given vehicle number has an open session
                        if ($already_ended_session) {
                            $result = [
                                "response_code" => "409",
                                "message" => "This parking session is already ended"
                            ];

                            $this->send_json_404($result);
                        } else {
                            $end_timestamp = time();

                            //end time is update in the parking_session
                            $this->session_model->end_session($session_id, $end_timestamp);

                            // add new officer activity to the officer_activity (type as end)
                            $this->officer_activity_model->end_officer_activity($session_id, $token_data, $end_timestamp);

                            // update the parking_space_status
                            $vehicle_type = $session_details["vehicle_type"];
                            $vehicle_type_letter = $this->convert_to_vehicle_category($vehicle_type);
                            $this->parking_space_status_model->increase_free_slots($vehicle_type_letter, $session_details["parking_id"]);

                            //Fetch the rate from the parking_space_status according to the parking_id and the vehicle_type
                            $hourly_rate_value = $this->parking_space_status_model->get_rate($vehicle_type_letter, $session_details["parking_id"]);
                            $hourly_rate = $hourly_rate_value->rate;

                            $start_timestamp = $session_details["start_time"];
                            $amount = $this->calculate_amount($start_timestamp, $end_timestamp, $hourly_rate);

                            // Start payment session 
                            $this->payment_model->start_payment_session($session_id, $amount);

                            // Fetch payment id
                            $payment_id = $this->payment_model->get_payment_id($session_id);

                            if (!empty($payment_id)) {
                                $payment_id = $this->encrypt_id($payment_id);

                                $result = [
                                    "response_code" => "800",
                                    "message" => "parking session is ended successfully!",
                                    "payment_id" => $payment_id
                                ];

                                $this->send_json_200($result);
                            }
                        }
                    } else {
                        $result = [
                            "response_code" => "204",
                            "message" => "This Parking Session is not belongs to this parking"
                        ];
                        $this->send_json_404($result);
                    }
                }
            } else { //parking_id is not similar to the assigned parking of the parking officer

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


    // View payment details of the given session in the officer mobile app
    public function view_payment_details_of_session($payment_id)
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $encoded_parking_id = $_SERVER['HTTP_ENCODED_PARKING_ID'];
            $parking_id = $this->decrypt_id($encoded_parking_id);


            if ($assigned_parking == $parking_id) {    //parking_id is similar to the assigned parking of the officer

                $payment_id = $this->decrypt_id($payment_id);
                $payment_session_exists = $this->payment_model->is_payment_session_id_exist($payment_id);


                if (!$payment_session_exists) {
                    $result = [
                        "response_code" => "204",
                        "message" => "No payment details for the session"
                    ];

                    $this->send_json_404($result);
                } else {
                    $payment_details = $this->payment_model->get_payment_details($payment_id);

                    $uppercase_vehicle_type = strtoupper($payment_details->vehicle_type);

                    // Calculate duration between start_time and end_time
                    $start_timestamp = $payment_details->start_time;
                    $end_timestamp = $payment_details->end_time;

                    $duration = $end_timestamp - $start_timestamp;

                    // Convert duration to hours and minutes
                    $hours = floor($duration / 3600);
                    $minutes = floor(($duration % 3600) / 60);

                    // Format the duration
                    $formatted_duration = sprintf('%02d H %02d Min', $hours, $minutes);

                    $formatted_amount = 'Rs. ' . number_format($payment_details->amount, 2);

                    $encrypted_payment_id = $this->encrypt_id($payment_id);

                    $formattedSDateTime = $this->format_time($start_timestamp);

                    $formattedEDateTime = $this->format_time($end_timestamp);

                    $formatted_SDateTime = implode(" ", $formattedSDateTime);
                    $formatted_EDateTime = implode(" ", $formattedEDateTime);

                    if ($payment_details) {
                        $result = [
                            "response_code" => "800",
                            "message" => "parking session is ended successfully!",
                            "payment_id" => $encrypted_payment_id,
                            "vehicle_number" => $payment_details->vehicle_number,
                            "vehicle_type" => $uppercase_vehicle_type,
                            "start_time" => $start_timestamp,
                            "end_time" => $end_timestamp,
                            "formatted_start_time" => $formatted_SDateTime,
                            "formatted_end_time" => $formatted_EDateTime,
                            "time_went" => $formatted_duration,
                            "amount" => $formatted_amount
                        ];

                        $this->send_json_200($result);
                    } else {
                        $result = [
                            "response_code" => "204",
                            "message" => "No payment details for the session"
                        ];

                        $this->send_json_404($result);
                    }
                }
            } else {    //parking_id is not similar to the assigned parking of the parking officer

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

            // Round down to the nearest integer
            $amount = floor($amount);

            return $amount;
        } else {
            return 'Invalid rate';
        }
    }


    // View payment details of the given session in the officer mobile app
    public function view_payment_details_of_force_ended_session($session_id)
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $encoded_parking_id = $_SERVER['HTTP_ENCODED_PARKING_ID'];
            $parking_id = $this->decrypt_id($encoded_parking_id);


            if ($assigned_parking == $parking_id) {    //parking_id is similar to the assigned parking of the officer

                $session_id = $this->decrypt_id($session_id);

                //check whether the session_id exists
                $session_exists = $this->session_model->is_session_exists($session_id);

                if (!$session_exists) {
                    // No such session found
                    $result = [
                        "response_code" => "204",
                        "message" => "No such session found"
                    ];

                    $this->send_json_404($result);
                } else {
                    // Session Found
                    $force_ended_session = $this->session_model->is_force_ended_session($session_id);

                    if (!$force_ended_session) {
                        // Session already ended
                        $result = [
                            "response_code" => "409",
                            "message" => "This is not a force ended session"
                        ];

                        $this->send_json_404($result);
                    } else { // force ended session
                        $force_ended_session_details = $this->session_model->get_force_ended_session_data($session_id);

                        $uppercase_vehicle_type = strtoupper($force_ended_session_details->vehicle_type);

                        // Calculate duration between start_time and end_time
                        $start_timestamp = $force_ended_session_details->start_time;
                        $end_timestamp = $force_ended_session_details->end_time;

                        $current_timestamp = time();

                        $duration = $current_timestamp - $start_timestamp;

                        // Convert duration to hours and minutes
                        $hours = floor($duration / 3600);
                        $minutes = floor(($duration % 3600) / 60);

                        // Format the duration
                        $formatted_duration = sprintf('%02d H %02d Min', $hours, $minutes);

                        ////////Calculate amount
                        // update the parking_space_status
                        $vehicle_type = $force_ended_session_details->vehicle_type;
                        $vehicle_type_letter = $this->convert_to_vehicle_category($vehicle_type);

                        //Fetch the rate from the parking_space_status according to the parking_id and the vehicle_type
                        $hourly_rate_value = $this->parking_space_status_model->get_rate($vehicle_type_letter, $force_ended_session_details->parking_id);
                        $hourly_rate = $hourly_rate_value->rate;

                        $amount = $this->calculate_amount($start_timestamp, $current_timestamp, $hourly_rate);
                        $formatted_amount = 'Rs. ' . number_format($amount, 2);

                        $formattedSDateTime = $this->format_time($start_timestamp);

                        $formattedEDateTime = $this->format_time($current_timestamp);

                        $formatted_SDateTime = implode(" ", $formattedSDateTime);
                        $formatted_EDateTime = implode(" ", $formattedEDateTime);


                        if ($force_ended_session_details) {
                            $result = [
                                "response_code" => "800",
                                "message" => "Force ended session!",
                                "session_id" => $this->encrypt_id($session_id),
                                "vehicle_number" => $force_ended_session_details->vehicle_number,
                                "vehicle_type" => $uppercase_vehicle_type,
                                "start_time" => $start_timestamp,
                                "current_time" => $current_timestamp,
                                "formatted_start_time" => $formatted_SDateTime,
                                "formatted_end_time" => $formatted_EDateTime,
                                "time_went" => $formatted_duration,
                                "amount" => $formatted_amount,
                                "amount_para" => $amount
                            ];

                            $this->send_json_200($result);
                        } else {
                            $result = [
                                "response_code" => "204",
                                "message" => "No payment details for the session"
                            ];

                            $this->send_json_404($result);
                        }
                    }
                }
            } else {    //parking_id is not similar to the assigned parking of the parking officer

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


    // Officer - show all details of the parking sessions
    public function view_status($vehicle_type, $status_type)
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $parking_id = $_SERVER['HTTP_X_ENCODED_DATA'];
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            // decrypting the parking id
            $parking_id = $this->decrypt_id($parking_id);

            if ($assigned_parking == $parking_id) //parking_id is similar to the assigned parking
            {
                // get status of parking session 
                $total_no_of_slots = $this->parking_space_status_model->get_no_of_total_slots($parking_id);
                $no_of_free_slots = $this->parking_space_status_model->get_no_of_free_slots($parking_id);
                $no_of_payment_due_sessions = $this->payment_model->get_no_of_payment_due_sessions($parking_id);
                $parking_slot_data = $this->parking_space_model->get_parking_space_status_details($parking_id);
                $no_of_ongoing_sessions = $total_no_of_slots - $no_of_free_slots;

                $final_session_stat_arr = [
                    "free_slots" => $no_of_free_slots,
                    "in_progress" => strval($no_of_ongoing_sessions),
                    "payment_due" => $no_of_payment_due_sessions,
                    "slot_status" => null
                ];

                $new_parking_slot_data = [
                    "A" => [
                        "availability" => "0",
                        "data" => null
                    ],
                    "B" => [
                        "availability" => "0",
                        "data" => null
                    ],
                    "C" => [
                        "availability" => "0",
                        "data" => null
                    ],
                    "D" => [
                        "availability" => "0",
                        "data" => null
                    ],
                ];

                foreach ($parking_slot_data as $slot_data) {
                    // add a slot to the new assosiative array
                    $new_parking_slot_data[$slot_data->vehicle_type]["data"] = [
                        "vehicle_type" => $this->convert_to_vehicle_type($slot_data->vehicle_type),
                        "free_slots" => $slot_data->free_slots,
                        "total_slots" => $slot_data->total_slots,
                        "rate" => $slot_data->rate
                    ];
                    $new_parking_slot_data[$slot_data->vehicle_type]["availability"] = "1";
                }

                // add new parking slot data array to final result array
                $final_session_stat_arr["slot_status"] = $new_parking_slot_data;

                // filterring for $vehicle_type, $status_type
                // status_type - {all => all, ip=>in progress, pd=>"payment due"}

                $final_payment_due_session_arr = null;
                $final_in_progress_session_arr = null;

                if ($status_type == "all") // all status type
                {
                    $final_payment_due_session_arr = $this->get_payment_due_session_data($parking_id, $vehicle_type);
                    $final_in_progress_session_arr = $this->get_in_progress_session_data($parking_id, $vehicle_type);
                } else if ($status_type == "ip") // only in progree
                {
                    $final_in_progress_session_arr = $this->get_in_progress_session_data($parking_id, $vehicle_type);
                    $final_payment_due_session_arr = [
                        "is_available" => "0"
                    ];
                } else // only payment due 
                {
                    $final_payment_due_session_arr = $this->get_payment_due_session_data($parking_id, $vehicle_type);
                    $final_in_progress_session_arr = [
                        "is_available" => "0"
                    ];
                }

                $result = [
                    "response_code" => "800",
                    "session_stat" => $final_session_stat_arr,
                    "payment_due_sessions" => $final_payment_due_session_arr,
                    "in_progress_sessions" => $final_in_progress_session_arr
                ];

                $this->send_json_200($result);
            } else // parking_id is not similar to the assigned parking
            {

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


    // return a array of payment due session data 
    private function get_payment_due_session_data($parking_id, $vehicle_type)
    {
        // vehicle_type code meanings - {all => all, car => car, bus=>bus, bike=>bike, lorry=>lorry, van=>van}
        if ($vehicle_type == "all") {
            $vehicle_type = "%";
        }

        $result_data = $this->payment_model->get_payment_due_session_details($parking_id, $vehicle_type);

        $final_arr = [];

        if ($result_data == false) // 0 payment due sessions
        {
            $final_arr = [
                "is_available" => "0"
            ];
        } else // payment due sessions exists
        {
            $final_arr = [
                "is_available" => "1",
                "data" => []
            ];

            foreach ($result_data as $data) {
                $temp_arr = [
                    "_id" => $this->encrypt_id($data->_id),
                    "session_end_time" => implode(" | ", $this->format_time($data->end_time)),
                    "vehicle_number" => $data->vehicle_number,
                    "vehicle_type" => $data->vehicle_type
                ];
                $final_arr["data"][] = $temp_arr;
            }
        }

        return $final_arr;
    }


    // return a array of in progress session data 
    private function get_in_progress_session_data($parking_id, $vehicle_type)
    {
        // vehicle_type code meanings - {all => all, car => car, bus=>bus, bike=>bike, lorry=>lorry, van=>van}
        if ($vehicle_type == "all") {
            $vehicle_type = "%";
        }

        $result_data = $this->session_model->get_in_progress_session_details($parking_id, $vehicle_type);

        $final_arr = [];

        if ($result_data == false) // 0 in progress sessions
        {
            $final_arr = [
                "is_available" => "0"
            ];
        } else // in progress sessions exists
        {
            $final_arr = [
                "is_available" => "1",
                "data" => []
            ];

            foreach ($result_data as $data) {
                $temp_arr = [
                    "_id" => $this->encrypt_id($data->_id),
                    "session_start_time" => implode(" | ", $this->format_time($data->start_time)),
                    "vehicle_number" => $data->vehicle_number,
                    "vehicle_type" => $data->vehicle_type
                ];
                $final_arr["data"][] = $temp_arr;
            }
        }

        return $final_arr;
    }


    public function force_end_sessions_list()
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

                $force_ended_session_data = $this->session_model->get_force_ended_sessions($parking_id);

                if ($force_ended_session_data == false) // not payments yet
                {
                    $result = [
                        "response_code" => "204",
                        "message" => "No Force Ended Sessions"
                    ];

                    $this->send_json_400($result);
                } else // there are force ended sessions
                {
                    $result_data = [];

                    foreach ($force_ended_session_data as $session_data) {
                        $session_start_timestamp = $session_data->start_time;
                        $session_force_end_timestamp = $session_data->end_time;

                        $formattedSDateTime = $this->format_time($session_start_timestamp);

                        $formattedEDateTime = $this->format_time($session_force_end_timestamp);

                        $formatted_SDateTime = implode(" - ", $formattedSDateTime);
                        $formatted_EDateTime = implode(" - ", $formattedEDateTime);

                        $temp = [
                            "response_code" => "800",
                            "session_id" => $this->encrypt_id($session_data->_id),
                            "vehicle" => $session_data->vehicle_number,
                            "vehicle_type" => $session_data->vehicle_type,
                            "session_start_date_and_timestamp" => implode(" | ", $this->format_time($session_start_timestamp)),
                            "session_end_date_and_timestamp" => implode(" | ", $this->format_time($session_force_end_timestamp)),
                            "formatted_SDateTime" => $formatted_SDateTime,
                            "formatted_EDateTime" => $formatted_EDateTime
                        ];
                        $result_data[] = $temp;
                    }

                    $this->send_json_200($result_data);
                }
            } else {    //parking_id is not similar to the assigned parking

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


    // driver mobile - used to force end the session 
    public function force_end($latitude, $longitude)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } else if ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            $parking_data = $this->session_model->get_ongoing_session_parking_data($token_data["user_id"]);

            if ($parking_data == false) // no ongoing session for the driver
            {
                $this->send_json_400("SE_NOPS");
            } else // have on going session 
            {

                // distance in KM
                $distance = $this->calculate_distance($parking_data->latitude, $parking_data->longitude, $latitude, $longitude);

                if ($distance > 0.2) // out of the parking space range
                {
                    $this->session_model->end_session_by_force($parking_data->session_id);
                    $this->parking_space_status_model->increase_free_slots($this->convert_to_vehicle_category($parking_data->vehicle_type), $parking_data->parking_id);

                    $officer_mobile_number = $this->parking_space_model->get_officer_mobile_number($parking_data->parking_id);

                    if ($officer_mobile_number !== false) // no officer assigned to the parking
                    {
                        $dnt = $this->format_time($parking_data->start_time);
                        $this->send_sms_force_end($officer_mobile_number, urlencode($parking_data->vehicle_type), urlencode($this->format_vehicle_number($parking_data->vehicle_number)), urlencode($dnt[0] . " " . $dnt[1]));
                        $this->send_json_200("SUCCESS");
                    }
                } else // still in the parking space range
                {
                    $this->send_json_400("SE_SIDPA");
                }
            }
        }
    }


    // calculate street distance between two points
    private function calculate_distance($source_lat, $source_long, $dest_lat, $dest_long)
    {
        $source_coordinates = $source_lat . "," . $source_long;
        $dest_coordinates = $dest_lat . "," . $dest_long;

        $uri = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" . $source_coordinates . "&destinations=" . $dest_coordinates . "&units=imperial&key=" . G_API_KEY;

        // Send request to Google Distance Matrix API
        $response = file_get_contents($uri);

        // Decode JSON response
        $decoded_response = json_decode($response, true);
        // distance in meters
        $distance = -1; // when google api can't find a root
        if (isset($decoded_response["rows"][0]["elements"][0]["distance"])) {
            $distance = $decoded_response["rows"][0]["elements"][0]["distance"]["value"];
        }


        return $distance / 1000;
    }


    // send the otp sms
    private function send_sms_force_end($mobile_number, $vehicle_type, $vehicle_number, $start_time)
    {
        $text = "Alert%21+A+vehicle+has+been+left+the+premises+unattended+and+the+session+has+been+ended+forcibly.%0AVehicle+Number+-+" . $vehicle_number . "+%28" . $vehicle_type . "%29%0ASession+Started+Time+-+" . $start_time;
        $url = "https://www.textit.biz/sendmsg?id=94713072925&pw=" . TEXTIT_KEY . "&to=0" . $mobile_number . "&text=" . $text;
        file_get_contents($url);
    }
}
