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
        $(document).ready(function() {
            $('.companyIDInput').on('input', function() {
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
                    <h3 class="ml-5">Suspend</h3>
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
                <div class="ml-20">
                    <select id="companyDropdown" class="company-select" onchange="populateFormFields()">
                        <option value="" disabled selected>Select Company</option>
                        <?php foreach ($data['approvedApplications'] as $application) {
                            echo "<option value='" . $application->_id . "'>" . $application->name . "</option>";
                        } ?>
                    </select>
                </div>
            </div>
            <div id="card-container" class="parking-card mt-10">

                <div id="confirmationCard" class="flex-column ml-20 justify-content-center align-items-center"></div>
            </div>
        </div>
    </div>
    <script>
        function populateFormFields() {
            var selectElement = document.getElementById("companyDropdown");
            var confirmationCard = document.getElementById('confirmationCard');
            var selectedCompanyId = selectElement.value;

            // Fetch officer details based on selected ID using AJAX or use a predefined JavaScript object
            // For example, assuming you have a JavaScript object containing officer details:
            var Companies = <?php echo json_encode($data['approvedApplications']); ?>;


            var selectedCompany = Companies.find(function(parking_space) {
                return parking_space._id == selectedCompanyId;
            });

            if (selectedCompany) {
                // Update the confirmation card content dynamically
                confirmationCard.innerHTML = `
                    <div class="company-card">
                        <div class="card-row">
                            <div class="card-name">
                                <p>${selectedCompany.name}</p>
                            </div>
                            <div class="card-address">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="location-icon">
                                    <path fill-rule="evenodd" d="m11.54 22.351.07.04.028.016a.76.76 0 0 0 .723 0l.028-.015.071-.041a16.975 16.975 0 0 0 1.144-.742 19.58 19.58 0 0 0 2.683-2.282c1.944-1.99 3.963-4.98 3.963-8.827a8.25 8.25 0 0 0-16.5 0c0 3.846 2.02 6.837 3.963 8.827a19.58 19.58 0 0 0 2.682 2.282 16.975 16.975 0 0 0 1.145.742ZM12 13.5a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z" clip-rule="evenodd" />
                                </svg>
                                <p>${selectedCompany.address}</p>
                            </div>
                        </div>
                        <div class="card-row">
                            <div class="card-id text-grey">
                                <p>ID: ${selectedCompany._id}</p>
                            </div>
                            <div class="bg-green card-status text-white">
                                <p>${selectedCompany.public}</p>
                            </div>
                        </div>
                        <div class="card-row">
                            <div class="card-column">
                                <div class="card-data">
                                    <p>Email:</p>
                                    <p class="font-semibold">${selectedCompany.email}</p>
                                </div>
                                <div class="card-data">
                                    <p>Parking Spaces:</p>
                                    <p class="font-semibold">${selectedCompany.parkingSlotsCount || 'N/A'}</p>
                                </div>
                            </div>
                        </div>
                        <div class="card-row">
                            <div class="card-column">
                                <div class="card-data">
                                    <p>Telephone:</p>
                                    <p class="font-semibold">${selectedCompany.phone_number}</p>
                                </div>
                                <div class="card-data">
                                    <p>Parking Officers:</p>
                                    <p class="font-semibold">${selectedCompany.parkingOfficersCount || 'N/A'}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <form class="suspend-form" onsubmit="saveData()">
                        <textarea id="suspend-msg" name="suspend-msg" rows="5" cols="50" maxlength="250" class="suspend-msg" placeholder="Enter reason to suspend..."></textarea>
                        <div class="suspend-time">
                            <div class="suspend-dropdown">
                                <p>Select the time duration for the suspend</p>
                                <select class="time-select">
                                    <option value=0 disabled selected>Time Duration</option>
                                    <option value=7>For 1 Week</option>
                                    <option value=14>For 2 Weeks</option>
                                    <option value=30>For 1 month</option>
                                    <option value=60>For 2 months</option>
                                    <option value=90>For 3 months</option>
                                </select>
                            </div>
                        </div>
                        <div class="suspend-submit bg-red">
                            <input type="submit" value="Suspend the company" class="bg-red text-white suspend-submit-btn">
                        </div>
                    </form>
                `;
            } else {
                // Display a message if no matching parking space is found
                confirmationCard.innerHTML = "<p>No data available for the selected parking space.</p>";
            }

        }

        function saveData() {

            let companyID = document.getElementById('companyDropdown').value;
            let suspendMsg = document.getElementById('suspend-msg').value;
            let suspendDuration = document.querySelector('.time-select').value;

            suspendDuration = suspendDuration * 24 * 60 * 60;
            let currentTime = Math.floor((new Date().getTime()) / 1000);

            const formData = {
                company_id: companyID,
                message: suspendMsg,
                duration: suspendDuration,
                time_stamp: currentTime
            };

            if (formData) {
                const apiUrl = '<?php echo URLROOT; ?>/admins/deletionView';

                fetch(apiUrl, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(formData),
                    })
                    .then(response => response.json())
                    .then(data => {
                        console.log('Success:', data);
                        // Optionally, you can handle success here, e.g., show a success message

                        // Redirect to a success page or perform any other actions as needed
                        window.location.href = '<?php echo URLROOT; ?>/admins/deletionView';
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        // Optionally, you can handle errors here, e.g., show an error message
                    });
            }
        }
    </script>

</body>

</html>