<?php

// table(otp) => mobile_number, code, time_stamp 

class OTPModel
{
        private $db;

        public function __construct()
        {
                // intiating the database connection
                $this->db = new Database;
        }


        // check whether mobile number is exist in otp table => return record if exist otherwise return false 
        public function is_mobile_number_exist($mobile_number)
        {
                // define the query
                $this->db->query("SELECT code, time_stamp FROM otp WHERE mobile_number = :mobile_number");

                //bind values to the query
                $this->db->bind(":mobile_number", $mobile_number);

                //Execute the query and fetch the result
                $result = $this->db->single();

                // check is a result is found
                if ($result) // result found
                {
                        return [
                                "mobile_number" => $mobile_number,
                                "code" => $result->code,
                                "time_stamp" => $result->time_stamp
                        ];
                } else // result not found
                {
                        return false;
                }
        }

        // adding new otp record
        public function add_otp($otp_data)
        {
                // define query
                $this->db->query("INSERT INTO otp (mobile_number, code, time_stamp) VALUES (:mobile_number, :code, :time_stamp)");

                // bind values to the query
                $this->db->bind(":mobile_number", $otp_data["mobile_number"]);
                $this->db->bind(":code", $otp_data["code"]);
                $this->db->bind(":time_stamp", $otp_data["time_stamp"]);

                //execute query
                return $this->db->execute();
        }

        // delete a otp record
        public function delete_otp($mobile_number)
        {
                $this->db->query("DELETE FROM otp WHERE mobile_number=:mobile_number");
                $this->db->bind(":mobile_number", $mobile_number);
                $this->db->execute();
        }
}
