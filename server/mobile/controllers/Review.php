<?php

class Review extends Controller
{

        private $review_model;
        private $driver_model;
        private $parking_space_model;

        public function __construct()
        {
                $this->review_model = $this->model("ReviewModel");
                $this->driver_model = $this->model("DriverModel");
                $this->parking_space_model = $this->model("ParkingSpaceModel");
        }

        // add a new review
        public function add()
        {
                $token_data = $this->verify_token_for_drivers();

                if ($token_data === 400) // invalid token
                {
                        $this->send_json_400("Invalid Token");
                } elseif ($token_data === 404) // token not found
                {
                        $this->send_json_404("Token Not Found");
                } else // token is valid
                {

                        $review_data = [
                                "time_stamp" => trim($_POST["time_stamp"]),
                                "no_of_stars" => trim($_POST["no_of_stars"]),
                                "content" => trim($_POST["content"]),
                                "parking_id" => trim($_POST["parking_id"]),
                                "driver_id" => $token_data["user_id"]
                        ];


                        if ($this->review_model->is_driver_id_exist($review_data["driver_id"], $review_data["parking_id"])) // driver has already a review on the table
                        {
                                $this->send_json_400("User has already a review on the parking space");
                        } else {
                                $this->review_model->add_review($review_data);
                                $total_star_count = $this->review_model->get_total_star_count($review_data["parking_id"]);
                                $current_review_count = $this->parking_space_model->get_review_count($review_data["parking_id"]);

                                $new_review_count = intval($current_review_count + 1);
                                $new_avg_star_count = intval($total_star_count / $new_review_count);

                                $this->parking_space_model->update_review_details($new_avg_star_count, $new_review_count, $review_data["parking_id"]);
                                $this->send_json_200("success");
                        }
                }
        }


        // Edit a review
        public function edit()
        {
                $token_data = $this->verify_token_for_drivers();

                if ($token_data === 400) {
                        $this->send_json_400("Invalid Token");
                } elseif ($token_data === 404) {
                        $this->send_json_404("Token Not Found");
                } else {
                        $review_data = [
                                "time_stamp" => trim($_POST["time_stamp"]),
                                "no_of_stars" => trim($_POST["no_of_stars"]),
                                "content" => trim($_POST["content"])
                        ];

                        $review_id = trim($_POST["_id"]);

                        // check driver_id match with the review_id
                        if ($this->review_model->get_driver_id_by_review_id($review_id) == $token_data["user_id"]) // they match
                        {
                                $this->review_model->update_review($review_id, $review_data);
                                $this->send_json_200("Update Successfull!");
                        } else // they don't match
                        {
                                $this->send_json_401("Update operation failed, Unauthrized access");
                        }
                }
        }


        // delete a review
        public function delete($review_id)
        {
                // verifing the token
                $token_data = $this->verify_token_for_drivers();

                if ($token_data === 400) {
                        $this->send_json_400("Invalid Token");
                } elseif ($token_data === 404) {
                        $this->send_json_404("Token Not Found");
                } else {
                        if ($this->review_model->get_driver_id_by_review_id($review_id) == $token_data["user_id"]) // review id and driver id is matching
                        {
                                // delete the review
                                $this->review_model->delete_review($review_id);
                                $this->send_json_200("Review is sucessfully deleted");
                        } else // they don't match
                        {
                                $this->send_json_401("Delete operation failed, Unauthrized access");
                        }
                }
        }
}
