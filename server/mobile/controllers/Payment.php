<?php


class Payment extends Controller
{

    private $payment_model;

    public function __construct()
    {
        $this->payment_model = $this->model("PaymentModel");
    }


    // driver mob - used to view open payment details for given payment id
    public function view_payment()
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("ERR_PAY_IT");
        } elseif ($token_data === 404) {
            $this->send_json_404("ERR_PAY_TNF");
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


    // driver mob
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


    // calculate time
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
