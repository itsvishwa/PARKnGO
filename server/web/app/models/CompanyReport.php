<?php
class CompanyReport
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }

  // Register company
  public function parkingOfficerRevenue()
  {
    $this->db->query("SELECT
                        po._id AS officer_id,
                        CONCAT(po.first_name, ' ', po.last_name) AS officer_name,
                        COUNT(DISTINCT ps._id) AS number_of_sessions,
                        COALESCE(SUM(p.amount), 0) AS total_earnings
                      FROM
                        (SELECT DISTINCT CONCAT(oa.officer_id, '-', oa.session_id) AS officer_session_id, oa.officer_id, oa.session_id
                         FROM officer_activity oa) oa_unique
                      INNER JOIN
                        parking_session ps ON oa_unique.session_id = ps._id
                      INNER JOIN
                        parking_officer po ON oa_unique.officer_id = po._id
                      LEFT JOIN
                        payment p ON ps._id = p.session_id
                      WHERE
                        FROM_UNIXTIME(p.time_stamp) >= DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY)
                      GROUP BY
                        po._id, officer_name
                      ORDER BY
                        total_earnings DESC");
    $row = $this->db->resultSet();
    return $row;
  }

  public function parkingOfficerForceStoppedSession()
  {
    $this->db->query("SELECT
                        po._id AS officer_id,
                        CONCAT(po.first_name, ' ', po.last_name) AS officer_name,
                        COUNT(DISTINCT CASE WHEN ps.is_force_end = 1 THEN oa.session_id END) AS number_of_sessions_is_force_end
                      FROM
                        officer_activity oa
                      INNER JOIN
                        parking_officer po ON oa.officer_id = po._id
                      INNER JOIN
                        parking_session ps ON oa.session_id = ps._id
                      WHERE
                        oa.time_stamp >= UNIX_TIMESTAMP(DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY))
                      AND ps.is_force_end = 1
                      GROUP BY
                        po._id, officer_name
                      ORDER BY
                        number_of_sessions_is_force_end DESC;");
    $row = $this->db->resultSet();
    return $row;
  }

  public function parkingSpaceRevenue($company_id)
  {
    $this->db->query("SELECT
                        ps._id AS parking_id,
                        ps.name AS parking_name,
                        COUNT(DISTINCT ps._id) AS number_of_sessions,
                        COALESCE(SUM(p.amount), 0) AS total_earnings
                      FROM
                        parking_spaces ps
                      LEFT JOIN
                        parking_session pss ON ps._id = pss.parking_id
                      LEFT JOIN
                        payment p ON pss._id = p.session_id
                      WHERE
                        FROM_UNIXTIME(p.time_stamp) >= DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY)
                        AND ps.company_id = :company_id
                      GROUP BY
                        ps._id, ps.name
                      ORDER BY
                        total_earnings DESC;");
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }

  public function parkingSpaceForceStoppedSession($company_id)
  {
    $this->db->query("SELECT
                        ps._id AS parking_id,
                        ps.name AS parking_name,
                        COUNT(*) AS number_of_sessions
                      FROM
                        parking_spaces ps
                      INNER JOIN
                        parking_session pss ON ps._id = pss.parking_id
                      WHERE
                        pss.is_force_end = 1
                        AND FROM_UNIXTIME(pss.start_time) >= DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY)
                        AND ps.company_id = :company_id
                      GROUP BY
                        ps._id, ps.name;");
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }

  public function dateWiseParkingSpaceRevenue($company_id)
  {
    $this->db->query("SELECT
                        DATE(FROM_UNIXTIME(p.time_stamp)) AS date,
                        COUNT(DISTINCT pss._id) AS number_of_sessions,
                        COALESCE(SUM(p.amount), 0) AS total_earnings
                      FROM
                        parking_spaces ps
                      LEFT JOIN
                        parking_session pss ON ps._id = pss.parking_id
                      LEFT JOIN
                        payment p ON pss._id = p.session_id
                      WHERE
                        FROM_UNIXTIME(p.time_stamp) >= DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY)
                        AND ps.company_id = :company_id
                      GROUP BY
                        date
                      ORDER BY
                        date;");
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }

  public function dateWiseParkingSpaceForceStoppedSession($company_id)
  {
    $this->db->query("SELECT
                        DATE(FROM_UNIXTIME(pss.start_time)) AS date,
                        COUNT(*) AS number_of_sessions
                      FROM
                        parking_spaces ps
                      INNER JOIN
                        parking_session pss ON ps._id = pss.parking_id
                      WHERE
                        pss.is_force_end = 1
                        AND FROM_UNIXTIME(pss.start_time) >= DATE_SUB(CURRENT_DATE(), INTERVAL 30 DAY)
                        AND ps.company_id = :company_id
                      GROUP BY
                        date
                      ORDER BY
                        date;");
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }

  // public function getReviewsBasedOnParkingSpace($company_id)
  // {
  //   $this->db->query('SELECT
  //                       rv.parking_id,
  //                       ps.name AS parking_name,
  //                       FROM_UNIXTIME(rv.time_stamp) AS time_stamp,
  //                       rv.no_of_stars,
  //                       rv.content
  //                     FROM
  //                       review rv
  //                     INNER JOIN
  //                       parking_spaces ps ON rv.parking_id = ps._id
  //                     WHERE
  //                       rv.time_stamp >= UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 30 DAY))
  //                       AND ps.company_id = :company_id;');
  //   $this->db->bind(':company_id', $company_id);
  //   $row = $this->db->resultSet();
  //   return $row;
  // }

  public function getReviewsBasedOnParkingSpace()
  {
    $this->db->query('SELECT
        rv.parking_id,
        ps.name AS parking_name,
        AVG(rv.no_of_stars) AS avg_no_of_stars
    FROM
        review rv
    INNER JOIN
        parking_spaces ps ON rv.parking_id = ps._id
    WHERE
        rv.time_stamp >= UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 30 DAY))
    GROUP BY
        rv.parking_id, ps.name;');

    $row = $this->db->resultSet();
    return $row;
  }

  public function getReviewsBasedOnDate($company_id)
  {
    $this->db->query('SELECT
                        FROM_UNIXTIME(rv.time_stamp) AS time_stamp,
                        rv.parking_id,
                        ps.name AS parking_name,
                        rv.no_of_stars,
                        rv.content
                      FROM
                        review rv
                      INNER JOIN
                        parking_spaces ps ON rv.parking_id = ps._id
                      WHERE
                        rv.time_stamp >= UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 30 DAY))
                        AND ps.company_id = :company_id;');
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }
}
