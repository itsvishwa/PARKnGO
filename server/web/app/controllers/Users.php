<?php
class Users extends Controller
{
  private $userModel;
  private $userModelAdmin;
  public function __construct()
  {
    $this->userModel = $this->model('Company');
    $this->userModelAdmin = $this->model('Admin');
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

        $data['password'] = password_hash($data['password'], PASSWORD_DEFAULT);

        // Register Company
        if ($this->userModel->register($data)) {
          flash('register_success', 'You are registered Successfully. You can log in');
          // Redirect to login
          redirect('users/loginView');
        } else {
          die('Something went wrong');
        }
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
        'password' => trim($_POST['password']),
        'email_err' => '',
        'password_err' => '',
      ];

      if ($this->userModelAdmin->findAdminByEmail($data['email'])) {
        // Admin found
        if (!empty($data['password']) && empty($data['email_err'])) {
          $loggedInUser = $this->userModelAdmin->login($data['email'], $data['password']);
          if ($loggedInUser) {
            // Create Session
            $this->createAdminSession($loggedInUser);
          } else {
            $data['password_err'] = 'Password incorrect';
            $this->view('loginView', $data);
          }
        }
      } else if ($this->userModel->findCompanyByEmail($data['email'])) {
        // Company found
        if (!empty($data['password']) && empty($data['email_err'])) {
          $loggedInUser = $this->userModel->login($data['email'], $data['password']);
          if ($loggedInUser) {
            // Create Session
            $this->createUserSession($loggedInUser);
          } else {
            $data['password_err'] = 'Password incorrect';
            $this->view('loginView', $data);
          }
        }
      } else {
        // Company not found
        $data['email_err'] = 'User not found';
        $this->view('loginView', $data);
      }

      // // Check errors are empty
      // if (!empty($data['email']) && !empty($data['password']) && empty($data['email_err'])) {
      //   //validate
      //   // Check and set logged in user
      //   $loggedInUser = $this->userModel->login($data['email'], $data['password']);
      //   if ($loggedInUser) {
      //     // Create Session
      //     $this->createUserSession($loggedInUser);
      //   } else {
      //     $data['password_err'] = 'Password incorrect';
      //     $this->view('loginView', $data);
      //   }
      // } else {
      //   // Load View with errors
      //   $this->view('loginView', $data);
      // }
    } else {
      // Init Data
      $data = [
        'email' => '',
        'password' => '',
        'email_err' => '',
        'password_err' => '',
      ];


      // Load View
      $this->view('loginView', $data);
    }
  }

  public function createUserSession($user)
  {

    $_SESSION['user_id'] = $user->_id;
    $_SESSION['user_email'] = $user->email;
    $_SESSION['user_name'] = $user->name;
    redirect('companys/dashboardView');
  }

  public function createAdminSession($user)
  {
    $_SESSION['user_id'] = $user->_id;
    $_SESSION['user_email'] = $user->email;
    $_SESSION['user_name'] = $user->name;
    redirect('admins/dashboardView');
  }

  public function logout()
  {
    unset($_SESSION['user_id']);
    unset($_SESSION['user_email']);
    unset($_SESSION['user_name']);
    session_destroy();
    redirect('users/loginView');
  }

  public function isLoggedIn()
  {
    if (isset($_SESSION['user_id'])) {
      return true;
    } else {
      return false;
    }
  }
}
