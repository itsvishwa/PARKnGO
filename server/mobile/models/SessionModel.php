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


    // return in progress session details of a given parking
    public function get_in_progress_session_details($parking_id)
    {
        $this->db->query("SELECT * FROM parking_session WHERE parking_id = :parking_id AND end_time IS NULL");

        $this->db->bind(":parking_id", $parking_id);

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
                parking_officer.last_name,
                parking_spaces.name,
                parking_spaces._id AS pid
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
                JOIN
                 parking_spaces
                ON
                 parking_session.parking_id = parking_spaces._id 
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


    // session end by force
    public function end_session_by_force($session_id)
    {
        $current_time_stamp = time();

        $this->db->query("UPDATE parking_session SET end_time = $current_time_stamp, is_force_end = 1  WHERE _id = :_id");
        $this->db->bind(":_id", $session_id);

        $this->db->execute();
    }


    // return session,parking data of a ongoing session for a given driver id
    public function get_ongoing_session_parking_data($driver_id)
    {
        $this->db->query(
            "SELECT 
                p.latitude,
                p.longitude,
                p._id AS parking_id,
                s._id AS session_id,
                s.vehicle_type,
                s.vehicle_number,
                s.start_time
                FROM 
                parking_spaces AS p
                JOIN
                parking_session AS s
                ON
                p._id = s.parking_id
                WHERE
                s.driver_id = :driver_id
                AND
                s.end_time IS NULL
            "
        );

        $this->db->bind(":driver_id", $driver_id);

        $result = $this->db->single();

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
