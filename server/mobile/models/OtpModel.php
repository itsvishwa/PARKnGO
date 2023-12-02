<?php
    class OtpModel {
        private $db;

        public function __construct() {
            // Instantiating the database connection
            $this->db = new Database;
        }



        // check whether mobile number is exist in otp table => return record if exist otherwise return false 
        public function is_mobile_number_exist($mobile_number) {
            
            // define the query
            $this->db->query("SELECT code, time_stamp FROM otp WHERE mobile_number = :mobile_number");

            // bind the values to the query
            $this->db->bind(":mobile_number", $mobile_number);

            // execute the query and fetch the result
            $result = $this->db->single();

            // check if a result is found
            if($result) {
                return [
                    "mobile_number" => $mobile_number,
                    "code" => $result->code,
                    "time_stamp" => $result->time_stamp
                ];
            } else {
                return false;
            }
        }



        // Adding new otp record
        public function add_otp($otp_data) {
            // Define query
            $this->db->query("INSERT INTO otp (mobile_number, code, time_stamp) VALUES (:mobile_number, :code, :time_stamp)");

            // Bind values to the query
            $this->db->bind(":mobile_number", $otp_data["mobile_number"]);
            $this->db->bind(":code", $otp_data["code"]);
            $this->db->bind(":time_stamp", $otp_data["time_stamp"]);

            // execute query
            return $this->db->execute();
        }


        // Delete a otp record
        public function delete_otp($mobile_number) {
            
            $this->db->query("DELETE FROM otp WHERE mobile_number=:mobile_number");
            $this->db->bind(":mobile_number", $mobile_number);

            $this->db->execute();
        }
    }

?>