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


    // get the selected number of new record
    public function get_selected_vehicle_number($driver_id)
    {
        $this->db->query("SELECT MAX(selected) as result FROM driver_qr WHERE driver_id = :driver_id");
        $this->db->bind(":driver_id", $driver_id);

        $result = $this->db->single();

        if ($this->db->rowCount() > 0) {
            return $result->result + 1;
        } else {
            return 1;
        }
    }

    // add  a new record 
    public function add_new_vehicle($v_name, $v_number, $v_type, $selected, $driver_id)
    {
        $this->db->query("INSERT INTO driver_qr (driver_id, vehicle_name, vehicle_number, vehicle_type, selected) VALUES (:driver_id, :v_name, :v_number, :v_type, :selected)");
        $this->db->bind(":driver_id", $driver_id);
        $this->db->bind(":v_name", $v_name);
        $this->db->bind(":v_number", $v_number);
        $this->db->bind(":v_type", $v_type);
        $this->db->bind(":selected", $selected);

        $this->db->execute();
    }
}
