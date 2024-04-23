<?php
class OfficerModel
{
    private $db;

    public function __construct()
    {
        $this->db = new Database;
    }

    // check whether the mobile number is registered or not
    public function is_mobile_number_exist($mobile_number)
    {

        // define the query
        $this->db->query("SELECT mobile_number FROM parking_officer WHERE mobile_number = :mobile_number");

        // bind the values to the query
        $this->db->bind(":mobile_number", $mobile_number);

        // execute the query
        $this->db->execute();

        // check the length of the result
        if ($this->db->rowCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // get officer details
    public function get_officer($mobile_number)
    {
        $this->db->query(
            "SELECT 
            parking_officer._id, 
            parking_officer.officer_id, 
            parking_officer.nic, 
            parking_officer.mobile_number, 
            parking_officer.first_name, 
            parking_officer.last_name, 
            parking_officer.company_id, 
            parking_officer.parking_id, 
            parking_spaces.name,
            company.phone_number AS company_phone_number,
            company.name AS company_name
            FROM 
            parking_officer 
            JOIN 
            parking_spaces 
            ON 
            parking_officer.parking_id = parking_spaces._id
            JOIN
            company
            ON
            parking_officer.company_id = company._id
            WHERE 
            parking_officer.mobile_number = :mobile_number"
        );

        $this->db->bind(":mobile_number", $mobile_number);

        $result = $this->db->single();

        if ($result) {
            return [
                "_id" => $result->_id,
                "officer_id" => $result->officer_id,
                "nic" => $result->nic,
                "mobile_number" => $result->mobile_number,
                "first_name" => $result->first_name,
                "last_name" => $result->last_name,
                "company_id" => $result->company_id,
                "parking_id" => $result->parking_id,
                "parking_name" => $result->name,
                "company_name" => $result->company_name,
                "company_phone_number" => $result->company_phone_number
            ];
        } else {
            return false;
        }
    }

    // check whether the officer exist or not using driver_id
    public function is_officer_id_exist($_id)
    {
        $this->db->query("SELECT * FROM parking_officer WHERE _id = :_id");
        $this->db->bind(":_id", $_id);
        $this->db->execute();

        // check the length of the result
        if ($this->db->rowCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //get the assigned parking_id for given officer_id
    public function get_parking_id($_id)
    {
        $this->db->query("SELECT * FROM parking_officer WHERE _id = :_id");
        $this->db->bind(":_id", $_id);

        $result = $this->db->single();

        return $result->parking_id;
    }
}
