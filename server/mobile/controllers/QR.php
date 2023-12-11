<?php


class QR extends Controller
{

    private $payment_model;

    public function __construct()
    {
        $this->payment_model = $this->model("PaymentModel");
    }

    public function get_qr($payment_id)
    {
        $encoded_payment_id = $this->encrypt_id($payment_id);
        $this->send_json_200($encoded_payment_id);

        // should implement rest 
        // should have generate the qr 
        // and display with other data which give by payment/view
    }
}
