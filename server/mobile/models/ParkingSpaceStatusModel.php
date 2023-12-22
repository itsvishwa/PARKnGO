<?php
class ParkingSpaceStatusModel
{

    private $db;

    public function __construct()
    {
        $this->db = new Database;
    }

    public function decrease_free_slots($vehicle_type, $parking_id)
    {
        $this->db->query("UPDATE parking_space_status SET free_slots = free_slots - 1 WHERE vehicle_type =:vehicle_type AND parking_id = :parking_id");

        $this->db->bind(":vehicle_type", $vehicle_type);
        $this->db->bind(":parking_id", $parking_id);

        $this->db->execute();
    }

    public function increase_free_slots($vehicle_type, $parking_id)
    {
        $this->db->query("UPDATE parking_space_status SET free_slots = free_slots + 1 WHERE vehicle_type =:vehicle_type AND parking_id = :parking_id");

        $this->db->bind(":vehicle_type", $vehicle_type);
        $this->db->bind(":parking_id", $parking_id);

        $this->db->execute();
    }

    public function get_rate($vehicle_type, $parking_id)
    {
        $this->db->query("SELECT rate FROM parking_space_status WHERE vehicle_type = :vehicle_type AND parking_id = :parking_id");

        $this->db->bind(":vehicle_type", $vehicle_type);
        $this->db->bind(":parking_id", $parking_id);



        return  $this->db->single();
    }
}
