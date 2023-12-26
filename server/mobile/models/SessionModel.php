<?php
    class SessionModel {
        private $db;

        public function __construct() {
            $this->db = new Database();
        }

        public function add_session($session_data) {
            $this->db->query("INSERT INTO parking_session (start_time, vehicle_number, vehicle_type, parking_id) VALUES (:start_time, :vehicle_number, :vehicle_type, :parking_id)");

            $this->db->bind(":start_time", $session_data["start_time"]);
            $this->db->bind(":vehicle_number", $session_data["vehicle_number"]);
            $this->db->bind(":vehicle_type", $session_data["vehicle_type"]);
            $this->db->bind(":parking_id", $session_data["parking_id"]);

            $this->db->execute();

        }

        public function get_session_id($vehicle_number, $start_time) {
            $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number AND start_time = :start_time");
            $this->db->bind(":vehicle_number", $vehicle_number);
            $this->db->bind(":start_time", $start_time);

            $result = $this->db->single();

            return $result->_id;
        }

        public function is_open_session_exists($vehicle_number) {
            $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number AND start_time IS NOT NULL AND end_time IS NULL");

            $this->db->bind(":vehicle_number", $vehicle_number);

            $this->db->execute();

            $rowCount = $this->db->rowCount();

            return $rowCount > 0;
        }



        public function search_session($vehicle_number) {
            $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number");
            $this->db->bind(":vehicle_number", $vehicle_number);

            $result = $this->db->single();

            if($result){
                return $result;
            } else {
                return false;
            }
        }


        public function get_session_data($_id) {
            $this->db->query("SELECT * FROM parking_session WHERE _id = :_id");
            $this->db->bind(":_id", $_id);

            $result = $this->db->single();
            
            if ($result) {
                $session_data = [
                    'vehicle_type' => $result->vehicle_type,
                    'parking_id' => $result->parking_id
                ];
                
                return $session_data;
            } else {
                return false;
            }
        }


        // public function end_session($_id) {
        //     $current_time_stamp = time();

        //     $this->db->query("UPDATE parking_session SET end_time = $current_time_stamp WHERE _id = :_id");

        //     $this->db->bind(":_id", $_id);

        //     $this->db->execute();
        // }
    }
?>