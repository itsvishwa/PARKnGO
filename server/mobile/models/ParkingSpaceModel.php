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
        public function get_available_parking_spaces($vehicle_type, $page_number)
        {
                $curr_time = time();
                $offset = (($page_number - 1) * 5);

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
                        (parking_spaces.closed_end_time IS NULL OR parking_spaces.closed_end_time < $curr_time) 
                        AND 
                        parking_space_status.vehicle_type = :vehicle_type
                LIMIT 6
                OFFSET :offset         
                ");

                $this->db->bind(":vehicle_type", $vehicle_type);
                $this->db->bind(":offset", $offset);

                $result = $this->db->resultSet();

                if ($this->db->rowCount() > 0) {
                        return $result;
                } else // no open parking spaces
                {
                        return false;
                }
        }


        // get only available parking spaces by search
        public function get_available_parking_spaces_by_search($vehicle_type, $keyword, $page_number)
        {
                $curr_time = time();
                $offset = (($page_number - 1) * 5);

                $this->db->query(
                        "SELECT
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
                        (parking_spaces.closed_end_time IS NULL OR parking_spaces.closed_end_time < $curr_time) 
                        AND 
                        parking_space_status.vehicle_type = :vehicle_type
                        AND
                        (
                                parking_spaces.name LIKE :keyword
                                OR
                                parking_spaces.address LIKE :keyword
                        )
                LIMIT 6
                OFFSET :offset         
                "
                );

                $this->db->bind(":vehicle_type", $vehicle_type);
                $this->db->bind(":keyword", "%$keyword%", PDO::PARAM_STR);
                $this->db->bind(":offset", $offset);

                $result = $this->db->resultSet();

                if ($result and $this->db->rowCount() > 0) {
                        return $result;
                } else // no open parking spaces
                {
                        return false;
                }
        }

        // get all parking spaces via search
        public function get_all_parking_spaces_by_search($keyword)
        {
                $this->db->query("SELECT * FROM parking_spaces WHERE name LIKE :keyword OR address LIKE :keyword");
                $this->db->bind(":keyword", "%$keyword%", PDO::PARAM_STR);

                $result = $this->db->resultSet();

                if ($result) {
                        return $result;
                } else // no open parking spaces
                {
                        return false;
                }
        }

        // get all open parking spaces for a given vehicle type
        public function get_all_parking_spaces_for_vehicle($vehicle_type)
        {
                $curr_time = time();

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
                        (parking_spaces.closed_end_time IS NULL OR parking_spaces.closed_end_time < $curr_time) 
                        AND 
                        parking_space_status.vehicle_type = :vehicle_type       
                ");

                $this->db->bind(":vehicle_type", $vehicle_type);

                $result = $this->db->resultSet();

                if ($this->db->rowCount() > 0) {
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

                if ($this->db->rowCount() > 0) {
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

                if ($this->db->rowCount() > 0) {
                        return $result;
                } else // company dosen't have defined yet those details 
                {
                        return false;
                }
        }

        // return the current review count of a given parking_id
        public function get_review_count($parking_id)
        {
                $this->db->query("SELECT total_review_count FROM parking_spaces WHERE _id = :_id");

                $this->db->bind(":_id", $parking_id);

                $result = $this->db->single();

                if ($result->total_review_count) {
                        return $result->total_review_count;
                } else {
                        return 0;
                }
        }

        // update the new review count and avg star count for a given parking_id
        public function update_review_details($avg_star_count, $review_count,  $parking_id)
        {
                $this->db->query("UPDATE parking_spaces SET avg_star_count=:avg_star_count, total_review_count=:total_review_count WHERE _id=:_id");

                $this->db->bind(":avg_star_count", $avg_star_count);
                $this->db->bind(":total_review_count", $review_count);
                $this->db->bind(":_id", $parking_id);

                $this->db->execute();
        }


        public function get_officer_mobile_number($parking_id)
        {
                $this->db->query(
                        "SELECT
                        po.mobile_number AS mobile_number
                        FROM 
                        parking_spaces AS ps
                        JOIN
                        parking_officer AS po
                        ON
                        ps._id = po.parking_id
                        WHERE 
                        ps._id = :parking_id
                        "
                );
                $this->db->bind(":parking_id", $parking_id);

                $result = $this->db->single();

                if ($this->db->rowCount() > 0) {
                        return $result->mobile_number;
                } else {
                        return false;
                }
        }
}
