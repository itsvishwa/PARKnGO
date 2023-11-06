<?php


class Driver extends Controller
{

        public $driver_model;
        public function __construct()
        {
                $driver_model = $this->model("DriverModel");
        }

        // view driver's profile
        public function view_profile()
        {
        }
}
