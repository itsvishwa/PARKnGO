<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="refresh" content="60" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/dashboardView.css" />
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/parkingSpaceView.css" />
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
              <a href="./dashboardView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M15.59 14.37a6 6 0 01-5.84 7.38v-4.8m5.84-2.58a14.98 14.98 0 006.16-12.12A14.98 14.98 0 009.631 8.41m5.96 5.96a14.926 14.926 0 01-5.841 2.58m-.119-8.54a6 6 0 00-7.381 5.84h4.8m2.581-5.84a14.927 14.927 0 00-2.58 5.84m2.699 2.7c-.103.021-.207.041-.311.06a15.09 15.09 0 01-2.448-2.448 14.9 14.9 0 01.06-.312m-2.24 2.39a4.493 4.493 0 00-1.757 4.306 4.493 4.493 0 004.306-1.758M16.5 9a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z" />
                </svg>
                Dashboard
              </a>
            </li>
            <li>
              <a href="./updateView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 002.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 00-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 00.75-.75 2.25 2.25 0 00-.1-.664m-5.8 0A2.251 2.251 0 0113.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25zM6.75 12h.008v.008H6.75V12zm0 3h.008v.008H6.75V15zm0 3h.008v.008H6.75V18z" />
                </svg>
                Updates
              </a>
            </li>
            <li>
              <a href="./forceStoppedSessionView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" />
                </svg>
                Aborted Sessions
              </a>
            </li>
            <li>
              <a href="./parkingSpaceView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M9 6.75V15m6-6v8.25m.503 3.498l4.875-2.437c.381-.19.622-.58.622-1.006V4.82c0-.836-.88-1.38-1.628-1.006l-3.869 1.934c-.317.159-.69.159-1.006 0L9.503 3.252a1.125 1.125 0 00-1.006 0L3.622 5.689C3.24 5.88 3 6.27 3 6.695V19.18c0 .836.88 1.38 1.628 1.006l3.869-1.934c.317-.159.69-.159 1.006 0l4.994 2.497c.317.158.69.158 1.006 0z" />
                </svg>
                Parking Spaces
              </a>
            </li>
            <li class="active">
              <a href="./parkingOfficerView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z" />
                </svg>
                Parking Officer
              </a>
            </li>
            <li>
              <a href="./reportGenerateView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 3v11.25A2.25 2.25 0 0 0 6 16.5h2.25M3.75 3h-1.5m1.5 0h16.5m0 0h1.5m-1.5 0v11.25A2.25 2.25 0 0 1 18 16.5h-2.25m-7.5 0h7.5m-7.5 0-1 3m8.5-3 1 3m0 0 .5 1.5m-.5-1.5h-9.5m0 0-.5 1.5M9 11.25v1.5M12 9v3.75m3-6v6" />
                </svg>
                Report Generate
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
          <h3>Parking Officer</h3>
        </div>

        <div class="profile">
          <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
          <a href="../users/logout" class="logout">Log out</a>
        </div>
      </div>
      <div class="btn-section">
        <a href="./parkingOfficerFormView">
          <div class="btn bg-off-white">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo text-green">
              <path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zM12.75 9a.75.75 0 00-1.5 0v2.25H9a.75.75 0 000 1.5h2.25V15a.75.75 0 001.5 0v-2.25H15a.75.75 0 000-1.5h-2.25V9z" clip-rule="evenodd" />
            </svg>
            Add Parking Officer
          </div>
        </a>
        <div class="search-bar flex">
          <input type="text" id="searchInput" placeholder="Search By Name." oninput="filterOfficers()" />
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="search-logo text-primary">
            <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
          </svg>
        </div>

      </div>
      <div class="parking-space-section">
        <div id="officerCards" class="officerCards">
          <!-- Inside your HTML file where you want to display officer cards -->
          <?php $i = 0;
          foreach ($data['officers'] as $officer) : ?>
            <div class="officer-card">
              <div class="officer-section-one">
                <img src="data:<?php $encodedImage = base64_encode($officer->profile_photo);
                                $imageMimeType = "image/jpeg";
                                echo $imageMimeType; ?>;base64,<?php echo $encodedImage; ?>" alt="profile image" class="dp-image" />
                <h3 class="officer-name"><?php echo htmlspecialchars($officer->first_name . ' ' . $officer->last_name); ?></h3>
                <h3 class="officer-id"><?php echo htmlspecialchars($officer->officer_id); ?></h3>
                <h3 class="allocated-parking"><?php if (empty($officer->parking_id)) {
                                                echo "Not assigned";
                                              } else {
                                                echo htmlspecialchars($officer->parking_name);
                                              } ?></h3>
                <?php
                $today = $midnight_timestamp = strtotime('today midnight');
                $duty_records = $data['duty_records'][$i];
                if (count($duty_records) == 2) {
                  if ($duty_records[0]->type == 'in' && $today < $duty_records[0]->time_stamp) {
                    echo '<p class="parking-type bg-green text-white font-semibold f-14">On Duty</p>';
                  } else if ($duty_records[0]->type == 'out') {
                    echo '<p class="parking-type bg-red text-white font-semibold f-14">Off Duty</p>';
                  } else if ($duty_records[0]->time_stamp < $today) {
                    echo '<p class="parking-type bg-red text-white font-semibold f-14">Off Duty</p>';
                  }
                } else if (count($duty_records) == 1) {
                  if ($duty_records[0]->type == 'in' && $today < $duty_records[0]->time_stamp) {
                    echo '<p class="parking-type bg-green text-white font-semibold f-14">On Duty</p>';
                  } else if ($duty_records[0]->type == 'out') {
                    echo '<p class="parking-type bg-red text-white font-semibold f-14">Off Duty</p>';
                  }
                } else {
                  echo '<p class="parking-type bg-red text-white font-semibold f-14">Off Duty</p>';
                };
                $i++; ?>
              </div>
              <div class="officer-section-second">
                <p class="text-gray">NIC <?php echo htmlspecialchars($officer->nic); ?></p>
                <p class="officer-number text-gray">
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 28 28" fill="currentColor" class="button-icon mr-5">
                    <path fill-rule="evenodd" d="M1.5 4.5a3 3 0 013-3h1.372c.86 0 1.61.586 1.819 1.42l1.105 4.423a1.875 1.875 0 01-.694 1.955l-1.293.97c-.135.101-.164.249-.126.352a11.285 11.285 0 006.697 6.697c.103.038.25.009.352-.126l.97-1.293a1.875 1.875 0 011.955-.694l4.423 1.105c.834.209 1.42.959 1.42 1.82V19.5a3 3 0 01-3 3h-2.25C8.552 22.5 1.5 15.448 1.5 6.75V4.5z" clip-rule="evenodd" />
                  </svg>
                  <?php echo htmlspecialchars($officer->mobile_number); ?>
                </p>
                <div class="flex">
                  <a href="./parkingOfficerEditView/<?php echo $officer->officer_id ?>" class="mr-10" title="Edit Details">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 28 28" fill="currentColor" class="menu-logo edit">
                      <path d="M21.731 2.269a2.625 2.625 0 00-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 000-3.712zM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 00-1.32 2.214l-.8 2.685a.75.75 0 00.933.933l2.685-.8a5.25 5.25 0 002.214-1.32l8.4-8.4z" />
                      <path d="M5.25 5.25a3 3 0 00-3 3v10.5a3 3 0 003 3h10.5a3 3 0 003-3V13.5a.75.75 0 00-1.5 0v5.25a1.5 1.5 0 01-1.5 1.5H5.25a1.5 1.5 0 01-1.5-1.5V8.25a1.5 1.5 0 011.5-1.5h5.25a.75.75 0 000-1.5H5.25z" />
                    </svg>
                  </a>

                  <a href="./parkingOfficerActivitiesView/<?php echo $officer->officer_id ?>" class="mr-10" title="View Activities">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 28 28" fill="currentColor" class="menu-logo view">
                      <path d="M12 15a3 3 0 100-6 3 3 0 000 6z" />
                      <path fill-rule="evenodd" d="M1.323 11.447C2.811 6.976 7.028 3.75 12.001 3.75c4.97 0 9.185 3.223 10.675 7.69.12.362.12.752 0 1.113-1.487 4.471-5.705 7.697-10.677 7.697-4.97 0-9.186-3.223-10.675-7.69a1.762 1.762 0 010-1.113zM17.25 12a5.25 5.25 0 11-10.5 0 5.25 5.25 0 0110.5 0z" clip-rule="evenodd" />
                    </svg>
                  </a>
                  <a href="./parkingOfficerAssignView/<?php echo $officer->officer_id ?>" class="mr-10" title="Assign to a Parking">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 28 28" stroke-width="1.5" stroke="currentColor" class="menu-logo assign">
                      <path stroke-linecap="round" stroke-linejoin="round" d="M9 6.75V15m6-6v8.25m.503 3.498l4.875-2.437c.381-.19.622-.58.622-1.006V4.82c0-.836-.88-1.38-1.628-1.006l-3.869 1.934c-.317.159-.69.159-1.006 0L9.503 3.252a1.125 1.125 0 00-1.006 0L3.622 5.689C3.24 5.88 3 6.27 3 6.695V19.18c0 .836.88 1.38 1.628 1.006l3.869-1.934c.317-.159.69-.159 1.006 0l4.994 2.497c.317.158.69.158 1.006 0z" />
                    </svg>
                  </a>

                  <a href="./parkingOfficerDeleteView/<?php echo $officer->officer_id ?>" class="mr-10" title="Delete Profile">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 28 28" fill="currentColor" class="menu-logo delete">
                      <path fill-rule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 013.878.512.75.75 0 11-.256 1.478l-.209-.035-1.005 13.07a3 3 0 01-2.991 2.77H8.084a3 3 0 01-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 01-.256-1.478A48.567 48.567 0 017.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 013.369 0c1.603.051 2.815 1.387 2.815 2.951zm-6.136-1.452a51.196 51.196 0 013.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 00-6 0v-.113c0-.794.609-1.428 1.364-1.452zm-.355 5.945a.75.75 0 10-1.5.058l.347 9a.75.75 0 101.499-.058l-.346-9zm5.48.058a.75.75 0 10-1.498-.058l-.347 9a.75.75 0 001.5.058l.345-9z" clip-rule="evenodd" />
                    </svg>
                  </a>
                </div>
              </div>
            </div>
          <?php endforeach; ?>

        </div>

      </div>
    </div>
  </div>
  <script>
    function filterOfficers() {
      var input, filter, cards, card, officerName, i;
      input = document.getElementById("searchInput");
      filter = input.value.toUpperCase();
      cards = document.getElementById("officerCards");
      card = cards.getElementsByClassName("officer-card");

      for (i = 0; i < card.length; i++) {
        officerName = card[i].getElementsByClassName("officer-name")[0];
        if (officerName.innerHTML.toUpperCase().indexOf(filter) > -1) {
          card[i].style.display = "";
        } else {
          card[i].style.display = "none";
        }
      }
    }
  </script>
</body>

</html>