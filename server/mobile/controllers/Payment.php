<?php


class Payment extends Controller
{

    private $payment_model;
    private $officer_model;
    private $session_model;
    private $parking_space_model;

    public function __construct()
    {
        $this->payment_model = $this->model("PaymentModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->session_model = $this->model("SessionModel");
        $this->parking_space_model = $this->model("ParkingSpaceModel");
    }


    public function view_payment()
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // valid token 
        {
            $encoded_string = $_SERVER['HTTP_X_ENCODED_DATA']; // encoded payment_id
            $payment_id = $this->decrypt_id($encoded_string); // decode the string 

            if ($payment_id === false) // invalid encoded_string
            {
                $this->send_json_400("Invalid Encoded String");
            } else // code decoded sucessfully
            {
                if ($this->payment_model->is_payment_session_id_exist($payment_id)) // valid payment id
                {

                    if ($this->payment_model->is_payment_session_ended($payment_id)) // payment session has been closed
                    {
                        $this->send_json_200("Payment is successfully closed");
                    } else  // payment session still open
                    {
                        $result = $this->payment_model->get_all_data($payment_id);
                        $payment_data = [
                            "payment_id" => $encoded_string,
                            "amount" => $result->amount,
                            "start_time" => $result->start_time,
                            "end_time" => $result->end_time,
                            "vehicle_number" => $result->vehicle_number,
                            "vehicle_type" => $result->vehicle_type,
                            "parking_space_name" => $result->name,
                            "duration" => $result->end_time - $result->start_time
                        ];
                        $this->send_json_200($payment_data);
                    }
                } else // invalid payment id
                {
                    $this->send_json_400("Invalid payment ID");
                }
            }
        }
    }


    // close the payment if user payment is successfull 
    // will not return anything as a json
    // reason is this will call automatically by the payhere gateway
    // this will only update the database if the payment is successfull => so call again payment/view after this 
    public function notify()
    {
        $merchant_id         = $_POST['merchant_id'];
        $order_id            = $_POST['order_id'];
        $payhere_amount      = $_POST['payhere_amount'];
        $payhere_currency    = $_POST['payhere_currency'];
        $status_code         = $_POST['status_code'];
        $md5sig              = $_POST['md5sig'];
        $time_stamp          = time();
        $merchant_secret = 'XXXXXXXXXXXXX'; // Replace with your Merchant Secret
        $local_md5sig = strtoupper(
            md5(
                $merchant_id .
                    $order_id .
                    $payhere_amount .
                    $payhere_currency .
                    $status_code .
                    strtoupper(md5($merchant_secret))
            )
        );

        if (($local_md5sig === $md5sig) and ($status_code == 2)) {

            $token_data  = $this->verify_token_for_driver_from_para($merchant_id);

            if ($token_data !== 400 and $token_data !== 404) {
                $payment_data = [
                    "payment_id" => $this->decrypt_id($order_id),
                    "driver_id" => $token_data["user_id"],
                    "payment_method" => "Card",
                    "time_stamp" => $time_stamp
                ];

                $this->payment_model->close_payment($payment_data);
            }
        }
    }

    // View payment details of the given session in the officer mobile app
    public function view_payment_details_of_session($parking_id, $encrypted_session_id) {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            if($assigned_parking === $parking_id) { //parking_id is similar to the assigned parking

                $session_id = $this->decrypt_session_id($encrypted_session_id);

                $payment_session_exists = $this->payment_model->is_payment_session_exists($session_id);

                
                if (!$payment_session_exists) {
                    $result = [
                        "response_code" => "204",
                        "message" => "No payment details for the session"
                    ];
                
                    $this->send_json_404($result);
                } else {
                    $payment_details = $this->payment_model->get_payment_details($session_id);

                    $uppercase_vehicle_type = strtoupper($payment_details->vehicle_type);

                    // Calculate duration between start_time and end_time
                    $start_timestamp = $payment_details->start_time;
                    $end_timestamp = $payment_details->end_time;

                    $duration = $end_timestamp - $start_timestamp;

                    // Convert duration to hours and minutes
                    $hours = floor($duration / 3600);
                    $minutes = floor(($duration % 3600) / 60);

                    // Format the duration
                    $formatted_duration = sprintf('%02d H %02d Min', $hours, $minutes);

                    $formatted_amount = 'Rs. ' . number_format($payment_details->amount, 2);

                    if($payment_details) {
                        $result = [
                            "response_code" => "800",
                            "vehicle_number" => $payment_details->vehicle_number, 
                            "vehicle_type" => $uppercase_vehicle_type, 
                            "start_time" => date('h:i A',$start_timestamp), 
                            "end_time" => date('h:i A', $end_timestamp),
                            "time_went" => $formatted_duration, 
                            "amount" => $formatted_amount 
                        ];

                        $this->send_json_200($result);
                    } else {
                        $result = [
                            "response_code" => "204",
                            "message" => "No payment details for the session"
                        ];
                    
                        $this->send_json_404($result);
                    }                 
                }

            } else { //parking_id is not similar to the assigned parking
                
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
