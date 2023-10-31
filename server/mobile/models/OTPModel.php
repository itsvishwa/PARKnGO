<?php

class OTPModel
{
        private $db;

        public function __construct()
        {
                // intiating the database connection
                $this->db = new Database;
        }

        public function add_otp($otp_data)
        {
                // define query
                $this->db->query("INSERT INTO otp_codes (mobile_number, otp, time_stamp) VALUES (:mobile_number, :otp, FROM_UNIXTIME(:time_stamp))");
                // bind values to the query
                $this->db->bind(":mobile_number", $otp_data["mobile_number"]);
                $this->db->bind(":otp", $otp_data["otp"]);
                $this->db->bind(":time_stamp", $otp_data["time_stamp"]);
                //execute query
                return $this->db->execute();
        }

        // check whether the mobile number is exist or not
        public function is_mobile_number_exist($mobile_number)
        {
                // define the query
                $this->db->query("SELECT mobile_number FROM otp_codes WHERE mobile_number = :mobile_number");

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

        public function get_otp($mobile_number)
        {
                // Define the query to select the OTP and timestamp
                $this->db->query("SELECT otp, time_stamp FROM otp_codes WHERE mobile_number = :mobile_number");

                // Bind the mobile number parameter
                $this->db->bind(":mobile_number", $mobile_number);

                // Execute the query and fetch the result
                $result = $this->db->single();

                // Check if a result is found
                if ($result) {
                        // Return an associative array containing OTP and timestamp
                        return [
                                "otp" => $result->otp,
                                "time_stamp" => $result->time_stamp
                        ];
                } else {
                        // Return null if no result is found
                        return false;
                }
        }

        public function delete_otp($mobile_number)
        {
                // Define the query to delete the OTP record for the given mobile number
                $this->db->query("DELETE FROM otp_codes WHERE mobile_number = :mobile_number");

                // Bind the mobile number parameter
                $this->db->bind(":mobile_number", $mobile_number);

                // Execute the query to delete the OTP record
                return $this->db->execute();
        }
}
