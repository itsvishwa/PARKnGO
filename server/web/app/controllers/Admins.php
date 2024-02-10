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
          $public = $this->adminModel-> getPublicOrNot($companyId);
          $company->public= $public;
        }
        
    
        // Prepare data for the view
        $data = [
            'approvedApplications' => $approvedApplications,
           
            
        ];

    $this->view('admin/deletionView' , $data);
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

    // Prepare data for the view
    $data = [
        'pendingApplications' => $pendingApplications
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

  public function index() {

    
    // Fetch pending company applications
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();

     // Prepare data for the view
     $data = [
      'pendingApplications' => $pendingApplications
  ];


    $this->view('admin/proceedView', $data);
  
    
}

public function downloadDocument($_id)
{
    // Retrieve document content from the database based on an ID or any other identifier
    $documentContent = ''; // Initialize the variable

    // Fetch the document content from the database
    // Example: Assuming you fetch the content from your model
    $documentContent = $this->adminModel->getDocumentContentById($_id);

    if ($documentContent !== false) {
        // Set headers for PDF content
        header('Content-Type: application/pdf');
        header('Content-Disposition: attachment; filename="document.pdf"');

        // Output the retrieved document content as a PDF file
        echo $documentContent;

        // Terminate script execution
        exit();
    } else {
        // Handle the case when the document content retrieval fails
        echo "Failed to retrieve document content.";
        // Optionally, you can redirect or show an error message
    }
}


/**************** */
public function approveApplication()
    {
        // Check if the request is POST and if the application ID is provided
        if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['dataset.application_id'])) {
            $applicationId = $_POST['dataset.application_id'];

            // Update the application status to 'approved' in your model
            $result = $this->adminModel->updateApplicationStatus($applicationId, 1); // Assuming 1 means approved status

            if ($result) {
                // If the update is successful, send a success response
                echo json_encode(['success' => true]);
                exit();
            }
        }

        // If something goes wrong, send an error response
        echo json_encode(['success' => false]);
        exit();
    }

// Inside your controller method that handles the deletion
/*public function delete($entry_id) {
  if ($this->adminModel->deleteEntry($entry_id)) {
      // Success - Entry deleted
      // Redirect or perform other actions as needed
      flash('post_message' , 'Company Removed');
      redirect('admin/companiesView');
      
  } else {
      // Failure - Handle the failure (e.g., show an error message)
      die('Something went wrong');
  }
}*/


/*public function delete($_id){
  if($_SERVER['REQUEST_METHOD'] == 'POST'){
    // Get existing post from model
    $post = $this->adminModel->getApprovedCompanyApplications($_id);

    // Check for owner
    if($post->user_id != $_SESSION['user_id']){
      redirect('admins/companiesView');
    }

    if($this->adminModel->deleteEntry($_id)){
      flash('post_message' , 'Company Removed');
      redirect('admins/companiesView');

    }else{
      die('Something went wrong');
    }

  }else{
    redirect('admins/companiesView');
  }
}*/
/*public function delete($_id) {
  if ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
    // Delete the company with the given ID using your model's method
    if ($this->adminModel->deleteCompany($_id)) {
      // Deletion successful
      http_response_code(200); // Respond with a success status code
      // You might also send a success message as a JSON response
      echo json_encode(['success' => true]);
      exit();
    } else {
      // Deletion failed
      http_response_code(500); // Respond with an error status code
      echo json_encode(['success' => false]);
      exit();
    }
  }
}*/
public function delete($id){
  if($_SERVER['REQUEST_METHOD'] == 'POST'){
    // Get existing post from model
    $company = $this->adminModel->getCompanyById($id);

    // Check for owner
    if($company->user_id != $_SESSION['user_id']){
      redirect('admins');
    }

    if($this->adminModel->deleteCompany($id)){
      flash('post_message' , 'Company Removed');
      redirect('admins');

    }else{
      die('Something went wrong');
    }

  }else{
    redirect('admins');
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



  
}

