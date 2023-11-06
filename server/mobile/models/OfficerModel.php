<?php
class OfficerModel
{
        private $db;


        public function __construct()
        {
                // intiating the database connection
                $this->db = new Database;
        }



        // check whether the mobile number is registered or not
        public function is_mobile_number_exist($mobile_number)
        {
                // define the query
                $this->db->query("SELECT mobile_number FROM parking_officer WHERE mobile_number = :mobile_number");

                // bind values to the query
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
                $this->db->query("SELECT * FROM parking_officer WHERE mobile_number = :mobile_number");

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
                                "parking_id" => $result->parking_id
                        ];
                } else {
                        return false;
                }
        }


        // check whether the driver exist or not using driver_id
        public function is_officer_id_exist($id)
        {
                $this->db->query("SELECT * FROM parking_officer WHERE _id = :_id");
                $this->db->bind(":_id", $id);
                $this->db->execute();

                // check the length of the result
                if ($this->db->rowCount() > 0) {
                        return true;
                } else {
                        return false;
                }
        }
}
