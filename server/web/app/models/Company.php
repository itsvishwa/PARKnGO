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
    $this->db->query('INSERT INTO company (name, address, email, phone_number, password) VALUES (:company_name, :company_address, :company_email, :phone_number, :password)');
    // Bind values
    $this->db->bind(':company_name', $data['company_name']);
    $this->db->bind(':company_address', $data['company_address']);
    $this->db->bind(':company_email', $data['company_email']);
    $this->db->bind(':phone_number', $data['phone_number']);
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
}
