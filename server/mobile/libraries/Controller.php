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
}
