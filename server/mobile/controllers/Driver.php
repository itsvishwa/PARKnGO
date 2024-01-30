<?php


class Driver extends Controller
{

        public $driver_model;
        public $driver_qr_model;

        public function __construct()
        {
                $this->driver_model = $this->model("DriverModel");
                $this->driver_qr_model = $this->model("DriverQRModel");
        }

        public function view_vehicle_info()
        {
                $token_data = $this->verify_token_for_drivers();
                if ($token_data === 400) {
                        $this->send_json_400("Invalid Token");
                } elseif ($token_data === 404) {
                        $this->send_json_404("Token Not Found");
                } else {
                        $result = $this->driver_qr_model->get_all($token_data["user_id"]);
                        if ($result === false) // no saved vahicle info
                        {
                                $result = [
                                        "status_code" => "E_DR_2032"
                                ];
                                $this->send_json_200($result);
                        } else // have saved vehicle info
                        {
                                $result_arr = [];
                                foreach ($result as $result_data) {
                                        $temp = [
                                                "selected" => $result_data->selected,
                                                "vehicle_name" => $result_data->vehicle_name,
                                                "vehicle_number" => $result_data->vehicle_number,
                                                "vehicle_type" => $result_data->vehicle_type
                                        ];
                                        $result_arr[] = $temp;
                                }
                                $result = [
                                        "status_code" => "S_DR_2031",
                                        "data" => $result_arr
                                ];
                                $this->send_json_200($result);
                        }
                }
        }

        public function add_vehicle()
        {
                $vehicle_data = [
                        "vehicle_name" => trim($_POST["vehicle_name"]),
                        "vehicle_number" => trim($_POST["vehicle_number"]),
                        "vehicle_type" => trim($_POST["vehicle_type"])
                ];

                $token_data = $this->verify_token_for_drivers();

                if ($token_data === 400) {
                        $this->send_json_400("Invalid Token");
                } else if ($token_data === 404) {
                        $this->send_json_404("Token not found");
                } else {
                        $selected_vehicle = $this->driver_qr_model->get_selected_vehicle_number($token_data["user_id"]);

                        $this->driver_qr_model->add_new_vehicle(
                                $vehicle_data["vehicle_name"],
                                $vehicle_data["vehicle_number"],
                                strtolower($vehicle_data["vehicle_type"]),
                                $selected_vehicle,
                                $token_data["user_id"]
                        );
                        $this->send_json_200("Vehicle details are added successfully!");
                }
        }
}
