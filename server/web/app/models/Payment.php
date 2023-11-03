<?php
class Payment
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }


  // Get all monthly earned amount from payment table
  public function getMonthlyEarnedAmount()
  {
    $this->db->query('SELECT SUM(amount) AS monthlyEarned FROM payment WHERE MONTH(time_stamp) = MONTH(CURDATE()) AND YEAR(time_stamp) = YEAR(CURDATE());');
    $monthlyEarned = $this->db->calcData();
    return $monthlyEarned['monthlyEarned'];
  }

  // Get today total earned amount
  public function getTodayEarnedAmount()
  {
    $this->db->query('SELECT SUM(amount) AS todayEarned FROM payment WHERE DATE(time_stamp) = CURDATE();');
    $todayEarned = $this->db->calcData();
    return $todayEarned['todayEarned'];
  }

  // get number of users
  public function getNumberOfUsers()
  {
    $this->db->query('SELECT COUNT(*) AS totalUsers FROM payment');
    $result = $this->db->calcData();
    return $result['totalUsers'];
  }
}
