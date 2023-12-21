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

    $userCount = $this->adminModel->getUsersCount($_SESSION['user_id']);
    $parkingOfficersCount = $this->adminModel->getParkingOfficersCount($_SESSION['user_id']);
    $companiesCount = $this->adminModel->getCompaniesCount($_SESSION['user_id']);
    $totalRevenue = $this->adminModel->getTotalRevenue();
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();

    // Prepare data for the view
    $data = [
      'userCount' => $userCount !== false ? $userCount : 0,
      'parkingOfficersCount' => $parkingOfficersCount !== false ? $parkingOfficersCount : 0,
      'companiesCount' => $companiesCount !== false ? $companiesCount : 0,
      'totalRevenue' => $totalRevenue !== false ? $totalRevenue : 0,
      'pendingApplications' => $pendingApplications
    ];

    // Pass the data to the view
    $this->view('admin/dashboardView', $data);
  }

  public function companiesView()
  {

    // Fetch pending company applications
    $approvedApplications = $this->adminModel->getApprovedCompanyApplications();

    // Prepare data for the view
    $data = [
        'approvedApplications' => $approvedApplications
    ];

    // Pass the data to the view

    $this->view('admin/companiesView', $data);
  }

  public function deletionView()
  {
    $this->view('admin/deletionView');
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
    $this->view('admin/driverReviews');
  }

  public function index() {

    // Fetch pending company applications
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();

     // Prepare data for the view
     $data = [
      'pendingApplications' => $pendingApplications
  ];


    $this->view('admin/proceedView', $data);
  
    // Your code logic here
}
  
}

