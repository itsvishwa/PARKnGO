<?php

require_once "User.php";

class Profile extends Controller
{

    private $profile_model;
    private $driver_model;
    private $otp_model;
    private $user_controller;

    public function __construct()
    {
        $this->profile_model = $this->model("ProfileModel");
        $this->driver_model = $this->model("DriverModel");
        $this->otp_model = $this->model("OTPModel");
        $this->user_controller = new User;
    }

    // update name
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


    // check mobile number existancce
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

    // if otp correct this will update the mobile number
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
}
