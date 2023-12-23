<?php
class Session extends Controller
{
    private $session_model;
    private $officer_model;
    private $officer_activity_model;
    private $parking_space_status_model;
    private $payment_model;


    public function __construct()
    {
        $this->session_model = $this->model("SessionModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->officer_activity_model = $this->model("OfficerActivityModel");
        $this->parking_space_status_model = $this->model("ParkingSpaceStatusModel");
        $this->payment_model = $this->model("PaymentModel");
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

            $session_data = [
                "vehicle_number" => trim($_POST["vehicle_number"]),
                "vehicle_type" => trim($_POST["vehicle_type"]),
                "start_time" => trim($_POST["start_time"]),
                "officer_id" => $token_data["user_id"],
                "parking_id" => $this->officer_model->get_parking_id($token_data["user_id"])
            ];

            // add new session data to the parking_session table
            $this->session_model->add_session($session_data);

            // fetch the session id
            $session_data["session_id"] = $this->session_model->get_session_id($session_data["vehicle_number"], $session_data["start_time"]);

            // add new officer activity to the officer_activity table
            $this->officer_activity_model->add_officer_activity($session_data);

            // update the parking_space_status table
            $this->parking_space_status_model->decrease_free_slots($session_data["vehicle_type"], $session_data["parking_id"]);

            $this->send_json_200("New parking session is created successfully!");
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
            $open_session = $this->session_model->is_open_session_exists($vehicle_number);
            //var_dump ($open_session);

            // check whether the given vehicle number has an open session
            if (!$open_session) {
                $this->send_json_404("No open session found for the vehicle number $vehicle_number");
            } else {
                // An open session exists for the vehicle number

                // get the parking session details from the parking_session table
                $parking_session_data = $this->session_model->search_session($vehicle_number);

                if ($parking_session_data === false) // no parking session for a given vehicle number
                {
                    $this->send_json_404("Parking Session Not Found");
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
                        "Parked Time/ Date" => $readable_date_time,
                        "Duration" => $formatted_duration,
                        "Amount" => $formatted_amount
                    ];

                    $this->send_json_200($result);
                    //return $result;

                    // $json_result = json_encode($result, JSON_UNESCAPED_SLASHES);


                    // echo $json_result;
                }
            }
        }
    }




    // public function end() {
    //     $token_data = $this->verify_token_for_officers();

    //     if ($token_data === 400) {
    //         $this->send_json_400("Invalid Token");
    //     } elseif ($token_data === 404) {
    //         $this->send_json_404("Token Not Found");
    //     } else // token is valid
    //     {
    //         $encrypted_session_id = trim($_POST["session_id"]);

    //         $session_id = $this->decrypt_session_id($encrypted_session_id);

    //         // //end is update in the parking_session
    //         $this->session_model->end_session($session_id);

    //         // add new officer activity to the officer_activity (type as end)
    //         $this->officer_activity_model->end_officer_activity($session_id, $token_data);

    //         // Fetch vehicle_type and parking_id based on the given _id from parking_session
    //         $session_details = $this->session_model->get_session_data($session_id);

    //         // update the parking_space_status
    //         $this->parking_space_status_model->increase_free_slots($session_details["vehicle_type"], $session_details["parking_id"]);

    //         //get amount
    //         $amount = $this->pay

    //         //start payment session
    //         $this->payment_model->start_payment_session($session_id, $amount);

    //     }
    // }



}
