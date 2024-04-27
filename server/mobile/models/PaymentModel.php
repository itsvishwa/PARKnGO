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
            payment._id,
            payment.amount,  
            parking_session.start_time, 
            parking_session.end_time,
            parking_session.vehicle_number,
            parking_session.vehicle_type,
            parking_spaces.name,
            parking_spaces._id AS pid,
            parking_officer.officer_id,
            parking_officer.first_name,
            parking_officer.last_name
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
            WHERE 
                payment._id = :payment_id
            AND
                officer_activity.type = :_end

        "
        );

        $this->db->bind(":payment_id", $payment_id);
        $this->db->bind(":_end",  "end");
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


    public function get_payment_id($session_id)
    {
        $this->db->query("SELECT _id FROM payment WHERE session_id = :session_id");

        $this->db->bind(":session_id", $session_id);

        $result = $this->db->single();

        if ($result) {
            return $result->_id;
        } else {
            return false;
        }
    }

    // close a card payment for a given id - from payehere
    public function close_card_payment_by_id($payment_id)
    {
        $timestamp = time();
        $this->db->query("UPDATE payment SET is_complete = 1, payment_method = :method, time_stamp = :timestamp WHERE _id = :payment_id");
        $this->db->bind(":method", "card");
        $this->db->bind(":timestamp", $timestamp);
        $this->db->bind(":payment_id", $payment_id);

        $this->db->execute();
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


    // close a cash payement for a given payment_id
    public function close_cash_payment($payment_data)
    {
        $this->db->query("UPDATE payment SET is_complete = 1, payment_method = :payment_method, time_stamp = :time_stamp WHERE _id = :payment_id");

        $this->db->bind(":payment_method", $payment_data["payment_method"]);
        $this->db->bind(":time_stamp", $payment_data["time_stamp"]);
        $this->db->bind(":payment_id", $payment_data["payment_id"]);

        $this->db->execute();
    }

    public function get_all_officer_payments_history_by_officer_id($officer_id)
    {
        $this->db->query(
            "SELECT 
            payment.time_stamp,
            payment.amount,
            parking_session.vehicle_number,
            payment.payment_method
         
            FROM 
                officer_activity
            JOIN 
                payment
            ON
                officer_activity.session_id = payment.session_id 
            JOIN
                parking_session
            ON
                officer_activity.session_id = parking_session._id 
            WHERE 
                officer_activity.officer_id = :officer_id AND officer_activity.type = 'end' AND payment.is_complete = 1 AND payment.payment_method = 'cash'"
        );

        $this->db->bind(":officer_id", $officer_id);

        $result = $this->db->resultSet();

        if ($result === []) {
            return false;
        } else {
            return $result;
        }
    }

    // get payment details by payment_id
    public function get_payment_details($_id)
    {
        $this->db->query(
            "SELECT  
            parking_session.vehicle_number,
            parking_session.vehicle_type,
            parking_session.start_time, 
            parking_session.end_time,
            payment.amount  
            FROM 
                payment
            JOIN 
                parking_session
            ON
                payment.session_id = parking_session._id 
            WHERE 
                payment._id = :_id"
        );

        $this->db->bind(":_id", $_id);

        return $this->db->single();
    }

    // return no of payment due session for a given parking
    public function get_no_of_payment_due_sessions($parking_id)
    {
        $this->db->query(
            "SELECT 
                  COUNT(payment._id) AS payment_due_count
                FROM 
                    payment 
                JOIN
                    parking_session
                ON
                    payment.session_id = parking_session._id
                WHERE 
                    parking_session.parking_id = :parking_id
                AND
                    payment.is_complete = 0"
        );

        $this->db->bind(":parking_id", $parking_id);

        $result = $this->db->single();

        if ($result) {
            return $result->payment_due_count;
        } else {
            return false;
        }
    }


    // return the payment due session's details of a given parking and vehicle type - retrun false if there is no such session
    public function get_payment_due_session_details($parking_id, $vehicle_type)
    {
        $this->db->query(
            "SELECT 
                payment._id,
                parking_session.end_time,
                parking_session.vehicle_number,
                parking_session.vehicle_type
            FROM
                payment
            JOIN
                parking_session
            ON
                payment.session_id = parking_session._id
            WHERE 
                payment.is_complete = 0
            AND
                parking_session.parking_id = :parking_id
            AND 
                parking_session.vehicle_type LIKE :vehicle_type"
        );

        $this->db->bind(":parking_id", $parking_id);
        $this->db->bind(":vehicle_type", $vehicle_type);

        $result = $this->db->resultSet();

        if ($this->db->rowCount() > 0) {
            return $result;
        } else {
            return false;
        }
    }

    public function close_force_ended_payment($session_id, $amount, $time_stamp) {
        $payment_method = 'cash';
        $is_complete = 1; 

        $this->db->query("INSERT INTO payment (amount, is_complete, payment_method, time_stamp, session_id) VALUES (:amount, :is_complete, :payment_method, :time_stamp, :session_id)");

        $this->db->bind(":amount", $amount);
        $this->db->bind(":is_complete", $is_complete);
        $this->db->bind(":payment_method", $payment_method);
        $this->db->bind(":time_stamp", $time_stamp);
        $this->db->bind(":session_id", $session_id);
        
        $this->db->execute();
    }
}
