<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="refresh" content="15" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/dashboardView.css" />
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/parkingSpaceView.css" />
  <title>Dashboard</title>
</head>

<body>
  <div class="container">
    <div class="left-container">

      <div class="bg-div">
        <img src="<?php echo URLROOT; ?>/css/assets/logo-black.png" alt="logo" width="100%" />
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
            <li>
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
          <h3>Dashboard</h3>
        </div>

        <div class="profile">
          <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
          <a href="../users/logout" class="logout">Log out</a>
        </div>
      </div>
      <div class="flex right-section">
        <div class="card-section-col-1">
          <div class="card-section-row-1 flex ml-20">
            <div class="b-card bg-dark-gray b-card-1 mr-20">
              <div class="b-card-icon bg-light-gray">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo text-light-yellow">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v12m-3-2.818l.879.659c1.171.879 3.07.879 4.242 0 1.172-.879 1.172-2.303 0-3.182C13.536 12.219 12.768 12 12 12c-.725 0-1.45-.22-2.003-.659-1.106-.879-1.106-2.303 0-3.182s2.9-.879 4.006 0l.415.33M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div class="b-card-content text-light-yellow">
                <h3>Rs: <?php if ($data['monthlyEarned'] != 0) {
                          echo $data['monthlyEarned'];
                        } else {
                          echo "0";
                        } ?>.00</h3>
                <p>Earned this month</p>
              </div>
            </div>
            <div class="b-card bg-dark-gray b-card-2">
              <div class="b-card-icon bg-light-gray">
                <span class="material-symbols-outlined menu-logo text-light-yellow">
                  group
                </span>
              </div>
              <div class="b-card-content text-light-yellow">
                <h3><?php echo $data['numberOfUsers'] ?></h3>
                <p>Monthly Users</p>
              </div>
            </div>
          </div>
          <div class="card-section-row-2 section-one-left bar-graph mt-20">
            <canvas id="parkingSessions"></canvas>
          </div>
          <div class="card-section-row-3 section-two-left bar-graph mt-20 mb-10">
            <canvas id="revenue"></canvas>
          </div>
        </div>
        <div class="card-section-col-2">
          <div class="card-section-row-1 ml-20">
            <div class="b-card bg-dark-gray">
              <div class="b-card-icon bg-light-gray">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo text-light-yellow">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v12m-3-2.818l.879.659c1.171.879 3.07.879 4.242 0 1.172-.879 1.172-2.303 0-3.182C13.536 12.219 12.768 12 12 12c-.725 0-1.45-.22-2.003-.659-1.106-.879-1.106-2.303 0-3.182s2.9-.879 4.006 0l.415.33M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div class="b-card-content text-light-yellow">
                <h3>Rs: <?php if ($data['todayEarned'] != 0) {
                          echo $data['todayEarned'];
                        } else {
                          echo "0";
                        } ?>.00</h3>
                <p>Earned today</p>
              </div>
            </div>
          </div>

          <div class="card-section-row-2 section-one-right pie-chart mt-20">
            <canvas id="myPieChart"></canvas>
          </div>
          <div class="card-section-row-3 section-two-right latest-reviews mt-20 mb-10">
            <h1 class="review-heading mb-10">Recent Driver Reviews</h1>
            <div class="content-body">
            </div>
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

  <!-- pie chart for vehicle types -->
  <!-- <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Fetch data for the pie chart (replace with your actual data fetching logic)
      const data = generateRandomData();

      // Create a pie chart
      const ctx = document.getElementById('myPieChart').getContext('2d');
      const myPieChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: data.labels,
          datasets: [{
            data: data.values,
            backgroundColor: [
              '#cb99c9',
              '#77dd77',
              '#fdfd96',
              '#ffb347',
              '#ff6961'
            ]
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false
        }
      });

      // Function to generate random data (replace with your actual data fetching logic)
      function generateRandomData() {
        const data = <?php echo json_encode($data['activities']); ?>;

        const labels = data.map(officer => officer.first_name + ' ' + officer.last_name);
        const values = data.map(officer => officer.no_of_activities);
        return {
          labels,
          values
        };
      }
    });
  </script> -->
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Fetch data for the pie chart (replace with your actual data fetching logic)
      const dummyData = [{
          first_name: 'Officer1',
          last_name: 'Last1',
          no_of_activities: 20
        },
        {
          first_name: 'Officer2',
          last_name: 'Last2',
          no_of_activities: 15
        },
        {
          first_name: 'Officer3',
          last_name: 'Last3',
          no_of_activities: 12
        },
        {
          first_name: 'Officer4',
          last_name: 'Last4',
          no_of_activities: 10
        },
        {
          first_name: 'Officer5',
          last_name: 'Last5',
          no_of_activities: 8
        },
        {
          first_name: 'Officer6',
          last_name: 'Last6',
          no_of_activities: 5
        },
        {
          first_name: 'Officer7',
          last_name: 'Last7',
          no_of_activities: 3
        },
        {
          first_name: 'Officer8',
          last_name: 'Last8',
          no_of_activities: 2
        },
      ];

      const data = generateRandomData();

      // Create a pie chart
      const ctx = document.getElementById('myPieChart').getContext('2d');
      const myPieChart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: data.labels,
          datasets: [{
            data: data.values,
            backgroundColor: [
              '#cb99c9',
              '#77dd77',
              '#fdfd96',
              '#ffb347',
              '#ff6961',
              '#4E4E4E' // color for "Others"
            ]
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false
        }
      });

      // Function to generate data for the pie chart
      function generateRandomData() {
        //replace with in php tag echo json_encode($data['activities']);
        const data = <?php echo json_encode($data['activities']); ?>;
        // const data = dummyData;

        // Sort the data by activity count in descending order
        data.sort((a, b) => b.no_of_activities - a.no_of_activities);

        // Extract top 5 officers
        const top5Officers = data.slice(0, 5);

        if (data.length > 5) {
          const otherOfficers = data.slice(5);
          const othersTotalCount = otherOfficers.reduce((sum, officer) => sum + officer.no_of_activities, 0);

          // Combine top 5 officers and "Others"
          const combinedData = [...top5Officers, {
            first_name: 'Others',
            last_name: '',
            no_of_activities: othersTotalCount
          }];

          const labels = combinedData.map(officer => officer.first_name + ' ' + officer.last_name);
          const values = combinedData.map(officer => officer.no_of_activities);

          return {
            labels,
            values
          };
        } else {
          // If there are 5 or fewer officers, use the original data
          const labels = top5Officers.map(officer => officer.first_name + ' ' + officer.last_name);
          const values = top5Officers.map(officer => officer.no_of_activities);

          return {
            labels,
            values
          };
        }
      }
    });
  </script>


  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <script src="<?php echo URLROOT; ?>/js/company/dashboard.js"></script>
</body>

</html>