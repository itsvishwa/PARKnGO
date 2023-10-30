<?php
class Driver extends Controller
{
        private $driver_model;
        private $otp_model;
        private $json;

        public function __construct()
        {
                $this->driver_model = $this->model("DriverModel");
                $this->otp_model = $this->model("OTPModel");
                $this->json = new Json();
        }

        public function login()
        {
        }

        public function register()
        {
                // echo "register called";

                $driver_data = [
                        "first_name" => trim($_POST["first_name"]),
                        "last_name" => trim($_POST["last_name"]),
                        "mobile_number" => trim($_POST["mobile_number"])
                ];

                // data validation
                if (empty($driver_data["first_name"]) || empty($driver_data["first_name"]) || empty($driver_data["first_name"]) || strlen($driver_data["mobile_number"]) != 9) {
                        // send the response => Invalid Data
                        echo $this->json->make_json_400("Invalid Data, Operation Failed!");
                } else {
                        // validation succefull
                        // add new driver details
                        $this->driver_model->add_driver($driver_data);
                        // send the response
                        echo $this->json->make_json_200("New Driver added succefully!");
                }
        }

        // check the mobile number exist or not
        public function is_pn_exist($mobile_number)
        {
                if ($this->driver_model->is_mobile_number_exist($mobile_number)) {
                        echo $this->json->make_json_200("true");
                } else {
                        echo $this->json->make_json_200("false");
                }
        }

        // generate and store the otp code
        public function init_otp($mobile_number)
        {
                // generate a random OTP Code
                $otp = rand(1000, 9000);

                $otp_data = [
                        "mobile_number" => $mobile_number,
                        "otp" => hash('sha256', (string)$otp),
                        "time_stamp" => time(),
                ];

                // store the OTP code in the database
                $this->otp_model->add_otp($otp_data);

                // send the otp code to the user via SMS gateway

                // response => sucessfull
                echo $this->json->make_json_200("OTP sended " . $otp);
        }

        // check otp validation
        public function validate_otp($mobile_number, $otp)
        {
                $otp = hash('sha256', (string)$otp);

                $result = $this->otp_model->get_otp($mobile_number);

                if ($result !== false && $result["otp"] == $otp) {
                        // response =>  otp is correct
                        echo $this->json->make_json_200("Correct OTP code!");
                } else {
                        // response => invalid otp
                        echo $this->json->make_json_400("Invalid OTP code!");
                }
        }
}
