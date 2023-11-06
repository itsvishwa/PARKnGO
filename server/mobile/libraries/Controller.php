<?php
/*
   * Base Controller
   * Loads the models
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
}
