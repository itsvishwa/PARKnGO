<?php
class QR extends Controller
{
    public $driver_qr_model;

    public function __construct()
    {
        $this->driver_qr_model = $this->model("DriverQRModel");
    }

    public function get_qr_code($selected_vehicle_number)
    {
        $token_data = $this->verify_token_for_drivers();
        if ($token_data === 400) {
            $this->send_json_400("Invalid Token");
        } elseif ($token_data === 404) {
            $this->send_json_404("Token Not Found");
        } else {
            $qr_id = $this->driver_qr_model->get_qr_id($token_data["user_id"], $selected_vehicle_number);
            if ($qr_id === false) // no records for a giver driver id and selected vehicle number
            {
                $this->send_json_400("E_QR_5023");
            } else {
                $time_stamp = time();

                //update timestamp
                $this->driver_qr_model->update_time_stamp($time_stamp, $qr_id);

                //encode qr_id
                $qr_id = $this->encrypt_id($qr_id);

                $this->send_json_200($qr_id);
            }
        }
    }
}
