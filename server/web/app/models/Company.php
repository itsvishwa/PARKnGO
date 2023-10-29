<?php
class Company
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
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
}
