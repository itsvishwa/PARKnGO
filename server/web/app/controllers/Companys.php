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
        'recently_added_officer_id' => $this->officerModel->getRecentlyAddedOfficerId()->officer_id,
        'mobile_number' => trim($_POST['mobile_number']),
        //'parking_id' => trim($_POST['parking_id']),
        'profile_image' => trim($_FILES['profile_image']['name']),
        'company_id' => $_SESSION['user_id'],
        'mobile_number_err' => '',
      ];

      if ($this->officerModel->findOfficerByPhoneNumber($data['mobile_number'])) {
        $data['mobile_number_err'] = 'Mobile Number is already taken';
      }

      if (empty($data['mobile_number_err'])) {
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
      ];

      // Load view
      $this->view('company/parkingOfficerFormView', $data);
    }
  }

  public function parkingOfficerEditView()
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
        'mobile_number' => trim($_POST['mobile_number']),
        //'parking_id' => trim($_POST['parking_id']),
        'profile_image' => trim($_FILES['profile_image']['name']),
        'company_id' => $_SESSION['user_id'],
        'mobile_number_err' => '',
      ];

      if ($this->officerModel->findOfficerByPhoneNumber($data['mobile_number'])  && $this->officerModel->getOfficerDetailsUsingPhoneNumber($data['phone_number'])->officer_id != $_POST['officer_id']) {
        $data['mobile_number_err'] = 'Mobile Number is already taken';
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
        'first_name' => '',
        'last_name' => '',
        'nic' => '',
        'officer_id' => '',
        'mobile_number' => '',
        //'parking_id' => '',
        'profile_image' => '',
        'company_id' => $_SESSION['user_id'],
        'mobile_number_err' => '',
      ];

      $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);

      // Load view
      $this->view('company/parkingOfficerEditView', $officers);
    }
  }

  public function parkingOfficerDeleteView()
  {

    $officers = $this->officerModel->getAllOfficersDetails($_SESSION['user_id']);
    $this->view('company/parkingOfficerDeleteView', $officers);
  }

  public function parkingOfficerAssignView()
  {
    $this->view('company/parkingOfficerAssignView');
  }

  public function parkingOfficerActivitiesView()
  {
    $this->view('company/parkingOfficerActivitiesView');
  }
}
