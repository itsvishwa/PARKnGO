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
}
