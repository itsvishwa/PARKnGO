<?php
class Pages extends Controller
{
  private $postModel;
  public function __construct()
  {
    $this->postModel = $this->model('Post');
  }

  public function index()
  {
    $admin = $this->postModel->getAdmin();

    $data = [
      'title' => 'Welcome',
      'admin' => $admin
    ];

    $this->view('homeview');
  }
}
