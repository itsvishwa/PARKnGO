<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/admin/deletionView.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    

    <script>
        $(document).ready(function() {
            $('.arrow-logo').click(function(e) {
                e.stopPropagation();
                $(this).closest('.time-dropdown').toggleClass('show');
            });

            $(document).click(function() {
                $('.time-dropdown').removeClass('show');
            });
        });
    </script>

<!--<script>
    $(document).ready(function () {
        // Attach an event listener to the input field
        $('#companyIDInput').on('input', function () {
            var enteredID = $(this).val();

            // Hide all company cards
            $('.select-card').hide();
            

            // Show only the relevant company card based on the entered ID
            $('.select-card[data-company-id="' + enteredID + '"]').show();
        });
    });
</script>-->

<!-- Updated script in the head section of your HTML -->
<!-- Updated script in the head section of your HTML -->
<script>
    $(document).ready(function () {
        $('.companyIDInput').on('input', function () {
            var enteredID = $(this).val();

            // Log entered ID for debugging
            console.log('Entered Company ID:', enteredID);

            // Hide all company cards
            $('.select-card').hide();

            // Show only the relevant company card based on the entered ID
            var $selectedCard = $('.select-card[data-company-id="' + enteredID + '"]');
            
            if ($selectedCard.length > 0) {
                $selectedCard.show();
                console.log('Company Card Found:', enteredID);
            } else {
                console.log('Company Card Not Found:', enteredID);
            }
        });
    });
</script>



    <title>Suspend</title>
</head>

<body>
    <div class="container">
        <div class="left-container">
            <div class="bg-div">
                <img src="<?php echo URLROOT; ?>/css/assets/logo-black.png" alt="logo" width="85%" />
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
                        <li class="active">
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
                    <h3>Suspend</h3>
                </div>

                <div class="profile">
                   <!-- <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo mr">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0" />
                    </svg>-->
                    <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
                    <a href="../users/logout" class="logout">Log out</a>
                </div>
            </div>
            <div class="business">
            
                <div class="suspend-card1-content p text-black">
                    <p>Suspend a Company</p>
                </div>
                <div class="suspend-card2-content p text-black">
                    <p>Select the company you want to suspend</p>
                </div>
                <?php foreach ($data['approvedApplications'] as $application) : ?>
               <!-- <div class="select-card"> -->
                <div class="select-card" data-company-id="<?php echo $application->_id; ?>">
                    <div class="select-card-content p text-grey">
                        <!--<p>Enter Company ID</p> -->
                        <label for="companyIDInput">Enter Company ID</label>
                        
                        <input type="text" class="companyIDInput" name="companyID" placeholder="COMP<?php echo $application->_id; ?>">

                    </div>
                    <div class="arrow-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="grey" class="arrow-logo">
                            <path fillRule="evenodd" d="M12.53 16.28a.75.75 0 01-1.06 0l-7.5-7.5a.75.75 0 011.06-1.06L12 14.69l6.97-6.97a.75.75 0 111.06 1.06l-7.5 7.5z" clipRule="evenodd" />
                        </svg>
                    </div>
                </div>
                <div class="custom-line">
                    <hr style="border: 1px solid #00000045;">
                </div>
                
                <div class="colombo ">
                    <div class="colombo-content1 text-black">
                     <!--   <p>Colombo Municipal Council - CMC</p>-->
                        <p><?php echo $application->name; ?></p>
                    </div>
                    <div class="location-icon">
                        <span class="material-symbols-outlined">
                            location_on
                        </span>
                    </div>
                    <div class="colombo-content2 text-black">
                       <!-- <p>WV87+F7V,Colombo 00700</p> -->
                        <p><?php echo $application->address; ?></p>
                    </div>
                    <div class="public-card bg-green">
                        <div class="public-card-content p text-white">
                        <!--   <p>public</p> -->
                        <p><?php echo $application->public; ?></p>
                        </div>
                    </div>
                    <div class="colombo-content3 text-grey">
                       <!-- <p>ID COMP001A1</p>-->
                        <p>ID COMP<?php echo $application->_id; ?></p>
                    </div>
                    <div class="colombo-content4 text-black">
                        <p>Email</p>
                    </div>
                    <div class="colombo-content5 text-black">
                      <!--  <p>commisioner@colombo.mc.gov.lk</p>-->
                        <p><?php echo $application->email; ?></p>
                    </div>
                    <div class="colombo-content6 text-black">
                        <p>Telephone Number 01</p> 
                        
                    </div>
                    <div class="colombo-content7 text-black">
                       <!-- <p>(011) 2691191</p>-->
                        <p><?php echo $application->phone_number; ?></p>
                    </div>
                    <div class="colombo-content6 text-black">
                        <p>Telephone Number 02</p>
                    </div>
                    <div class="colombo-content7 text-black">
                        <p>(011) 3591591</p>
                    </div>
                    <div class="colombo-content8 text-black">
                        <p>Total Number of Parking Officers</p>
                    </div>
                    <div class="colombo-content9 text-black">
                       <!-- <p>15</p> -->
                        <?php
                        // Check if the property 'parkingOfficersCount' exists in the current application
                        if (property_exists($application, 'parkingOfficersCount')) {
                          $parkingOfficersCount = $application->parkingOfficersCount;
                            // Adjust the label based on the count
                          
                          echo "<p>{$parkingOfficersCount}</p>";
                        } else {
                          echo "<p>No Parking Officers</p>"; // Default value if count is not available
                        }
                        ?>
                    </div>
                    <div class="colombo-content10 text-black">
                        <p>Total Number of Parking Spaces</p>
                        
                    </div>
                    <div class="colombo-content11 text-black">
                      <!--  <p>10</p> -->
                        <?php
                        // Check if the property 'parkingSlotsCount' exists in the current application
                        if (property_exists($application, 'parkingSlotsCount')) {
                          $parkingSlotsCount = $application->parkingSlotsCount;
                        
                          echo "<p>{$parkingSlotsCount}</p>";
                        } else {
                          echo "<p>No Parking Spaces</p>"; // Default value if count is not available
                        }
                        ?>
                    </div>
                </div>
                        
                
                
                <div class="b-card3">
                    <div class="colombo-content12 text-grey">
                        <p>Write the reason to suspend....</p>
                    </div>
                </div>
                <div class="colombo-content13 text-black">
                    <p>Select the time duration for the suspend</p>
                </div>
                <div class="time-card">
    <div class="time-card-content p text-grey">
        <p>Time Duration</p>
        <div class="time-dropdown">
           <!-- <div class="arrow-icon2">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="grey" class="arrow-logo">
                    <path fill-rule="evenodd" d="M12.53 16.28a.75.75 0 01-1.06 0l-7.5-7.5a.75.75 0 011.06-1.06L12 14.69l6.97-6.97a.75.75 0 111.06 1.06l-7.5 7.5z" clip-rule="evenodd" />
                </svg>
            </div>-->
            <select class="time-select">
                <option value="1">1 month</option>
                <option value="2">2 months</option>
                <option value="3">3 months</option>
                <option value="3">1 year</option>
            </select>
        </div>
    </div>
</div>
            </div>
           
                <button class="sus bg-red" type="button">
                
                    <div class="sus-content text-white">
                        <p>Suspend the company</p>
                    </div>
                </div>
                </div>
                </button>
                     
        
    </div>
                    
    
    <?php endforeach; ?>

</body>

</html>