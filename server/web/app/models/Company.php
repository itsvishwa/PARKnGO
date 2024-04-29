<?php
class Company
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }

  // Register company
  public function register($data)
  {
    $this->db->query('INSERT INTO company (name, address, email, phone_number, documents, password) VALUES (:company_name, :company_address, :company_email, :phone_number, :documents, :password)');
    // Bind values
    $this->db->bind(':company_name', $data['company_name']);
    $this->db->bind(':company_address', $data['company_address']);
    $this->db->bind(':company_email', $data['company_email']);
    $this->db->bind(':phone_number', $data['phone_number']);
    $this->db->bind(':documents', $data['file_upload']);
    $this->db->bind(':password', $data['password']);

    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  // Login company
  public function login($email, $password)
  {


    $this->db->query('SELECT * FROM company WHERE email = :email');
    $this->db->bind(':email', $email);

    $row = $this->db->single();
    $hashed_password = $row->password;
    if (password_verify($password, $hashed_password)) {
      return $row;
    } else {
      return false;
    }
  }

  // Find company by email
  public function findCompanyByEmail($email)
  {
    $this->db->query('SELECT * FROM company WHERE email = :email');
    $this->db->bind(':email', $email);

    $row = $this->db->single();

    // Check roww
    if ($this->db->rowCount() > 0) {
      return true;
    } else {
      return false;
    }
  }

  //get the latest updates from data base
  public function getLatestUpdates($company_id)
  {
    $this->db->query(
      'SELECT 
                        ps.vehicle_number, 
                        ps.start_time, 
                        ps.end_time, 
                        p.amount, 
                        p.payment_method
                      FROM 
                        parking_session ps
                      JOIN 
                        payment p ON ps._id = p._id
                      JOIN 
                        parking_spaces pspace ON ps.parking_id = pspace._id
                      JOIN 
                        company c ON pspace.company_id = c._id
                      WHERE 
                        c._id = :company_id
                      ORDER BY 
                        p.time_stamp DESC
                      LIMIT 10;'
    );
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }

  //get all the parking spaces that related to that company
  public function getParkingSpaces($company_id)
  {
    $this->db->query('SELECT ps._id ,
    ps.name ,
    ps.address,
    ps.no_of_slots,
    ps.is_public,
    ps.closed_start_time,
    ps.closed_end_time,
    ps.latitude,
    ps.longitude,
    ps.avg_star_count,
    ps.total_review_count,
    ps.company_id FROM parking_spaces ps WHERE company_id = :company_id');
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }

  public function getParkingSpaceStatusDetails($company_id)
  {
    $this->db->query('SELECT 
      pss._id AS status_id,
      pss.vehicle_type AS vehicle_type,
      pss.free_slots AS free_slots,
      pss.total_slots AS total_slots,
      pss.rate AS rate,
      ps._id AS parking_id,
      ps.company_id AS company_id
    FROM 
      parking_space_status pss
    LEFT JOIN 
      parking_spaces ps ON pss.parking_id = ps._id
    WHERE 
      ps.company_id = :company_id;
');
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }

  // get parking spaces details
  // public function getParkingSpacesDetails()
  // {
  //   $this->db->query("SELECT 
  //     ps.name AS parking_name,
  //     ps.address AS parking_address,
  //     ps.no_of_slots AS total_slots,
  //     ps.is_public,
  //     -- GROUP_CONCAT(CONCAT(ps.vehicle_type, ': ', COUNT(ps.vehicle_type))) AS vehicle_types_slots,
  //     IFNULL(MAX(pss.session_id), 0) AS session_id,
  //     IFNULL(SUM(pmt.amount), 0) AS today_earnings,
  //     po.officer_id,
  //     CONCAT(po.first_name, ' ', po.last_name) AS officer_name
  // FROM 
  //     parking_spaces ps
  // LEFT JOIN 
  //     parking_slot ps2 ON ps.company_id = ps2.company_id AND ps.parking_id = ps2.parking_id
  // LEFT JOIN 
  //     parking_session pss ON ps.parking_id = pss.parking_id
  // LEFT JOIN 
  //     payment pmt ON pss.session_id = pmt.session_id
  // LEFT JOIN 
  //     parking_officer po ON ps.company_id = po.company_id AND ps.parking_id = po.parking_id
  // WHERE 
  //     ps.company_id = :company_id
  // GROUP BY 
  //     ps.parking_id
  // ");
  //   $this->db->bind(':company_id', $_SESSION['user_id']);
  //   $row = $this->db->resultSet();
  //   return $row;
  // }

  // public function getParkingSpacesDetails()
  // {
  //   $this->db->query("SELECT name AS parking_name, address AS parking_address, no_of_slots AS total_slots, is_public FROM parking_spaces WHERE company_id = :company_id");
  //   $this->db->bind(':company_id', $_SESSION['user_id']);
  //   $row = $this->db->resultSet();
  //   return $row;
  // }

  public function getCompanySuspendDetails($company_id)
  {
    $this->db->query('SELECT 
      cs.time_stamp AS start_time,
      cs.time_stamp + cs.duration AS end_time,
      cs.message
    FROM 
      company_suspend cs
    WHERE 
      cs.company_id = :company_id
    ORDER BY
      cs.time_stamp DESC
    LIMIT 1;');
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->single();
    return $row;
  }

  public function updateRecoveryToken($data)
  {
    $this->db->query('UPDATE company SET token = :token WHERE email = :email');
    $this->db->bind(':token', $data['token']);
    $this->db->bind(':email', $data['email']);

    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  public function updatePassword($data)
  {
    $this->db->query('UPDATE company SET password = :password WHERE token = :token AND email = :email');
    $this->db->bind(':password', $data['password']);
    $this->db->bind(':token', $data['token']);
    $this->db->bind(':email', $data['email']);

    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  public function isApproved($company_id)
  {
    $this->db->query('SELECT is_approved, is_reviewd, review_message FROM company WHERE _id = :company_id LIMIT 1');
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->single();
    return $row;
  }
}
