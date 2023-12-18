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

        public function end_session() {
            $this->db->query("UPDATE parking_session SET end_time = ");
        }
    }
?>