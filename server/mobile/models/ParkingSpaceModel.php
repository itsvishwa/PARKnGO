<?php
class ParkingSpaceModel
{
        private $db;

        public function __construct()
        {
                // intiating the database connection
                $this->db = new Database;
        }

        // get only available parking spaces
        public function get_available_parking_spaces($vehicle_type)
        {
                $this->db->query("SELECT
                parking_spaces._id,
                parking_spaces.name,
                parking_spaces.address,
                parking_spaces.latitude,
                parking_spaces.longitude,
                parking_spaces.is_public,
                parking_spaces.avg_star_count,
                parking_spaces.total_review_count,
                parking_space_status.free_slots,
                parking_space_status.total_slots,
                parking_space_status.rate
                FROM
                        parking_spaces
                JOIN
                        parking_space_status ON parking_spaces._id = parking_space_status.parking_id
                WHERE
                        is_closed = 0
                        AND 
                        parking_space_status.vehicle_type = :vehicle_type");

                $this->db->bind(":vehicle_type", $vehicle_type);

                $result = $this->db->resultSet();

                if ($result) {
                        return $result;
                } else // no open parking spaces
                {
                        return false;
                }
        }


        // get all parking spaces
        public function get_all_parking_spaces()
        {
                $this->db->query("SELECT * FROM parking_spaces");

                $result = $this->db->resultSet();

                if ($result) {
                        return $result;
                } else // no open parking spaces
                {
                        return false;
                }
        }


        // get all details of a parking space for a given parking _id
        public function get_parking_space_details($_id)
        {
                $this->db->query("SELECT * FROM parking_spaces WHERE _id = :_id");

                $this->db->bind(":_id", $_id);

                $result = $this->db->single();

                if ($result) {
                        return $result;
                } else // no parking space for given id
                {
                        return false;
                }
        }

        // get all details of a parking space status(slots) for a given parking _id
        public function get_parking_space_status_details($_id)
        {
                $this->db->query("SELECT * FROM parking_space_status WHERE parking_id = :_id");

                $this->db->bind(":_id", $_id);

                $result = $this->db->resultSet();

                if ($result) {
                        return $result;
                } else // no parking space for given id
                {
                        return false;
                }
        }


        // get all details of a parking space status(slots) for a given parking _id
        public function get_reviews_of_a_parking_space($_id)
        {
                $this->db->query("SELECT 
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
}
