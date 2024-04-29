<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <meta http-equiv="refresh" content="60" />
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/dashboardView.css" />
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/parkingSpaceView.css" />
  <link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/reviewPopup.css" />
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
            <li class="active">
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
          <h3>Parking Spaces</h3>
        </div>

        <div class="profile">
          <a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
          <a href="../users/logout" class="logout">Log out</a>
        </div>
      </div>
      <div class="btn-section">
        <a href="./parkingSpaceFormView">
          <div class="btn bg-off-white">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo text-green">
              <path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zM12.75 9a.75.75 0 00-1.5 0v2.25H9a.75.75 0 000 1.5h2.25V15a.75.75 0 001.5 0v-2.25H15a.75.75 0 000-1.5h-2.25V9z" clip-rule="evenodd" />
            </svg>
            Add Parking Space
          </div>
        </a>
        <div class="search-bar flex">
          <input type="text" id="searchInput" placeholder="Search By Name." oninput="filterOfficers()" />
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="search-logo text-primary">
            <path stroke-linecap="round" stroke-linejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
          </svg>
        </div>

      </div>
      <div class="parking-space-section mb-20">

        <div id="parkingCards" class="parking-cards">
          <?php $i = 0;
          foreach ($data['parking_spaces'] as $parking) : ?>
            <div class="parking-space-card <?php $currentUnixTime = time();
                                            if ($parking->parking_closed_start_time <= $currentUnixTime && $parking->parking_closed_end_time >= $currentUnixTime) {
                                              echo "closed";
                                            } ?>">
              <div class="parking-card-header">
                <div class="parking-name">
                  <h3 class="parking-card-bold"><?php echo htmlspecialchars($parking->parking_name); ?></h3>
                  <?php $currentUnixTime = time();
                  if ($parking->parking_closed_start_time <= $currentUnixTime && $parking->parking_closed_end_time >= $currentUnixTime) {
                    echo '<p class="parking-type bg-red text-white">Closed</p>';
                  } else {
                    echo "";
                  } ?>

                </div>

                <p class="parking-card-bold"><?php echo htmlspecialchars($parking->parking_address) ?></p>
              </div>
              <div class="parking-card-body">
                <div class="parking-card-info">
                  <p>Total Slots: <span class="parking-card-bold"><?php echo htmlspecialchars($parking->parking_total_slots) ?></span></p>

                  <?php if ($parking->parking_is_public) {
                    echo '<p class="parking-type bg-green text-white">Public</p>';
                  } else {
                    echo '<p class="parking-type bg-primary text-white">Private</p>';
                  } ?>

                </div>
                <p class="parking-officer">Number Of Parking Officers: <span class="parking-card-bold"><?php if ($parking->officer_count != 0) {
                                                                                                          echo $parking->officer_count;
                                                                                                        } else {
                                                                                                          echo "Not Assigned";
                                                                                                        } ?></span></p>
                <p class="parking-officer">On Duty: <?php $is_available = 0;
                                                    foreach ($data['duty_records'] as $duty_record) {
                                                      if ($duty_record->parking_id == $parking->parking_id) {
                                                        echo "<span class='parking-type bg-green text-white'>" . $duty_record->duty_count . "</span>";
                                                        $is_available = 1;
                                                        break;
                                                      }
                                                    }
                                                    if ($is_available == 0) {
                                                      echo "<span class='parking-type bg-red text-white'>N/A</span>";
                                                    } ?></span></p>


                <table>
                  <thead>
                    <th>Type</th>
                    <th>Free Slots</th>
                    <th>Total Slots</th>
                    <th>Rate</th>
                  </thead>
                  <tbody>

                    <?php foreach ($data['parking_spaces_status'] as $parking_status) : ?>
                      <tr>
                        <?php if ($parking_status->parking_space_id == $parking->parking_id) {

                          echo '<td>';
                          echo htmlspecialchars($this->convert_to_vehicle_type($parking_status->vehicle_type));
                          echo '</td>';
                          echo '<td>';
                          echo htmlspecialchars($parking_status->each_type_free_slots);
                          echo '</td>';
                          echo '<td>';
                          echo htmlspecialchars($parking_status->each_type_total_slots);
                          echo '</td>';
                          echo '<td>';
                          echo htmlspecialchars($parking_status->each_type_rate);
                          echo '</td>';
                        }

                        ?>
                      </tr>

                    <?php endforeach; ?>


                  </tbody>
                </table>
                <div class="flex mt-20">
                  <a href="./parkingSpaceEditView/<?php echo $parking->parking_id ?>" class="text-decoration-none text-black">
                    <div class="btn flex ">
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo text-primary">
                        <path d="M21.731 2.269a2.625 2.625 0 00-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 000-3.712zM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 00-1.32 2.214l-.8 2.685a.75.75 0 00.933.933l2.685-.8a5.25 5.25 0 002.214-1.32l8.4-8.4z" />
                        <path d="M5.25 5.25a3 3 0 00-3 3v10.5a3 3 0 003 3h10.5a3 3 0 003-3V13.5a.75.75 0 00-1.5 0v5.25a1.5 1.5 0 01-1.5 1.5H5.25a1.5 1.5 0 01-1.5-1.5V8.25a1.5 1.5 0 011.5-1.5h5.25a.75.75 0 000-1.5H5.25z" />
                      </svg>
                      Edit
                    </div>
                  </a>
                  <a href="./parkingSpaceDeleteView/<?php echo $parking->parking_id ?>" class="text-decoration-none text-black">
                    <div class="btn flex">
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo text-red">
                        <path fill-rule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 013.878.512.75.75 0 11-.256 1.478l-.209-.035-1.005 13.07a3 3 0 01-2.991 2.77H8.084a3 3 0 01-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 01-.256-1.478A48.567 48.567 0 017.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 013.369 0c1.603.051 2.815 1.387 2.815 2.951zm-6.136-1.452a51.196 51.196 0 013.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 00-6 0v-.113c0-.794.609-1.428 1.364-1.452zm-.355 5.945a.75.75 0 10-1.5.058l.347 9a.75.75 0 101.499-.058l-.346-9zm5.48.058a.75.75 0 10-1.498-.058l-.347 9a.75.75 0 001.5.058l.345-9z" clip-rule="evenodd" />
                      </svg>
                      Remove
                    </div>
                  </a>
                  <a href="./parkingSpaceCloseView/<?php echo $parking->parking_id ?>" class="text-decoration-none text-black">
                    <div class="btn ">
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo">
                        <path fill-rule="evenodd" d="M6.72 5.66l11.62 11.62A8.25 8.25 0 006.72 5.66zm10.56 12.68L5.66 6.72a8.25 8.25 0 0011.62 11.62zM5.105 5.106c3.807-3.808 9.98-3.808 13.788 0 3.808 3.807 3.808 9.98 0 13.788-3.807 3.808-9.98 3.808-13.788 0-3.808-3.807-3.808-9.98 0-13.788z" clip-rule="evenodd" />
                      </svg>
                      Close
                    </div>
                  </a>
                  <a class="text-decoration-none text-black review-btn" data-parking-id="<?php echo $parking->parking_id ?>">
                    <div class="btn ">
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo text-blue">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M6.633 10.5c.806 0 1.533-.446 2.031-1.08a9.041 9.041 0 012.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 00.322-1.672V3a.75.75 0 01.75-.75A2.25 2.25 0 0116.5 4.5c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282h3.126c1.026 0 1.945.694 2.054 1.715.045.422.068.85.068 1.285a11.95 11.95 0 01-2.649 7.521c-.388.482-.987.729-1.605.729H13.48c-.483 0-.964-.078-1.423-.23l-3.114-1.04a4.501 4.501 0 00-1.423-.23H5.904M14.25 9h2.25M5.904 18.75c.083.205.173.405.27.602.197.4-.078.898-.523.898h-.908c-.889 0-1.713-.518-1.972-1.368a12 12 0 01-.521-3.507c0-1.553.295-3.036.831-4.398C3.387 10.203 4.167 9.75 5 9.75h1.053c.472 0 .745.556.5.96a8.958 8.958 0 00-1.302 4.665c0 1.194.232 2.333.654 3.375z" />
                      </svg>
                      Reviews
                    </div>
                  </a>

                </div>



              </div>
            </div>

          <?php endforeach; ?>
          <div id="popupContainer" class="popup-container">

            <div class="popup-content">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="close text-red" id="closePopup" width="30px">
                <path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zm-1.72 6.97a.75.75 0 10-1.06 1.06L10.94 12l-1.72 1.72a.75.75 0 101.06 1.06L12 13.06l1.72 1.72a.75.75 0 101.06-1.06L13.06 12l1.72-1.72a.75.75 0 10-1.06-1.06L12 10.94l-1.72-1.72z" clip-rule="evenodd" />
              </svg>
              <h1 class="popup-parking-name"></h1>
              <h2 class="popup-parking-address"></h2>
              <div class="content-body">

              </div>
              <!-- Add your review form or content here -->
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
  <script>
    function filterOfficers() {
      var input, filter, cards, card, officerName, i;
      input = document.getElementById("searchInput");
      filter = input.value.toUpperCase();
      cards = document.getElementById("parkingCards");
      card = cards.getElementsByClassName("parking-space-card");

      for (i = 0; i < card.length; i++) {
        officerName = card[i].getElementsByClassName("parking-card-bold")[0];
        if (officerName.innerHTML.toUpperCase().indexOf(filter) > -1) {
          card[i].style.display = "";
        } else {
          card[i].style.display = "none";
        }
      }
    }
  </script>
  <script>
    document.addEventListener('DOMContentLoaded', function() {

      // Get all elements with the class 'review-btn'
      const reviewButtons = document.querySelectorAll('.review-btn');
      const closePopupButton = document.getElementById('closePopup');
      const popupContainer = document.getElementById('popupContainer');
      const popupContent = document.querySelector('.popup-content');
      const popupContentBody = document.querySelector('.content-body');
      const parkingName = document.querySelector('.popup-parking-name');
      const parkingAddress = document.querySelector('.popup-parking-address');
      const reviewsData = <?php echo json_encode($data['reviews']); ?>;

      reviewButtons.forEach(function(button) {
        button.addEventListener('click', function() {
          const parkingId = this.getAttribute('data-parking-id');
          const filteredReviews = reviewsData.filter(review => review.parking_id == parkingId);

          function generateStars(rating) {
            const fullStars = Math.floor(rating);
            const emptyStars = 5 - fullStars;

            const stars = Array(fullStars).fill(`
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6 text-primary" width="30px">
                <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
              </svg>
            `);

            stars.push(...Array(emptyStars).fill(`
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6 text-light-gray" width="30px">
                <path fill-rule="evenodd" d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.007 5.404.433c1.164.093 1.636 1.545.749 2.305l-4.117 3.527 1.257 5.273c.271 1.136-.964 2.033-1.96 1.425L12 18.354 7.373 21.18c-.996.608-2.231-.29-1.96-1.425l1.257-5.273-4.117-3.527c-.887-.76-.415-2.212.749-2.305l5.404-.433 2.082-5.006z" clip-rule="evenodd" />
              </svg>
            `));

            return stars.join('');
          }

          parkingName.innerHTML = `${filteredReviews[0].parking_name}`;
          parkingAddress.innerHTML = `${filteredReviews[0].parking_address}`;
          popupContentBody.innerHTML = '';

          filteredReviews.forEach(review => {
            const card = document.createElement('div');
            card.classList.add('review-card');

            const starsSVG = generateStars(review.no_of_stars);
            card.innerHTML = `
                        <div class="review-head">
                            <h3>${review.driver_first_name} ${review.driver_last_name}</h3>
                            <p>${review.time_stamp}</p>
                        </div>
                        <div class="review-body">
                            ${review.content}
                        </div>
                        <div class="stars-container">
                            ${starsSVG}
                        </div>
                    `;
            popupContentBody.appendChild(card);
          });

          popupContainer.style.display = 'block';

          if (filteredReviews.length > 5) {
            popupContentBody.style.maxHeight = '300px';
            popupContentBody.style.overflowY = 'auto';
          } else {
            popupContentBody.style.maxHeight = 'none';
            popupContentBody.style.overflowY = 'hidden';
          }

          const popupContainerRect = popupContainer.getBoundingClientRect();
          const closePopupButtonRect = closePopupButton.getBoundingClientRect();

          closePopupButton.style.position = 'fixed';
          closePopupButton.style.top = `${popupContainerRect.top + closePopupButtonRect.height}px`;
          closePopupButton.style.right = `${popupContainerRect.right - closePopupButtonRect.width}px`;
        });
      });

      closePopupButton.addEventListener('click', function() {
        popupContainer.style.display = 'none';
      });

      window.addEventListener('click', function(event) {
        if (event.target === popupContainer) {
          popupContainer.style.display = 'none';
        }
      });
    });
  </script>




</body>

</html>