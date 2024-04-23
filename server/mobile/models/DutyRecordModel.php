<?php
class DutyRecordModel {
    private $db;

    public function __construct() {
        $this->db = new Database;
    }

    public function mark_duty_in($time_stamp, $officer_id) {
        $this->db->query("INSERT INTO duty_record (type, time_stamp, officer_id) VALUES (:type, :time_stamp, :officer_id)");

        $this->db->bind(":type", "in");
        $this->db->bind(":time_stamp", $time_stamp);
        $this->db->bind(":officer_id", $officer_id);

        $this->db->execute();
    }

    public function mark_duty_off($time_stamp, $officer_id) {
        $this->db->query("INSERT INTO duty_record (type, time_stamp, officer_id) VALUES (:type, :time_stamp, :officer_id)");

        $this->db->bind(":type", "out");
        $this->db->bind(":time_stamp", $time_stamp);
        $this->db->bind(":officer_id", $officer_id);

        $this->db->execute();
    }
}