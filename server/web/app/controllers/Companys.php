<?php
class Companys extends Controller
{
  private $companyModel;
  private $officerModel;
  private $paymentModel;
  private $parkingSpaceModel;
  private $companyReport;

  public function __construct()
  {
    $this->companyModel = $this->model('company');
    $suspend_data = $this->companyModel->getCompanySuspendDetails($_SESSION['user_id']);
    $approved_data = $this->companyModel->isApproved($_SESSION['user_id']);
    if (!isset($_SESSION['user_id'])) {
      redirect('users/login');
    } else if ($suspend_data != null && date('Y-m-d H:i:s', $suspend_data->end_time) > date('Y-m-d H:i:s')) {
      redirect('users/suspendView');
    }

    $this->officerModel = $this->model('Officer');
    $this->paymentModel = $this->model('Payment');
    $this->parkingSpaceModel = $this->model('ParkingSpace');
    $this->companyReport = $this->model('CompanyReport');
  }

  public function index()
  {
    $this->dashboardView();
  }

  public function dashboardView()
  {
    $monthlyEarned = $this->paymentModel->getMonthlyEarnedAmount($_SESSION['user_id']);
    $todayEarned = $this->paymentModel->getTodayEarnedAmount($_SESSION['user_id']);
    $numberOfUsers = $this->paymentModel->getNumberOfUsers($_SESSION['user_id']);
    $parkingOfficers = $this->officerModel->getOfficerDetails($_SESSION['user_id']);
    $parkingSpacesStatus = $this->parkingSpaceModel->getCardDetailsFromParkingSpaceStatus($_SESSION['user_id']);
    //$parkingSpaces = $this->companyModel->getParkingSpacesDetails();
    $reviews = $this->parkingSpaceModel->getLatestReviews($_SESSION['user_id']);
    $activities = $this->officerModel->getOfficerActivitiesCount($_SESSION['user_id']);
    //$parkingSessions = $this->parkingSpaceModel->dailyParkingSessionCount($_SESSION['user_id']);
    //echo json_encode($parkingSessions);
    $parkingSessions = $this->parkingSpaceModel->parkingSession($_SESSION['user_id']);
    $revenues = $this->paymentModel->getRevenue($_SESSION['user_id']);

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

    $data = [
      'monthlyEarned' => $monthlyEarned,
      'todayEarned' => $todayEarned,
      'numberOfUsers' => $numberOfUsers,
      'parkingOfficers' => $parkingOfficers,
      'parking_spaces_status' => $parkingSpacesStatus,
      'reviews' => $reviews,
      'activities' => $activities,
      'parkingSessions' => $parkingSessions,
      'revenues' => $processedRevenues,
    ];

    $this->view('company/dashboardView', $data);
  }

  public function updateView()
  {
    $updates = $this->paymentModel->getUpdates($_SESSION['user_id']);

    $this->view('company/updateView', $updates);
  }

  public function parkingSpaceView()
  {

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
      // Get the raw POST data
      $json_data = file_get_contents('php://input');

      // Log the received JSON data
      file_put_contents('received_data.log', $json_data);

      // Decode the JSON data into an associative array
      $data = json_decode($json_data, true);

      $data['parking_image'] = explode(',', $data['parking_image'])[1];
      file_put_contents('received_data3.log', $data['parking_image']);

      $data['parking_image'] = base64_decode($data['parking_image']);

      $result = $this->parkingSpaceModel->parkingSave($data);

      if ($result) {
        // Successfully registered parking
        echo json_encode(['message' => 'Parking space saved successfully']);
      } else {
        // Error in registering parking
        http_response_code(500);
        echo json_encode(['message' => 'Error saving parking space']);
      }
    }

    $parkingSpaces = $this->parkingSpaceModel->getCardDetailsForParkingSpaces($_SESSION['user_id']);
    // $officerCount = $this->parkingSpaceModel->getParkingOfficerCount($_SESSION['user_id']);
    $parkingSpacesStatus = $this->parkingSpaceModel->getCardDetailsFromParkingSpaceStatus($_SESSION['user_id']);
    $todayEarned = $this->paymentModel->getTodayEarnedAmount($_SESSION['user_id']);
    $reviews = $this->parkingSpaceModel->getReviewDetails();
    $dutyRecord = $this->parkingSpaceModel->getDutyRecord($_SESSION['user_id']);

    foreach ($reviews as &$review) {
      $review->time_stamp = date('Y-m-d H:i:s', $review->time_stamp);
    }

    $data = [
      'parking_spaces' => $parkingSpaces,
      'parking_spaces_status' => $parkingSpacesStatus,
      'todayEarned' => $todayEarned,
      // 'officer_count' => $officerCount,
      'reviews' => $reviews,
      'duty_records' => $dutyRecord,
    ];
    $this->view('company/parkingSpaceView', $data);
  }

  public function parkingSpaceFormView()
  {
    $this->view('company/parkingSpaceFormView');
  }

  public function parkingSpaceSaveView($parking_id = null)
  {
    $this->view('company/parkingSpaceSaveView', $parking_id);
  }


  public function parkingSpaceEditView($parking_id)
  {
    $parkingSpace = $this->parkingSpaceModel->getCardDetailsFromParkingOfficer($_SESSION['user_id'], $parking_id);
    $parkingSpaceStatus = $this->parkingSpaceModel->getCardDetailsFromParkingSpaceStatus($_SESSION['user_id'], $parking_id);

    $data = [
      'parking_space' => $parkingSpace,
      'parking_space_status' => $parkingSpaceStatus,
    ];

    $this->view('company/parkingSpaceEditView', $data);
  }

  public function parkingSpaceDeleteView($parking_id)
  {
    if ($_SERVER['REQUEST_METHOD'] == "POST") {
      if ($this->parkingSpaceModel->deleteParkingSpace($parking_id)) {
        redirect('companys/parkingSpaceView');
      } else {
        die('Something went wrong');
      }
    } else {
      $parkingSpace = $this->parkingSpaceModel->getCardDetailsFromParkingOfficer($_SESSION['user_id'], $parking_id);
      $parkingSpaceStatus = $this->parkingSpaceModel->getCardDetailsFromParkingSpaceStatus($_SESSION['user_id'], $parking_id);

      $data = [
        'parking_space' => $parkingSpace,
        'parking_space_status' => $parkingSpaceStatus,
      ];

      $this->view('company/parkingSpaceDeleteView', $data);
    }
  }

  public function parkingSpaceCloseView($parking_id)
  {
    if ($_SERVER['REQUEST_METHOD'] == "POST") {

      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      $data = [
        'parking_id' => $parking_id,
        'closed_start_time' => trim($_POST['closed_start_time']),
        'closed_end_time' => trim($_POST['closed_end_time']),
      ];

      if ($this->parkingSpaceModel->updateClosedTime($data)) {
        redirect('companys/parkingSpaceView');
      } else {
        die('Something went wrong');
      }
    } else {
      $parkingSpace = $this->parkingSpaceModel->getCardDetailsFromParkingOfficer($_SESSION['user_id'], $parking_id);
      $parkingSpaceStatus = $this->parkingSpaceModel->getCardDetailsFromParkingSpaceStatus($_SESSION['user_id'], $parking_id);

      $data = [
        'parking_space' => $parkingSpace,
        'parking_space_status' => $parkingSpaceStatus,
        'closed_start_time' => '',
        'closed_end_time' => '',
      ];

      $this->view('company/parkingSpaceCloseView', $data);
    }
  }

  public function parkingOfficerView()
  {

    // Inside your controller or wherever you're processing the request
    $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);
    $dutyRecord = [];
    foreach ($officers as $officer) {
      $dutyRecord[] = $this->parkingSpaceModel->getDutyRecordForParkingOfficer($officer->_id);
    }

    $data = [
      'officers' => $officers,
      'duty_records' => $dutyRecord,
    ];
    $this->view('./company/parkingOfficerView', $data);
  }

  public function parkingOfficerFormView()
  {
    if ($_SERVER['REQUEST_METHOD'] == "POST") {
      //Pocess form
      // Sanitize POST data

      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      if (isset($_FILES['profile_image']) && $_FILES['profile_image']['error'] === UPLOAD_ERR_OK) {
        // Set upload file size limits
        ini_set('upload_max_filesize', '32M');
        ini_set('post_max_size', '32M');

        // Read the contents of the uploaded file
        $fileData = $_FILES['profile_image'];
        $fileContent = file_get_contents($fileData['tmp_name']);
      } else {
        // Handle file upload error here, for example:
        // i want to add default image as profile image it is in <?php echo URLROOT; how to do that
        $defaultImage = file_get_contents(URLROOT . '/css/assets/default-dp.jpg');
        $fileContent = $defaultImage; // Use the existing image or set a default value
      }

      // Init data
      $data = [
        'first_name' => trim($_POST['first_name']),
        'last_name' => trim($_POST['last_name']),
        'nic' => trim($_POST['nic']),
        'officer_id' => trim($_POST['officer_id']),
        'recently_added_officer_id' => $this->officerModel->getRecentlyAddedOfficer()->officer_id,
        'mobile_number' => trim($_POST['mobile_number']),
        //'parking_id' => trim($_POST['parking_id']),
        'profile_image' => $fileContent,
        'company_id' => $_SESSION['user_id'],
        'mobile_number_err' => '',
        'officer_id_err' => '',
        'nic_err' => '',
      ];

      if ($this->officerModel->findOfficerByPhoneNumber($data['mobile_number'])) {
        $data['mobile_number_err'] = 'Mobile Number is already taken';
      }

      //if mobile number length > 9 
      if (strlen($data['mobile_number']) != 9) {
        $data['mobile_number_err'] = 'Mobile Number should be 9 numbers';
      }

      //check officer nic is valid
      if ($this->officerModel->findOfficerByNic($data['nic'])) {
        $data['nic_err'] = 'NIC is already taken';
      }

      if (strlen($data['nic']) != 12) {
        $data['nic_err'] = 'NIC should be 10 numbers';
      }

      //check officer id is valid
      if ($this->officerModel->findOfficerByOfficerId($data['officer_id'])) {
        $data['officer_id_err'] = 'Officer ID is already taken';
      }

      if (empty($data['mobile_number_err']) && empty($data['officer_id_err']) && empty($data['nic_err'])) {
        if ($this->officerModel->register($data)) {
          $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);
          redirect('companys/parkingOfficerView');
        } else {
          die('Something went wrong');
        }
        $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);
        $this->view('company/parkingOfficerView', $officers);
      } else {
        // Load view
        $data['profile_image'] = '';
        $this->view('company/parkingOfficerFormView', $data);
      }
    } else {
      // Init data
      $data = [
        'first_name' => '',
        'last_name' => '',
        'nic' => '',
        'officer_id' => '',
        'recently_added_officer_id' => $this->officerModel->getRecentlyAddedOfficer()->officer_id,
        'mobile_number' => '',
        //'parking_id' => '',
        'profile_image' => '',
        'company_id' => $_SESSION['user_id'],
        'mobile_number_err' => '',
        'officer_id_err' => '',
        'nic_err' => '',
      ];

      // Load view
      $this->view('company/parkingOfficerFormView', $data);
    }
  }

  public function parkingOfficerEditView($officer_id)
  {
    $officer = $this->officerModel->findOfficerByOfficerId($officer_id);
    if ($_SERVER['REQUEST_METHOD'] == "POST") {
      //Pocess form
      // Sanitize POST data

      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      if (!empty($_FILES['profile_image']['tmp_name']) && is_uploaded_file($_FILES['profile_image']['tmp_name'])) {
        ini_set('upload_max_filesize', '32M');
        ini_set('post_max_size', '32M');
        $fileData = $_FILES['profile_image'];
        $fileContent = file_get_contents($fileData['tmp_name']);
      } else {
        // Handle the case where no file was uploaded
        $fileContent = $officer->profile_photo; // Use the existing image or set a default value
      }

      // Init data
      $data = [
        'first_name' => trim($_POST['first_name']),
        'last_name' => trim($_POST['last_name']),
        'nic' => trim($_POST['nic']),
        'officer_id' => $officer->officer_id,
        'mobile_number' => trim($_POST['mobile_number']),
        //'parking_id' => trim($_POST['parking_id']),
        'profile_image' => $fileContent,
        'mobile_number_err' => '',
        'company_id' => $_SESSION['user_id'],
      ];

      if (strlen($data['mobile_number']) != 9) {
        $data['mobile_number_err'] = 'Mobile Number should be 9 numbers';
      }

      if (strlen($data['nic']) != 12) {
        $data['nic_err'] = 'NIC should be 10 numbers';
      }

      if (empty($data['mobile_number_err'])) {
        if ($this->officerModel->update($data)) {
          redirect('companys/parkingOfficerView');
        } else {
          die('Something went wrong');
        }
      } else {
        $this->view('company/parkingOfficerEditView', $data);
      }
    } else {
      // Init data
      $data = [
        'first_name' => $officer->first_name,
        'last_name' => $officer->last_name,
        'nic' => $officer->nic,
        'officer_id' => $officer->officer_id,
        'mobile_number' => $officer->mobile_number,
        //'parking_id' => '',
        'profile_image' => $officer->profile_photo,
        'mobile_number_err' => '',
        'company_id' => $_SESSION['user_id'],
      ];

      // Load view
      $this->view('company/parkingOfficerEditView', $data);
    }
  }

  public function parkingOfficerDeleteView($officer_id)
  {

    if ($_SERVER['REQUEST_METHOD'] == "POST") {


      if ($this->officerModel->delete($officer_id, $_SESSION['user_id'])) {
        redirect('companys/parkingOfficerView');
      } else {
        die('Something went wrong');
      }
    } else {
      $officer = $this->officerModel->getOfficerCardDetails($officer_id, $_SESSION['user_id']);

      $data = [
        'officer_id' => $officer->officer_id,
        'first_name' => $officer->first_name,
        'last_name' => $officer->last_name,
        'nic' => $officer->nic,
        'mobile_number' => $officer->mobile_number,
        'profile_photo' => $officer->profile_photo,
        'company_id' => $_SESSION['user_id'],
        'parking_space' => $officer->parking_space,
      ];

      $this->view('company/parkingOfficerDeleteView', $data);
    }
  }

  public function parkingOfficerAssignView($officer_id, $parking_id = null)
  {
    if ($_SERVER['REQUEST_METHOD'] == "POST") {
      if ($this->officerModel->assignParkingSpace($officer_id, $parking_id, $_SESSION['user_id'])) {
        redirect('companys/parkingOfficerView');
      } else {
        die('Something went wrong');
      }
    } else {
      $officer = $this->officerModel->getOfficerCardDetails($officer_id, $_SESSION['user_id']);
      $parkingSpaces = $this->companyModel->getParkingSpaces($_SESSION['user_id']);
      $parkingSpacesStatus = $this->companyModel->getParkingSpaceStatusDetails($_SESSION['user_id']);


      $data = [
        'officer_id' => $officer->officer_id,
        'first_name' => $officer->first_name,
        'last_name' => $officer->last_name,
        'nic' => $officer->nic,
        'mobile_number' => $officer->mobile_number,
        'profile_photo' => $officer->profile_photo,
        'company_id' => $_SESSION['user_id'],
        'parking_space' => $officer->parking_space,
        'parking_spaces' => $parkingSpaces,
        'parking_spaces_status' => $parkingSpacesStatus,
      ];

      $this->view('company/parkingOfficerAssignView', $data);
    }
  }

  public function parkingOfficerActivitiesView($officer_id)
  {

    $officer = $this->officerModel->getOfficerCardDetails($officer_id, $_SESSION['user_id']);
    $officerActivities = $this->officerModel->getOfficerActivities($officer_id, $_SESSION['user_id']);

    $data = [
      'officer_id' => $officer->officer_id,
      'first_name' => $officer->first_name,
      'last_name' => $officer->last_name,
      'nic' => $officer->nic,
      'mobile_number' => $officer->mobile_number,
      'profile_photo' => $officer->profile_photo,
      'company_id' => $_SESSION['user_id'],
      'parking_space' => $officer->parking_space,
      'activities' => $officerActivities,
    ];

    $this->view('company/parkingOfficerActivitiesView', $data);
  }

  public function forceStoppedSessionView()
  {
    $data = $this->parkingSpaceModel->getForceStoppedSessions($_SESSION['user_id']);
    $this->view('company/forceStoppedSessionView', $data);
  }

  public function reportGenerateView()
  {
    $this->view('company/reportGenerateView');
  }
}
