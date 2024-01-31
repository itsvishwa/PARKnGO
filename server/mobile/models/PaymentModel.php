<?php
class PaymentModel
{
    private $db;

    public function __construct()
    {
        // intiating the database connection
        $this->db = new Database;
    }

    // retrun whether a open payment session exist for given driver id. if exist return payment_id else return false
    public function is_open_payment_exist($driver_id)
    {
        $this->db->query(
            "SELECT
            payment._id 
             FROM
            payment
            JOIN
            parking_session
            ON
            parking_session._id = payment.session_id
            WHERE 
            parking_session.driver_id = :driver_id
            AND
            payment.is_complete = 0
             "
        );
        $this->db->bind(":driver_id", $driver_id);
        $result = $this->db->single();

        if ($this->db->rowCount() > 0) {
            return $result->_id;
        } else {
            return false;
        }
    }

    // get all data of a selected payment
    public function get_all_data($payment_id)
    {
        $this->db->query(
            "SELECT 
            payment.amount,  
            parking_session.start_time, 
            parking_session.end_time,
            parking_session.vehicle_number,
            parking_session.vehicle_type,
            parking_spaces.name,
            parking_officer.officer_id,
            parking_officer.first_name,
            parking_officer.last_name,
            parking_space_status.rate
            FROM 
                payment
            JOIN 
                parking_session
            ON
                payment.session_id = parking_session._id 
            JOIN
                parking_spaces
            ON
                parking_session.parking_id = parking_spaces._id 
            JOIN
                officer_activity
            ON
                parking_session._id = officer_activity.session_id
            JOIN
                parking_officer
            ON
                parking_officer._id = officer_activity.officer_id
            JOIN 
                parking_space_status
            ON  
                parking_space_status.parking_id = parking_spaces._id
            WHERE 
                payment._id = :payment_id
            AND
                parking_session.vehicle_type = parking_space_status.vehicle_type
        "
        );

        $this->db->bind(":payment_id", $payment_id);

        return $this->db->single();
    }


    // check payment session is ended or not
    public function is_payment_session_ended($payment_id)
    {
        $this->db->query("SELECT * FROM payment WHERE _id = :_id");

        $this->db->bind(":_id", $payment_id);

        $result = $this->db->single();

        if ($result->is_complete == 1) {
            return true;
        } else {
            return false;
        }
    }


    // check payment session _id is exist or not
    public function is_payment_session_id_exist($payment_id)
    {
        $this->db->query("SELECT * FROM payment WHERE _id = :_id");

        $this->db->bind(":_id", $payment_id);

        $this->db->execute();

        if ($this->db->rowCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    // get driver payments by a selected 
    public function get_all_driver_payments_by_id($driver_id)
    {
        $this->db->query(
            "SELECT 
         payment.amount,
         payment.payment_method,
         payment.time_stamp,
         payment.payment_method,
         parking_session.start_time, 
         parking_session.end_time,
         parking_session.vehicle_number,
         parking_session.vehicle_type,
         parking_spaces.name
         FROM 
             payment
         JOIN 
             parking_session
         ON
             payment.session_id = parking_session._id 
         JOIN
             parking_spaces
         ON
             parking_session.parking_id = parking_spaces._id 
         WHERE 
            parking_session.driver_id = :driver_id
             AND
             payment.is_complete = 1"
        );

        $this->db->bind(":driver_id", $driver_id);

        $result = $this->db->resultSet();

        if ($result === []) {
            return false;
        } else {
            return $result;
        }
    }


    public function start_payment_session($session_id, $amount)
    {
        $this->db->query("INSERT INTO payment (amount, session_id) VALUES (:amount, :session_id)");

        $this->db->bind(":session_id", $session_id);
        $this->db->bind(":amount", $amount);

        $this->db->execute();
    }


    public function get_all_officer_payments_by_session_id($session_id)
    {
        $this->db->query("SELECT * FROM payment WHERE payment_method = 'cash' AND is_complete = 1 AND session_id = :session_id");

        $this->db->bind(":sessionID", $session_id);

        $result = $this->db->resultSet();

        print_r($result);
    }

    // close a payement for a given payment_id
    public function close_payment($payment_data)
    {
        $this->db->query("UPDATE payment SET is_complete = 1, payment_method = :payment_method, time_stamp = :time_stamp, driver_id = :driver_id WHERE _id = :payment_id");
        $this->db->bind(":payment_method", $payment_data["payment_method"]);
        $this->db->bind(":time_stamp", $payment_data["time_stamp"]);
        $this->db->bind(":driver_id", $payment_data["driver_id"]);
        $this->db->bind(":payment_id", $payment_data["payment_id"]);
        $this->db->execute();
    }
}
