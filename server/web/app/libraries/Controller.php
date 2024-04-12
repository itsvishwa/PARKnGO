<?php
/*
   * Base Controller
   * Loads the models and views
   */

class Controller
{
  // Load model
  public function model($model)
  {
    // Require model file
    require_once '../app/models/' . $model . '.php';

    // Instantiate model
    return new $model();
  }

  // Load view
  public function view($view, $data = [])
  {
    // Check for view file
    if (file_exists('../app/views/' . $view . '.php')) {
      require_once '../app/views/' . $view . '.php';
    } else {
      // View does not exist
      die('View does not exist');
    }
  }

  public function format_vehicle_number($vehicle_number)
  {
    $data = explode("#", $vehicle_number);

    $temp1 = "";
    if ($data[1] === "NA") {
      $temp1 = "";
    } else if ($data[1] === "SRI") {
      $temp1 = "ශ්‍රී";
    } else {
      $temp1 = "-";
    }

    $temp2 = "";
    if ($data[3] === "NA") {
      $temp2 = "";
    } else {
      $temp2 = $data[3];
    }

    $result = $data[0] . $temp1 . $data[2] . $temp2;

    return $result;
  }

  // convert the category to vehicle types
  public function convert_to_vehicle_type($category)
  {
    $result = "";

    if ($category === "A") {
      $result = "Cak|Tuktuk|Mini Van";
    } else if ($category === "B") {
      $result = "Bicycle";
    } else if ($category === "C") {
      $result = "Van|Lorry|Mini Bus";
    } else {
      $result = "Long Vehicles";
    }

    return $result;
  }
}
