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
  public function getTotalRevenue()
  {
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
    $this->db->query('SELECT _id ,name, registered_time_stamp , address , documents FROM company WHERE is_approved = 0 AND is_reviewd = 0');
    $rows = $this->db->resultSet(); // Assuming this function returns multiple rows



    return $rows;
  }

  // Function to get approved and rejected applications

  /***************************************************************************** */

  // Function to get pending, approved, or rejected company applications
  public function getCompanyApplications($isApproved, $isReviewed)
  {
    $this->db->query('SELECT name, _id , registered_time_stamp, review_message , documents ,address FROM company WHERE is_approved = :approved AND is_reviewd = :reviewed');
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
    $this->db->query('SELECT COUNT(po._id) AS parking_officers_count
    FROM parking_officer po
    WHERE po.company_id = :company_id
    ');
    $this->db->bind(':company_id', $companyId);
    $row = $this->db->single();

    return $row->parking_officers_count;
  }


  // Function to get the parking slots count for a specific company
  public function getParkingSlotsCountForCompany($companyId)
  {
    // Query to count parking slots for the specified company
    $this->db->query('SELECT COUNT(ps.no_of_slots) AS parking_slots_count 
      FROM parking_spaces ps
      WHERE ps.company_id = :company_id
      ');
    $this->db->bind(':company_id', $companyId);
    $row = $this->db->single();

    return $row->parking_slots_count;
  }

  // Function to check if there is at least one public parking space for a specific company
  public function getPublicOrNot($companyId)
  {
    // Query to check if there is at least one public parking space for the specified company
    $this->db->query('SELECT ps.is_public
                      FROM parking_spaces ps
                      WHERE ps.company_id = :company_id
                          AND ps.is_public = 1
                      LIMIT 1
                  ');
    $this->db->bind(':company_id', $companyId);
    $row = $this->db->single();

    // If there is at least one public parking space, return true; otherwise, return false
    return $row ? "Public" : "Private";
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
  public function updateApplicationStatus($applicationId, $newStatus)
  {
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
  public function getCompanyById($id)
  {
    $this->db->query('SELECT * FROM company WHERE _id = :id');
    $this->db->bind(':id', $id);

    $row = $this->db->single();

    return $row;
  }

  public function deleteCompany($id)
  {
    $this->db->query('DELETE FROM company WHERE _id = :id');
    // Bind values
    $this->db->bind(':id', $id);
    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  // Method to fetch top two reviews with specific fields
  public function getTopTwoReviewsData()
  {
    $this->db->query('SELECT driver_id, parking_id, content FROM review ORDER BY time_stamp DESC LIMIT 2');
    // return $this->db->resultSet(); // Assuming resultSet() fetches all rows

    // $rows = $this->db->resultSet(); 

    // return $rows;
    try {
      $rows = $this->db->resultSet(); // Assuming resultSet() fetches all rows
      return $rows;
    } catch (Exception $e) {
      // Handle database query error
      error_log('Database error: ' . $e->getMessage());
      return false;
    }
  }

  public function getReviewDetails()
  {
    $this->db->query(
      'SELECT
      review._id AS review_id,
      review.parking_id AS parking_id,
      parking_spaces.name AS parking_name,
      parking_spaces.address AS parking_address,
      driver.first_name AS driver_first_name, 
      driver.last_name AS driver_last_name,
      review.content,
      review.time_stamp,
      review.no_of_stars
    FROM
      review
    LEFT JOIN parking_spaces ON review.parking_id = parking_spaces._id
    LEFT JOIN driver ON review.driver_id = driver._id'
    );

    $row = $this->db->resultSet();

    return $row;
  }

  public function getLatestReviews()
  {
    $this->db->query(
      'SELECT
      review._id AS review_id,
      review.parking_id AS parking_id,
      parking_spaces.name AS parking_name,
      parking_spaces.address AS parking_address,
      driver.first_name AS driver_first_name,
      driver.last_name AS driver_last_name,
      review.content,
      review.time_stamp,
      review.no_of_stars
    FROM
      review
    LEFT JOIN parking_spaces ON review.parking_id = parking_spaces._id
    LEFT JOIN driver ON review.driver_id = driver._id
    ORDER BY review.time_stamp DESC
    LIMIT 5'
    );
    $rows = $this->db->resultSet();
    return $rows;
  }

  public function getAllReviews()
  {


    //$this->db->query('SELECT _id , time_stamp , no_of_stars , content , driver_id , parking_id FROM review');
    $this->db->query('
  SELECT review._id, review.time_stamp, review.no_of_stars, review.content,
         driver.first_name, driver.last_name , parking_spaces.name AS parking_name
  FROM review
  INNER JOIN driver ON review.driver_id = driver._id
  INNER JOIN parking_spaces ON review.parking_id = parking_spaces._id
  
');

    $rows = $this->db->resultSet(); // Assuming this function returns multiple rows

    return $rows;
  }


  public function parkingSession($company_id)
  {
    $this->db->query(
      'SELECT parking_session._id,
        parking_session.start_time,
        parking_session.parking_id,
        parking_spaces.company_id
      FROM parking_session
      LEFT JOIN parking_spaces ON parking_session.parking_id = parking_spaces._id;
      WHERE parking_spaces.company_id = :company_id'
    );

    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
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

  public function getDocument($documentId) {
    $params = [':id' => $documentId];
  
    // Assuming you have a 'company_documents' table where you store the documents
    $result = $this->db->query('SELECT documents FROM company WHERE _id = :id', $params);
  
    if ($result && !empty($result[0]['documents'])) {
      $documentData = $result[0]['documents'];
  
      // Set the appropriate headers for downloading a PDF file
      header('Content-Type: application/pdf');
      header('Content-Disposition: attachment; filename="document.pdf"');
  
      // Output the document data
      echo $documentData;
      exit;
    }
  
    return false; // Return false if the document is not found or an error occurs
  }

  public function insertCompanySuspendDetails($data)
  {
    $this->db->query('INSERT INTO company_suspend (company_id, message, duration, time_stamp) VALUES (:company_id, :message, :duration, :time_stamp)');
    $this->db->bind(':company_id', $data['company_id']);
    $this->db->bind(':message', $data['message']);
    $this->db->bind(':duration', $data['duration']);
    $this->db->bind(':time_stamp', $data['time_stamp']);

    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }
}
