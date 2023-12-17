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
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo mr">
            <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0" />
          </svg>


          <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
          <a href="../users/logout" class="logout">Log out</a>
        </div>
      </div>
      <div class="business">
        <div class="heading">
          <span class="material-symbols-outlined menu-icon ">
            monitoring
          </span>
          <h4>Business Analysis</h4>
        </div>
        <div class="card-section">
          <div class="b-card bg-dark-gray">
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
          <div class="b-card bg-dark-gray">
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
      </div>
      <div class="section-one">
        <div class="section-one-left bar-graph mt--30">
          <canvas id="parkingSessions"></canvas>
        </div>
        <div class="section-one-right pie-chart mt--30">
          <canvas id="myPieChart"></canvas>
        </div>

      </div>
      <div class="section-two">
        <div class="section-two-left bar-graph">
          <canvas id="revenue"></canvas>
        </div>
      </div>
    </div>
  </div>
  </div>


  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Fetch data for the last 30 days (replace with your actual data fetching logic)
      const data = generateRandomData(30);

      // Create a bar chart
      const ctx = document.getElementById('parkingSessions').getContext('2d');
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

      // Function to generate random data (replace with your actual data fetching logic)
      function generateRandomData(days) {
        const startDate = new Date();
        startDate.setDate(startDate.getDate() - days + 1);

        const dates = [];
        const values = [];

        for (let i = 0; i < days; i++) {
          const date = new Date(startDate);
          date.setDate(startDate.getDate() + i);

          // Format the date to show only the day
          const formattedDate = date.getDate();
          dates.push(formattedDate);

          // Generate random values for demonstration purposes
          values.push(Math.floor(Math.random() * 100));
        }

        return {
          dates,
          values
        };
      }
    });
  </script>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Fetch data for the last 30 days (replace with your actual data fetching logic)
      const data = generateRandomData(30);

      // Create a bar chart
      const ctx = document.getElementById('revenue').getContext('2d');
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

      // Function to generate random data (replace with your actual data fetching logic)
      function generateRandomData(days) {
        const startDate = new Date();
        startDate.setDate(startDate.getDate() - days + 1);

        const dates = [];
        const values = [];

        for (let i = 0; i < days; i++) {
          const date = new Date(startDate);
          date.setDate(startDate.getDate() + i);

          // Format the date to show only the day
          const formattedDate = date.getDate();
          dates.push(formattedDate);

          // Generate random values for demonstration purposes
          values.push(Math.floor(Math.random() * 100));
        }

        return {
          dates,
          values
        };
      }
    });
  </script>
  <script>
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
              '#FF6384',
              '#36A2EB',
              '#FFCE56',
              '#4CAF50',
              '#9C27B0',
              '#FF9800',
              '#795548'
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
        const labels = ['Category 1', 'Category 2', 'Category 3', 'Category 4', 'Category 5'];
        const values = labels.map(() => Math.floor(Math.random() * 100));
        return {
          labels,
          values
        };
      }
    });
  </script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>

</html>