<?php

class Review extends Controller
{

        private $review_model;
        private $driver_model;

        public function __construct()
        {
                $this->review_model = $this->model("ReviewModel");
                $this->driver_model = $this->model("DriverModel");
        }

        public function add()
        {
                if (isset($_SERVER['HTTP_TOKEN'])) // fetch the token
                {
                        $token = $_SERVER['HTTP_TOKEN'];

                        $token_data =  $this->decode_token($token);

                        if ($this->is_token_valid($token_data)) // token has valid keys
                        {
                                if ($token_data["user_type"] === "driver" and $this->driver_model->is_driver_id_exist($token_data["user_id"])) //token has valid values
                                {
                                        $review_data = [
                                                "time_stamp" => trim($_POST["time_stamp"]),
                                                "no_of_stars" => trim($_POST["no_of_stars"]),
                                                "content" => trim($_POST["content"]),
                                                "parking_id" => trim($_POST["parking_id"]),
                                                "driver_id" => $token_data["user_id"]
                                        ];

                                        $this->review_model->add_review($review_data);

                                        $this->send_json_200("Review Added Successfully!");
                                } else // token has invalid values
                                {
                                        $this->send_json_400("Invalid Token");
                                }
                        } else // token has invalid keys
                        {
                                $this->send_json_400("Invalid Token");
                        }
                } else // no token
                {
                        $this->send_json_404("Token Not Found");
                }
        }
}
