<?php
class Companys extends Controller
{
  private $companyModel;

  public function __construct()
  {
    $this->companyModel = $this->model('company');
  }

  public function dashboardView()
  {
    $this->view('company/dashboardView');
  }

  public function updateView()
  {
    $this->view('company/updateView');
  }

  public function parkingOfficerView()
  {
    $this->view('company/parkingOfficerView');
  }

  public function parkingSpaceView()
  {
    $this->view('company/parkingSpaceView');
  }
}
