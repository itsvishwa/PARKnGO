<?php
class DriverQRModel
{
    private $db;

    public function __construct()
    {
        $this->db = new Database;
    }

    public function get_qr_id($driver_id, $selected)
    {
        $this->db->query("SELECT _id FROM driver_qr WHERE driver_id = :driver_id AND selected = :selected");

        $this->db->bind(":driver_id", $driver_id);
        $this->db->bind(":selected", $selected);

        $result = $this->db->single();
        if ($this->db->rowCount() > 0) {
            return $result->_id;
        } else {
            return false;
        }
    }

    public function update_time_stamp($time_stamp, $qr_id)
    {
        $this->db->query("UPDATE driver_qr SET auth_time_stamp = :time_stamp WHERE _id = :_id");
        $this->db->bind(":time_stamp", $time_stamp);
        $this->db->bind(":_id", $qr_id);

        $this->db->execute();
    }

    public function get_all($driver_id)
    {
        $this->db->query("SELECT * FROM driver_qr WHERE driver_id = :driver_id");
        $this->db->bind(":driver_id", $driver_id);

        $result = $this->db->resultSet();
        if ($this->db->rowCount() > 0) {
            return $result;
        } else {
            return false;
        }
    }
}
