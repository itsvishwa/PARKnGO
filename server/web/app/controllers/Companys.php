<?php
class Companys extends Controller
{
  private $companyModel;
  private $officerModel;
  private $paymentModel;
  private $parkingSpaceModel;

  public function __construct()
  {
    $this->companyModel = $this->model('company');
    if (!isset($_SESSION['user_id'])) {
      redirect('users/login');
    }

    $this->officerModel = $this->model('Officer');
    $this->paymentModel = $this->model('Payment');
    $this->parkingSpaceModel = $this->model('ParkingSpace');
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
    $latestUpdates = $this->companyModel->getLatestUpdates($_SESSION['user_id']);
    $parkingOfficers = $this->officerModel->getOfficerDetails($_SESSION['user_id']);
    //$parkingSpaces = $this->companyModel->getParkingSpacesDetails();


    $data = [
      'monthlyEarned' => $monthlyEarned,
      'todayEarned' => $todayEarned,
      'numberOfUsers' => $numberOfUsers,
      'latestUpdates' => $latestUpdates,
      'parkingOfficers' => $parkingOfficers,
    ];

    $this->view('company/dashboardView', $data);
  }

  public function updateView()
  {
    $this->view('company/updateView');
  }

  public function parkingSpaceView()
  {

    // if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    //   $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

    //   $data = [
    //     'parking_name' => trim($_POST['name']),
    //     'parking_address' => trim($_POST['address']),
    //     'parking_latitude' => trim($_POST['latitude']),
    //     'parking_longitude' => trim($_POST['longitude']),
    //     'parking_type' => trim($_POST['parkingType']),
    //   ];
    // } else {
    //   $data = [
    //     'parking_name' => '',
    //     'parking_address' => '',
    //     'parking_latitude' => '',
    //     'parking_longitude' => '',
    //     'parking_type' => '',
    //   ];

    //   $this->view('company/parkingSpaceView', $data);
    // }

    $parkingSpaces = $this->parkingSpaceModel->getCardDetailsFromParkingOfficer($_SESSION['user_id']);
    $parkingSpacesStatus = $this->parkingSpaceModel->getCardDetailsFromParkingSpaceStatus($_SESSION['user_id']);
    $todayEarned = $this->paymentModel->getTodayEarnedAmount($_SESSION['user_id']);
    $data = [
      'parking_spaces' => $parkingSpaces,
      'parking_spaces_status' => $parkingSpacesStatus,
      'todayEarned' => $todayEarned,
    ];
    $this->view('company/parkingSpaceView', $data);
  }

  public function parkingSpaceFormView()
  {

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

      // Get the raw POST data
      $json_data = file_get_contents('php://input');

      // Decode the JSON data into an associative array
      $data = json_decode($json_data, true);

      $this->parkingSpaceModel->registerParking($data);

      echo json_encode($data);
    } else {
      $this->view('company/parkingSpaceFormView');
    }
  }

  public function parkingSpaceSaveView()
  {
    $this->view('company/parkingSpaceSaveView');
  }

  public function parkingSpaceEditView()
  {
    $this->view('company/parkingSpaceEditView');
  }

  public function parkingSpaceDeleteView()
  {
    $this->view('company/parkingSpaceDeleteView');
  }

  public function parkingSpaceCloseView()
  {
    $this->view('company/parkingSpaceCloseView');
  }

  public function parkingOfficerView()
  {

    // Inside your controller or wherever you're processing the request
    $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);
    $this->view('./company/parkingOfficerView', $officers);
  }

  public function parkingOfficerFormView()
  {
    if ($_SERVER['REQUEST_METHOD'] == "POST") {
      //Pocess form
      // Sanitize POST data

      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      // Init data
      $data = [
        'first_name' => trim($_POST['first_name']),
        'last_name' => trim($_POST['last_name']),
        'nic' => trim($_POST['nic']),
        'officer_id' => trim($_POST['officer_id']),
        'recently_added_officer_id' => $this->officerModel->getRecentlyAddedOfficer()->officer_id,
        'mobile_number' => trim($_POST['mobile_number']),
        //'parking_id' => trim($_POST['parking_id']),
        'profile_image' => trim($_FILES['profile_image']['name']),
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

      //check officer id is valid
      if ($this->officerModel->findOfficerByOfficerId($data['officer_id'])) {
        $data['officer_id_err'] = 'Officer ID is already taken';
      }

      if (empty($data['mobile_number_err']) && empty($data['officer_id_err']) && empty($data['nic_err'])) {
        if ($this->officerModel->register($data)) {
          $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);
          $this->view('company/parkingOfficerView', $officers);
        } else {
          die('Something went wrong');
        }
      } else {
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

      // Init data
      $data = [
        'first_name' => trim($_POST['first_name']),
        'last_name' => trim($_POST['last_name']),
        'nic' => trim($_POST['nic']),
        'officer_id' => $officer->officer_id,
        'mobile_number' => trim($_POST['mobile_number']),
        //'parking_id' => trim($_POST['parking_id']),
        'profile_image' => trim($_FILES['profile_image']['name']),
        'mobile_number_err' => '',
        'company_id' => $_SESSION['user_id'],
      ];

      if (strlen($data['mobile_number']) != 9) {
        $data['mobile_number_err'] = 'Mobile Number should be 9 numbers';
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
        'profile_image' => '',
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
        //'profile_image' => $officer->profile_image,
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
        //'profile_image' => $officer->profile_image,
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
      //'profile_image' => $officer->profile_image,
      'company_id' => $_SESSION['user_id'],
      'parking_space' => $officer->parking_space,
      'activities' => $officerActivities,
    ];

    $this->view('company/parkingOfficerActivitiesView', $data);
  }
}
