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
  public function findOfficerByPhoneNumber($mobile_number)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE mobile_number = :mobile_number');
    $this->db->bind(':mobile_number', $mobile_number);

    $row = $this->db->single();

    // Check roww
    if ($this->db->rowCount() > 0) {
      return true;
    } else {
      return false;
    }
  }

  public function getOfficerDetailsUsingPhoneNumber($mobile_number)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE mobile_number = :mobile_number');
    $this->db->bind(':mobile_number', $mobile_number);

    $row = $this->db->single();

    return $row;
  }
  public function getAllOfficersDetails($company_id)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE company_id = :company_id');
    $this->db->bind(':company_id', $company_id);
    $results = $this->db->resultSet();
    return $results;
  }

  public function getOfficerDetails($company_id)
  {
    $this->db->query("SELECT CONCAT(po.first_name, ' ', po.last_name) AS officer_name, 
      IFNULL(CONCAT(ps._id, ' ', ps.name), 'Not Assigned') AS parking_details
      FROM parking_officer po
      LEFT JOIN parking_spaces ps ON po._id = ps._id 
      WHERE po.company_id = :company_id
      LIMIT 10");
    $this->db->bind(':company_id', $company_id);
    $results = $this->db->resultSet();
    return $results;
  }

  //get resently added officer
  public function getRecentlyAddedOfficer()
  {
    $this->db->query("SELECT * FROM parking_officer ORDER BY _id DESC LIMIT 1");
    $row = $this->db->single();
    return $row;
  }
}
