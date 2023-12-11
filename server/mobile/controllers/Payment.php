<?php


class Payment extends Controller
{

    private $payment_model;

    public function __construct()
    {
        $this->payment_model = $this->model("PaymentModel");
    }

    // TODO :: PAYMENT ID SHOULD BE ENCRYPTED
    public function view($payment_id)
    {
        $token_data = $this->verify_token_for_drivers();

        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else // valid token 
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
