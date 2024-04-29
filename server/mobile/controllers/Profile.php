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
    private $parking_space_model;
    private $duty_record_model;


    public function __construct()
    {
        $this->profile_model = $this->model("ProfileModel");
        $this->driver_model = $this->model("DriverModel");
        $this->otp_model = $this->model("OTPModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->payment_model = $this->model("PaymentModel");
        $this->parking_space_model = $this->model("ParkingSpaceModel");
        $this->duty_record_model = $this->model("DutyRecordModel");
        $this->user_controller = new User;
    }

    // driver mobile - update name
    public function update_name($first_name, $last_name)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            $this->profile_model->update_name($token_data["user_id"], $first_name, $last_name);
            $this->send_json_200("SUCCESS");
        }
    }


    // driver mobile - check mobile number existancce
    public function send_otp($mobile_number)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            if ($this->driver_model->is_mobile_number_exist($mobile_number)) // mobile number is a registered one
            {
                $this->send_json_400("PROF_MNAE");
            } else // new mobile number
            {
                $this->user_controller->send_otp($mobile_number);
            }
        }
    }

    // driver mobile - if otp correct this will update the mobile number
    public function update_mobile_number($mobile_number, $otp_code)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else {
            switch ($this->user_controller->check_otp($otp_code, $mobile_number)) {
                case 1: // otp is correct
                    $this->otp_model->delete_otp($mobile_number);
                    $this->profile_model->update_mobile_number($token_data["user_id"], $mobile_number);
                    $this->send_json_200("SUCCESS");
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


    // driver mobile - send payment history of the driver
    public function driver_payment_history()
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data == 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data == 404) {
            $this->send_json_404("ERR_TNF");
        } else // token is valid
        {
            $payments_data = $this->payment_model->get_all_driver_payments_by_id($token_data["user_id"]);

            if ($payments_data == false) // not payments yet
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


    // get payment history of the officer
    public function officer_payment_history()
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $encoded_parking_id = $_SERVER['HTTP_ENCODED_PARKING_ID'];
            $parking_id = $this->decrypt_id($encoded_parking_id);

            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            if ($assigned_parking == $parking_id) { //parking_id is similar to the assigned parking

                $payments_history_data = $this->payment_model->get_all_officer_payments_history_by_officer_id($token_data["user_id"]);

                if ($payments_history_data == false) // not payments yet
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
                            "Date_and_Time" => implode(" | ", $this->format_time($payment_history_data->time_stamp)),
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


    public function mark_work_shift_on()
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $encrypted_parking_id = trim($_POST["parking_id"]);

            // Decrypt the parking_id
            $parking_id = $this->decrypt_id($encrypted_parking_id);

            if ($assigned_parking == $parking_id) { //parking_id is similar to the assigned parking of the officer

                // Check the Location

                // Device location
                $device_latitude = trim($_POST["latitude"]);
                $device_longitude = trim($_POST["longitude"]);

                // Parking Location
                $parking_space_details = $this->parking_space_model->get_parking_space_details($parking_id);
                $parking_latitude = $parking_space_details->latitude;
                $parking_longitude = $parking_space_details->longitude;

                // Calculate distance between officer location and assigned parking location
                $distance = $this->calculateDistance($device_latitude, $device_longitude, $parking_latitude, $parking_longitude);

                // Define a distance threshold (in kilometers) within which the location is considered valid
                $distanceThreshold = 0.2;

                if ($distance <= $distanceThreshold) { // Location is within the threshold
                    // If location is fine then update the database
                    $time_stamp = time();

                    // Update the Duty_record table
                    $this->duty_record_model->mark_duty_in($time_stamp, $token_data["user_id"]);

                    // Retrieve the start time stamp
                    $time_data = $this->format_time($time_stamp);
                    $result = [
                        "response_code" => "800",
                        "message" => "Duty record is marked IN!",
                        "time_stamp" => [
                            "date" => $time_data[1],
                            "time" => $time_data[0]
                        ]
                    ];

                    $this->send_json_200($result);
                } else {
                    // Location is outside the threshold, consider it invalid
                    $result = [
                        "response_code" => "802",
                        "message" => "Location is too far from the assigned parking"
                    ];

                    $this->send_json_200($result);
                }
            } else { //parking_id is not similar to the assigned parking of the parking officer
                $assigned_parking_details = $this->parking_space_model->get_parking_space_details($assigned_parking);

                if ($assigned_parking_details) {
                    $assigned_parking_name = $assigned_parking_details->name;

                    $result = [
                        "response_code" => "101",
                        "updated parking_id" => $assigned_parking,
                        "updated parking_name" => $assigned_parking_name,
                    ];

                    $this->send_json_200($result);
                } else {
                    $result = [
                        "response_code" => "204",
                        "message" => "parking details not found"
                    ];

                    $this->send_json_404($result);
                }
            }
        }
    }


    // Calculate distance between two points using Haversine formula
    private function calculateDistance($lat1, $lon1, $lat2, $lon2)
    {
        $earthRadius = 6371; // Earth's radius in kilometers

        $dLat = deg2rad($lat2 - $lat1);
        $dLon = deg2rad($lon2 - $lon1);

        $a = sin($dLat / 2) * sin($dLat / 2) + cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * sin($dLon / 2) * sin($dLon / 2);
        $c = 2 * atan2(sqrt($a), sqrt(1 - $a));

        $distance = $earthRadius * $c; // Distance in kilometers

        return $distance;
    }


    public function mark_work_shift_off()
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data == 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data == 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $encrypted_parking_id = trim($_POST["parking_id"]);

            // Decrypt the parking_id
            $parking_id = $this->decrypt_id($encrypted_parking_id);

            if ($assigned_parking == $parking_id) { //parking_id is similar to the assigned parking of the officer

                $time_stamp = time();

                // Update the Duty_record table
                $this->duty_record_model->mark_duty_off($time_stamp, $token_data["user_id"]);

                // Retrieve the start time stamp
                $time_data = $this->format_time($time_stamp);
                $result = [
                    "response_code" => "800",
                    "message" => "Duty record is marked OFF!",
                    "time_stamp" => [
                        "date" => $time_data[1],
                        "time" => $time_data[0]
                    ]
                ];

                $this->send_json_200($result);
            } else { //parking_id is not similar to the assigned parking of the parking officer
                $assigned_parking_details = $this->parking_space_model->get_parking_space_details($assigned_parking);

                if ($assigned_parking_details) {
                    $assigned_parking_name = $assigned_parking_details->name;

                    $result = [
                        "response_code" => "101",
                        "updated parking_id" => $assigned_parking,
                        "updated parking_name" => $assigned_parking_name,
                    ];

                    $this->send_json_200($result);
                } else {
                    $result = [
                        "response_code" => "204",
                        "message" => "parking details not found"
                    ];

                    $this->send_json_404($result);
                }
            }
        }
    }
}
