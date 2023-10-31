<?php
class DriverModel
{
        private $db;

        public function __construct()
        {
                // intiating the database connection
                $this->db = new Database;
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
}
