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

            }
        }



        // public function end() {
        //     //end time should be updated in the "parking_session" table


        //     // add new officer activity to the officer_activity table (type as end)
        //     $this->officer_activity_model->end_officer_activity($session_data);

        //     // update the parking_space_status table
        //     $this->parking_space_status_model->increase_free_slots($session_data["vehicle_type"], $session_data["parking_id"]);

        //     //payment session should be start
        // }
    }
?>