<?php


class Driver extends Controller
{

        public $driver_model;
        public $driver_qr_model;
        public $session_model;
        public $payment_model;

        public function __construct()
        {
                $this->driver_model = $this->model("DriverModel");
                $this->driver_qr_model = $this->model("DriverQRModel");
                $this->session_model = $this->model("SessionModel");
                $this->payment_model = $this->model("PaymentModel");
        }

        // driver req - view saved vehicle informations
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
                                                "vehicle_type" => ucfirst($result_data->vehicle_type) // first letter to capital
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

        // driver req - add new vehicle
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

        // driver req - edit vehicle information
        public function edit_vehicle()
        {
                $vehicle_data = [
                        "vehicle_name" => trim($_POST["vehicle_name"]),
                        "vehicle_number" => trim($_POST["vehicle_number"]),
                        "vehicle_type" => trim($_POST["vehicle_type"]),
                        "selected" => trim($_POST["selected"])
                ];

                $token_data = $this->verify_token_for_drivers();

                if ($token_data === 400) {
                        $this->send_json_400("Invalid Token");
                } else if ($token_data === 404) {
                        $this->send_json_404("Token not found");
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
                                $this->send_json_200("Vehicle information has been updated");
                        } else {
                                $this->send_json_400("Invalid user details");
                        }
                }
        }

        // driver req - delete vehicle infomation
        public function delete_vehicle($selected_vehicle)
        {
                $token_data = $this->verify_token_for_drivers();

                if ($token_data === 400) {
                        $this->send_json_400("Invalid Token");
                } else if ($token_data === 404) {
                        $this->send_json_404("Token not found");
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
                                $this->send_json_200("Vehicle information has been deleted successfully!");
                        } else {
                                // driver don't have a vehicle information related to requested data
                                $this->send_json_400("Invald user details");
                        }
                }
        }

        // driver req - check whether the driver has a open parking session, send data if exist
        public function view_open_parking_session()
        {
                $token_data = $this->verify_token_for_drivers();

                if ($token_data === 400) {
                        $this->send_json_400("Invalid Token");
                } else if ($token_data === 404) {
                        $this->send_json_404("Token not found");
                } else {
                        $result = $this->session_model->is_driver_session_exist($token_data["user_id"]);
                        if ($result) {
                                $result_arr = [
                                        "status_code" => "S_D001",
                                        "session_id" => $this->encrypt_id($result->_id),
                                        "start_time" => $result->start_time,
                                        "vehicle_number" => $result->vehicle_number,
                                        "vehicle_type" => ucfirst($result->vehicle_type),
                                        "officer_id" => $result->officer_id,
                                        "officer_name" => $result->first_name . " " . $result->last_name
                                ];
                                $this->send_json_200($result_arr);
                        } else // no open parking session for driver
                        {
                                $result_arr = [
                                        "sattus_code" => "E_D001"
                                ];
                                $this->send_json_200("result_arr");
                        }
                }
        }

        // driver req - check whether the driver has a payment due session, send data if exist
        public function view_open_payments()
        {
                $token_data = $this->verify_token_for_drivers();

                if ($token_data === 400) {
                        $this->send_json_400("Invalid Token");
                } else if ($token_data === 404) {
                        $this->send_json_404("Token not found");
                } else {
                        $payment_id = $this->payment_model->is_open_payment_exist($token_data["user_id"]);
                        if ($payment_id) {
                                $payment_data = $this->payment_model->get_all_data($payment_id);
                                $result_arr = [
                                        "amount" => $payment_data->amount,
                                        "start_time" => $payment_data->start_time,
                                        "end_time" => $payment_data->end_time,
                                        "rate" => $payment_data->rate,
                                        "vehicle_number" => $payment_data->vehicle_number,
                                        "vehicle_type" => $payment_data->vehicle_type,
                                        "parking_space_name" => $payment_data->name,
                                        "officer_id" => $payment_data->officer_id,
                                        "officer_name" => $payment_data->first_name . " " . $payment_data->last_name
                                ];
                                $this->send_json_200(
                                        [
                                                "status_code" => "S_D002",
                                                "data" => $result_arr
                                        ]
                                );
                        } else {
                                $this->send_json_200(
                                        [
                                                "status_code" => "E_D002",
                                                "msg" => "Driver dosen't have open payemnt session"
                                        ]
                                );
                        }
                }
        }
}
