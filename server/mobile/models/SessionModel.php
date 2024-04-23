<?php
class SessionModel
{
    private $db;

    public function __construct()
    {
        $this->db = new Database();
    }


    // Returns TRUE if there is at least one row matching with given vehicle number and conditions
    public function is_open_session_exists($vehicle_number)
    {
        $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number AND start_time IS NOT NULL AND end_time IS NULL");

        $this->db->bind(":vehicle_number", $vehicle_number);

        $this->db->execute();

        $rowCount = $this->db->rowCount();

        return $rowCount > 0;
    }


    // Insert session data to the table
    public function add_session($session_data)
    {
        $this->db->query("INSERT INTO parking_session (start_time, vehicle_number, vehicle_type, driver_id, parking_id) VALUES (:start_time, :vehicle_number, :vehicle_type, :driver_id, :parking_id)");

        $this->db->bind(":start_time", $session_data["start_time"]);
        $this->db->bind(":vehicle_number", $session_data["vehicle_number"]);
        $this->db->bind(":vehicle_type", $session_data["vehicle_type"]);
        $this->db->bind(":driver_id", $session_data["driver_id"]);
        $this->db->bind(":parking_id", $session_data["parking_id"]);

        $this->db->execute();
    }


    // Retrieve the generated session_id 
    public function get_session_id($vehicle_number, $start_time)
    {
        $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number AND start_time = :start_time");
        $this->db->bind(":vehicle_number", $vehicle_number);
        $this->db->bind(":start_time", $start_time);

        $result = $this->db->single();

        return $result->_id;
    }


    public function search_session($vehicle_number)
    {
        $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number AND end_time IS NULL");
        $this->db->bind(":vehicle_number", $vehicle_number);

        $result = $this->db->single();

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }

    public function get_open_session_data($_id)
    {
        $this->db->query("SELECT * FROM parking_session WHERE _id = :_id AND end_time IS NULL");
        $this->db->bind(":_id", $_id);

        $result = $this->db->single();

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }


    public function get_session_data($_id)
    {
        $this->db->query("SELECT * FROM parking_session WHERE _id = :_id");
        $this->db->bind(":_id", $_id);

        $result = $this->db->single();

        if ($result) {
            $session_data = [
                'start_time' => $result->start_time,
                'end_time' => $result->end_time,
                'vehicle_type' => $result->vehicle_type,
                'parking_id' => $result->parking_id,
            ];

            return $session_data;
        } else {
            return false;
        }
    }


    public function is_session_exists($_id)
    {
        $this->db->query("SELECT * FROM parking_session WHERE _id = :_id");

        $this->db->bind(":_id", $_id);

        $this->db->execute();

        $rowCount = $this->db->rowCount();

        return $rowCount > 0;
    }

    public function is_session_already_ended($_id)
    {
        $this->db->query("SELECT * FROM parking_session WHERE _id = :_id AND start_time IS NOT NULL AND end_time IS NOT NULL");

        $this->db->bind(":_id", $_id);

        $this->db->execute();

        $rowCount = $this->db->rowCount();

        return $rowCount > 0;
    }



    public function is_force_ended_session($_id)
    {
        $this->db->query("SELECT * FROM parking_session WHERE _id = :_id AND is_force_end = 1");

        $this->db->bind(":_id", $_id);

        $this->db->execute();

        $rowCount = $this->db->rowCount();

        return $rowCount > 0;
    }


    public function get_force_ended_session_data($_id)
    {
        $this->db->query("SELECT * FROM parking_session WHERE _id = :_id AND is_force_end = 1");
        $this->db->bind(":_id", $_id);

        $result = $this->db->single();

        if ($result) {
            return $result;
        } else {
            return false;
        }
    }




    public function end_session($_id, $end_timestamp)
    {

        $this->db->query("UPDATE parking_session SET end_time = $end_timestamp WHERE _id = :_id");

        $this->db->bind(":_id", $_id);

        $this->db->execute();
    }

    // return in progress session details of a given parking and vehicle_type
    public function get_in_progress_session_details($parking_id, $vehicle_type)
    {
        $this->db->query("SELECT * FROM parking_session WHERE parking_id = :parking_id AND end_time IS NULL AND vehicle_type LIKE :vehicle_type");

        $this->db->bind(":parking_id", $parking_id);
        $this->db->bind(":vehicle_type", $vehicle_type);


        $result = $this->db->resultSet();

        if ($this->db->rowCount() > 0) {
            return $result;
        } else {
            return false;
        }
    }

    public function get_force_ended_sessions($parking_id) 
    {
        $this->db->query("SELECT * FROM parking_session WHERE parking_id = :parking_id AND is_force_end = 1");

        $this->db->bind(":parking_id", $parking_id);

        
        $result = $this->db->resultSet();

        if ($this->db->rowCount() > 0) {
            return $result;
        } else {
            return false;
        }
    }

    
    public function update_force_ended_session($_id) {
        $this->db->query("UPDATE parking_session SET is_force_end = 0 WHERE _id = :_id");

        $this->db->bind(":_id", $_id);

        $this->db->execute();
    }
}
