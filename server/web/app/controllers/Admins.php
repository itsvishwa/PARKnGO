<?php
class Admins extends Controller
{
  private $adminModel;

  public function __construct()
  {
    $this->adminModel = $this->model('admin');
  }

  public function dashboardView()
  {
    $this->view('admin/dashboardView');
  }
}
