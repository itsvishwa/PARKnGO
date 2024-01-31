<?php
class SessionModel
{
    private $db;

    public function __construct()
    {
        $this->db = new Database();
    }

    // check whether a session is exist for driver_id or not
    public function is_driver_session_exist($driver_id)
    {
        $this->db->query(
            "SELECT 
            parking_session._id,
            parking_session.start_time,
            parking_session.vehicle_number,
            parking_session.vehicle_type,
            parking_officer.officer_id,
            parking_officer.first_name,
            parking_officer.last_name
            FROM
            parking_session 
            JOIN
            officer_activity
            ON
            parking_session._id = officer_activity.session_id
            JOIN
            parking_officer
            ON
            parking_officer._id = officer_activity.officer_id
            WHERE 
            parking_session.driver_id = :driver_id 
            AND 
            parking_session.end_time IS NULL
            AND 
            officer_activity.type = :_type
            "
        );
        $this->db->bind(":driver_id", $driver_id);
        $this->db->bind(":_type", "start");
        $result = $this->db->single();
        if ($this->db->rowCount() > 0) {
            return $result;
        } else {
            return false;
        }
    }

    public function add_session($session_data)
    {
        $this->db->query("INSERT INTO parking_session (start_time, vehicle_number, vehicle_type, parking_id) VALUES (:start_time, :vehicle_number, :vehicle_type, :parking_id)");

        $this->db->bind(":start_time", $session_data["start_time"]);
        $this->db->bind(":vehicle_number", $session_data["vehicle_number"]);
        $this->db->bind(":vehicle_type", $session_data["vehicle_type"]);
        $this->db->bind(":parking_id", $session_data["parking_id"]);

        $this->db->execute();
    }

    public function get_session_id($vehicle_number, $start_time)
    {
        $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number AND start_time = :start_time");
        $this->db->bind(":vehicle_number", $vehicle_number);
        $this->db->bind(":start_time", $start_time);

        $result = $this->db->single();

        return $result->_id;
    }

    public function is_open_session_exists($vehicle_number)
    {
        $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number AND start_time IS NOT NULL AND end_time IS NULL");

        $this->db->bind(":vehicle_number", $vehicle_number);

        $this->db->execute();

        $rowCount = $this->db->rowCount();

        return $rowCount > 0;
    }



    public function search_session($vehicle_number)
    {
        $this->db->query("SELECT * FROM parking_session WHERE vehicle_number = :vehicle_number");
        $this->db->bind(":vehicle_number", $vehicle_number);

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
                'parking_id' => $result->parking_id
            ];

            return $session_data;
        } else {
            return false;
        }
    }


    public function is_session_already_ended($_id)
    {
        $this->db->query("SELECT * FROM parking_session WHERE _id = :_id AND start_time IS NOT NULL AND end_time IS NOT NULL");

        $this->db->bind(":_id", $_id);

        $this->db->execute();

        $rowCount = $this->db->rowCount();

        return $rowCount > 0;
    }


    public function end_session($_id)
    {
        $current_time_stamp = time();

        $this->db->query("UPDATE parking_session SET end_time = $current_time_stamp WHERE _id = :_id");

        $this->db->bind(":_id", $_id);

        $this->db->execute();
    }
}
