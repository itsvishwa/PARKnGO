<?php
class Payment
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }


  // Get all monthly earned amount from payment table
  public function getMonthlyEarnedAmount($company_id)
  {
    $this->db->query('SELECT 
                      SUM(p.amount) AS monthlyEarned
                        FROM 
                          payment p
                        JOIN 
                          parking_session ps ON p.session_id = ps._id
                        JOIN 
                          parking_spaces pspace ON ps.parking_id = pspace._id
                        JOIN 
                          company c ON pspace.company_id = c._id
                        WHERE 
                          MONTH(p.time_stamp) = MONTH(CURDATE()) 
                          AND YEAR(p.time_stamp) = YEAR(CURDATE())
                          AND c._id = :company_id;
    ');
    $this->db->bind(':company_id', $company_id);
    $monthlyEarned = $this->db->calcData();
    return $monthlyEarned['monthlyEarned'];
  }

  // Get today total earned amount
  public function getTodayEarnedAmount($company_id)
  {
    $this->db->query('SELECT 
                        SUM(p.amount) AS todayEarned
                      FROM 
                        payment p
                      JOIN 
                        parking_session ps ON p.session_id = ps._id
                      JOIN 
                        parking_spaces pspace ON ps.parking_id = pspace._id
                      JOIN 
                        company c ON pspace.company_id = c._id
                      WHERE 
                        DATE(p.time_stamp) = CURDATE()
                        AND c._id = :company_id;
    ');
    $this->db->bind(':company_id', $company_id);
    $todayEarned = $this->db->calcData();
    return $todayEarned['todayEarned'];
  }

  // get number of users
  public function getNumberOfUsers($company_id)
  {
    $this->db->query(
      'SELECT 
                        COUNT(*) AS totalUsers
                      FROM 
                        payment p
                      JOIN 
                        parking_session ps ON p.session_id = ps._id
                      JOIN 
                        parking_spaces pspace ON ps.parking_id = pspace._id
                      JOIN 
                        company c ON pspace.company_id = c._id
                      WHERE
                        MONTH(p.time_stamp) = MONTH(CURDATE()) 
                        AND YEAR(p.time_stamp) = YEAR(CURDATE())
                        AND c._id = :company_id;'
    );
    $this->db->bind(':company_id', $company_id);
    $result = $this->db->calcData();
    return $result['totalUsers'];
  }
}