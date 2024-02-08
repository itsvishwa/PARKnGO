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

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {

            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $session_data = [
                "vehicle_number" => trim($_POST["vehicle_number"]),
                "vehicle_type" => trim($_POST["vehicle_type"]),
                "start_time" => trim($_POST["start_time"]),
                "officer_id" => $token_data["user_id"],
                "parking_id" => trim($_POST["parking_id"]),
                "driver_id" => trim($_POST["driver_id"])
            ];

            // Decrypt the parking_id
            $decrypted_parking_id = $this->decrypt_id($session_data["parking_id"]);

            // Replace the encrypted parking_id with the decrypted value
            $session_data["parking_id"] = $decrypted_parking_id;


            if ($assigned_parking === $session_data["parking_id"]) { //parking_id is similar to the assigned parking 

                $open_session = $this->session_model->is_open_session_exists($session_data["vehicle_number"]);

                // check whether the given vehicle number has an open session
                if ($open_session) {
                    $result = [
                        "response_code" => "409",
                        "message" => "This vehicle number is already having a ongoing parking session"
                    ];

                    $this->send_json_404($result);
                } else {    // No open session exists for the vehicle number
                    if ($session_data["driver_id"] === "-1") {
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
                    $this->parking_space_status_model->decrease_free_slots($session_data["vehicle_type"], $session_data["parking_id"]);


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
                        "message" => "parking details not found"
                    ];

                    $this->send_json_404($result);
                }
            }
        }
    }


    // Search parking session
    public function search($vehicle_number)
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {

            if (isset($_SERVER['HTTP_ENCODED_PARKING_ID'])) {
                $encoded_parking_id = $_SERVER['HTTP_ENCODED_PARKING_ID'];
                $parking_id = $this->decrypt_id($encoded_parking_id);


                $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            if ($assigned_parking === $parking_id) { //parking_id is similar to the assigned parking

                $open_session = $this->session_model->is_open_session_exists($vehicle_number);

                // check whether the given vehicle number has an open session
                if (!$open_session) {
                    $result = [
                        "response_code" => "409",
                        "message" => "No open session found for the vehicle number $vehicle_number"
                    ];

                    $this->send_json_404($result);
                } else {    // An open session exists for the vehicle number

                    // get the parking session details from the parking_session table
                    $parking_session_data = $this->session_model->search_session($vehicle_number);

                    if ($parking_session_data === false) // no parking session for a given vehicle number
                    {
                        $result = [
                            "response_code" => "204",
                            "message" => "Parking Session Not Found"
                        ];

                        $this->send_json_404($result);
                    } else // parking space found
                    {
                        $encrypted_session_id = $this->encrypt_id($parking_session_data->_id);

                        $start_timestamp = $parking_session_data->start_time;
                        $readable_date_time = date("h.i A  d M Y", $start_timestamp);
                        $readable_start_time = date("h.i A", $start_timestamp);

                        $end_timestamp = time();
                        $duration = $end_timestamp - $start_timestamp;
                        $readable_date_time = date("h.i A  d M Y", $end_timestamp);
                        $readable_end_time = date("h.i A", $end_timestamp);

                        // Convert duration to hours and minutes
                        $hours = floor($duration / 3600);
                        $minutes = floor(($duration % 3600) / 60);

                        // Format the duration
                        $formatted_duration = sprintf('%02d H %02d Min', $hours, $minutes);


                        // calculate amount

                        if (isset($parking_session_data->vehicle_type) && isset($parking_session_data->parking_id)) {
                            $vehicle_type = $parking_session_data->vehicle_type;
                            $parking_id = $parking_session_data->parking_id;

                            $hourly_rate_value = $this->parking_space_status_model->get_rate($vehicle_type, $parking_id);
                            $hourly_rate = $hourly_rate_value->rate;

                            $amount = $this->calculate_amount($start_timestamp, $end_timestamp, $hourly_rate);

                            $formatted_amount = 'Rs. ' . number_format($amount, 0) . '.00';


                            $result = [
                                "response_code" => "800",
                                "session_id" => $encrypted_session_id,
                                "parking_id" => $encoded_parking_id,
                                "end_Time_Stamp" => $end_timestamp,
                                "Parked_Time_Date" => $readable_date_time,
                                "Duration" => $formatted_duration,
                                "Amount" => $formatted_amount,
                                "Vehicle_Number" => $vehicle_number,
                                "Vehicle_Type" => $vehicle_type,
                                "Session_started_at" => $readable_start_time,
                                "Ended_Time_Date" => $readable_date_time,
                                "Session_ended_at" => $readable_end_time
                            ];

                            $this->send_json_200($result);
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


            } else {
                
                $this->send_json_404(["message" => "HTTP_ENCODED_PARKING_ID not set"]);
            }
        }
    }


    public function end()
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $encrypted_parking_id = trim($_POST["parking_id"]);

            // Decrypt the parking_id
            $parking_id = $this->decrypt_id($encrypted_parking_id);


            if ($assigned_parking === $parking_id) { //parking_id is similar to the assigned parking

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

                    $already_ended_session = $this->session_model->is_session_already_ended($session_id);

                    // check whether the given vehicle number has an open session
                    if ($already_ended_session) {
                        $result = [
                            "response_code" => "409",
                            "message" => "This parking session is already ended"
                        ];

                        $this->send_json_404($result);
                    } else {
                        $end_timestamp = trim($_POST["timestamp"]);

                        //end time is update in the parking_session
                        $this->session_model->end_session($session_id, $end_timestamp);

                        // add new officer activity to the officer_activity (type as end)
                        $this->officer_activity_model->end_officer_activity($session_id, $token_data, $end_timestamp);

                        // Fetch vehicle_type and parking_id based on the given _id from parking_session
                        $session_details = $this->session_model->get_session_data($session_id);

                        // update the parking_space_status
                        $this->parking_space_status_model->increase_free_slots($session_details["vehicle_type"], $session_details["parking_id"]);

                        //Fetch the rate from the parking_space_status according to the parking_id and the vehicle_type
                        $hourly_rate_value = $this->parking_space_status_model->get_rate($session_details["vehicle_type"], $session_details["parking_id"]);
                        $hourly_rate = $hourly_rate_value->rate;

                        $start_timestamp = $session_details["start_time"];
                        $amount = $this->calculate_amount($start_timestamp, $end_timestamp, $hourly_rate);

                        // Start payment session 
                        $this->payment_model->start_payment_session($session_id, $amount);

                        // Fetch payment id
                        $payment_id = $this->payment_model->get_payment_id($session_id);

                        $payment_id = $this->encrypt_id($payment_id);

                        //view payment details
                        $this->view_payment_details_of_session($payment_id);
                    }
                }
            } else { //parking_id is not similar to the assigned parking

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

            if ($payment_details) {
                $result = [
                    "response_code" => "800",
                    "message" => "parking session is ended successfully!",
                    "payment_id" => $encrypted_payment_id,
                    "vehicle_number" => $payment_details->vehicle_number,
                    "vehicle_type" => $uppercase_vehicle_type,
                    "start_time" => date('h:i A', $start_timestamp),
                    "end_time" => date('h:i A', $end_timestamp),
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



    // Officer - show all details of the parking sessions
    public function view_status($parking_id)
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            if ($assigned_parking === $parking_id) //parking_id is similar to the assigned parking
            {
                // get status of parking session 
                $total_no_of_slots = $this->parking_space_status_model->get_no_of_total_slots($parking_id);
                $no_of_free_slots = $this->parking_space_status_model->get_no_of_free_slots($parking_id);
                $no_of_payment_due_sessions = $this->payment_model->get_no_of_payment_due_sessions($parking_id);
                $no_of_ongoing_sessions = $total_no_of_slots - $no_of_free_slots;

                $final_session_stat_arr = [
                    "free_slots" => $no_of_free_slots,
                    "in_progress" => strval($no_of_ongoing_sessions),
                    "payment_due" => $no_of_payment_due_sessions
                ];

                // get payment_due parking sessions details
                $final_payment_due_session_arr = $this->get_payment_due_session_data($parking_id);

                // get payment_due parking sessions details
                $final_in_progress_session_arr = $this->get_in_progress_session_data($parking_id);

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
                if ($assigned_parking_details) {
                    $assigned_parking_name = $assigned_parking_details->name;

                    $result = [
                        "response_code" => "101",
                        "updated parking_id" => $assigned_parking,
                        "updated parking_name" => $assigned_parking_name,
                    ];

                    $this->send_json_400($result);
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

    // return a array of payment due session data 
    private function get_payment_due_session_data($parking_id)
    {
        $result_data = $this->payment_model->get_payment_due_session_details($parking_id);

        $final_arr = [];

        if ($result_data === false) // 0 payment due sessions
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
                    "payment_id" => $this->encrypt_id($data->_id),
                    "session_end_time" => date("h:i A | d/m/y", $data->end_time),
                    "vehicle_number" => $data->vehicle_number,
                    "vehicle_type" => $data->vehicle_type
                ];
                $final_arr["data"][] = $temp_arr;
            }
        }

        return $final_arr;
    }


    // return a array of in progress session data 
    private function get_in_progress_session_data($parking_id)
    {
        $result_data = $this->session_model->get_in_progress_session_details($parking_id);

        $final_arr = [];

        if ($result_data === false) // 0 in progress sessions
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
                    "session_start_time" => date("h:i A | d/m/y", $data->start_time),
                    "vehicle_number" => $data->vehicle_number,
                    "vehicle_type" => $data->vehicle_type
                ];
                $final_arr["data"][] = $temp_arr;
            }
        }

        return $final_arr;
    }
}
