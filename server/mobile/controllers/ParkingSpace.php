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
        $result = $this->parking_space_model->get_available_parking_spaces();

        if ($result === false) // no open parking spaces
        {
            $this->send_json_400("No open parking spaces, Please try again later");
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
                    "is_closed" => $space_data->is_closed,
                    "companyy_id" => $space_data->company_id
                ];
                $data = null;
                if ($vehicle_type === "bike") {
                    $data = $space_data->bike_slots;
                } elseif ($vehicle_type === "car") {
                    $data = $space_data->car_slots;
                } else {
                    $data = $space_data->van_slots;
                }
                $data = explode(":", $data);
                $temp["free_slots"] = $data[0];
                $temp["total_slots"] = $data[1];
                $temp["rate"] = $data[2];

                $spaces_data[] = $temp; // add temp assosiative array to spaces_data[]
            }

            $this->send_json_200($spaces_data);
        }
    }
}
