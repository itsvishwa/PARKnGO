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

                // new mobile number - confirmed by get_otp
                // add new driver details
                $this->driver_model->add_driver($driver_data);
                // send the response
                echo $this->json->make_json_200("New Driver added succefully!");
        }

        // generate and store the otp code
        public function get_otp($mobile_number)
        {
                // if mobile number is already registered
                if ($this->driver_model->is_mobile_number_exist($mobile_number) === true) {
                        echo $this->json->make_json_400("mobile number is already registerd!");
                        exit; // TODO :: Check this
                }

                // if mobile number has a otp assigned to it already in the OTP table
                if ($this->otp_model->is_mobile_number_exist($mobile_number) === true) {

                        $result = $this->otp_model->get_otp($mobile_number);
                        // calculate the time difference
                        $time_diff = time() - strtotime($result["time_stamp"]);

                        if ($time_diff > 60) {
                                // delete old otp if the time limit exceed
                                $this->otp_model->delete_otp($mobile_number);
                        } else {
                                echo $this->json->make_json_400("Wait till the time limit");
                                exit; // TODO :: Check this
                        }
                }

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
