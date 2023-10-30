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

  public function parkingOfficerFormView()
  {
    $this->view('company/parkingOfficerFormView');
  }

  public function parkingOfficerEditView()
  {
    $this->view('company/parkingOfficerEditView');
  }

  public function parkingOfficerDeleteView()
  {
    $this->view('company/parkingOfficerDeleteView');
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
