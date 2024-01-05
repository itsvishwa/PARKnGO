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
            ];

            if($assigned_parking === $session_data["parking_id"]) { //parking_id is similar to the assigned parking {


                $open_session = $this->session_model->is_open_session_exists($session_data["vehicle_number"]);
               
                // check whether the given vehicle number has an open session
                if ($open_session) {
                    $result = [
                        "response_code" => "409",
                        "message" => "This vehicle number is already having a ongoing parking session"
                    ];

                    $this->send_json_404($result);

                } else {    // No open session exists for the vehicle number

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
    public function search($vehicle_number, $parking_id)
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            if($assigned_parking === $parking_id) { //parking_id is similar to the assigned parking

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
                        $start_timestamp = $parking_session_data->start_time;
                        $readable_date_time = date("h.i A  d M Y", $start_timestamp);

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
                            $parking_id = $parking_session_data->parking_id;

                            $hourly_rate_value = $this->parking_space_status_model->get_rate($vehicle_type, $parking_id);
                            $hourly_rate = $hourly_rate_value->rate;

                            // Validate if $hourly_rate is a valid number
                            if (is_numeric($hourly_rate)) {
                                $total_parked_time = $hours + ($minutes / 60);
                                $amount = $hourly_rate * $total_parked_time;
                                $formatted_amount = 'Rs. ' . number_format($amount, 0) . '.00';
                            } else {
                                $formatted_amount = 'Invalid rate';
                            }
                        } else {
                            $formatted_amount = 'Rate or parking data not available'; // Handle missing data scenario
                        }

                        $result = [
                            "response_code" => "800",
                            "Parked Time/ Date" => $readable_date_time,
                            "Duration" => $formatted_duration,
                            "Amount" => $formatted_amount
                        ];

                        $this->send_json_200($result);
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


    public function end() {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $parking_id = trim($_POST["parking_id"]);

            if($assigned_parking === $parking_id) { //parking_id is similar to the assigned parking

                $encrypted_session_id = trim($_POST["session_id"]);

                $session_id = $this->decrypt_session_id($encrypted_session_id);

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

                        //end time is update in the parking_session
                        $this->session_model->end_session($session_id);

                        // add new officer activity to the officer_activity (type as end)
                        $this->officer_activity_model->end_officer_activity($session_id, $token_data);

                        // Fetch vehicle_type and parking_id based on the given _id from parking_session
                        $session_details = $this->session_model->get_session_data($session_id);

                        // update the parking_space_status
                        $this->parking_space_status_model->increase_free_slots($session_details["vehicle_type"], $session_details["parking_id"]);

                        //start payment session
            
                        //Fetch the rate from the parking_space_status according to the parking_id and the vehicle_type
                        $rate = $this->parking_space_status_model->get_rate($session_details["vehicle_type"], $session_details["parking_id"]);
            
                        // Check if $rate exists and contains the 'rate' property
                        if ($rate && property_exists($rate, 'rate') && is_numeric($rate->rate)) {
                            $hourly_rate = $rate->rate;

                            // Calculate duration
                            $start_timestamp = $session_details["start_time"];
                            $end_timestamp = $session_details["end_time"];

                            $duration = $end_timestamp - $start_timestamp;

                            // Convert duration to hours and minutes
                            $hours = floor($duration / 3600);
                            $minutes = floor(($duration % 3600) / 60);

                            $total_duration_hours = $hours + ($minutes / 60);

                            $total_amount = $total_duration_hours * $hourly_rate;
                            $amount = round($total_amount, 2);

                            // Start payment session
                            $this->payment_model->start_payment_session($session_id, $amount);

                            $result = [
                                "response_code" => "800",
                                "message" => "parking session is ended successfully!"
                            ];
    
                            $this->send_json_200($result);

                        } else {
                            $result = [
                                "response_code" => "204",
                                "message" => "Invalid rate or rate not available"
                            ];
                        
                            $this->send_json_404($result);
                        }
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
}
