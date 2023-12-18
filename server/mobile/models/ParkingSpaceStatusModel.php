<?php
    class ParkingSpaceStatusModel {

        private $db;

        public function __construct() {
            $this->db = new Database;
        }

        public function decrease_free_slots($vehicle_type, $_id) {
            $this->db->query("UPDATE parking_space_status SET free_slots = free_slots - 1 WHERE vehicle_type =:vehicle_type AND parking_id = :_id");

            $this->db->bind(":vehicle_type", $vehicle_type);
            $this->db->bind(":_id", $_id);
            
            $this->db->execute();
        }

        public function increase_free_slots($vehicle_type, $_id) {
            $this->db->query("UPDATE parking_space_status SET free_slots = free_slots + 1 WHERE vehicle_type =:vehicle_type AND parking_id = :_id");

            $this->db->bind(":vehicle_type", $vehicle_type);
            $this->db->bind(":_id", $_id);
            
            $this->db->execute();
        }
    }
?>