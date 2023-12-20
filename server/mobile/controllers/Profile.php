<?php


class Profile extends Controller
{

    private $profile_model;
    private $driver_model;
    private $otp_model;

    private $officer_model;

    private $payment_model;

    public function __construct()
    {
        $this->profile_model = $this->model("ProfileModel");
        $this->driver_model = $this->model("DriverModel");
        $this->otp_model = $this->model("OTPModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->payment_model = $this->model("PaymentModel");
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


    // send profile details of the driver
    public function get_officer_details() {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else {
            $details = $this->officer_model->get_officer($token_data["user_id"]);
            print_r($details);
        }





        // if($this->officer_model->is_mobile_number_exist($mobile_number)) {
        //     $details = $this->officer_model->get_officer($mobile_number);
        //     print_r($details);
        // } else {
        //     $this->send_json_400("Mobile number is not exists");
        // }
    }



    // // send payment history of the officer
    // public function officer_payment_history()
    // {
    //     $token_data = $this->verify_token_for_officers();

    //     if ($token_data === 400) {
    //         $this->send_json_400("Invalid Token");
    //     } elseif ($token_data === 404) {
    //         $this->send_json_404("Token Not Found");
    //     } else // token is valid
    //     {
    //         $payments_data = $this->payment_model->get_all_driver_payments_by_id($token_data["user_id"]);

    //         if ($payments_data === false) // not payments yet
    //         {
    //             $this->send_json_400("No Payments has been made yet");
    //         } else // there are payments data
    //         {
    //             $result_data = [];

    //             foreach ($payments_data as $payment_data) {
    //                 $temp = [
    //                     "amount" => $payment_data->amount,
    //                     "time_duration" =>  $payment_data->end_time - $payment_data->start_time,
    //                     "vehicle_number" => $payment_data->vehicle_number,
    //                     "parking_space_name" => $payment_data->name,
    //                     "payment_time_stamp" => $payment_data->time_stamp,
    //                     "payment_method" => $payment_data->payment_method
    //                 ];
    //                 $result_data[] = $temp;
    //             }

    //             $this->send_json_200($result_data);
    //         }
    //     }
    // }
}
