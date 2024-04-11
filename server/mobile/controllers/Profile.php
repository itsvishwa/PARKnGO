<?php

require_once "User.php";

class Profile extends Controller
{

    private $profile_model;
    private $driver_model;
    private $otp_model;
    private $payment_model;
    private $user_controller;
    private $officer_model;
    private $officer_activity_model;



    public function __construct()
    {
        $this->profile_model = $this->model("ProfileModel");
        $this->driver_model = $this->model("DriverModel");
        $this->otp_model = $this->model("OTPModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->payment_model = $this->model("PaymentModel");
        $this->officer_activity_model = $this->model("OfficerActivityModel");
        $this->user_controller = new User;
    }

    // driver mob - update name
    public function update_name($first_name, $last_name)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else {
            $this->profile_model->update_name($token_data["user_id"], $first_name, $last_name);
            $this->send_json_200("Your name updated successfully!");
        }
    }


    // driver mob - check mobile number existancce
    public function send_otp($mobile_number)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else {
            if ($this->driver_model->is_mobile_number_exist($mobile_number)) // mobile number is a registered one
            {
                $this->send_json_400("Mobile number you entered is already registered one");
            } else // new mobile number
            {
                $this->user_controller->send_otp($mobile_number);
            }
        }
    }

    // driver mob - if otp correct this will update the mobile number
    public function update_mobile_number($mobile_number, $otp_code)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else {
            switch ($this->user_controller->check_otp($otp_code, $mobile_number)) {
                case 1: // otp is correct
                    $this->otp_model->delete_otp($mobile_number);
                    $this->profile_model->update_mobile_number($token_data["user_id"], $mobile_number);
                    $this->send_json_200("Mobile Number sucessfully updated");
                    break;
                case 2:
                    $this->send_json_400("OTP has been expired");
                    break;
                case 3:
                    $this->send_json_400("Invalid OTP");
                    break;
            }
        }
    }


    // driver mob - send payment history of the driver
    public function driver_payment_history()
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("PRF_IT");
        } elseif ($token_data === 404) {
            $this->send_json_404("PRF_TNF");
        } else // token is valid
        {
            $payments_data = $this->payment_model->get_all_driver_payments_by_id($token_data["user_id"]);

            if ($payments_data === false) // not payments yet
            {
                $this->send_json_400("PRF_NPY"); // No payments have been made yet
            } else // there are payments data
            {
                $result_data = [];

                foreach ($payments_data as $payment_data) {
                    $time_duration = $payment_data->end_time - $payment_data->start_time;
                    // Calculate hours, minutes, and seconds
                    $hours = floor($time_duration / 3600);
                    $minutes = floor(($time_duration % 3600) / 60);

                    // Format the result as a string
                    $time_duration = sprintf("%02d hours %02d minutes", $hours, $minutes);

                    $temp = [
                        "amount" => $payment_data->amount,
                        "payment_method" => strtoupper($payment_data->payment_method),
                        "time_duration" =>  $time_duration,
                        "vehicle_type" => $payment_data->vehicle_type,
                        "vehicle_number" => $this->format_vehicle_number($payment_data->vehicle_number),
                        "parking_space_name" => $payment_data->name,
                        "payment_time_stamp" =>  implode(" | ", $this->format_time($payment_data->time_stamp)),
                    ];
                    $result_data[] = $temp;
                }

                $this->send_json_200($result_data);
            }
        }
    }


    // send profile details of the driver
    public function get_officer_details()
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else {
            $details = $this->officer_model->get_officer($token_data["user_id"]);

            if ($details) {

                // Concatenate first name and last name
                $full_name = $details["first_name"] . " " . $details["last_name"];

                $officer_details = [
                    "officer_id" => $details["officer_id"],
                    "full_name" => $full_name,
                    "mobile_number" => $details["mobile_number"],
                    "nic" => $details["nic"],
                    "parking_id" => $details["parking_id"]
                ];

                $this->send_json_200($officer_details);
            } else {
                $this->send_json_404("Officer details not found");
            }
        }
    }
}
