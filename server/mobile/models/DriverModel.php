<?php
class DriverModel
{
        private $db;

        public function __construct()
        {
                // intiating the database connection
                $this->db = new Database;
        }



        // get driver details
        public function get_driver($mobile_number)
        {
                $this->db->query("SELECT * FROM driver WHERE mobile_number = :mobile_number");
                $this->db->bind(":mobile_number", $mobile_number);

                $result = $this->db->single();

                if ($result) {
                        return [
                                "_id" => $result->_id,
                                "first_name" => $result->first_name,
                                "last_name" => $result->last_name,
                                "mobile_number" => $result->mobile_number
                        ];
                } else {
                        return false;
                }
        }



        // add a new driver
        public function add_driver($driver_data)
        {
                // define query
                $this->db->query("INSERT INTO driver (first_name, last_name, mobile_number) VALUES (:first_name, :last_name, :mobile_number)");

                // bind values to the query
                $this->db->bind(":first_name", $driver_data["first_name"]);
                $this->db->bind(":last_name", $driver_data["last_name"]);
                $this->db->bind(":mobile_number", $driver_data["mobile_number"]);

                //execute query
                return $this->db->execute();
        }



        // check whether the mobile number is exist or not
        public function is_mobile_number_exist($mobile_number)
        {
                // define the query
                $this->db->query("SELECT mobile_number FROM driver WHERE mobile_number = :mobile_number");

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



        // check whether the driver exist or not using driver_id
        public function is_driver_id_exist($id)
        {
                $this->db->query("SELECT * FROM driver WHERE _id = :_id");
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
