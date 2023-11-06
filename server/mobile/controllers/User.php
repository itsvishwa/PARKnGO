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
                $sid    = SID;
                $token  = TOKEN;
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
                if ($otp_data !== false && ($otp_data["code"] === hash('sha256', (string)$code))) {
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
                                $this->send_response_with_token("driver", $driver_data["mobile_number"], "Registration"); // send the response with tokens
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
        public function login($user_type, $mobile_number, $code)
        {
                // check_otp() return
                // 1 => valid otp
                // 2 => expired otp
                // 3 => invalid otp

                switch ($this->check_otp($code, $mobile_number)) {
                        case 1:
                                $this->otp_model->delete_otp($mobile_number);
                                $this->send_response_with_token($user_type, $mobile_number, "Login"); // send the response with tokens
                                break;
                        case 2:
                                $this->send_json_400("OTP has been expired");
                                break;
                        case 3:
                                $this->send_json_400("Invalid OTP");
                                break;
                }
        }



        // Send Json response for a succefull login or a registration  
        private function send_response_with_token($user_type, $mobile_number, $op)
        {
                $token_data = null;
                $user_data = null;

                if ($user_type === "driver") // user is a driver
                {
                        // fetch driver data
                        $driver_data = $this->driver_model->get_driver($mobile_number);


                        $token_data = [
                                "user_id" => $driver_data["_id"],
                                "user_type" => "driver",
                                "time_stamp" => time()
                        ];

                        $user_data = [
                                "first_name" => $driver_data["first_name"],
                                "last_name" => $driver_data["last_name"],
                                "mobile_number" => $driver_data["mobile_number"]
                        ];
                } else // user is a officer
                {
                        // fetch parking officer data
                        $officer_data = $this->officer_model->get_officer($mobile_number);


                        $token_data = [
                                "user_id" => $officer_data["_id"],
                                "user_type" => "officer",
                                "time_stamp" => time()
                        ];

                        $user_data = [
                                "officer_id" => $officer_data["officer_id"],
                                "nic" => $officer_data["nic"],
                                "first_name" => $officer_data["first_name"],
                                "last_name" => $officer_data["last_name"],
                                "mobile_number" => $officer_data["mobile_number"],
                                "company_id" => $officer_data["company_id"],
                                "parking_id" => $officer_data["parking_id"]
                        ];
                }


                // generate a token
                $token = $this->encode_token($token_data);

                $response = ["response" => "$op Successfull", "user_data" => $user_data, "token" => $token];
                $response = json_encode($response);
                header('Content-Type: application/json');
                echo $response;
        }



        // encode a token
        private function encode_token($token_data)
        {
                // Serialize the data into a JSON string
                $data_json = json_encode($token_data);

                // Generate a random 16-byte IV (Initialization Vector)
                $iv = openssl_random_pseudo_bytes(16);

                // Encrypt the compressed data with AES-128-CBC and the IV
                $encrypted_string = openssl_encrypt($data_json, 'aes-128-cbc', TOKEN_KEY, 0, $iv);

                // Combine the IV and ciphertext to create the final token
                $token = $iv . $encrypted_string;

                // Encode the binary token as a Base64 string
                $token = base64_encode($token);

                return $token;
        }



        // decode a token
        private function decode_token($token_string)
        {
                // Decode the Base64-encoded token to get the binary token
                $binary_token = base64_decode($token_string);

                // Extract the IV from the first 16 bytes of the binary token
                $iv = substr($binary_token, 0, 16);

                // Extract the ciphertext from the rest of the binary token
                $ciphertext = substr($binary_token, 16);

                // Decrypt the ciphertext with AES-128-CBC and the IV
                $decrypted_data = openssl_decrypt($ciphertext, 'aes-128-cbc', TOKEN_KEY, 0, $iv);

                if ($decrypted_data === false) {
                        // Decryption error, you can log or return an error message
                        return "Decryption error: " . openssl_error_string();
                }

                // Parse the JSON data to obtain the original array
                $token_data = json_decode($decrypted_data, true);

                return $token_data;
        }



        // // logout
        // public function logout()
        // {
        //         if (isset($_SERVER['HTTP_TOKEN'])) {
        //                 $token = $_SERVER['HTTP_TOKEN'];

        //                 $token_data = $this->decode_token($token);

        //                 if (!isset($token_data["user_type"]) || !isset($token_data["user_id"]) || !isset($token_data["time_stamp"])) // token is invalid 
        //                 {
        //                         $this->send_json_400("Invalid Token");
        //                 } elseif ($token_data["user_type"] !== null and $token_data["user_type"] === "driver") // user is a driver
        //                 {
        //                         if ($this->driver_model->is_driver_id_exist($token_data["user_id"])) {
        //                                 $this->send_json_200("Logout Successfull");
        //                         } else {
        //                                 $this->send_json_400("Invalid Token");
        //                         }
        //                 } else // user is a parking officer
        //                 {
        //                         if ($this->officer_model->is_officer_id_exist($token_data["user_id"])) {
        //                                 $this->send_json_200("Logout Successfull");
        //                         } else {
        //                                 $this->send_json_400("Invalid Token");
        //                         }
        //                 }
        //         } else {
        //                 $this->send_json_404("Token Not Found");
        //         }
        // }
}
