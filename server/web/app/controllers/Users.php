<?php

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

class Users extends Controller
{
  private $userModel;
  private $userModelAdmin;
  public function __construct()
  {
    $this->userModel = $this->model('Company');
    $this->userModelAdmin = $this->model('Admin');
  }

  public function index()
  {
    $this->loginView();
  }

  public function registrationSuccussfulView()
  {
    $this->view('company/registrationSuccussfulView');
  }

  public function suspendView()
  {
    $data = [
      'suspend_details' => $this->userModel->getCompanySuspendDetails($_SESSION['user_id']),
    ];
    $this->view('company/suspendView', $data);
  }

  public function notApprovedView() {
    $data =  $this->userModel->isApproved($_SESSION['user_id']);
    print_r($data);
    $this->view('company/notApprovedView', $data);
  }

  public function registrationView()
  {
    // Check for POST
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
      // Process Form

      // Sanitize POST data
      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      $fileData = $_FILES['file_upload'];
      $fileContent = file_get_contents($fileData['tmp_name']);

      // Init Data
      $data = [
        'company_name' => trim($_POST['company_name']),
        'company_address' => trim($_POST['company_address']),
        'company_email' => trim($_POST['company_email']),
        'phone_number' => trim($_POST['phone_number']),
        'password' => trim($_POST['password']),
        'confirm_password' => trim($_POST['confirm_password']),
        'file_upload' => $fileContent,
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
        $data['file_upload'] = '';
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
            if ($loggedInUser->is_approved) {
              $this->createUserSession($loggedInUser);
            } else if ($loggedInUser->is_approved == 0 && $loggedInUser->is_reviewd == 0) {
              redirect('users/registrationSuccussfulView');
            } 
            else if ($loggedInUser->is_approved == 0 && $loggedInUser->is_reviewd == 1) {
              $this->view('company/notApprovedView', $loggedInUser->review_message);
            }
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
      // end
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



  public function forgotPasswordView()
  {
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      function send_password_reset_email($email, $token)
      {

        $mail = new PHPMailer(true);

        try {
          //Server settings
          $mail->SMTPDebug = SMTP::DEBUG_SERVER;                      //Enable verbose debug output
          $mail->isSMTP();                                            //Send using SMTP
          $mail->Host       = 'smtp.gmail.com';                     //Set the SMTP server to send through
          $mail->SMTPAuth   = true;                                   //Enable SMTP authentication
          $mail->Username   = 'janidusandeepa2020@gmail.com';                     //SMTP username
          $mail->Password   = 'gsdl wnkh fdog ggcy';                               //SMTP password
          $mail->SMTPSecure = 'tls';            //Enable implicit TLS encryption
          $mail->Port       = 587;                                    //TCP port to connect to; use 587 if you have set `SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS`

          //Recipients
          $mail->setFrom('janidusandeepa2020@gmail.com', 'Admin');
          $mail->addAddress($email, "PARK'n Go User");

          //Content
          $mail->isHTML(true);
          $mail->Subject = 'Reset Password Notification from PARK\'n GO';

          $email_template = "<h2>Hello, From PARK'n GO</h2>
                            <h4>You are receiving this email because we received a password reset request for your account.</h4>
                            <p>If it is you, click the link to reset your password: <a href='http://localhost/PARKnGO/server/web/users/resetPasswordView?email=$email&token=$token'>Reset Password</a></p>";

          $mail->Body    = $email_template;

          $mail->send();
        } catch (Exception $e) {
          echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
        }
      }

      // Init Data
      $token = md5(rand());
      $data = [
        'email' => trim($_POST['email']),
        'email_err' => '',
        'token' => $token,
      ];

      // Validate email
      if (empty($data['email'])) {
        $data['email_err'] = 'Please enter email';
      } else {
        if ($this->userModelAdmin->findAdminByEmail($data['email'])) {
          if (empty($data['email_err'])) {
            $updateToken = $this->userModelAdmin->updateRecoveryToken($data);
            if ($updateToken) {
              send_password_reset_email($data['email'], $token);
              redirect('users/loginView');
            }
          }
        } else if ($this->userModel->findCompanyByEmail($data['email'])) {
          if (empty($data['email_err'])) {
            $updateToken = $this->userModel->updateRecoveryToken($data);
            if ($updateToken) {
              send_password_reset_email($data['email'], $token);
              redirect('users/loginView');
            }
          }
        } else {
          $data['email_err'] = 'No user found';
          $this->view('ForgotPasswordView', $data);
        }
      }
    } else {
      // Init Data
      $data = [
        'email' => '',
        'email_err' => '',
      ];

      // Load View
      $this->view('ForgotPasswordView', $data);
    }
  }

  public function resetPasswordView()
  {

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
      $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);

      // Init Data
      $data = [
        'email' => trim($_POST['email']),
        'token' => trim($_POST['token']),
        'password' => trim($_POST['password']),
        'confirm_password' => trim($_POST['confirm_password']),
        'password_err' => '',
        'confirm_password_err' => '',
      ];


      // Validate password
      if (strlen($data['password']) < 6) {
        $data['password_err'] = 'Password must be at least 6 characters';
      }

      // Validate confirm password
      if ($data['password'] != $data['confirm_password']) {
        $data['confirm_password_err'] = 'Passwords does not match';
      }

      // Check errors are empty
      if (empty($data['password_err']) && empty($data['confirm_password_err'])) {
        // Hash Password
        $data['password'] = password_hash($data['password'], PASSWORD_DEFAULT);

        if ($this->userModelAdmin->findAdminByEmail($data['email'])) {
          // Update Password
          if ($this->userModelAdmin->updatePassword($data)) {
            redirect('users/loginView');
          } else {
            die('Something went wrong');
          }
        } else if ($this->userModel->findCompanyByEmail($data['email'])) {
          // Update Password
          if ($this->userModel->updatePassword($data)) {
            redirect('users/loginView');
          } else {
            die('Something went wrong');
          }
        }
      } else {
        // Load View with errors
        $this->view('resetPasswordView', $data);
      }
    } else {

      $email = '';
      if (isset($_GET['email'])) {
        $email = $_GET['email'];
      };

      $token = '';
      if (isset($_GET['token'])) {
        $token = $_GET['token'];
      };
      // Init Data
      $data = [
        'email' => $email,
        'token' => $token,
        'password' => '',
        'confirm_password' => '',
        'password_err' => '',
        'confirm_password_err' => '',
      ];

      // Load View
      $this->view('resetPasswordView', $data);
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
