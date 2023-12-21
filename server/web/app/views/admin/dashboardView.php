<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/admin/dashboardView.css" />
  <title>Dashboard</title>
</head>

<body>
  <div class="container">
    <div class="left-container">
      <div class="bg-div">
        <img src="<?php echo URLROOT; ?>css/assets/logo-black.png" alt="logo" width="85%" />
        <div>
          <ul class="menu">
            <li class="active">
              <a href="./dashboardView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M15.59 14.37a6 6 0 01-5.84 7.38v-4.8m5.84-2.58a14.98 14.98 0 006.16-12.12A14.98 14.98 0 009.631 8.41m5.96 5.96a14.926 14.926 0 01-5.841 2.58m-.119-8.54a6 6 0 00-7.381 5.84h4.8m2.581-5.84a14.927 14.927 0 00-2.58 5.84m2.699 2.7c-.103.021-.207.041-.311.06a15.09 15.09 0 01-2.448-2.448 14.9 14.9 0 01.06-.312m-2.24 2.39a4.493 4.493 0 00-1.757 4.306 4.493 4.493 0 004.306-1.758M16.5 9a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z" />
                </svg>
                Dashboard
              </a>
            </li>
            <li>
              <a href="./requestsView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M7.5 8.25h9m-9 3H12m-9.75 1.51c0 1.6 1.123 2.994 2.707 3.227 1.129.166 2.27.293 3.423.379.35.026.67.21.865.501L12 21l2.755-4.133a1.14 1.14 0 01.865-.501 48.172 48.172 0 003.423-.379c1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0012 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018z" />
                </svg>
                Requests
              </a>
            </li>
            <li>
              <a href="./requestsHistoryView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                Request History
              </a>
            </li>
            <li>
              <a href="./companiesView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M2.25 21h19.5m-18-18v18m10.5-18v18m6-13.5V21M6.75 6.75h.75m-.75 3h.75m-.75 3h.75m3-6h.75m-.75 3h.75m-.75 3h.75M6.75 21v-3.375c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21M3 3h12m-.75 4.5H21m-3.75 3.75h.008v.008h-.008v-.008zm0 3h.008v.008h-.008v-.008zm0 3h.008v.008h-.008v-.008z" />
                </svg>
                Companies
              </a>
            </li>
            <li>
              <a href="./deletionView">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M18 18.72a9.094 9.094 0 003.741-.479 3 3 0 00-4.682-2.72m.94 3.198l.001.031c0 .225-.012.447-.037.666A11.944 11.944 0 0112 21c-2.17 0-4.207-.576-5.963-1.584A6.062 6.062 0 016 18.719m12 0a5.971 5.971 0 00-.941-3.197m0 0A5.995 5.995 0 0012 12.75a5.995 5.995 0 00-5.058 2.772m0 0a3 3 0 00-4.681 2.72 8.986 8.986 0 003.74.477m.94-3.197a5.971 5.971 0 00-.94 3.197M15 6.75a3 3 0 11-6 0 3 3 0 016 0zm6 3a2.25 2.25 0 11-4.5 0 2.25 2.25 0 014.5 0zm-13.5 0a2.25 2.25 0 11-4.5 0 2.25 2.25 0 014.5 0z" />
                </svg>
                Suspend
              </a>
            </li>
            <li>
              <a href="./driverReviews">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="menu-logo">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M7.5 8.25h9m-9 3H12m-9.75 1.51c0 1.6 1.123 2.994 2.707 3.227 1.129.166 2.27.293 3.423.379.35.026.67.21.865.501L12 21l2.755-4.133a1.14 1.14 0 01.865-.501 48.172 48.172 0 003.423-.379c1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0012 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018z" />
                </svg>
                Driver Reviews
              </a>
            </li>

          </ul>
        </div>
      </div>
    </div>
    <div class="right-container">
      <div class="header">
        <div class="pageName">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo">
            <path fillRule="evenodd" d="M3 6.75A.75.75 0 013.75 6h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 6.75zM3 12a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 12zm0 5.25a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75a.75.75 0 01-.75-.75z" clipRule="evenodd" />
          </svg>

          <h3>Dashboard</h3>
        </div>

        <div class="profile">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo mr">
            <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0" />
          </svg>

          <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
          <a href="../users/logout" class="logout">Log out</a>

        </div>
      </div>
      <div class="business">
        <!-- <div class="heading">
          <span class="material-symbols-outlined menu-icon ">
            monitoring
          </span>
          <h4>Status</h4>
        </div>-->
        <div class="card-section ">
          <div class="b-card ">
            <div class="b-card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
                <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
              </svg>

            </div>
            <div class="b-card-content text-bla">

              <!-- Check if $companyCount is defined before using it -->
              <p>Companies</p>
              <div class="b-card-content2 text-black">
                <?php if (isset($data['companiesCount'])) : ?>
                  <p> <?php echo $data['companiesCount']; ?></p>
                <?php endif; ?>
              </div>
            </div>
          </div>
          <div class="b-card">
            <div class="b-card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
                <path d="M4.5 6.375a4.125 4.125 0 118.25 0 4.125 4.125 0 01-8.25 0zM14.25 8.625a3.375 3.375 0 116.75 0 3.375 3.375 0 01-6.75 0zM1.5 19.125a7.125 7.125 0 0114.25 0v.003l-.001.119a.75.75 0 01-.363.63 13.067 13.067 0 01-6.761 1.873c-2.472 0-4.786-.684-6.76-1.873a.75.75 0 01-.364-.63l-.001-.122zM17.25 19.128l-.001.144a2.25 2.25 0 01-.233.96 10.088 10.088 0 005.06-1.01.75.75 0 00.42-.643 4.875 4.875 0 00-6.957-4.611 8.586 8.586 0 011.71 5.157v.003z" />
              </svg>
            </div>
            <div class="b-card-content text-bla">

              <!-- Check if $parkingOfficersCount is defined before using it -->
              <p>Parking Officers</p>
              <div class="b-card-content2 text-black">
                <?php if (isset($data['parkingOfficersCount'])) : ?>
                  <p> <?php echo $data['parkingOfficersCount'];; ?></p>
                <?php endif; ?>
              </div>
            </div>
          </div>
          <div class="b-card bg-white">
            <div class="b-card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
                <path fillRule="evenodd" d="M8.25 6.75a3.75 3.75 0 117.5 0 3.75 3.75 0 01-7.5 0zM15.75 9.75a3 3 0 116 0 3 3 0 01-6 0zM2.25 9.75a3 3 0 116 0 3 3 0 01-6 0zM6.31 15.117A6.745 6.745 0 0112 12a6.745 6.745 0 016.709 7.498.75.75 0 01-.372.568A12.696 12.696 0 0112 21.75c-2.305 0-4.47-.612-6.337-1.684a.75.75 0 01-.372-.568 6.787 6.787 0 011.019-4.38z" clipRule="evenodd" />
                <path d="M5.082 14.254a8.287 8.287 0 00-1.308 5.135 9.687 9.687 0 01-1.764-.44l-.115-.04a.563.563 0 01-.373-.487l-.01-.121a3.75 3.75 0 013.57-4.047zM20.226 19.389a8.287 8.287 0 00-1.308-5.135 3.75 3.75 0 013.57 4.047l-.01.121a.563.563 0 01-.373.486l-.115.04c-.567.2-1.156.349-1.764.441z" />
              </svg>
            </div>
            <div class="b-card-content text-bla">

              <!-- Check if $userCount is defined before using it -->
              <p>Drivers</p>
              <div class="b-card-content2 text-black">
                <?php if (isset($data['userCount'])) : ?>
                  <p> <?php echo $data['userCount']; ?></p>
                <?php endif; ?>
              </div>
            </div>
          </div>

          <div class="b-card2">
            <div class="b-card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" class="revenue-menu-logo">
                <path strokeLinecap="round" strokeLinejoin="round" d="M12 6v12m-3-2.818l.879.659c1.171.879 3.07.879 4.242 0 1.172-.879 1.172-2.303 0-3.182C13.536 12.219 12.768 12 12 12c-.725 0-1.45-.22-2.003-.659-1.106-.879-1.106-2.303 0-3.182s2.9-.879 4.006 0l.415.33M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>

            </div>
            <div class="b-card-content text-bla">

              <!-- Check if $parkingOfficersCount is defined before using it -->
              <p>Total Revenue</p>
              <div class="b-card-content2 text-black">
                <?php if (isset($data['totalRevenue'])) : ?>
                  <p>Rs.<?php echo $data['totalRevenue']; ?></p>
                <?php endif; ?>
              </div>
            </div>
          </div>

        </div>
      </div>
      <div class="card-section2 ">
        <div class="b-card3 ">
          <div class="b2-card-content text-bla">
            <p>Number of parking sessions</p>
            <canvas id="parkingSessionsChart" width="400" height="200"></canvas>  
            
          </div>
        </div>

        <div class="b-card4">
          <div class="b2-card-content text-bla">
            <p>Application Requests</p>
          </div>
          <div class="b-card-small">

            <span class="material-symbols-outlined">

              error
            </span>
<!--<p>You have 03 new company applications to review</p> -->
<div class="b3-card-content text-white">

<p>You have 04 new company applications to review</p> 


</div>

          </div>
        </div>

        <div class="b-card5">
          <div class="b2-card-content text-bla">
            <p>Suspended Companies</p>
          </div>
          <div class="b-card-small">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#00A372" class="db-menu-logo">
              <path fillRule="evenodd" d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12zm13.36-1.814a.75.75 0 10-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 00-1.06 1.06l2.25 2.25a.75.75 0 001.14-.094l3.75-5.25z" clipRule="evenodd" />
            </svg>
            <div class="b3-card-content text-white">

<p>Currently, no company has been suspended</p> 


</div>


          </div>
        </div>

      </div>

      <div class="card-section3 ">
        <div class="b-card3 ">
          <div class="b2-card-content text-bla">
            <p>Subscription revenue for last 30 days</p>
            <canvas id="revenueChart" width="400" height="200"></canvas>  
          </div>

        </div>
        <div class="b-card6 ">
          <div class="b2-card-content text-bla">
            <p>Recent Driver Reviews</p>
          </div>

          <!-- Include the reviews here -->
  <div class="content-body">
    <!-- Reviews script will populate content here -->
     <!-- Reviews -->
  <div class="latest-reviews">
    <div class="dashboard-review-head">
      <p class="dashboard-driver-name">John Doe</p>
      <div class="driver-rating">
      <div class="star-rating" id="starRating">
        <!-- Stars SVG -->
        <!-- Sample: Full stars -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#000000" class="w-6 h-6 text-primary">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
        <!-- Sample: Empty stars -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#000000" class="w-6 h-6 text-light-gray">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
         <!-- Sample: Empty stars -->
         <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#000000" class="w-6 h-6 text-light-gray">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
         <!-- Sample: Empty stars -->
         <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#808080" class="w-6 h-6 text-light-gray">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
         <!-- Sample: Empty stars -->
         <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#808080" class="w-6 h-6 text-light-gray">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
        
        <!-- Adjust stars based on rating -->
        <!-- Add or remove these SVG elements dynamically using JavaScript -->
      </div>
    </div>
    </div>
    <p class="review-parking-name">CMC CAR PARK 01</p>
    <p class="review-content">The parking area was clean and well-maintained, contributing to a positive overall experience.</p>
    <div class="horizontal-line"></div>
  </div>

  <div class="custom-line">
                    <hr style="border: 1px solid #00000045;">
                </div>
  
  <!-- Add more reviews following the same structure -->
  <div class="content-body">
    <!-- Reviews script will populate content here -->
     <!-- Reviews -->
  <div class="latest-reviews">
    <div class="dashboard-review-head">
      <p class="dashboard-driver-name">Ross Geller</p>
      <div class="driver-rating">
      <div class="star-rating" id="starRating">
        <!-- Stars SVG -->
        <!-- Sample: Full stars -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#000000" class="w-6 h-6 text-primary">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
        <!-- Sample: Empty stars -->
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#000000" class="w-6 h-6 text-light-gray">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
         <!-- Sample: Empty stars -->
         <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#000000" class="w-6 h-6 text-light-gray">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
         <!-- Sample: Empty stars -->
         <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#808080" class="w-6 h-6 text-light-gray">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
         <!-- Sample: Empty stars -->
         <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#808080" class="w-6 h-6 text-light-gray">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
        
        <!-- Adjust stars based on rating -->
        <!-- Add or remove these SVG elements dynamically using JavaScript -->
      </div>
    </div>
    </div>
    <p class="review-parking-name">CMC CAR PARK 01</p>
    <p class="review-content">The parking area was clean and well-maintained, contributing to a positive overall experience.</p>
    <div class="horizontal-line"></div>
  </div>
    
  </div>  

        </div>
      </div>
      <div class="dashboard-main-section">
        <div class="update-section">
          <div class="table-heading">
            <div class="heading">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- reviews -->
  <script>
    document.addEventListener('DOMContentLoaded', function() {

      const popupContentBody = document.querySelector('.content-body');
      const reviewsData = <?php echo json_encode($data['reviews']); ?>;

      function generateStars(rating) {
        const fullStars = Math.floor(rating);
        const emptyStars = 5 - fullStars;

        const stars = Array(fullStars).fill(`
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6 text-primary" width="15px">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
      `);

        stars.push(...Array(emptyStars).fill(`
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6 text-light-gray" width="15px">
          <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
        </svg>
      `));

        return stars.join('');
      }


      popupContentBody.innerHTML = '';

      reviewsData.forEach(review => {
        const card = document.createElement('div');
        card.classList.add('latest-reviews');

        const starsSVG = generateStars(review.no_of_stars);

        card.innerHTML = `
            <div class="dashboard-review-head">
              <h2 class="dashboard-driver-name">${review.driver_first_name} ${review.driver_last_name}</h2>
              <div class="star-rating" id="starRating">
                ${starsSVG}
              </div>
            </div>
            <h3 class="review-parking-name">${review.parking_name}</h3>
            <p class="review-content">${review.content}</p>
            <div class="horizontal-line"></div>
          `;

        popupContentBody.appendChild(card);
      });
    });
  </script>
<!--  <script>
  document.addEventListener('DOMContentLoaded', function() {
    // Fetch data for the last 30 days (replace with your actual data fetching logic)
    const data = generateRandomData(30);

    // Create a bar chart
    const ctx = document.getElementById('parkingSessionsChart').getContext('2d');
    const myChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: data.dates,
        datasets: [{
          label: 'Number of Parking Sessions Last 30 Days',
          data: data.values,
          backgroundColor: '#363636',
          borderColor: '#363636',
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });

    // Function to generate random data for testing purposes
    function generateRandomData(days) {
      const dates = [];
      const values = [];
      const currentDate = new Date();
      currentDate.setDate(currentDate.getDate() - days);

      for (let i = 0; i < days; i++) {
        dates.push(currentDate.toLocaleDateString());
        values.push(Math.floor(Math.random() * 100)); // Generating random values (replace with your actual data)
        currentDate.setDate(currentDate.getDate() + 1);
      }

      return { dates, values };
    }
  });
</script>-->
<script>
  document.addEventListener('DOMContentLoaded', function() {
    // Fetch data for the last 24 hours
    const data = generateRandomData(24);

    // Create a bar chart for the last 24 hours
    const ctx = document.getElementById('parkingSessionsChart').getContext('2d');
    const myChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: data.hours,
        datasets: [{
          label: 'Number of Parking Sessions (Past 24 Hours)',
          data: data.values,
          backgroundColor: '#363636',
          borderColor: '#363636',
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });

    // Function to generate random data for the last 24 hours
    function generateRandomData(hours) {
      const hoursData = [];
      const values = [];
      const currentDate = new Date();
      currentDate.setHours(currentDate.getHours() - hours + 1);

      for (let i = 0; i < hours; i++) {
        hoursData.push(currentDate.getHours());
        values.push(Math.floor(Math.random() * 100)); // Generating random values (replace with your actual data)
        currentDate.setHours(currentDate.getHours() + 1);
      }

      return { hours: hoursData, values };
    }
  });
</script>


<script>
            document.addEventListener('DOMContentLoaded', function() {
              // Fetch data for the last 30 days (replace with your actual data fetching logic)
              const data = generateRandomData(30);

              // Create a bar chart
              const ctx = document.getElementById('revenueChart').getContext('2d');
              const myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                  labels: data.dates,
                  datasets: [{
                    label: 'Revenue from Parking Spaces Last 30 Days',
                    data: data.values,
                    backgroundColor: '#363636',
                    borderColor: '#363636',
                    borderWidth: 1
                  }]
                },
                options: {
                  scales: {
                    y: {
                      beginAtZero: true
                    }
                  }
                }
              });

              // Function to generate random data for testing purposes
              function generateRandomData(days) {
                const dates = [];
                const values = [];
                const currentDate = new Date();
                currentDate.setDate(currentDate.getDate() - days);

                for (let i = 0; i < days; i++) {
                  dates.push(currentDate.toLocaleDateString());
                  values.push(Math.floor(Math.random() * 100)); // Generating random values (replace with your actual data)
                  currentDate.setDate(currentDate.getDate() + 1);
                }

                return { dates, values };
              }
            });
          </script>

  
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</body>

</html>