<?php
class Officer
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }

  // Register Parking Officer
  public function register($data)
  {
    $this->db->query('INSERT INTO parking_officer (nic, phone_number, first_name, last_name, company_id) VALUES (:nic, :phone_number, :first_name, :last_name, :company_id)');
    // Bind values
    $this->db->bind(':nic', $data['nic']);
    $this->db->bind(':phone_number', $data['phone_number']);
    $this->db->bind(':first_name', $data['first_name']);
    $this->db->bind(':last_name', $data['last_name']);
    $this->db->bind(':company_id', $data['company_id']);
    //$this->db->bind(':officer_id', $data['officer_id']);
    //$this->db->bind(':parking_id', $data['parking_id']);

    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  public function update($data)
  {
    $this->db->query('UPDATE parking_officer SET nic = :nic, phone_number = :phone_number, first_name = :first_name, last_name = :last_name WHERE officer_id = :officer_id AND company_id = :company_id');

    // Bind values
    $this->db->bind(':nic', $data['nic']);
    $this->db->bind(':phone_number', $data['phone_number']);
    $this->db->bind(':first_name', $data['first_name']);
    $this->db->bind(':last_name', $data['last_name']);
    $this->db->bind(':officer_id', $data['officer_id']);
    $this->db->bind(':company_id', $data['company_id']);

    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }



  // Find Parking Officer by Phone number
  public function findOfficerByPhoneNumber($phone_number)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE phone_number = :phone_number');
    $this->db->bind(':phone_number', $phone_number);

    $row = $this->db->single();

    // Check roww
    if ($this->db->rowCount() > 0) {
      return true;
    } else {
      return false;
    }
  }

  public function getOfficerDetailsUsingPhoneNumber($phone_number)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE phone_number = :phone_number');
    $this->db->bind(':phone_number', $phone_number);

    $row = $this->db->single();

    return $row;
  }
  public function getAllOfficersDetails()
  {
    $this->db->query('SELECT * FROM parking_officer');
    $results = $this->db->resultSet();
    return $results;
  }
}
