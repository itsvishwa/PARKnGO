<?php

class ParkingSpace extends Controller
{

    private $parking_space_model;
    private $review_model;

    public function __construct()
    {
        $this->parking_space_model = $this->model("ParkingSpaceModel");
        $this->review_model = $this->model("ReviewModel");
    }


    // driver mobile - show only available parking spaces related to the given vehicle type
    public function view_available($vehicle_type, $latitude, $longitude, $page_number)
    {

        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else // token is valid
        {
            $result = $this->parking_space_model->get_available_parking_spaces($this->convert_to_vehicle_category($vehicle_type), $page_number);
            $is_next = true;
            if ($result == false) // no open parking spaces available for selected vehicle type
            {
                $this->send_json_400("PS_NOPS");
            } else // there are open parking spaces
            {
                if (count($result) < 6) {
                    $is_next = false;
                } else {
                    array_pop($result);
                }
                $spaces_data = []; // final array to send as a response

                foreach ($result as $space_data) {
                    $temp = [
                        "_id" => $space_data->_id,
                        "name" => $space_data->name,
                        "address" => $space_data->address,
                        "latitude" => $space_data->latitude,
                        "longitude" => $space_data->longitude,
                        "is_public" => $space_data->is_public,
                        "free_slots" => $space_data->free_slots,
                        "total_slots" => $space_data->total_slots,
                        "rate" => $space_data->rate,
                        "avg_star_count" => $space_data->avg_star_count,
                        "total_review_count" => $space_data->total_review_count
                    ];

                    $temp["distance"] = $this->calculate_distance($latitude, $longitude, $space_data->latitude, $space_data->longitude);
                    if ($temp["distance"] == -1) {
                        $temp["distance"] = 999;
                    }
                    $spaces_data["data"][] = $temp; // add temp assosiative array to spaces_data[]
                }

                if ($is_next == false) {
                    $spaces_data["is_next_available"] = "0";
                } else {
                    $spaces_data["is_next_available"] = "1";
                }

                // sort ASC order of distances
                usort($spaces_data["data"], function ($a, $b) {
                    return $a['distance'] <=> $b['distance'];
                });

                $this->send_json_200($spaces_data);
            }
        }
    }


    // driver mobile - show all parking spaces
    public function view_all()
    {

        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else // token is valid
        {

            $result = $this->parking_space_model->get_all_parking_spaces();

            if ($result == false) // no parking spaces 
            {
                $this->send_json_400("PS_NPS");
            } else // have parking spaces
            {
                $spaces_data = [];
                $curr_time = time();
                foreach ($result as $space_data) {
                    $temp = [
                        "_id" => $space_data->_id,
                        "name" => $space_data->name,
                        "address" => $space_data->address,
                        "is_public" => $space_data->is_public,
                        "is_closed" => ($space_data->closed_end_time !== NULL && $curr_time < $space_data->closed_end_time) ? "1" : "0",
                        "avg_star_count" => $space_data->avg_star_count,
                        "total_review_count" => $space_data->total_review_count
                    ];

                    $spaces_data[] = $temp; // add temp assosiative array to spaces_data[]
                }

                $this->send_json_200($spaces_data);
            }
        }
    }


    // driver mobile - show all details of the selected parking space
    public function view_one($_id)
    {

        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else // token is valid
        {
            $parking_space_data = $this->parking_space_model->get_parking_space_details($_id);
            $parking_slot_data = $this->parking_space_model->get_parking_space_status_details($_id);
            $reviews_data = $this->review_model->get_reviews_of_a_parking_space($_id);


            if ($parking_space_data == false || $parking_slot_data == false) // no parking space for a given _id
            {
                $this->send_json_404("PS_IPSID");
            } else // parking space found
            {
                $curr_time = time();
                $result = [
                    "_id" => $parking_space_data->_id,
                    "name" => $parking_space_data->name,
                    "address" => $parking_space_data->address,
                    "latitude" => $parking_space_data->latitude,
                    "longitude" => $parking_space_data->longitude,
                    "is_public" => $parking_space_data->is_public,
                    "is_closed" => ($parking_space_data->closed_end_time !== NULL && $curr_time < $parking_space_data->closed_end_time) ? "1" : "0",
                    "avg_star_count" => $parking_space_data->avg_star_count,
                    "total_review_count" => $parking_space_data->total_review_count,
                    "slot_status" => null,
                    "reviews" => null,
                    "user_own_reviews" => null,
                    "image" => base64_encode($parking_space_data->image)
                ];

                $new_parking_slot_data = [];

                foreach ($parking_slot_data as $slot_data) {
                    // add a slot to the new assosiative array
                    $new_parking_slot_data[] = [
                        "vehicle_type" => $this->convert_to_vehicle_type($slot_data->vehicle_type),
                        "free_slots" => $slot_data->free_slots,
                        "total_slots" => $slot_data->total_slots,
                        "rate" => $slot_data->rate
                    ];
                }

                // add new parking slot data array to final result array
                $result["slot_status"] = $new_parking_slot_data;

                if ($reviews_data == false) // no reviews yet
                {
                    $result["reviews"] = [
                        "availability" => "N/A"
                    ];
                    $result["user_own_reviews"] = [
                        "availability" => "N/A"
                    ];
                } else // reviews found
                {
                    $new_reviews_data = [
                        "availability" => "N/A",
                        "data" => []
                    ];
                    $new_user_review_data = [
                        "availability" => "N/A"
                    ];

                    foreach ($reviews_data as $review_data) {
                        // add a review to the new assosiative array
                        if ($review_data->driver_id == $token_data["user_id"]) // user's review
                        {
                            $new_user_review_data = [
                                "availability" => "AV",
                                "_id" => $review_data->_id,
                                "name" => $review_data->first_name . " " . $review_data->last_name,
                                "time_stamp" => implode(" | ", $this->format_time($review_data->time_stamp)),
                                "no_of_stars" => $review_data->no_of_stars,
                                "content" => $review_data->content,
                            ];
                        } else {
                            $new_reviews_data["availability"] = "AV";
                            $new_reviews_data["data"][] = [
                                "name" => $review_data->first_name . " " . $review_data->last_name,
                                "time_stamp" => implode(" | ", $this->format_time($review_data->time_stamp)),
                                "no_of_stars" => $review_data->no_of_stars,
                                "content" => $review_data->content,
                            ];
                        }
                    }

                    $result["reviews"] = $new_reviews_data;
                    $result["user_own_reviews"] = $new_user_review_data;
                }

                $this->send_json_200($result);
            }
        }
    }


    // driver mobile - show all available parking spaces respect to the given vehicle type and given keyword
    public function search_available($vehicle_type, $keyword, $latitude, $longitude, $page_number)
    {

        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else // token is valid
        {
            $keyword = str_replace("_", " ", $keyword);
            $result = $this->parking_space_model->get_available_parking_spaces_by_search($this->convert_to_vehicle_category($vehicle_type), $keyword, $page_number);
            $is_next = true;
            if ($result == false)  // no open parking spaces available for selected vehicle type 
            {
                $this->send_json_400("PS_NOPS");
            } else // there are open parking spaces
            {

                if (count($result) < 6) {
                    $is_next = false;
                } else {
                    array_pop($result);
                }

                $spaces_data = []; // final array to send as a response

                foreach ($result as $space_data) {
                    $temp = [
                        "_id" => $space_data->_id,
                        "name" => $space_data->name,
                        "address" => $space_data->address,
                        "latitude" => $space_data->latitude,
                        "longitude" => $space_data->longitude,
                        "is_public" => $space_data->is_public,
                        "free_slots" => $space_data->free_slots,
                        "total_slots" => $space_data->total_slots,
                        "rate" => $space_data->rate,
                        "avg_star_count" => $space_data->avg_star_count,
                        "total_review_count" => $space_data->total_review_count
                    ];

                    $temp["distance"] = $this->calculate_distance($latitude, $longitude, $space_data->latitude, $space_data->longitude);

                    $spaces_data["data"][] = $temp; // add temp assosiative array to spaces_data[]
                }
                if ($is_next == false) {
                    $spaces_data["is_next_available"] = "0";
                } else {
                    $spaces_data["is_next_available"] = "1";
                }

                // sort ASC order of distances
                usort($spaces_data["data"], function ($a, $b) {
                    return $a['distance'] <=> $b['distance'];
                });

                $this->send_json_200($spaces_data);
            }
        }
    }


    // driver mobile - show all parking spaces respect to the given vehicle type and given keyword
    public function search_all($keyword)
    {

        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else // token is valid
        {
            $keyword = str_replace("_", " ", $keyword);
            $result = $this->parking_space_model->get_all_parking_spaces_by_search($keyword);

            if ($result == false) // no parking spaces 
            {
                $this->send_json_400("PS_NPS");
            } else // have parking spaces
            {
                $spaces_data = [];
                $curr_time = time();
                foreach ($result as $space_data) {
                    $temp = [
                        "_id" => $space_data->_id,
                        "name" => $space_data->name,
                        "address" => $space_data->address,
                        "is_public" => $space_data->is_public,
                        "is_closed" => ($space_data->closed_end_time !== NULL && $curr_time < $space_data->closed_end_time) ? "1" : "0",
                        "avg_star_count" => $space_data->avg_star_count,
                        "total_review_count" => $space_data->total_review_count
                    ];

                    $spaces_data[] = $temp; // add temp assosiative array to spaces_data[]
                }

                $this->send_json_200($spaces_data);
            }
        }
    }


    // driver mobile - get data of all parking space for a given vehicle - for the map
    public function get_map_data($vehicle_type)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else // token is valid
        {
            $result = $this->parking_space_model->get_all_parking_spaces_for_vehicle($this->convert_to_vehicle_category($vehicle_type));
            if ($result == false)  // no open parking spaces available for selected vehicle type 
            {
                $this->send_json_400("PS_NOPS");
            } else // there are open parking spaces
            {
                $spaces_data = []; // final array to send as a response

                foreach ($result as $space_data) {
                    $temp = [
                        "_id" => $space_data->_id,
                        "name" => $space_data->name,
                        "address" => $space_data->address,
                        "latitude" => $space_data->latitude,
                        "longitude" => $space_data->longitude,
                        "is_public" => $space_data->is_public,
                        "free_slots" => $space_data->free_slots,
                        "total_slots" => $space_data->total_slots,
                        "rate" => $space_data->rate
                    ];
                    $spaces_data[] = $temp; // add temp assosiative array to spaces_data[]
                }

                $this->send_json_200($spaces_data);
            }
        }
    }


    // calculate distance
    private function calculate_distance($source_lat, $source_long, $dest_lat, $dest_long)
    {
        $source_coordinates = $source_lat . "," . $source_long;
        $dest_coordinates = $dest_lat . "," . $dest_long;

        $uri = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" . $source_coordinates . "&destinations=" . $dest_coordinates . "&units=imperial&key=" . G_API_KEY;

        // Send request to Google Distance Matrix API
        $response = file_get_contents($uri);

        // Decode JSON response
        $decoded_response = json_decode($response, true);


        // distance in meters
        $distance = -1000; // when google api can't find a root
        if (isset($decoded_response["rows"][0]["elements"][0]["distance"])) {
            $distance = $decoded_response["rows"][0]["elements"][0]["distance"]["value"];
        }

        return $distance / 1000;
    }
}
