<?php
class Users extends Controller
{
  private $userModel;
  public function __construct()
  {
    $this->userModel = $this->model('Company');
  }

  public function registrationView()
  {
    // Check for POST
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
      // Process Form

      // Sanitize POST data
      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      // Init Data
      $data = [
        'company_name' => trim($_POST['company_name']),
        'company_address' => trim($_POST['company_address']),
        'company_email' => trim($_POST['company_email']),
        'phone_number' => trim($_POST['phone_number']),
        'password' => trim($_POST['password']),
        'confirm_password' => trim($_POST['confirm_password']),
        'file_upload' => trim($_FILES['file_upload']['name']),
        'company_email_err' => '',
        'password_err' => '',
        'confirm_password_err' => ''
      ];

      if (!empty($data['company_email'])) {
        // Validate email
        if ($this->userModel->findCompanyByEmail($data['company_email'])) {
          $data['company_email_err'] = 'Email is already taken';
        }
      }


      // Validate password
      if (strlen($data['password']) < 6) {
        $data['password_err'] = 'Password must be at least 6 characters';
      }

      // Validate confirm password
      if ($data['password'] != $data['confirm_password']) {
        $data['confirm_password_err'] = 'Passwords does not match';
      }

      // check errors are empty
      if (empty($data['password_err']) && empty($data['confirm_password_err']) && empty($data['company_email_err'])) {
        // Handle file upload here (move the file to the desired location)
        // $uploadDirectory = URLROOT . '/files';
        // $fileDestination = $uploadDirectory . $_FILES['file_upload']['name'];

        // if (move_uploaded_file($_FILES['file_upload']['tmp_name'], $fileDestination)) {
        //   // File upload successful, continue processing
        //   // ...
        //   die('SUCCESS');
        // } else {
        //   // File upload failed, handle the error
        //   $data['file_upload_err'] = 'File upload failed';
        //   $this->view('company/registrationView', $data);
        // }

        die('SUCCESS');
      } else {
        // Load View with errors
        $this->view('company/registrationView', $data);
      }
    } else {
      // Init Data
      $data = [
        'company_name' => '',
        'company_address' => '',
        'company_email' => '',
        'phone_number' => '',
        'password' => '',
        'confirm_password' => '',
        'file_upload' => '',
        'company_email_err' => '',
        'password_err' => '',
        'confirm_password_err' => '',
      ];

      // Load View
      $this->view('company/registrationView', $data);
    }
  }

  public function loginView()
  {

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
      // Process Form
      // Sanitize POST data
      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      // Init Data
      $data = [
        'email' => trim($_POST['email']),
        'password' => trim($_POST['password'])
      ];

      // Check errors are empty
      if (!empty($data['email']) && !empty($data['password'])) {
        //validate
        die('SUCCESS');
      } else {
        // Load View with errors
        $this->view('loginView', $data);
      }
    } else {
      // Init Data
      $data = [
        'email' => '',
        'password' => '',
      ];


      // Load View
      $this->view('loginView', $data);
    }
  }
}
