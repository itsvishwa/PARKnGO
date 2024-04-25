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


    // get all saved vehicle information for a given driver id , return false if there non are there
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


    // get the selected number count for the new record
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


    // update vehicle informations
    public function update_vehicle_info($name, $number, $type, $selected, $driver_id)
    {
        $this->db->query("UPDATE driver_qr SET vehicle_name = :name, vehicle_number = :number, vehicle_type = :type WHERE selected = :selected AND driver_id = :driver_id");
        $this->db->bind(":name", $name);
        $this->db->bind(":number", $number);
        $this->db->bind(":type", $type);
        $this->db->bind(":selected", $selected);
        $this->db->bind(":driver_id", $driver_id);
        $this->db->execute();
    }


    // validating whether the vehicle record is exist or not for a given driver id and and the selected count
    public function is_exist_vehicle_record($driver_id, $selected)
    {
        $this->db->query("SELECT _id FROM driver_qr WHERE driver_id = :driver_id AND selected = :selected");
        $this->db->bind(":driver_id", $driver_id);
        $this->db->bind(":selected", $selected);

        $this->db->execute();

        if ($this->db->rowCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    // delete the vehicle record
    public function delete_driver_vehicle($selected_vehicle, $driver_id)
    {
        $this->db->query("DELETE FROM driver_qr WHERE driver_id = :driver_id AND selected = :selected");
        $this->db->bind(":driver_id", $driver_id);
        $this->db->bind(":selected", $selected_vehicle);
        $this->db->execute();
    }


    // update selected count
    public function update_driver_selected_vehicle_level($driver_id, $old_selected, $new_selected)
    {
        $this->db->query("UPDATE driver_qr SET selected = :new_selected WHERE driver_id = :driver_id AND selected = :old_selected");
        $this->db->bind(":driver_id", $driver_id);
        $this->db->bind(":old_selected", $old_selected);
        $this->db->bind(":new_selected", $new_selected);
        $this->db->execute();
    }


    public function is_qr_exists($_id)
    {
        $this->db->query("SELECT * FROM driver_qr WHERE _id = :_id");

        $this->db->bind(":_id", $_id);

        $this->db->execute();

        if ($this->db->rowCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    // get all saved vehicle information for a given qr_id to start a session , return false if there non are there
    public function get_vehicle_info($_id)
    {
        $this->db->query("SELECT driver_id, auth_time_stamp, vehicle_number, vehicle_type FROM driver_qr WHERE _id = :_id");

        $this->db->bind(":_id", $_id);

        $result = $this->db->single();

        if ($result) {
            return [
                "_id" => $_id,
                "driver_id" => $result->driver_id,
                "auth_time_stamp" => $result->auth_time_stamp,
                "vehicle_number" => $result->vehicle_number,
                "vehicle_type" => $result->vehicle_type
            ];
        } else {
            return false;
        }
    }
}
