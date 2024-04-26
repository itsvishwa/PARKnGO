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

    $adminModel = $this->model('Admin');
    $topTwoReviews = $adminModel->getTopTwoReviewsData();
    $userCount = $this->adminModel->getUsersCount($_SESSION['user_id']);
    $parkingOfficersCount = $this->adminModel->getParkingOfficersCount($_SESSION['user_id']);
    $companiesCount = $this->adminModel->getCompaniesCount($_SESSION['user_id']);
    $totalRevenue = $this->adminModel->getTotalRevenue();
    $totalPendingApplications = $this->adminModel->getPendingCompanyApplicationsWithCount()['totalPendingApplications'];
    $totalSuspendApplications = $this->adminModel->getSuspendCompanyCount()['totalSuspendApplications'];
    $reviews = $this->adminModel->getLatestReviews();
    $parkingSessions = $this->adminModel->parkingSession();
    $revenues = $this->adminModel->getRevenue();

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

      return $sessionsPerDay;
    }

    $parkingSessions = getParkingSessionsPerDay($parkingSessions);

    foreach ($reviews as &$review) {
      $review->time_stamp = date('Y-m-d H:i:s', $review->time_stamp);
    }

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

    $this->view('admin/dashboardView', $data);
  }

  public function companiesView()
  {

    $approvedApplications = $this->adminModel->getApprovedCompanyApplications();

    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $parkingOfficersCount = $this->adminModel->getParkingOfficersCountForCompany($companyId);
      $company->parkingOfficersCount = $parkingOfficersCount;
    }

    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $parkingSlotsCount = $this->adminModel->getParkingSlotsCountForCompany($companyId);
      $company->parkingSlotsCount = $parkingSlotsCount;
    }

    $data = [
      'approvedApplications' => $approvedApplications,
    ];

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
        echo json_encode(['message' => 'Company Suspended..']);
      } else {
        http_response_code(500);
        echo json_encode(['message' => 'Error Occured...']);
      }
    }

    $approvedApplications = $this->adminModel->getApprovedCompanyApplications();
    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $parkingOfficersCount = $this->adminModel->getParkingOfficersCountForCompany($companyId);
      $company->parkingOfficersCount = $parkingOfficersCount;
    }

    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $parkingSlotsCount = $this->adminModel->getParkingSlotsCountForCompany($companyId);
      $company->parkingSlotsCount = $parkingSlotsCount;
    }

    foreach ($approvedApplications as &$company) {
      $companyId = $company->_id;
      $public = $this->adminModel->getPublicOrNot($companyId);
      $company->public = $public;
    }

    $data = [
      'approvedApplications' => $approvedApplications,
    ];

    $this->view('admin/deletionView', $data);
  }

  public function requestsHistoryView()
  {

    $approvedApplications = $this->adminModel->getCompanyApplications(1, 1);
    $rejectedApplications = $this->adminModel->getCompanyApplications(0, 1);

    $data = [
      'approvedApplications' => $approvedApplications,
      'rejectedApplications' => $rejectedApplications
    ];

    $this->view('admin/requestsHistoryView', $data);
  }

  public function requestsView()
  {

    $pendingApplications = $this->adminModel->getPendingCompanyApplications();
    $totalPendingApplications = $this->adminModel->getPendingCompanyApplicationsWithCount()['totalPendingApplications'];

    $data = [
      'pendingApplications' => $pendingApplications,
      'totalPendingApplications' => $totalPendingApplications,
    ];

    $this->view('admin/requestsView', $data);
  }

  public function driverReviews()
  {
    $reviews = $this->adminModel->getAllReviews();
    $data = [
      'reviews' => $reviews
    ];

    $this->view('admin/driverReviews', $data);
  }

  public function index()
  {
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();

    $data = [
      'pendingApplications' => $pendingApplications
    ];

    $this->view('admin/proceedView', $data);
  }

  public function reportGenerateView()
  {

    $this->view('admin/reportGenerateView');
  }

  public function delete($id)
  {
    if ($_SERVER['REQUEST_METHOD'] == 'GET') {

      $company = $this->adminModel->getCompanyById($id);

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

  public function downloadDocument($documentId)
  {
    $documentData = $this->adminModel->getDocumentData($documentId);

    header('Content-Type: application/pdf');
    header('Content-Disposition: attachment; filename="document.pdf"');


    echo $documentData;
    exit();
  }

  public function approveApplication()
  {
    $companyId = $_POST['_id'] ?? '';
    $adminId = $_SESSION['user_id'] ?? ''; // Assuming admin_id is retrieved from session, adjust as necessary

    // Update the database to set is_approved to 1 and is_reviewed to 1
    $updateResult = $this->adminModel->updateApproveOrRejectApplication($companyId, $adminId, true);

    if ($updateResult) {
      // Return JSON response with success message and redirect URL
      echo json_encode(['success' => true, 'redirect' => 'requestsView']);
    } else {
      // Return JSON response with error message
      echo json_encode(['success' => false, 'error' => 'Failed to approve application']);
    }
  }

  public function rejectApplication()
  {
    $companyId = $_POST['_id'] ?? '';
    // Get reject reason from POST data
    $rejectReason = $_POST['rejectReason'] ?? '';
    $adminId = $_SESSION['user_id'] ?? '';

    // Update the database to set is_approved to 0, is_reviewed to 1, and set the review_message
    $updateResult = $this->adminModel->updateApproveOrRejectApplication($companyId, $adminId, false, $rejectReason);

    if ($updateResult) {
      // Return JSON response with success message and redirect URL
      echo json_encode(['success' => true, 'redirect' => 'requestsView']);
    } else {
      // Return JSON response with error message
      echo json_encode(['success' => false, 'error' => 'Failed to reject application']);
    }
  }
}
