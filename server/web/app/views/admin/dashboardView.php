<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <!-- <meta http-equiv="refresh" content="15" /> -->
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
                  <path strokeLinecap="round" strokeLinejoin="round" d="M20.25 8.511c.884.284 1.5 1.128 1.5 2.097v4.286c0 1.136-.847 2.1-1.98 2.193-.34.027-.68.052-1.02.072v3.091l-3-3c-1.354 0-2.694-.055-4.02-.163a2.115 2.115 0 0 1-.825-.242m9.345-8.334a2.126 2.126 0 0 0-.476-.095 48.64 48.64 0 0 0-8.048 0c-1.131.094-1.976 1.057-1.976 2.192v4.286c0 .837.46 1.58 1.155 1.951m9.345-8.334V6.637c0-1.621-1.152-3.026-2.76-3.235A48.455 48.455 0 0 0 11.25 3c-2.115 0-4.198.137-6.24.402-1.608.209-2.76 1.614-2.76 3.235v6.226c0 1.621 1.152 3.026 2.76 3.235.577.075 1.157.14 1.74.194V21l4.155-4.155" />
                </svg>
                Driver Reviews
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
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo">
            <path fillRule="evenodd" d="M3 6.75A.75.75 0 013.75 6h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 6.75zM3 12a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 12zm0 5.25a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75a.75.75 0 01-.75-.75z" clipRule="evenodd" />
          </svg>

          <h3 class="ml-5">Dashboard</h3>
        </div>

        <div class="profile">

          <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
          <a href="../users/logout" class="logout">Log out</a>

        </div>
      </div>

      <div class="card-section1 ">
        <div class="section-left">
          <div class="b-card ">
            <div class="b-card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
                <path fillRule="evenodd" d="M3 2.25a.75.75 0 000 1.5v16.5h-.75a.75.75 0 000 1.5H15v-18a.75.75 0 000-1.5H3zM6.75 19.5v-2.25a.75.75 0 01.75-.75h3a.75.75 0 01.75.75v2.25a.75.75 0 01-.75.75h-3a.75.75 0 01-.75-.75zM6 6.75A.75.75 0 016.75 6h.75a.75.75 0 010 1.5h-.75A.75.75 0 016 6.75zM6.75 9a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM6 12.75a.75.75 0 01.75-.75h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 6a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zm-.75 3.75A.75.75 0 0110.5 9h.75a.75.75 0 010 1.5h-.75a.75.75 0 01-.75-.75zM10.5 12a.75.75 0 000 1.5h.75a.75.75 0 000-1.5h-.75zM16.5 6.75v15h5.25a.75.75 0 000-1.5H21v-12a.75.75 0 000-1.5h-4.5zm1.5 4.5a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008zm.75 2.25a.75.75 0 00-.75.75v.008c0 .414.336.75.75.75h.008a.75.75 0 00.75-.75v-.008a.75.75 0 00-.75-.75h-.008zM18 17.25a.75.75 0 01.75-.75h.008a.75.75 0 01.75.75v.008a.75.75 0 01-.75.75h-.008a.75.75 0 01-.75-.75v-.008z" clipRule="evenodd" />
              </svg>

            </div>
            <div class="b-card-content text-black">

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
            <div class="b-card-content text-black">

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
            <div class="b-card-content text-black">

              <!-- Check if $userCount is defined before using it -->
              <p>Drivers</p>
              <div class="b-card-content2 text-black">
                <?php if (isset($data['userCount'])) : ?>
                  <p> <?php echo $data['userCount']; ?></p>
                <?php endif; ?>
              </div>
            </div>
          </div>
        </div>
        <div class="section-right">
          <div class="b-card2 ">
            <div class="b-card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="r-menu-logo">
                <path d="M10.464 8.746c.227-.18.497-.311.786-.394v2.795a2.252 2.252 0 0 1-.786-.393c-.394-.313-.546-.681-.546-1.004 0-.323.152-.691.546-1.004ZM12.75 15.662v-2.824c.347.085.664.228.921.421.427.32.579.686.579.991 0 .305-.152.671-.579.991a2.534 2.534 0 0 1-.921.42Z" />
                <path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25ZM12.75 6a.75.75 0 0 0-1.5 0v.816a3.836 3.836 0 0 0-1.72.756c-.712.566-1.112 1.35-1.112 2.178 0 .829.4 1.612 1.113 2.178.502.4 1.102.647 1.719.756v2.978a2.536 2.536 0 0 1-.921-.421l-.879-.66a.75.75 0 0 0-.9 1.2l.879.66c.533.4 1.169.645 1.821.75V18a.75.75 0 0 0 1.5 0v-.81a4.124 4.124 0 0 0 1.821-.749c.745-.559 1.179-1.344 1.179-2.191 0-.847-.434-1.632-1.179-2.191a4.122 4.122 0 0 0-1.821-.75V8.354c.29.082.559.213.786.393l.415.33a.75.75 0 0 0 .933-1.175l-.415-.33a3.836 3.836 0 0 0-1.719-.755V6Z" clip-rule="evenodd" />
              </svg>
            </div>
            <div class="b-card-content text-black">
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
        <div class="b-card3 section-left">
          <canvas id="parkingSessions"></canvas>
        </div>
        <div class="section-right">
          <div class="b-card4">
            <div class="b2-card-content text-black">
              <p>Application Requests</p>
            </div>
            <div class="b-card-small">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="db-menu-logo text-primary mt-0">
                <path fill-rule="evenodd" d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12ZM12 8.25a.75.75 0 0 1 .75.75v3.75a.75.75 0 0 1-1.5 0V9a.75.75 0 0 1 .75-.75Zm0 8.25a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Z" clip-rule="evenodd" />
              </svg>
              <div class="b3-card-content text-white">

                <?php if (isset($data['totalPendingApplications'])) : ?>
                  <?php if ($data['totalPendingApplications'] === 0) : ?>
                    <p>You have no new company applications to review</p>
                  <?php elseif ($data['totalPendingApplications'] === 1) : ?>
                    <p>You have 1 new company application to review</p>
                  <?php else : ?>
                    <p>You have <?php echo $data['totalPendingApplications']; ?> new company applications to review</p>
                  <?php endif; ?>
                <?php endif; ?>
              </div>
            </div>
          </div>

          <div class="b-card5">
            <div class="b2-card-content text-black">
              <p>Suspended Companies</p>
            </div>
            <div class="b-card-small">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#00A372" class="db-menu-logo text-green">
                <path fillRule="evenodd" d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12zm13.36-1.814a.75.75 0 10-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 00-1.06 1.06l2.25 2.25a.75.75 0 001.14-.094l3.75-5.25z" clipRule="evenodd" />
              </svg>
              <div class="b3-card-content text-white">

                <?php if (isset($data['totalSuspendApplications'])) : ?>
                  <?php if ($data['totalSuspendApplications'] === 0) : ?>
                    <p>Currently, no company has been suspended</p>
                  <?php elseif ($data['totalSuspendApplications'] === 1) : ?>
                    <p>Currently, one company has been suspended</p>
                  <?php else : ?>
                    <p>Currently <?php echo $data['totalSuspendApplications']; ?> companies have been suspended</p>
                  <?php endif; ?>
                <?php endif; ?>
              </div>
            </div>
          </div>
        </div>

      </div>

      <div class="card-section3 ">
        <div class="b-card3 section-left">
          <canvas id="revenue"></canvas>
        </div>
        <div class="b-card6 section-right latest-reviews">
          <h1 class="review-heading">Recent Driver Reviews</h1>
          <div class="content-body">
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

      <!-- bar graph for number of parking sessions -->
      <script>
        document.addEventListener('DOMContentLoaded', function() {
          // Replace this with your actual server response data
          const responseData = <?php echo json_encode($data['parkingSessions']); ?>;

          // Extract dates and values from the response
          const dates = Object.keys(responseData);
          const values = Object.values(responseData);

          // Format dates to show only the day
          const formattedDates = dates.map(date => {
            const formattedDate = new Date(date);
            const month = formattedDate.getMonth() + 1; // Months are zero-based
            const day = formattedDate.getDate();
            return `${month}-${day}`;
          });

          // Create a bar chart
          const ctx = document.getElementById('parkingSessions').getContext('2d');
          const myChart = new Chart(ctx, {
            type: 'bar',
            data: {
              labels: formattedDates,
              datasets: [{
                label: 'Number of Parking Sessions Last 30 Days',
                data: values,
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
        });
      </script>


      <!-- bar graph for revenue -->
      <script>
        document.addEventListener('DOMContentLoaded', function() {
          // Replace the data variable with your actual data
          const rawData = <?php echo json_encode($data['revenues']); ?>;

          // Convert raw data to an array of objects
          const data = Object.entries(rawData).map(([date, value]) => ({
            date,
            value
          }));

          // Extract dates and values from the data
          const dates = data.map(entry => entry.date);
          const values = data.map(entry => entry.value);

          const formattedDates = dates.map(date => {
            const formattedDate = new Date(date);
            const month = formattedDate.getMonth() + 1; // Months are zero-based
            const day = formattedDate.getDate();
            return `${month}-${day}`;
          });

          // Create a bar chart
          const ctx = document.getElementById('revenue').getContext('2d');
          const myChart = new Chart(ctx, {
            type: 'bar',
            data: {
              labels: formattedDates,
              datasets: [{
                label: 'Revenue from Parking Spaces Last 30 Days',
                data: values,
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
        });
      </script>

      <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


</body>

</html>