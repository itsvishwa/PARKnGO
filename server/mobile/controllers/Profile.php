<?php


class Profile extends Controller
{

    private $profile_model;
    private $driver_model;
    private $otp_model;

    public function __construct()
    {
        $this->profile_model = $this->model("ProfileModel");
        $this->driver_model = $this->model("DriverModel");
        $this->otp_model = $this->model("OTPModel");
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
    public function check_mobile_number($mobile_number)
    {
        if ($this->driver_model->is_mobile_number_exist($mobile_number)) // mobile number already exist 
        {
            $this->send_json_400("Mobile number you entered is already registered one! ");
        } else // mobile number is a new one
        {
            $this->send_json_200("Mobile number you entered can be updatable");
        }
        // generate and send otp
        // TODO :: FIND A WAY TO REDUCE THE REPETITION
    }

    // update mobile number
    public function update_mobile_number($mobile_number, $otp_code)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else {
            // check the otp
            // if opt correct -> update the mobile number
            // TODO :: FIND A WAY TO REDUCE THE REPETITION
        }
    }
}
