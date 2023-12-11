<?php
    class User extends Controller {
        private $otp_model;
        private $Officer_model;

        public function __construct() {
                $this->Officer_model = $this->model("OfficerModel");
                $this->otp_model = $this->model("OtpModel"); 
        }


        // Generate a new otp and sdd it to the database
        private function generate_otp($mobile_number) {
            $code = rand(1000, 9999);
            $otp_data = [
                "mobile_number" => $mobile_number,
                "code" => hash("sha256", (string)$code),
                "time_stamp" => time()
            ];

            $this->otp_model -> add_otp($otp_data);

            return $code;
        }

        // check otp is correct or not
        private function check_otp($code, $mobile_number) {
            $otp_data = $this->otp_model->is_mobile_number_exist($mobile_number);
            if($otp_data["code"] === hash("sha256", (string)$code)) {
                if(time() - $otp_data["time_stamp"] > 60) {
                    return 2;   //expired
                } else {
                    return 1;   //valid otp
                }
            } else {
                return 3;       //invalid otp
            }
        }

         // send otp in login process
         public function send_otp_login($user_type, $mobile_number)
         {
                 if ($user_type === "driver") // if user is a driver
                 {
                         /*if ($this->driver_model->is_mobile_number_exist($mobile_number)) // if exist
                         {
                                 $this->send_otp($mobile_number);
                         } else // mobile number is not exist 
                         {
                                 $this->send_json_400("Mobile number is not a registered one");
                         }*/
                 } else // user is a officer
                 {
                         if ($this->Officer_model->is_mobile_number_exist($mobile_number)) // if exist
                         {
                                 $this->send_otp($mobile_number);
                         } else // mobile number is not exist 
                         {
                                 $this->send_json_400("Your mobile number is not yet registered by your company");
                         }
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

?>
