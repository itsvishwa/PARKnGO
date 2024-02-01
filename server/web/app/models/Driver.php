<?php
class Driver
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }


  public function getNumberOfDrivers()
  {
    $this->db->query('SELECT COUNT(*) AS totalDrivers FROM driver');
    $result = $this->db->calcData();
    return $result['totalDrivers'];
  }
}
