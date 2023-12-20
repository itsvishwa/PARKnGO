<?php
    class Session extends Controller {
        private $session_model;
        private $officer_model;
        private $officer_activity_model;
        private $parking_space_status_model;

        
        public function __construct() {
            $this->session_model = $this->model("SessionModel");
            $this->officer_model = $this->model("OfficerModel");
            $this->officer_activity_model = $this->model("OfficerActivityModel");
            $this->parking_space_status_model = $this->model("ParkingSpaceStatusModel");
        }

        public function start() { 

            $token_data = $this->verify_token_for_officers();
            
            if ($token_data === 400) {
                $this->send_json_400("Invalid Token");
            } elseif ($token_data === 404) {
                $this->send_json_404("Token Not Found");
            } else // token is valid
            {
                if ($token_data["user_type"] === "officer" and $this->officer_model->is_officer_id_exists($token_data["user_id"])) {
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
                $encrypted_session_id = trim($_POST["session_id"]);

                $session_id = $this->decrypt_session_id($encrypted_session_id);
                
                // //end time should be updated in the "parking_session" table
                $this->session_model->end_session($session_id);

                // add new officer activity to the officer_activity table (type as end)
                $this->officer_activity_model->end_officer_activity($session_id, $token_data);

                // Fetch vehicle_type and parking_id based on the given _id from parking_session table
                $session_details = $this->session_model->get_session_data($session_id);
            
                // update the parking_space_status table
                $this->parking_space_status_model->increase_free_slots($session_details["vehicle_type"], $session_details["parking_id"]);

                //start payment session


            }
        }


        // Search parking session
        public function search($vehicle_number) {
            $token_data = $this->verify_token_for_officers();
                 
            if ($token_data === 400) {
                $this->send_json_400("Invalid Token");
            } elseif ($token_data === 404) {
                $this->send_json_404("Token Not Found");
            } else // token is valid
            {
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
                   
                    $result = [
                        "Parked Time/ Date" => $readable_date_time,
                        "Duration" => $formatted_duration
                        //"Amount" => 
                    ];

                    print_r($result);

                }
        }
    }

}
?>