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
    $this->view('admin/dashboardView');
  }
  public function companiesView(){
    $this->view('admin/companiesView');
  }
  public function deletionView(){
    $this->view('admin/deletionView');
  }
  public function requestsHistoryView(){
    $this->view('admin/requestsHistoryView');
  }
  public function requestsView(){
    $this->view('admin/requestsView');
  }
}
