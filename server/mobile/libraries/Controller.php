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

                        return $this->verify_token_for_driver_from_para($token);
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

                        return $this->verify_token_for_officer_from_para($token);
                } else // there is no token setted
                {
                        return 404;
                }
        }


        // pass the token as a parameter
        // pass the usertype as a parameter
        // return the decoded data if everything is ok, or return status code if there is a error
        public function verify_token_for_driver_from_para($token)
        {
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
        }


        // pass the token as a parameter
        // pass the usertype as a parameter
        // return the decoded data if everything is ok, or return status code if there is a error
        public function verify_token_for_officer_from_para($token)
        {
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
        }


        // return a encrypted string when pass the id
        public function encrypt_id($payment_id)
        {
                // Generate a new random 16-byte IV (Initialization Vector)
                $iv = openssl_random_pseudo_bytes(16);

                // Encrypt the payment_id with AES-128-CBC and the IV
                $encrypted_data = openssl_encrypt($payment_id, 'aes-128-cbc', ID_KEY, 0, $iv);

                // Combine the IV and modified ciphertext to create the final encoded string
                $encoded_string = $iv . $encrypted_data;

                // Encode the binary data as a Base64 string
                $encoded_string = base64_encode($encoded_string);

                // Make the Base64 string URL safe by replacing certain characters
                $url_safe_encrypted_id = strtr($encoded_string, '+/', '.~');

                // Send the Base64-encoded string as a JSON response
                return $url_safe_encrypted_id;
        }


        // return the decrypted data when pass the encoded string
        public function decrypt_id($url_safe_encrypted_id)
        {
                // Reverse the URL-safe character replacements to get the original Base64 string
                $encoded_string = strtr($url_safe_encrypted_id, '.~', '+/');

                // Decode the Base64-encoded string to get the binary data
                $binary_data = base64_decode($encoded_string);

                // Extract the IV from the first 16 bytes of the binary data
                $iv = substr($binary_data, 0, 16);

                // Extract the modified ciphertext from the rest of the binary data
                $ciphertext = substr($binary_data, 16);

                // Decrypt the modified ciphertext with AES-128-CBC and the IV
                $decrypted_payment_id = openssl_decrypt($ciphertext, 'aes-128-cbc', ID_KEY, 0, $iv);

                if ($decrypted_payment_id === false) {
                        // Decryption error, send an error message as a JSON response
                        // $this->send_json_200("Decryption error: " . openssl_error_string());
                        return false;
                }

                // Send the decrypted payment_id as a JSON response
                return $decrypted_payment_id;
        }


        // return a encrypted url safe string when pass the session id
        public function encrypt_session_id($session_id)
        {
                // Create an initialization vector
                $iv = openssl_random_pseudo_bytes(16);

                // Encrypt the session ID using the chosen cipher method and encryption key
                $encoded_session_id = openssl_encrypt($session_id, 'aes-128-cbc', SESSION_KEY, 0, $iv);

                // Combine the IV and modified ciphertext to create the final encoded string
                $encrypted_session_id = $iv . $encoded_session_id;

                // Encode the binary data as a Base64 string
                $base64_encoded = base64_encode($encrypted_session_id);

                // Make the Base64 string URL safe by replacing certain characters
                $url_safe_encrypted_session_id = strtr($base64_encoded, '+/', '-_'); // Replace '+' with '-' and '/' with '_'

                return $url_safe_encrypted_session_id;
        }


        public function decrypt_session_id($url_safe_encrypted_session_id)
        {
                // Reverse the URL-safe character replacements to get the original Base64 string
                $base64_encoded = strtr($url_safe_encrypted_session_id, '-_', '+/'); // Reverse replacements

                // Decode the Base64 string back to binary data
                $encrypted_session_id = base64_decode($base64_encoded);

                // Extract the initialization vector (IV) from the beginning of the string
                $iv = substr($encrypted_session_id, 0, 16);

                // Get the encrypted data (ciphertext) after the IV
                $encoded_session_id = substr($encrypted_session_id, 16);

                // Decrypt the encoded session ID using the provided IV, cipher method, and decryption key
                $decrypted_session_id = openssl_decrypt($encoded_session_id, 'aes-128-cbc', SESSION_KEY, 0, $iv);

                if ($decrypted_session_id === false) {
                        // Decryption error, send an error message as a JSON response
                        $this->send_json_200("Decryption error: " . openssl_error_string());
                        return false;
                }

                // Return the decrypted session_id
                return $decrypted_session_id;
        }


        // format the vehicle number to human readable version
        public function format_vehicle_number($vehicle_number)
        {
                $data = explode("#", $vehicle_number);

                $temp1 = "";
                if ($data[1] === "NA") {
                        $temp1 = "";
                } else if ($data[1] === "SRI") {
                        $temp1 = "ශ්‍රී";
                } else {
                        $temp1 = "-";
                }

                $temp2 = "";
                if ($data[3] === "NA") {
                        $temp2 = "";
                } else {
                        $temp2 = $data[3];
                }

                $result = $data[0] . $temp1 . $data[2] . $temp2;

                return $result;
        }


        // format the timestamp to human readable version
        public function format_time($time_stamp)
        {
                // Format the time in IST with offset
                $time = date("h:i A", $time_stamp);
                $date = date("d/m/y", $time_stamp);
                $result = [$time, $date];
                return $result;
        }


        // Convert the vehicle type to its Category
        public function convert_to_vehicle_category($vehicle_type)
        {
                $cat = "";
                if ($vehicle_type === "car" or $vehicle_type === "tuktuk" or $vehicle_type === "mini_van") {
                        $cat = "A";
                } else if ($vehicle_type === "bicycle") {
                        $cat = "B";
                } else if ($vehicle_type === "van" or $vehicle_type === "lorry" or $vehicle_type === "mini_bus") {
                        $cat = "C";
                } else {
                        $cat = "D";
                }
                return $cat;
        }


        // convert the category to vehicle types
        public function convert_to_vehicle_type($category)
        {
                $result = "";

                if ($category === "A") {
                        $result = "Car|Tuktuk|Mini Van";
                } else if ($category === "B") {
                        $result = "Bicycle";
                } else if ($category === "C") {
                        $result = "Van|Lorry|Mini Bus";
                } else {
                        $result = "Long Vehicles";
                }

                return $result;
        }
}
