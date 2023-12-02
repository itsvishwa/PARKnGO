<?php
    /*
    * Base Controller
    * Load models
    */

    Class Controller {
        // Load Model
        public function model($model) {
            // Require model file 
            require_once "./models/" . $model . ".php";

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



    }

?>