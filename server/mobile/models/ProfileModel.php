<?php
class ProfileModel
{
    private $db;

    public function __construct()
    {
        // intiating the database connection
        $this->db = new Database;
    }


    // update name for given user id
    public function update_name($driver_id, $first_name, $last_name)
    {
        $this->db->query("UPDATE driver SET first_name = :first_name, last_name = :last_name WHERE _id = :_id");

        $this->db->bind(":first_name", $first_name);
        $this->db->bind(":last_name", $last_name);
        $this->db->bind(":_id", $driver_id);

        $this->db->execute();
    }


    // update the mobile number of given driver_id
    public function update_mobile_number($driver_id, $mobile_number)
    {
        $this->db->query("UPDATE driver SET mobile_number = :mobile_number WHERE _id = :_id");

        $this->db->bind(":_id", $driver_id);
        $this->db->bind(":mobile_number", $mobile_number);

        $this->db->execute();
    }
}
