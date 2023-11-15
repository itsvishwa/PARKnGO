<?php
class Companys extends Controller
{
  private $companyModel;
  private $officerModel;
  private $paymentModel;

  public function __construct()
  {
    $this->companyModel = $this->model('company');
    if (!isset($_SESSION['user_id'])) {
      redirect('users/login');
    }

    $this->officerModel = $this->model('Officer');
    $this->paymentModel = $this->model('Payment');
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
    $this->view('company/parkingSpaceView');
  }

  public function parkingSpaceFormView()
  {
    $this->view('company/parkingSpaceFormView');
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
      ];

      if ($this->officerModel->findOfficerByPhoneNumber($data['mobile_number'])) {
        $data['mobile_number_err'] = 'Mobile Number is already taken';
      }

      //if mobile number length > 9 
      if (strlen($data['mobile_number']) != 9) {
        $data['mobile_number_err'] = 'Mobile Number should be 9 numbers';
      }

      //check officer id is valid
      if ($this->officerModel->findOfficerByOfficerId($data['officer_id'])) {
        $data['officer_id_err'] = 'Officer ID is already taken';
      }

      if (empty($data['mobile_number_err']) && empty($data['officer_id_err'])) {
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

      if ($this->officerModel->findOfficerByPhoneNumber($data['mobile_number'])) {
        $data['mobile_number_err'] = 'Mobile Number is already taken';
      }

      if (strlen($data['mobile_number']) != 9) {
        $data['mobile_number_err'] = 'Mobile Number should be 9 numbers';
      }

      if (empty($data['mobile_number_err'])) {
        if ($this->officerModel->update($data)) {
          $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);
          $this->view('company/parkingOfficerView', $officers);
        } else {
          die('Something went wrong');
        }
      } else {
        $this->view('company/parkingOfficerView', $data);
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

  public function parkingOfficerDeleteView()
  {

    $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);
    $this->view('company/parkingOfficerDeleteView', $officers);
  }

  public function parkingOfficerAssignView($officer_id)
  {

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
