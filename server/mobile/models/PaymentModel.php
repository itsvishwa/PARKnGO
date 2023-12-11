<?php
class PaymentModel
{
    private $db;

    public function __construct()
    {
        // intiating the database connection
        $this->db = new Database;
    }

    public function get_all_data($payment_id)
    {
        $this->db->query(
            "SELECT 
            payment.amount,  
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
                payment._id = :payment_id"
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
}
