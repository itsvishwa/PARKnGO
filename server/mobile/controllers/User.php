<?php
// handle user login and signup 

// http://localhost/PARKnGO/server/mobile/user/send_otp_login/driver/713072925 => login continue btn for driver
// http://localhost/PARKnGO/server/mobile/user/send_otp_login/officer/713072925 => login continue btn for officer


// http://localhost/PARKnGO/server/mobile/user/send_otp_register/123456789 => register continue btn => only for driver


// http://localhost/PARKnGO/server/mobile/user/send_otp/123456789 => resend otp => work for both drivers and parking officers


// http://localhost/PARKnGO/server/mobile/user/register/1855 => register => only for drivers


// http://localhost/PARKnGO/server/mobile/user/login/713072925/3950 => login => for both drivers and officers



use Twilio\Rest\Client;

class User extends Controller
{
        private $driver_model;
        private $officer_model;
        private $otp_model;

        public function __construct()
        {
                $this->driver_model = $this->model("DriverModel");
                $this->officer_model = $this->model("OfficerModel");
                $this->otp_model = $this->model("OTPModel");
        }



        // generate a new otp and add it to the db
        private function generate_otp($mobile_number)
        {
                $code = rand(1000, 9999); // generate a new otp
                $otp_data = [
                        "mobile_number" => $mobile_number,
                        "code" => hash('sha256', (string)$code),
                        "time_stamp" => time()
                ];

                $this->otp_model->add_otp($otp_data); // add new record to db

                return $code;
        }


        // send the otp sms
        private function send_sms($code, $mobile_number)
        {
                $sid    = "";
                $token  = "";
                $twilio = new Client($sid, $token);

                $message = $twilio->messages
                        ->create(
                                "+94713072925", // to
                                array(
                                        "from" => "+12255353202",
                                        "body" => "Your One-Time Password (OTP): $code , Thanks for choosing PARKnGO!"
                                )
                        );
        }



        // check otp is correct or not
        private function check_otp($code, $mobile_number)
        {
                // mobile number is always exist for this function call, thus data will be returned
                $otp_data = $this->otp_model->is_mobile_number_exist($mobile_number);
                if ($otp_data["code"] === hash('sha256', (string)$code)) {
                        if (time() - $otp_data["time_stamp"] > 60) {
                                return 2; // expired otp
                        } else {
                                return 1; // valid otp
                        }
                } else {
                        return 3; // invalid otp
                }
        }



        // send otp in login process
        public function send_otp_login($user_type, $mobile_number)
        {
                if ($user_type === "driver") // if user is a driver
                {
                        if ($this->driver_model->is_mobile_number_exist($mobile_number)) // if exist
                        {
                                $this->send_otp($mobile_number);
                        } else // mobile number is not exist 
                        {
                                $this->send_json_400("Mobile number is not a registered one");
                        }
                } else // user is a officer
                {
                        if ($this->officer_model->is_mobile_number_exist($mobile_number)) // if exist
                        {
                                $this->send_otp($mobile_number);
                        } else // mobile number is not exist 
                        {
                                $this->send_json_400("Your mobile number is not yet registered by your company");
                        }
                }
        }



        // send otp in register process
        public function send_otp_register($mobile_number)
        {
                if ($this->driver_model->is_mobile_number_exist($mobile_number)) // if exist
                {
                        $this->send_json_400("Mobile number is already registered");
                } else // mobile number is not exist 
                {
                        $this->send_otp($mobile_number);
                }
        }



        // for resend-otp button
        public function send_otp($mobile_number)
        {
                $otp_data = $this->otp_model->is_mobile_number_exist($mobile_number);

                if ($otp_data === false) // mobile number not exist
                {
                        // generate and add the new otp to db
                        $code = $this->generate_otp($mobile_number);

                        // send the sms
                        // $this->send_sms($code, $mobile_number);

                        $this->send_json_200("OTP is Sent " . $code);
                } else // mobile number exist
                {
                        $time_diff = time() - $otp_data["time_stamp"];
                        if ($time_diff > 60) // has expired
                        {
                                // delete the exisiting one
                                $this->otp_model->delete_otp($mobile_number);

                                // generate and add the new otp to db
                                $code = $this->generate_otp($mobile_number);

                                // send the sms
                                // $this->send_sms($code, $mobile_number);

                                $this->send_json_200("OTP is Sent " . $code);
                        } else // has not expired
                        {
                                $this->send_json_400("Wait " . (60 - $time_diff) . " seconds");
                        }
                }
        }



        // register
        public function register($code)
        {
                $driver_data = [
                        "first_name" => trim($_POST["first_name"]),
                        "last_name" => trim($_POST["last_name"]),
                        "mobile_number" => trim($_POST["mobile_number"])
                ];

                // check_otp() return
                // 1 => valid
                // 2 => expired otp
                // 3 => invalid otp

                switch ($this->check_otp($code, $driver_data["mobile_number"])) {
                        case 1:
                                $this->driver_model->add_driver($driver_data);
                                $this->otp_model->delete_otp($driver_data["mobile_number"]);
                                $this->send_json_200("Registration Successfull");
                                break;
                        case 2:
                                $this->send_json_400("OTP has been expired");
                                break;
                        case 3:
                                $this->send_json_400("Invalid OTP");
                                break;
                }
        }



        // login
        public function login($mobile_number, $code)
        {
                // check_otp() return
                // 1 => valid otp
                // 2 => expired otp
                // 3 => invalid otp

                switch ($this->check_otp($code, $mobile_number)) {
                        case 1:
                                $this->otp_model->delete_otp($mobile_number);
                                $this->send_json_200("Login Successfull");
                                break;
                        case 2:
                                $this->send_json_400("OTP has been expired");
                                break;
                        case 3:
                                $this->send_json_400("Invalid OTP");
                                break;
                }
        }
}
