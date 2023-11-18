<?php

class ParkingSpace extends Controller
{

    private $parking_space_model;

    public function __construct()
    {
        $this->parking_space_model = $this->model("ParkingSpaceModel");
    }


    // show only available parking spaces related to the given vehicle type
    public function view_available($vehicle_type)
    {
        $result = $this->parking_space_model->get_available_parking_spaces($vehicle_type);

        if ($result === false) // no open parking spaces available for selected vehicle type
        {
            $this->send_json_400("No parking spaces available at the movement for selected vehicle type");
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
                    "rate" => $space_data->rate,
                    "avg_star_count" => $space_data->avg_star_count,
                    "total_review_count" => $space_data->total_review_count
                ];
                $spaces_data[] = $temp; // add temp assosiative array to spaces_data[]
            }

            $this->send_json_200($spaces_data);
        }
    }


    // show all parking spaces
    public function view_all()
    {
        $result = $this->parking_space_model->get_all_parking_spaces();

        if ($result === false) // no parking spaces 
        {
            $this->send_json_400("Sorry, No parking spaces available");
        } else // have parking spaces
        {
            $spaces_data = [];

            foreach ($result as $space_data) {
                $temp = [
                    "_id" => $space_data->_id,
                    "name" => $space_data->name,
                    "address" => $space_data->address,
                    "is_public" => $space_data->is_public,
                    "is_closed" => $space_data->is_closed,
                    "avg_star_count" => $space_data->avg_star_count,
                    "total_review_count" => $space_data->total_review_count
                ];

                $spaces_data[] = $temp; // add temp assosiative array to spaces_data[]

            }

            $this->send_json_200($spaces_data);
        }
    }


    // show all details of the selected parking space
    public function view($_id)
    {
        $parking_space_data = $this->parking_space_model->get_parking_space_details($_id);
        $parking_slot_data = $this->parking_space_model->get_parking_space_status_details($_id);
        $reviews_data = $this->parking_space_model->get_reviews_of_a_parking_space($_id);


        if ($parking_space_data === false || $parking_slot_data === false) // no parking space for a given _id
        {
            $this->send_json_404("Parking Space Not Found");
        } else // parking space found
        {
            $result = [
                "name" => $parking_space_data->name,
                "address" => $parking_space_data->address,
                "latitude" => $parking_space_data->latitude,
                "longitude" => $parking_space_data->longitude,
                "is_public" => $parking_space_data->is_public,
                "is_closed" => $parking_space_data->is_closed,
                "avg_star_count" => $parking_space_data->avg_star_count,
                "total_review_count" => $parking_space_data->total_review_count,
                "slot_status" => null,
                "reviews" => null
            ];

            $new_parking_slot_data = [];

            foreach ($parking_slot_data as $slot_data) {
                // add a slot to the new assosiative array
                $new_parking_slot_data[] = [
                    "vehicle_type" => $slot_data->vehicle_type,
                    "free_slots" => $slot_data->free_slots,
                    "total_slots" => $slot_data->total_slots,
                    "rate" => $slot_data->rate
                ];
            }

            // add new parking slot data array to final result array
            $result["slot_status"] = $new_parking_slot_data;

            if ($reviews_data === false) // no reviews yet
            {
                $result["reviews"] = "N/A";
            } else // reviews found
            {
                $new_reviews_data = [];

                foreach ($reviews_data as $review_data) {
                    // add a review to the new assosiative array
                    $new_reviews_data[] = [
                        "name" => $review_data->first_name . " " . $review_data->last_name,
                        "time_stamp" => $review_data->time_stamp,
                        "no_of_stars" => $review_data->no_of_stars,
                        "content" => $review_data->content,
                    ];
                }

                $result["reviews"] = $new_reviews_data;
            }

            $this->send_json_200($result);
        }
    }
}
