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

  public function getUpdates($company_id)
  {
    $this->db->query("SELECT
                        p.amount,
                        p.payment_method,
                        ps.start_time,
                        ps.end_time,
                        ps.vehicle_number,
                        ps.vehicle_type,
                        ps.parking_id,
                        pspace.name,
                        po.officer_id,
                        po.first_name,
                        po.last_name
                      FROM
                          payment p
                      LEFT JOIN
                          parking_session ps ON p.session_id = ps._id
                      LEFT JOIN
                          parking_spaces pspace ON ps.parking_id = pspace._id
                      LEFT JOIN
                          parking_officer po ON ps.parking_id = po.parking_id
                      WHERE pspace.company_id = :company_id 
                      ORDER BY p.time_stamp DESC");
    $this->db->bind(':company_id', $company_id);
    $rows = $this->db->resultSet();
    return $rows;
  }

  public function getRevenue($company_id)
  {
    $thirtyDaysAgo = strtotime('-30 days');

    $this->db->query(
      'SELECT payment._id, payment.time_stamp, payment.amount
        FROM payment
        LEFT JOIN parking_session ON payment.session_id = parking_session._id
        LEFT JOIN parking_spaces ON parking_session.parking_id = parking_spaces._id
        WHERE parking_spaces.company_id = :company_id
        AND payment.time_stamp >= :thirty_days_ago
        ORDER BY payment.time_stamp DESC'
    );

    $this->db->bind(':company_id', $company_id);
    $this->db->bind(':thirty_days_ago', $thirtyDaysAgo);

    $row = $this->db->resultSet();
    return $row;
  }
}
