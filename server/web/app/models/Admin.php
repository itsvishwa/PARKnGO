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

  // Function to get the total revenue from the database
  public function getTotalRevenue() {
        // Perform a query to get the total revenue from the 'payment' table
        $query = "SELECT SUM(amount) AS total_revenue FROM payment";

        // Execute the query and fetch the result
        $this->db->query($query);
        $row = $this->db->single();

        // Check if the row is retrieved properly and the property exists
    if ($row && property_exists($row, 'total_revenue')) {
      // Return the total revenue
      return $row->total_revenue;
  } else {
      // Return a default value or false if the property is not found
      return 5; // You can change this default value as needed
  }
        
  }
  // Function to get pending company applications
  public function getPendingCompanyApplications()
  {
    $this->db->query('SELECT name, registered_time_stamp , address FROM company WHERE is_approved = 0 AND is_reviewd = 0');
    $rows = $this->db->resultSet(); // Assuming this function returns multiple rows

    

    return $rows;
  }

  // Function to get approved and rejected applications

  /***************************************************************************** */

  // Function to get pending, approved, or rejected company applications
public function getCompanyApplications($isApproved, $isReviewed)
{
    $this->db->query('SELECT name, _id , registered_time_stamp, address FROM company WHERE is_approved = :approved AND is_reviewd = :reviewed');
    $this->db->bind(':approved', $isApproved);
    $this->db->bind(':reviewed', $isReviewed);
    $rows = $this->db->resultSet(); // Assuming this function returns multiple rows

    return $rows;
}

//Function to get approved companies

public function getApprovedCompanyApplications()
  {
    $this->db->query('SELECT name, _id , email , phone_number , address FROM company WHERE is_approved = 1 AND is_reviewd = 1');
    $rows = $this->db->resultSet(); // Assuming this function returns multiple rows

    return $rows;
  }

/******************************** */

  // Function to get the parking officers count for a specific company
  public function getParkingOfficersCountForCompany($companyId)
  {
    // Query to count parking officers for the specified company
    $this->db->query('SELECT COUNT(po.id) AS parking_officers_count
    FROM parking_officer po
    WHERE po.company_id = :company_id
');
    $this->db->bind(':company_id', $companyId);
    $row = $this->db->single();

    return $row->parking_officers_count;
  }



  // Function to get pending company applications total count
public function getPendingCompanyApplicationsWithCount()
{
    
    // Get the count of pending applications
    $this->db->query('SELECT COUNT(*) as totalPendingApplications FROM company WHERE is_approved = 0 AND is_reviewd = 0');
    $countResult = $this->db->single();

    $totalCount = $countResult->totalPendingApplications;

    return [
        
        'totalPendingApplications' => $totalCount
    ];
}

  // Function to get suspend company count
  public function getSuspendCompanyCount()
  {
      
      // Get the count of pending applications
      $this->db->query('SELECT COUNT(*) as totalSuspendApplications FROM company_suspend');
      $countResult = $this->db->single();
  
      $totalSuspendCount = $countResult->totalSuspendApplications;
  
      return [
          
          'totalSuspendApplications' => $totalSuspendCount
      ];
  }

    // Method to update application status based on ID
    public function updateApplicationStatus($applicationId, $newStatus) {
      $this->db->query('UPDATE company SET status = :newStatus WHERE id = :applicationId');
      $this->db->bind(':newStatus', $newStatus);
      $this->db->bind(':applicationId', $applicationId);
      
      if ($this->db->execute()) {
          return true; // Return true if the update was successful
      } else {
          return false; // Return false if there was an error in the update
      }
  }



/*// Modify the function to accept the entry_id as a parameter
public function deleteEntry($_id) {
  $this->db->query('DELETE FROM company WHERE _id = :_id');
  // Bind values
  $this->db->bind(':_id', $_id);
  // Execute
  if ($this->db->execute()) {
      return true;
  } else {
      return false;
  }
}*/

// Function to delete a company by ID
/*public function deleteCompany($_id)
{
    try {
        $query = "DELETE FROM company WHERE _id = :_id";
        $this->db->query($query);
        $this->db->bind(':_id', $_id);
        $this->db->execute();

        // Check if any row was affected
        $rowCount = $this->db->rowCount();
        if ($rowCount > 0) {
            return "Company with ID $_id has been deleted successfully.";
        } else {
            return "No company found with ID _id.";
        }
    } catch (PDOException $e) {
        return "Error: " . $e->getMessage();
    }
}*/
public function getCompanyById($id){
  $this->db->query('SELECT * FROM company WHERE _id = :id');
  $this->db->bind(':id', $id);

  $row = $this->db->single();

  return $row;
}

public function deleteCompany($id){
  $this->db->query('DELETE FROM company WHERE _id = :id');
  // Bind values
  $this->db->bind(':id', $id);
  // Execute
  if($this->db->execute()){
    return true;
  } else {
    return false;
  }
}

}

