<?php
class Admin
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }
  // Login admin
  public function login($email, $password)
  {


    $this->db->query('SELECT * FROM admin WHERE email = :email');
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
  public function findAdminByEmail($email)
  {
    $this->db->query('SELECT * FROM admin WHERE email = :email');
    $this->db->bind(':email', $email);

    $row = $this->db->single();

    // Check roww
    if ($this->db->rowCount() > 0) {
      return true;
    } else {
      return false;
    }
  }
  
  //********************************************************************* */
  // Function to get the number of users from the database
  public function getUsersCount()
  {
    $this->db->query('SELECT COUNT(*) as user_count FROM driver');
    $row = $this->db->single();
    return $row->user_count;
  }

  // Function to get the number of parking officers from the database
  public function getParkingOfficersCount()
  {
    // Query to count parking officers from the database
    $this->db->query('SELECT COUNT(*) as officer_count FROM parking_officer');
    $row = $this->db->single();
    return $row->officer_count;
  }

  // Function to get the number of companies from the database
  public function getCompaniesCount()
  {
    // Query to count companies from the database
    $this->db->query('SELECT COUNT(*) as company_count FROM company');
    $row = $this->db->single();
    return $row->company_count;
  }
}
