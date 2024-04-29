<?php


class Driver extends Controller
{

    public $driver_model;
    public $driver_qr_model;
    public $session_model;
    public $payment_model;
    public $parking_space_status_model;

    public function __construct()
    {
        $this->driver_model = $this->model("DriverModel");
        $this->driver_qr_model = $this->model("DriverQRModel");
        $this->session_model = $this->model("SessionModel");
        $this->payment_model = $this->model("PaymentModel");
        $this->parking_space_status_model = $this->model("ParkingSpaceStatusModel");
    }

    // driver mobile - used to view saved vehicle informations of a driver
    public function view_vehicle_info()
    {
        $token_data = $this->verify_token_for_drivers();
        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            $result = $this->driver_qr_model->get_all($token_data["user_id"]);
            if ($result == false) // no saved vahicle info
            {
                $result = [
                    "status_code" => "DR_NSV"
                ];
                $this->send_json_200($result);
            } else // have saved vehicle info
            {
                $result_arr = [];
                foreach ($result as $result_data) {
                    $temp = [
                        "selected" => $result_data->selected,
                        "vehicle_name" => $result_data->vehicle_name,
                        "vehicle_number" => $this->format_vehicle_number($result_data->vehicle_number),
                        "row_vehicle_number" => $result_data->vehicle_number,
                        "vehicle_type" => ucfirst($result_data->vehicle_type) // first letter to capital
                    ];
                    $result_arr[] = $temp;
                }
                $result = [
                    "status_code" => "SUCCESS",
                    "data" => $result_arr
                ];
                $this->send_json_200($result);
            }
        }
    }

    // driver mobile - used to add a new vehicle
    public function add_vehicle()
    {
        $vehicle_data = [
            "vehicle_name" => trim($_POST["vehicle_name"]),
            "vehicle_number" => trim($_POST["vehicle_number"]),
            "vehicle_type" => trim($_POST["vehicle_type"])
        ];

        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } else if ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            // get selected number for the new vehicle
            $selected_vehicle = $this->driver_qr_model->get_selected_vehicle_number($token_data["user_id"]);

            $this->driver_qr_model->add_new_vehicle(
                $vehicle_data["vehicle_name"],
                $vehicle_data["vehicle_number"],
                strtolower($vehicle_data["vehicle_type"]),
                $selected_vehicle,
                $token_data["user_id"]
            );
            $this->send_json_200("SUCCESS");
        }
    }

    // driver mobile - used to edit vehicle information
    public function edit_vehicle()
    {
        $vehicle_data = [
            "vehicle_name" => trim($_POST["vehicle_name"]),
            "vehicle_number" => trim($_POST["vehicle_number"]),
            "vehicle_type" => strtolower(trim($_POST["vehicle_type"])),
            "selected" => trim($_POST["selected"])
        ];

        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } else if ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            $result = $this->driver_qr_model->is_exist_vehicle_record($token_data["user_id"], $vehicle_data["selected"]);
            if ($result) {
                $this->driver_qr_model->update_vehicle_info(
                    $vehicle_data["vehicle_name"],
                    $vehicle_data["vehicle_number"],
                    $vehicle_data["vehicle_type"],
                    $vehicle_data["selected"],
                    $token_data["user_id"]
                );
                $this->send_json_200("SUCCESS");
            } else {
                $this->send_json_400("DR_IUD");
            }
        }
    }

    // driver mobile - used to delete vehicle infomation
    public function delete_vehicle($selected_vehicle)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } else if ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            $result = $this->driver_qr_model->is_exist_vehicle_record($token_data["user_id"], $selected_vehicle);
            if ($result) {
                $this->driver_qr_model->delete_driver_vehicle($selected_vehicle, $token_data["user_id"]);

                // has to restructure reamainng vehicle informations
                if ($selected_vehicle == 2) {
                    $result = $this->driver_qr_model->is_exist_vehicle_record($token_data["user_id"], 3);
                    if ($result) {
                        $this->driver_qr_model->update_driver_selected_vehicle_level($token_data["user_id"], 3, 2);
                    }
                } else if ($selected_vehicle == 1) {
                    $result = $this->driver_qr_model->is_exist_vehicle_record($token_data["user_id"], 2);
                    if ($result) {
                        $this->driver_qr_model->update_driver_selected_vehicle_level($token_data["user_id"], 2, 1);
                    }
                    $result = $this->driver_qr_model->is_exist_vehicle_record($token_data["user_id"], 3);
                    if ($result) {
                        $this->driver_qr_model->update_driver_selected_vehicle_level($token_data["user_id"], 3, 2);
                    }
                }
                $this->send_json_200("SUCCESS");
            } else {
                // driver don't have a vehicle information related to requested data
                $this->send_json_400("DR_IUD");
            }
        }
    }

    // driver mobile - used to check whether the driver has a open parking session, send data if exist
    public function view_open_parking_session()
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } else if ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            $result = $this->session_model->is_driver_session_exist($token_data["user_id"]);
            if ($result) {
                $date_and_time = $this->format_time($result->start_time);

                $result_arr = [
                    "status_code" => "SUCCESS", // means user has a open parking session
                    "data" => [
                        "session_id" => $this->encrypt_id($result->_id),
                        "parking_name" => $result->name,
                        "start_time" => $date_and_time[0] . " | " . $date_and_time[1],
                        "vehicle_number" => $this->format_vehicle_number($result->vehicle_number),
                        "vehicle_type" => ucfirst($result->vehicle_type),
                        "officer_id" => $result->officer_id,
                        "officer_name" => $result->first_name . " " . $result->last_name,
                        "time_went" => $this->calculate_time(time() - $result->start_time)
                    ]
                ];
                $parking_rate = $this->parking_space_status_model->get_rate($this->convert_to_vehicle_category($result->vehicle_type), $result->pid);
                $result_arr["data"]["parking_rate"] = $parking_rate->rate;

                $this->send_json_200($result_arr);
            } else // no open parking session for driver
            {
                $result_arr = [
                    "status_code" => "DR_NOPS" // means user has no open parking session
                ];
                $this->send_json_200($result_arr);
            }
        }
    }

    // driver mobile - used to check whether the driver has a payment due session, send data if exist
    public function view_open_payments()
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } else if ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            $payment_id = $this->payment_model->is_open_payment_exist($token_data["user_id"]);
            if ($payment_id) {
                $payment_data = $this->payment_model->get_all_data($payment_id);

                $start_date_and_time = $this->format_time($payment_data->start_time);
                $end_date_and_time = $this->format_time($payment_data->end_time);

                $result_arr = [
                    "payment_id" => $this->encrypt_id($payment_data->_id),
                    "amount" => $payment_data->amount,
                    "start_time" => $start_date_and_time[0] . " | " . $start_date_and_time[1],
                    "end_time" => $end_date_and_time[0] . " | " . $end_date_and_time[1],
                    "time_went" => $this->calculate_time($payment_data->end_time - $payment_data->start_time),
                    "vehicle_number" => $this->format_vehicle_number($payment_data->vehicle_number),
                    "vehicle_type" => $payment_data->vehicle_type,
                    "parking_space_name" => $payment_data->name,
                    "officer_id" => $payment_data->officer_id,
                    "officer_name" => $payment_data->first_name . " " . $payment_data->last_name
                ];

                $parking_rate = $this->parking_space_status_model->get_rate($this->convert_to_vehicle_category($payment_data->vehicle_type), $payment_data->pid);
                $result_arr["rate"] = $parking_rate->rate;

                $this->send_json_200(
                    [
                        "status_code" => "SUCCESS", // means that the user has a open payemnt due session
                        "data" => $result_arr
                    ]
                );
            } else {
                $this->send_json_200(
                    [
                        "status_code" => "DR_NOPAYS", // means that the user has no open payemnt due session 
                        "msg" => "Driver dosen't have open payemnt session"
                    ]
                );
            }
        }
    }

    // calculate no of seconds to no of hours and minutes
    private function calculate_time($time)
    {
        $hours = floor($time / 3600);
        $minutes = floor(($time % 3600) / 60);

        $result = [
            "hours" => $hours . "",
            "minutes" => $minutes . ""
        ];

        return $result;
    }
}
