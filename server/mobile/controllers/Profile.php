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
    private $parking_space_model;



    public function __construct()
    {
        $this->profile_model = $this->model("ProfileModel");
        $this->driver_model = $this->model("DriverModel");
        $this->otp_model = $this->model("OTPModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->payment_model = $this->model("PaymentModel");
        $this->officer_activity_model = $this->model("OfficerActivityModel");
        $this->parking_space_model = $this->model("ParkingSpaceModel");
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


    // send payment history of the driver
    public function driver_payment_history()
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $payments_data = $this->payment_model->get_all_driver_payments_by_id($token_data["user_id"]);

            if ($payments_data === false) // not payments yet
            {
                $this->send_json_400("ERROR_6001"); // ERROR_6001 => No payments have been made yet
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
                        "vehicle_number" => $payment_data->vehicle_number,
                        "parking_space_name" => $payment_data->name,
                        "payment_time_stamp" =>  date("h:i A | d/m/y", $payment_data->time_stamp)
                    ];
                    $result_data[] = $temp;
                }

                $this->send_json_200($result_data);
            }
        }
    }


    // profile details of officer
    // public function get_officer_details()
    // {
    //     $token_data = $this->verify_token_for_officers();

    //     if ($token_data === 400) {
    //         $this->send_json_400("Invalid Token");
    //     } elseif ($token_data === 404) {
    //         $this->send_json_404("Token Not Found");
    //     } else {
    //         $details = $this->officer_model->get_officer($token_data["user_id"]);
            
    //         if ($details) {

    //             // Concatenate first name and last name
    //             $full_name = $details["first_name"] . " " . $details["last_name"]; 
    
    //             $officer_details = [
    //                 "officer_id" => $details["officer_id"],
    //                 "full_name" => $full_name,
    //                 "mobile_number" => $details["mobile_number"],
    //                 "nic" => $details["nic"],
    //                 "parking_id" => $details["parking_id"]
    //             ];
    
    //             $this->send_json_200($officer_details);
    //         } else {
    //             $this->send_json_404("Officer details not found");
    //         }

    //     }

    // }



    // get payment history of the officer
    public function officer_payment_history()
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $encoded_parking_id = $_SERVER['HTTP_ENCODED_PARKING_ID'];
            $parking_id = $this->decrypt_id($encoded_parking_id);

             $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            if($assigned_parking === $parking_id) { //parking_id is similar to the assigned parking

                $payments_history_data = $this->payment_model->get_all_officer_payments_history_by_officer_id($token_data["user_id"]);

                if ($payments_history_data === false) // not payments yet
                {
                    $result = [
                        "response_code" => "204",
                        "message" => "No Payment transactions has been made yet"
                    ];

                    $this->send_json_400($result);
                } else // there are payments data
                {
                    $result_data = [];

                    foreach ($payments_history_data as $payment_history_data) {
                        $timestamp = $payment_history_data->time_stamp;
                        // $formatted_date = date("h.i A | d M", $timestamp);
        
                        $formatted_amount = 'Rs. ' . number_format($payment_history_data->amount, 2);


                        $payment_method = ($payment_history_data->payment_method);

                        $temp = [
                            "response_code" => "800",
                            "Date_and_Time" => $timestamp,
                            "Amount" => $formatted_amount,
                            "Vehicle" => $payment_history_data->vehicle_number,
                            "Payment_Method" => $payment_method
                        ];
                        $result_data[] = $temp;
                    }

                    $this->send_json_200($result_data);
                }
            
            } else {    //parking_id is not similar to the assigned parking

                $assigned_parking_details = $this->parking_space_model->get_parking_space_details($assigned_parking);
                if ($assigned_parking_details) // new parking has been assigned to the officer
                {
                    // You have been reassigned to a new parking space
                    $this->send_json_400("101");
                } else // no parking has been assigned to the officer
                {
                    // parking details not found
                    $this->send_json_404("204");
                }
            }
        }
    }
}
