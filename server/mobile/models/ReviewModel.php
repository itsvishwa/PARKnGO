<?php

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
}
