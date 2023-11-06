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
        public function get_available_parking_spaces()
        {
                $this->db->query("SELECT * FROM parking_spaces WHERE is_closed = 0");
                $result = $this->db->resultSet();

                if ($result) {
                        return [
                                "_id" => $result->_id,
                                "name" => $result->name,
                                "latitude" => $result->latitude,
                                "longitude" => $result->longitude,
                                "no_of_slots" => $result->no_of_slots,
                                "is_public" => $result->is_public,
                                "is_closed" => $result->is_closed,
                                "bike_slots" => $result->bike_slots,
                                "car_slots" => $result->car_slots,
                                "van_slots" => $result->van_slots,
                                "companyy_id" => $result->company_id
                        ];
                } else // no open parking spaces
                {
                        return false;
                }
        }
}
