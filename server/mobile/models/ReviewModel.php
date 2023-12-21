<?php

use LDAP\Result;

class ReviewModel
{

        private $db;

        public function __construct()
        {
                $this->db = new Database;
        }


        // add a new review to the review table
        function add_review($review_data)
        {
                $this->db->query("INSERT INTO review (time_stamp, no_of_stars, content, driver_id, parking_id) values (:time_stamp, :no_of_stars, :content, :driver_id, :parking_id)");
                $this->db->bind(":time_stamp", $review_data["time_stamp"]);
                $this->db->bind(":no_of_stars", $review_data["no_of_stars"]);
                $this->db->bind(":content", $review_data["content"]);
                $this->db->bind(":driver_id", $review_data["driver_id"]);
                $this->db->bind(":parking_id", $review_data["parking_id"]);

                $this->db->execute();
        }

        // get all the reviews of a parking space for a given parking _id
        public function get_reviews_of_a_parking_space($_id)
        {
                $this->db->query("SELECT 
                            review._id,
                            review.driver_id,    
                            review.time_stamp, 
                            review.no_of_stars,
                            review.content,
                            driver.first_name,
                            driver.last_name
                    FROM 
                            review
                    JOIN
                            driver
                    ON
                            review.driver_id = driver._id
                    WHERE 
                            review.parking_id = :_id");

                $this->db->bind(":_id", $_id);

                $result = $this->db->resultSet();

                if ($result) {
                        return $result;
                } else // no parking space for given id
                {
                        return false;
                }
        }


        // get all user reviews by given user _id and parking _id
        public function get_user_reviews($user_id, $parking_id)
        {
                $this->db->query("SELECT * FROM review WHERE driver_id = :_driver_id AND parking_id = :_parking_id");

                $this->db->bind(":_driver_id", $user_id);
                $this->db->bind(":_parking_id", $parking_id);

                return $this->db->resultSet();
        }


        // update the review
        public function update_review($review_id, $review_data)
        {
                $this->db->query("UPDATE review SET time_stamp=:time_stamp, no_of_stars=:no_of_stars, content=:content WHERE _id=:_id");

                $this->db->bind(":time_stamp", $review_data["time_stamp"]);
                $this->db->bind(":no_of_stars", $review_data["no_of_stars"]);
                $this->db->bind(":content", $review_data["content"]);
                $this->db->bind(":_id", $review_id);

                $this->db->execute();
        }


        // get driver_id by review_id
        public function get_driver_id_by_review_id($review_id)
        {
                $this->db->query("SELECT driver_id FROM review WHERE _id = :_id");

                $this->db->bind(":_id", $review_id);

                $result = $this->db->single();

                if ($result) {
                        return $result->driver_id;
                } else {
                        return False;
                }
        }


        // delete a review
        public function delete_review($review_id)
        {
                $this->db->query("DELETE FROM review WHERE _id = :_id");

                $this->db->bind(":_id", $review_id);

                $this->db->execute();
        }


        // check whether the driver has a review on a selected parking space already
        public function is_driver_id_exist($driver_id, $parking_id)
        {
                $this->db->query("SELECT * FROM review WHERE driver_id = :driver_id AND parking_id = :parking_id");

                $this->db->bind(":driver_id", $driver_id);
                $this->db->bind(":parking_id", $parking_id);

                $this->db->execute();

                if ($this->db->rowCount() > 0) {
                        return true;
                } else {
                        return false;
                }
        }


        // return the sum of all star count for a given parking id
        public function get_total_star_count($parking_id)
        {
                $this->db->query("SELECT SUM(no_of_stars) AS total_star_count FROM review WHERE parking_id = :parking_id");
                $this->db->bind(":parking_id", $parking_id);

                $result = $this->db->single();

                if ($result->total_star_count) {
                        return $result->total_star_count;
                } else {
                        return 0;
                }
        }


        // return the parking_id of a relevent review
        public function get_parking_id_by_review_id($review_id)
        {
                $this->db->query("SELECT parking_id FROM review WHERE _id = :_id");
                $this->db->bind(":_id", $review_id);

                $result = $this->db->single();

                if ($result->parking_id) {
                        return $result->parking_id;
                } else {
                        return false;
                }
        }
}
