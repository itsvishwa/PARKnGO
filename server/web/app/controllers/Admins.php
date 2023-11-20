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

    // Prepare data for the view
    $data = [
      'userCount' => $userCount !== false ? $userCount : 0,
      'parkingOfficersCount' => $parkingOfficersCount !== false ? $parkingOfficersCount : 0,
      'companiesCount' => $companiesCount !== false ? $companiesCount : 0,
      
    ];

    // Pass the data to the view
    $this->view('admin/dashboardView', $data);
  }

  public function companiesView()
  {
    $this->view('admin/companiesView');
  }

  public function deletionView()
  {
    $this->view('admin/deletionView');
  }

  public function requestsHistoryView()
  {
    $this->view('admin/requestsHistoryView');
  }

  public function requestsView()
  {
    $this->view('admin/requestsView');
  }
}
