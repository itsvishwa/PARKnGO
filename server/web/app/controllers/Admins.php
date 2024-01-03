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
    $totalRevenue = $this->adminModel->getTotalRevenue();
    $totalPendingApplications = $this->adminModel->getPendingCompanyApplicationsWithCount()['totalPendingApplications'];
    $totalSuspendApplications = $this->adminModel->getSuspendCompanyCount()['totalSuspendApplications'];

    // Prepare data for the view
    $data = [
      'userCount' => $userCount !== false ? $userCount : 0,
      'parkingOfficersCount' => $parkingOfficersCount !== false ? $parkingOfficersCount : 0,
      'companiesCount' => $companiesCount !== false ? $companiesCount : 0,
      'totalRevenue' => $totalRevenue !== false ? $totalRevenue : 0,
      'totalPendingApplications' => $totalPendingApplications,
      'totalSuspendApplications' => $totalSuspendApplications
    ];

    // Pass the data to the view
    $this->view('admin/dashboardView', $data);
  }

  public function companiesView()
  {

    // Fetch approved company applications details
    $approvedApplications = $this->adminModel->getApprovedCompanyApplications();

    
    

    // Prepare data for the view
    $data = [
        'approvedApplications' => $approvedApplications,
        
    ];


    // Pass the data to the view

    $this->view('admin/companiesView', $data);
  }

  public function deletionView()
  {
    $this->view('admin/deletionView');
  }

  public function requestsHistoryView()
  {

    // Fetch approved company applications
    $approvedApplications = $this->adminModel->getCompanyApplications(1, 1);

    // Fetch rejected company applications
    $rejectedApplications = $this->adminModel->getCompanyApplications(0, 1);

    // Prepare data for the view
    $data = [
        'approvedApplications' => $approvedApplications,
        'rejectedApplications' => $rejectedApplications
    ];

    $this->view('admin/requestsHistoryView', $data);
  }

  public function requestsView()
  {  
    // Fetch pending company applications
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();

    // Prepare data for the view
    $data = [
        'pendingApplications' => $pendingApplications
    ];

    // Pass the data to the view
     
    $this->view('admin/requestsView', $data);
  }

  public function driverReviews()
  {
    $this->view('admin/driverReviews');
  }

  public function index() {

    // Fetch pending company applications
    $pendingApplications = $this->adminModel->getPendingCompanyApplications();

     // Prepare data for the view
     $data = [
      'pendingApplications' => $pendingApplications
  ];


    $this->view('admin/proceedView', $data);
  
    
}
/**************** */
public function approveApplication()
    {
        // Check if the request is POST and if the application ID is provided
        if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['dataset.application_id'])) {
            $applicationId = $_POST['dataset.application_id'];

            // Update the application status to 'approved' in your model
            $result = $this->adminModel->updateApplicationStatus($applicationId, 1); // Assuming 1 means approved status

            if ($result) {
                // If the update is successful, send a success response
                echo json_encode(['success' => true]);
                exit();
            }
        }

        // If something goes wrong, send an error response
        echo json_encode(['success' => false]);
        exit();
    }

// Inside your controller method that handles the deletion
/*public function delete($entry_id) {
  if ($this->adminModel->deleteEntry($entry_id)) {
      // Success - Entry deleted
      // Redirect or perform other actions as needed
      flash('post_message' , 'Company Removed');
      redirect('admin/companiesView');
      
  } else {
      // Failure - Handle the failure (e.g., show an error message)
      die('Something went wrong');
  }
}*/


/*public function delete($_id){
  if($_SERVER['REQUEST_METHOD'] == 'POST'){
    // Get existing post from model
    $post = $this->adminModel->getApprovedCompanyApplications($_id);

    // Check for owner
    if($post->user_id != $_SESSION['user_id']){
      redirect('admins/companiesView');
    }

    if($this->adminModel->deleteEntry($_id)){
      flash('post_message' , 'Company Removed');
      redirect('admins/companiesView');

    }else{
      die('Something went wrong');
    }

  }else{
    redirect('admins/companiesView');
  }
}*/
/*public function delete($_id) {
  if ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
    // Delete the company with the given ID using your model's method
    if ($this->adminModel->deleteCompany($_id)) {
      // Deletion successful
      http_response_code(200); // Respond with a success status code
      // You might also send a success message as a JSON response
      echo json_encode(['success' => true]);
      exit();
    } else {
      // Deletion failed
      http_response_code(500); // Respond with an error status code
      echo json_encode(['success' => false]);
      exit();
    }
  }
}*/
public function delete($id){
  if($_SERVER['REQUEST_METHOD'] == 'POST'){
    // Get existing post from model
    $company = $this->adminModel->getCompanyById($id);

    // Check for owner
    if($company->user_id != $_SESSION['user_id']){
      redirect('admins');
    }

    if($this->adminModel->deleteCompany($id)){
      flash('post_message' , 'Company Removed');
      redirect('admins');

    }else{
      die('Something went wrong');
    }

  }else{
    redirect('admins');
  }
}
  
  
}

