<?php
    class OfficerModel {
        private $db;

        public function __construct() {
            $this->db = new Database;
        }

        // check whether the mobile number is registered or not
        public function is_mobile_number_exist($mobile_number) {
            
            // define the query
            $this->db->query("SELECT code, time_stamp FROM parking_officer WHERE mobile_number = :mobile_number");

            // bind the values to the query
            $this->db->bind(":mobile_number", $mobile_number);

            // execute the query
            $this->db->execute();

            // check the length of the result
            if($this->db->rowCount() > 0) {
                return true;
            } else {
                return false;
            }
        }

    }

?>
