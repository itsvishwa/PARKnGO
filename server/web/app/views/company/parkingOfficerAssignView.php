<?php $selectedParkingSpaceId = null;

// Check if the form is submitted
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  // Check if the selected parking space is set in the POST data
  if (isset($_POST['parkingDropdown'])) {
    // Assign the selected parking space ID to $selectedParkingSpaceId
    $selectedParkingSpaceId = $_POST['parkingDropdown'];
  }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/dashboardView.css" />
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/parkingSpaceView.css" />
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/confirmationCard.css" />
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/parkingOfficerView.css" />
  <title>Parking Spaces</title>
</head>

<body>
  <div class="container">
    <div class="left-container">
      <div class="bg-div">
        <img src="<?php echo URLROOT; ?>/css/assets/logo-black.png" alt="logo" width="100%" />
        <div>
          <ul class="menu">
            <li>
              <a href="../dashboardView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M15.59 14.37a6 6 0 01-5.84 7.38v-4.8m5.84-2.58a14.98 14.98 0 006.16-12.12A14.98 14.98 0 009.631 8.41m5.96 5.96a14.926 14.926 0 01-5.841 2.58m-.119-8.54a6 6 0 00-7.381 5.84h4.8m2.581-5.84a14.927 14.927 0 00-2.58 5.84m2.699 2.7c-.103.021-.207.041-.311.06a15.09 15.09 0 01-2.448-2.448 14.9 14.9 0 01.06-.312m-2.24 2.39a4.493 4.493 0 00-1.757 4.306 4.493 4.493 0 004.306-1.758M16.5 9a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z" />
                </svg>
                Dashboard
              </a>
            </li>
            <li>
              <a href="../updateView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 002.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 00-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 00.75-.75 2.25 2.25 0 00-.1-.664m-5.8 0A2.251 2.251 0 0113.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25zM6.75 12h.008v.008H6.75V12zm0 3h.008v.008H6.75V15zm0 3h.008v.008H6.75V18z" />
                </svg>
                Updates
              </a>
            </li>
            <li>
              <a href="../parkingSpaceView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M9 6.75V15m6-6v8.25m.503 3.498l4.875-2.437c.381-.19.622-.58.622-1.006V4.82c0-.836-.88-1.38-1.628-1.006l-3.869 1.934c-.317.159-.69.159-1.006 0L9.503 3.252a1.125 1.125 0 00-1.006 0L3.622 5.689C3.24 5.88 3 6.27 3 6.695V19.18c0 .836.88 1.38 1.628 1.006l3.869-1.934c.317-.159.69-.159 1.006 0l4.994 2.497c.317.158.69.158 1.006 0z" />
                </svg>
                Parking Spaces
              </a>
            </li>
            <li class="active">
              <a href="../parkingOfficerView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z" />
                </svg>
                Parking Officer
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="right-container">
      <div class="header">
        <div class="pageName">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo mr">
            <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
          </svg>
          <h3>Assign Parking Officer</h3>
        </div>
        <div class="profile">
          <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
          <a href="../users/logout" class="logout">Log out</a>
        </div>
      </div>
      <div class="filter-parking">
        <div class="header text-md">
          <p>Search and Select the parking space you want to assgin.</p>
        </div>
        <div class="ml-20">
          <div class="flex justify-content-left align-items-center">
            <input type="text" id="parkingSearch" class="parking-search-bar" oninput="searchParking()" placeholder="Search Parking Space.">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="parking-search-logo text-primary">
              <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
            </svg>
          </div>
          <ul id="searchResults" class="search-results" onclick="selectParkingSpace(event)"></ul>
        </div>
      </div>

      <div class="form-div  mb-20">
        <div class="officer-parking-div">
          <div class="officer-card mt-10">
            <div class="officer-section-one">
              <img src="data:<?php $encodedImage = base64_encode($data['profile_photo']);
                              $imageMimeType = "image/jpeg";
                              echo $imageMimeType; ?>;base64,<?php echo $encodedImage; ?>" alt="profile image" class="dp-image" />
              <h3 class="officer-name"><?php echo ($data['first_name'] . " " . $data['last_name']) ?></h3>
              <h3 class="officer-id"><?php echo $data['officer_id'] ?></h3>
              <h3 class="allocated-parking"><?php echo $data['parking_space'] ?></h3>
            </div>
            <div class="officer-section-second">
              <p>NIC <?php echo $data['nic'] ?></p>
              <p class="officer-number">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="button-icon mr-5">
                  <path fill-rule="evenodd" d="M1.5 4.5a3 3 0 013-3h1.372c.86 0 1.61.586 1.819 1.42l1.105 4.423a1.875 1.875 0 01-.694 1.955l-1.293.97c-.135.101-.164.249-.126.352a11.285 11.285 0 006.697 6.697c.103.038.25.009.352-.126l.97-1.293a1.875 1.875 0 011.955-.694l4.423 1.105c.834.209 1.42.959 1.42 1.82V19.5a3 3 0 01-3 3h-2.25C8.552 22.5 1.5 15.448 1.5 6.75V4.5z" clip-rule="evenodd" />
                </svg>
                <?php echo $data['mobile_number'] ?>
              </p>
            </div>
          </div>
          <div id="card-container" class="parking-card mt-20">

            <div id="confirmationCard" class="ml-40"></div>

            <div>

            </div>
          </div>

        </div>
        <script>
          function searchParking() {
            var searchInput = document.getElementById("parkingSearch").value.toLowerCase();
            var parkingSpaces = <?php echo json_encode($data['parking_spaces']); ?>;
            var resultsContainer = document.getElementById("searchResults");

            // Clear previous search results
            resultsContainer.innerHTML = "";

            // Filter parking spaces based on the search input
            var filteredSpaces = parkingSpaces.filter(function(parking_space) {
              return parking_space.name.toLowerCase().includes(searchInput);
            });

            // Display search results
            filteredSpaces.forEach(function(result) {
              var li = document.createElement("li");
              li.textContent = result._id + "-" + result.name;
              resultsContainer.appendChild(li);
            });

            // Show the results container
            resultsContainer.style.display = filteredSpaces.length > 0 ? "block" : "none";
          }

          function selectParkingSpace(event) {
            var selectedParkingSpace = event.target.textContent;
            var inputField = document.getElementById("parkingSearch");

            // Populate the input field with the selected parking space name

            selectedParkingSpaceID = parseInt(selectedParkingSpace.split("-")[0]);
            inputField.value = selectedParkingSpace.split("-")[1];




            // Hide the results container
            document.getElementById("searchResults").style.display = "none";

            // Find the selected space and perform additional actions if needed
            var selectedSpace = <?php echo json_encode($data['parking_spaces']); ?>.find(function(parking_space) {
              return parking_space._id === selectedParkingSpaceID;
            });

            if (selectedSpace) {
              populateFormFields(selectedSpace);
            }
          }

          function populateFormFields(selectedParkingSpace) {
            var confirmationCard = document.getElementById('confirmationCard');
            var selectedParkingSpaceId = selectedParkingSpace._id;

            // Fetch officer details based on selected ID using AJAX or use a predefined JavaScript object
            // For example, assuming you have a JavaScript object containing officer details:
            var parking_spaces_status = <?php echo json_encode($data['parking_spaces_status']); ?>;


            var selectedParkingSpaceStatus = parking_spaces_status.filter(function(parking_space_status) {
              return parking_space_status.parking_id == selectedParkingSpaceId;
            });
            // console.log(selectedParkingSpace);
            // console.log(selectedParkingSpaceStatus);
            // Populate form fields with officer details

            if (selectedParkingSpace) {
              // Update the confirmation card content dynamically
              confirmationCard.innerHTML = `
              <div class="parking-space-card mb-20">
                <div class="parking-card-header">
                  <div class="parking-name">
                    <h3 class="parking-card-bold ml-20">${selectedParkingSpace.name}</h3>
                  </div>
                  <p class="parking-card-bold">${selectedParkingSpace.address}</p>
                </div>
                <div class='confirmation-card-line mb-10'>
                  <div class="parking-card-info">
                    <p>Total Slots: <span class="parking-card-bold">${selectedParkingSpace.no_of_slots}</span></p>
                    <p class='parking-type bg-green text-white'>${selectedParkingSpace.is_public ? 'Public' : 'Private'}</p>
                  </div>
                </div>
                <div class='confirmation-card-line'>
                    <h3 class='f-14'>Parking Slots</h3>
                </div>
                <table class='confirmation-card-table'>
                    <thead>
                        <tr>
                            <th>Vehicle Type</th>
                            <th>Slots</th>
                        </tr>
                    </thead>
                    <tbody>
                      ${selectedParkingSpaceStatus.length > 0 ?
                        selectedParkingSpaceStatus.map(function(vehicle) {
                          return `
                            <tr class='tr-b'>
                              <td>${vehicle.vehicle_type}</td>
                              <td>${vehicle.total_slots}</td>
                            </tr>
                          `;
                        }).join('') :
                        "<tr><td colspan='2'>No data available for the parking space.</td></tr>"
                      }
                    </tbody>
                </table>
              </div>
              <form action="<?php echo URLROOT; ?>companys/parkingOfficerAssignView/<?php echo $data['officer_id'] ?>/${selectedParkingSpace._id}" class="c-btn-section2" method="POST" enctype="multipart/form-data">
                <input type="button" value="Cancel" class="c-btn bg-black40" onclick="window.history.back()">
                <input type="submit" value="Assign Parking Officer" class="c-btn bg-green">
              </form>
              `;
            } else {
              // Display a message if no matching parking space is found
              confirmationCard.innerHTML = "<p>No data available for the selected parking space.</p>";
            }
          }

          window.onclick = function(event) {
            if (!event.target.matches('#parkingSearch')) {
              var resultsContainer = document.getElementById("searchResults");
              if (resultsContainer.style.display === 'block') {
                resultsContainer.style.display = 'none';
              }
            }
          };
        </script>

        <script>
          //cancel button
          document.getElementById('cancelButton').addEventListener('click', function() {
            window.history.back();
          });
        </script>
        <script src="<?php echo URLROOT; ?>/js/company/parkingOfficerView.js"></script>
</body>

</html>