<?php
class Admins extends Controller
{
  private $adminModel;


  public function __construct()
  {
    $this->adminModel = $this->model('admin');

    if (!isset($_SESSION['user_id'])) {
      redirect('users/login');
    }
  }

  public function dashboardView()
  {
    //*********************************************************** */
    // Get counts of users, parking officers, and companies from the model
    $adminModel = $this->model('Admin');
    $topTwoReviews = $adminModel->getTopTwoReviewsData();
    $userCount = $this->adminModel->getUsersCount($_SESSION['user_id']);
    $parkingOfficersCount = $this->adminModel->getParkingOfficersCount($_SESSION['user_id']);
    $companiesCount = $this->adminModel->getCompaniesCount($_SESSION['user_id']);
    $totalRevenue = $this->adminModel->getTotalRevenue();
    $totalPendingApplications = $this->adminModel->getPendingCompanyApplicationsWithCount()['totalPendingApplications'];
    $totalSuspendApplications = $this->adminModel->getSuspendCompanyCount()['totalSuspendApplications'];
    $reviews = $this->adminModel->getLatestReviews();
    $parkingSessions = $this->adminModel->parkingSession($_SESSION['user_id']);
    $revenues = $this->adminModel->getRevenue($_SESSION['user_id']);


    foreach ($revenues as &$revenue) {
      $revenue->time_stamp = date('Y-m-d H:i:s', $revenue->time_stamp);
    }

    function processRevenues($revenues)
    {
      // Initialize an associative array to store daily revenues
      $dailyRevenues = [];

      // Get the current date and time
      $currentDate = date('Y-m-d H:i:s');

      // Loop through the revenues and populate the dailyRevenues array
      foreach ($revenues as $revenue) {
        // Convert the timestamp to a formatted date
        $date = date('Y-m-d', strtotime($revenue->time_stamp));

        // If the date is within the last 30 days, add the amount to the dailyRevenues array
        if (strtotime($date) >= strtotime('-30 days', strtotime($currentDate))) {
          if (!isset($dailyRevenues[$date])) {
            $dailyRevenues[$date] = 0;
          }

          $dailyRevenues[$date] += $revenue->amount;
        }
      }

      // Fill in any missing days with zero amounts for the last 30 days
      for ($i = 29; $i >= 0; $i--) {
        $date = date('Y-m-d', strtotime("-$i days", strtotime($currentDate)));

        if (!isset($dailyRevenues[$date])) {
          $dailyRevenues[$date] = 0;
        }
      }

      ksort($dailyRevenues);

      // Return an associative array with daily revenues and total amount
      return $dailyRevenues;
    }

    $processedRevenues = processRevenues($revenues);


    foreach ($parkingSessions as &$parkingSession) {
      $parkingSession->start_time = date('Y-m-d H:i:s', $parkingSession->start_time);
    }

    function getParkingSessionsPerDay($sessionsData)
    {
      // Initialize an associative array to store the count of sessions for each day
      $sessionsPerDay = [];

      // Loop through each parking session data
      foreach ($sessionsData as $session) {
        // Check if the session is an object and convert it to an array
        if (is_object($session)) {
          $session = (array) $session;
        }

        // Extract the date from the start_time
        $date = date('Y-m-d', strtotime($session['start_time']));

        // Increment the count for the specific date
        if (!isset($sessionsPerDay[$date])) {
          $sessionsPerDay[$date] = 1;
        } else {
          $sessionsPerDay[$date]++;
        }
      }

      // Fill in missing dates with 0 sessions
      $startDate = date('Y-m-d', strtotime('-30 days'));
      $endDate = date('Y-m-d');

      $currentDate = $startDate;
      while (strtotime($currentDate) <= strtotime($endDate)) {
        if (!isset($sessionsPerDay[$currentDate])) {
          $sessionsPerDay[$currentDate] = 0;
        }
        $currentDate = date('Y-m-d', strtotime($currentDate . ' +1 day'));
      }

      // Sort the array by keys
      ksort($sessionsPerDay);

      // Return the result
      return $sessionsPerDay;
    }

    $parkingSessions = getParkingSessionsPerDay($parkingSessions);

    foreach ($reviews as &$review) {
      $review->time_stamp = date('Y-m-d H:i:s', $review->time_stamp);
    }

    // Prepare data for the view
    $data = [
      'userCount' => $userCount !== false ? $userCount : 0,
      'parkingOfficersCount' => $parkingOfficersCount !== false ? $parkingOfficersCount : 0,
      'companiesCount' => $companiesCount !== false ? $companiesCount : 0,
      'totalRevenue' => $totalRevenue !== false ? $totalRevenue : 0,
      'totalPendingApplications' => $totalPendingApplications,
      'totalSuspendApplications' => $totalSuspendApplications,
      'topTwoReviews' => $topTwoReviews,
      'reviews' => $reviews,
      'parkingSessions' => $parkingSessions,
      'revenues' => $processedRevenues,

    ];

    /* var_dump($data['reviews']);
    var_dump($data['revenues']);*/

    // Pass the data to the view
    $this->view('admin/dashboardView', $data);
  }

  public function companiesView()
  {
   
    

    // Fetch approved company applications details
    $approvedApplications = $this->adminModel->getApprovedCompanyApplications();
    

    // Get parking officers count for each approved company
    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $parkingOfficersCount = $this->adminModel->getParkingOfficersCountForCompany($companyId);
      $company->parkingOfficersCount = $parkingOfficersCount;
    }

    // Get parking officers count for each approved company
    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $parkingSlotsCount = $this->adminModel->getParkingSlotsCountForCompany($companyId);
      $company->parkingSlotsCount = $parkingSlotsCount;
    }


    // Prepare data for the view
    $data = [
      'approvedApplications' => $approvedApplications,


    ];
    
    

    // Pass the data to the view

    $this->view('admin/companiesView', $data);
  }

  public function deletionView()
  {

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
      // Get the raw POST data
      $json_data = file_get_contents('php://input');

      // Log the received JSON data
      file_put_contents('received_suspend_data.log', $json_data);

      // Decode the JSON data into an associative array
      $data = json_decode($json_data, true);

      $result = $this->adminModel->insertCompanySuspendDetails($data);

      if ($result) {
        // Successfully registered parking
        echo json_encode(['message' => 'Company Suspended..']);
      } else {
        // Error in registering parking
        http_response_code(500);
        echo json_encode(['message' => 'Error Occured...']);
      }
    }

    // Fetch approved company applications details
    $approvedApplications = $this->adminModel->getApprovedCompanyApplications();

    // Get parking officers count for each approved company
    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $parkingOfficersCount = $this->adminModel->getParkingOfficersCountForCompany($companyId);
      $company->parkingOfficersCount = $parkingOfficersCount;
    }

    // Get parking officers count for each approved company
    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $parkingSlotsCount = $this->adminModel->getParkingSlotsCountForCompany($companyId);
      $company->parkingSlotsCount = $parkingSlotsCount;
    }

    // Get parking spaces public or not for each approved company
    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $public = $this->adminModel->getPublicOrNot($companyId);
      $company->public = $public;
    }


    // Prepare data for the view
    $data = [
      'approvedApplications' => $approvedApplications,
    ];

    $this->view('admin/deletionView', $data);
  }

  public function requestsHistoryView()
  {

    // Fetch approved company applications
    $approvedApplications = $this->adminModel->getCompanyApplications(1, 1);

    // Fetch rejected company applications
    $rejectedApplications = $this->adminModel->getCompanyApplications(0, 1);

    // Prepare data for the view
    $data = [
      'approvedApplications' => $approvedApplications,
      'rejectedApplications' => $rejectedApplications
    ];

    $this->view('admin/requestsHistoryView', $data);
  }

  public function requestsView()
  {
    // Fetch pending company applications
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();
    $totalPendingApplications = $this->adminModel->getPendingCompanyApplicationsWithCount()['totalPendingApplications'];

    // Prepare data for the view
    $data = [
      'pendingApplications' => $pendingApplications,
      'totalPendingApplications' => $totalPendingApplications,
    ];

    // Pass the data to the view

    $this->view('admin/requestsView', $data);
  }

  public function driverReviews()
  {
    // Instantiate the ReviewModel
    // $adminModel = $this->model('Admin');


    // Call the method to get all reviews
    //$reviews = $adminModel->getAllReviews();

    // Call the method to get all reviews using the existing model instance
    $reviews = $this->adminModel->getAllReviews();

    // Prepare data to pass to the view
    $data = [
      'reviews' => $reviews
    ];

    $this->view('admin/driverReviews', $data);
  }

 /* public function index()
  {
    $this->dashboardView();
  }
*/

 public function index()
  {


    // Fetch pending company applications
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();
    

    // Prepare data for the view
    $data = [
      'pendingApplications' => $pendingApplications
    ];


    $this->view('admin/proceedView', $data);
  }

  /*public function approve($companyId)
{
    // Approve the application
    if ($this->adminModel->approveApplication($companyId)) {
        // If the update was successful, redirect to requestView.php
        echo "Update successful";
        header("Location: requestView.php");
        exit(); // Terminate script execution after redirect
    } else {
      echo "Update failed";
        // If the update failed, handle the error
        // Display an error message or redirect to an error page
        // Example: redirect('/admins/error');
    }
}

public function reject($companyId)
{
    // Get the reject reason from the request
    $rejectReason = $_POST['rejectReason']; // Assuming the reject reason is submitted via POST

    // Reject the application
    if ($this->adminModel->rejectApplication($companyId, $rejectReason)) {
        // If the update was successful, redirect to requestView.php
        header("Location: requestView.php");
        exit(); // Terminate script execution after redirect
    } else {
        // If the update failed, handle the error
        // Display an error message or redirect to an error page
        // Example: redirect('/admins/error');
    }
}*/
  

 /* public function proceedView()
  {


    // Fetch pending company applications
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();
    

    // Prepare data for the view
    $data = [
      'pendingApplications' => $pendingApplications
    ];


    $this->view('admin/proceedView', $data);
  }*/




  public function delete($id)
  {
    if ($_SERVER['REQUEST_METHOD'] == 'GET') {
      // Get existing post from model
      $company = $this->adminModel->getCompanyById($id);

      // Check for owner
      if ($company->user_id != $_SESSION['user_id']) {
        redirect('admins/companiesView');
      }

      if ($this->adminModel->deleteCompany($id)) {
        flash('post_message', 'Company Removed');
        redirect('admins/companiesView');
      } else {
        die('Something went wrong');
      }
    } else {
      redirect('admins/companiesView');
    }
  }

  /****************************** */
  /*public function getTopTwoReviews() {
  $adminModel = new Admin();
  $topTwoReviews = $adminModel->getTopTwoReviewsData();

  // Pass data to the view
  // Assuming a method to load the view
 // $this->view('admin/dashboardView', ['topTwoReviews' => $topTwoReviews]);

 if ($topTwoReviews !== null && !empty($topTwoReviews)) {
  // Pass data to the view if it's not null or empty
  $this->view('admin/dashboardView', ['topTwoReviews' => $topTwoReviews]);
} else {
  // Handle the case when no reviews are available
  error_log("No reviews fetched or issue with data retrieval."); // Log an error message
  $this->view('admin/dashboardView', ['topTwoReviews' => []]); // Passing an empty array
}
}*/

/*public function downloadDocument($documentId) {
  // Load the Company model
 
 // $adminModel = $this->model('Admin');
  $this->adminModel = $this->model('Admin');

  // Fetch the document from the model based on $documentId
  $documents = $this->adminModel->getDocument($documentId);

  var_dump($documents);

  // Check if the document exists
  if ($documents) {
     // Set appropriate headers for PDF file
     
     header('Content-Type: application/pdf');
     header('Content-Disposition: attachment; filename="document.pdf"');

    // header('Content-Disposition: inline; filename="document.pdf"');
    // header('Content-Length: ' . strlen($documents));
    
     // Encode binary data to base64
     $base64Encoded = base64_encode($documents);

     // Set Content-Length header to the length of base64 data
    // header('Content-Length: ' . strlen($base64Encoded));
     header('Content-Length: ' . strlen(base64_decode($base64Encoded)));

     // Output the document content
   //  echo $documents;
     // Output the document content
     //echo $base64Encoded;
     echo base64_decode($base64Encoded);

  } else {
     // Handle case when the document is not found
     
     // You can redirect to an error page or show a message
     echo 'Document not found';
  }
}*/

/*public function downloadDocument($documentId) {
  $this->adminModel = $this->model('Admin');

  // Fetch the document from the model based on $documentId
  $document = $this->adminModel->getDocument($documentId);

  // Check if the document exists
  if ($document) {
      // Set appropriate headers for PDF file
      header('Content-Type: application/pdf');
      header('Content-Disposition: attachment; filename="document.pdf"');
      header('Content-Length: ' . strlen($document));

      // Output the document content
      echo $document;
  } else {
      // Handle case when the document is not found
      echo 'Document not found';
  }
}*/


/********************************* */
// In your Admins.php controller

public function downloadDocument($documentId)
{
    // Fetch the PDF data from the database based on the $documentId
    $documentData = $this->adminModel->getDocumentData($documentId);

    // Set appropriate headers for PDF response
    header('Content-Type: application/pdf');
    header('Content-Disposition: attachment; filename="document.pdf"');

    // Output the PDF data
    echo $documentData;
    exit();
}


/*public function submitRejectReason()
{
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        // Retrieve the reject reason from the POST data
        $rejectReason = $_POST['rejectReason'];

        // Perform validation on $rejectReason if necessary

        // Get the company ID from your session or another source
        $companyId = $_SESSION['user_id']; // Replace with your logic

        // Update the company table with the reject reason
        if ($this->adminModel->updateRejectReason($companyId, $rejectReason)) {
            // Send a success response
            echo 'Reject reason submitted successfully.';
            
        } else {
            // Send an error response
            echo 'Failed to submit reject reason.';
        }
    } else {
        // Handle non-POST requests
        echo 'Invalid request method.';
    }
}

public function approveApplication($companyId) {
  $this->adminModel = $this->model('Admin');

  // Update the company table with approval status
  $success = $this->adminModel->updateApprovalStatus($companyId);

  // Send a JSON response indicating success or failure
  header('Content-Type: application/json');
  echo json_encode(['success' => $success]);
}*/

/*public function approveApplication($companyId) {
  // Update the database to set is_approved to 1 and is_reviewed to 1
  // Implement this logic based on your database structure
  $this->adminModel->approveApplication($companyId);

  // You can return a JSON response indicating success or failure
  echo json_encode(['success' => true]);
}

public function rejectApplication($companyId) {
  // Get reject reason from POST data
  $rejectReason = $_POST['rejectReason'];

  // Update the database to set is_approved to 0, is_reviewed to 1, and set the review_message
  // Implement this logic based on your database structure
  $this->adminModel->rejectApplication($companyId, $rejectReason);

  // You can return a JSON response indicating success or failure
  echo json_encode(['success' => true]);
}*/

public function approveApplication() {
  $companyId = $_GET['_id'] ?? '';
  
  // Update the database to set is_approved to 1 and is_reviewed to 1
  // Implement this logic based on your database structure
  $this->adminModel->approveApplication($companyId);

  // You can return a JSON response indicating success or failure
  echo json_encode(['success' => true]);
  
}



public function rejectApplication() {
  $companyId = $_GET['_id'] ?? '';
  // Get reject reason from POST data
  $rejectReason = $_POST['rejectReason'];

  // Update the database to set is_approved to 0, is_reviewed to 1, and set the review_message
  // Implement this logic based on your database structure
  $this->adminModel->rejectApplication($companyId, $rejectReason);

  // You can return a JSON response indicating success or failure
  echo json_encode(['success' => true]);
}




}
