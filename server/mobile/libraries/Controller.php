<?php
/*
   * Base Controller
   */

class Controller
{
        // Load model
        public function model($model)
        {
                // Require model file
                require_once './models/' . $model . '.php';

                // Instantiate model
                return new $model();
        }

        // sucess response
        public function send_json_200($msg)
        {
                $response = ["response" => $msg];
                $response = json_encode($response);
                header('Content-Type: application/json');
                echo $response;
        }

        // bad request response
        public function send_json_400($msg)
        {
                header('HTTP/1.1 400 Bad Request');
                $response = ["response" => $msg];
                $response = json_encode($response);
                header('Content-Type: application/json');
                echo $response;
        }


        // not found request response
        public function send_json_401($msg)
        {
                header('HTTP/1.1 401 Unauthorized');
                $response = ["response" => $msg];
                $response = json_encode($response);
                header('Content-Type: application/json');
                echo $response;
        }


        // not found request response
        public function send_json_404($msg)
        {
                header('HTTP/1.1 404 Not Found');
                $response = ["response" => $msg];
                $response = json_encode($response);
                header('Content-Type: application/json');
                echo $response;
        }


        // sucess response with the token
        public function send_json_200_with_token($msg, $token)
        {
                header('HTTP/1.1 404 Not Found');
                $response = ["response" => $msg, "token" => $token];
                $response = json_encode($response);
                header('Content-Type: application/json');
                echo $response;
        }


        // encode a token
        public function encode_token($token_data)
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
        public function decode_token($token_string)
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


        // return false if token is invalid(dosen't have proper keys), 
        public function is_token_key_valid($token_data)
        {

                if (!isset($token_data["user_type"]) || !isset($token_data["user_id"]) || !isset($token_data["time_stamp"])) // token is invalid 
                {
                        return false;
                } else {
                        return true;
                }
        }


        // fully verify the token 
        // return the bad status code if the token is invalid
        // otherwise return the decoded token data
        public function verify_token_for_drivers()
        {
                if (isset($_SERVER['HTTP_TOKEN'])) // token recieved from the request
                {
                        $token = $_SERVER['HTTP_TOKEN'];

                        $token_data =  $this->decode_token($token);

                        if ($this->is_token_key_valid($token_data)) // token has valid keys
                        {
                                $driver_model = $this->model("DriverModel");
                                if ($token_data["user_type"] === "driver" and $driver_model->is_driver_id_exist($token_data["user_id"])) // token has valid values
                                {
                                        return $token_data;
                                } else // invalid values 
                                {
                                        return 400;
                                }
                        } else // token is invalid
                        {
                                return 400;
                        }
                } else // there is no token setted
                {
                        return 404;
                }
        }


        // fully verify the token 
        // return the bad status code if the token is invalid
        // otherwise return the decoded token data
        public function verify_token_for_officers()
        {
                if (isset($_SERVER['HTTP_TOKEN'])) // token recieved from the request
                {
                        $token = $_SERVER['HTTP_TOKEN'];

                        $token_data =  $this->decode_token($token);

                        if ($this->is_token_key_valid($token_data)) // token has valid keys
                        {
                                $officer_model = $this->model("OfficerModel");
                                if ($token_data["user_type"] === "officer" and $officer_model->is_officer_id_exist($token_data["user_id"])) // token has valid values
                                {
                                        return $token_data;
                                } else // invalid values 
                                {
                                        return 400;
                                }
                        } else // token is invalid
                        {
                                return 400;
                        }
                } else // there is no token setted
                {
                        return 404;
                }
        }


        // return a encrypted string when pass the id
        public function encrypt_payment_id($payment_id)
        {
                // Generate a new random 16-byte IV (Initialization Vector)
                $iv = openssl_random_pseudo_bytes(16);

                // Encrypt the payment_id with AES-128-CBC and the IV
                $encrypted_data = openssl_encrypt($payment_id, 'aes-128-cbc', PAYMENT_KEY, 0, $iv);

                // Combine the IV and modified ciphertext to create the final encoded string
                $encoded_string = $iv . $encrypted_data;

                // Encode the binary data as a Base64 string
                $encoded_string = base64_encode($encoded_string);

                // Send the Base64-encoded string as a JSON response
                return $encoded_string;
        }

        // return the decrypted data when pass the encoded string
        public function decrypt_payment_id($encoded_string)
        {
                // Decode the Base64-encoded string to get the binary data
                $binary_data = base64_decode($encoded_string);

                // Extract the IV from the first 16 bytes of the binary data
                $iv = substr($binary_data, 0, 16);

                // Extract the modified ciphertext from the rest of the binary data
                $ciphertext = substr($binary_data, 16);

                // Decrypt the modified ciphertext with AES-128-CBC and the IV
                $decrypted_payment_id = openssl_decrypt($ciphertext, 'aes-128-cbc', PAYMENT_KEY, 0, $iv);

                if ($decrypted_payment_id === false) {
                        // Decryption error, send an error message as a JSON response
                        // $this->send_json_200("Decryption error: " . openssl_error_string());
                        return false;
                }

                // Send the decrypted payment_id as a JSON response
                return $decrypted_payment_id;
        }
}
