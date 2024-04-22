<?php


class Payment extends Controller
{

    private $payment_model;
    private $officer_model;
    private $parking_space_model;

    public function __construct()
    {
        $this->payment_model = $this->model("PaymentModel");
        $this->officer_model = $this->model("OfficerModel");
        $this->parking_space_model = $this->model("ParkingSpaceModel");
    }


    // driver mobile - used to view open payment details for given payment id
    public function view_payment()
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("ERR_IT");
        } elseif ($token_data === 404) {
            $this->send_json_404("ERR_TNF");
        } else // valid token 
        {
            $encoded_string = $_SERVER['HTTP_X_ENCODED_DATA']; // encoded payment_id
            $payment_id = $this->decrypt_id($encoded_string); // decode the string 

            if ($payment_id === false) // invalid encoded_string
            {
                $this->send_json_400("PAY_IDATA");
            } else // code decoded sucessfully
            {
                if ($this->payment_model->is_payment_session_id_exist($payment_id)) // valid payment id
                {

                    if ($this->payment_model->is_payment_session_ended($payment_id)) // payment session has been closed
                    {
                        $this->send_json_200("PAY_CL");
                    } else  // payment session still open
                    {
                        $result = $this->payment_model->get_all_data($payment_id);
                        $payment_data = [
                            "payment_id" => $encoded_string,
                            "amount" => $result->amount,
                            "start_time" => implode(" | ", $this->format_time($result->start_time)),
                            "end_time" => implode(" | ", $this->format_time($result->end_time)),
                            "vehicle_number" => $this->format_vehicle_number($result->vehicle_number),
                            "vehicle_type" => $result->vehicle_type,
                            "parking_space_name" => $result->name,
                            "duration" => $this->calculate_time($result->end_time - $result->start_time)
                        ];
                        $this->send_json_200($payment_data);
                    }
                } else // invalid payment id
                {
                    $this->send_json_400("PAY_IPID");
                }
            }
        }
    }


    // driver mobile
    // close the payment if user payment is successfull 
    // will not return anything as a json
    // reason is this will call automatically by the payhere gateway
    // this will only update the database if the payment is successfull => so call again payment/view after this 
    public function notify()
    {
        $order_id            = $_POST['order_id'];
        $status_code         = $_POST['status_code'];

        if (($status_code == 2)) // means success payment 
        {
            $this->payment_model->close_card_payment_by_id($this->decrypt_id($order_id));
        }
    }



    public function cash()
    {
        $token_data = $this->verify_token_for_officers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // token is valid
        {
            $assigned_parking = $this->officer_model->get_parking_id($token_data["user_id"]);

            $encrypted_parking_id = trim($_POST["parking_id"]);
            // Decrypt the parking_id
            $parking_id = $this->decrypt_id($encrypted_parking_id);

            if ($assigned_parking === $parking_id) { //parking_id is similar to the assigned parking
                $encrypted_payment_id = trim($_POST["payment_id"]);

                $payment_id = $this->decrypt_id($encrypted_payment_id);

                $is_payment_session = $this->payment_model->is_payment_session_id_exist($payment_id);

                if (!$is_payment_session) {
                    $result = [
                        "response_code" => "204",
                        "message" => "No payment session found"
                    ];

                    $this->send_json_404($result);
                } else {
                    $is_ended_payment_session = $this->payment_model->is_payment_session_ended($payment_id);

                    if ($is_ended_payment_session) {
                        $result = [
                            "response_code" => "409",
                            "message" => "This payment session is already ended"
                        ];

                        $this->send_json_404($result);
                    } else {
                        $time_stamp = time();

                        $payment_data = [
                            "payment_id" => $payment_id,
                            "payment_method" => "Cash",
                            "time_stamp" => $time_stamp
                        ];

                        // update the payment table
                        $this->payment_model->close_cash_payment($payment_data);

                        $result = [
                            "response_code" => "800",
                            "message" => "Payment is successfully closed"
                        ];

                        $this->send_json_200($result);
                    }
                }
            } else {  //parking_id is not similar to the assigned parking
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
