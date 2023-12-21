<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="styles.css">
  <title>Popup Design</title>
</head>

<body>
  <button id="showPopup">Show Popup</button>

  <div id="popupContainer" class="popup-container">
    <div class="popup-content">
      <span class="close" id="closePopup">&times;</span>
      <h2>Your Popup Content Goes Here</h2>
      <!-- Add your review form or content here -->
    </div>
  </div>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const showPopupButton = document.getElementById('showPopup');
      const closePopupButton = document.getElementById('closePopup');
      const popupContainer = document.getElementById('popupContainer');

      showPopupButton.addEventListener('click', function() {
        popupContainer.style.display = 'block';
      });

      closePopupButton.addEventListener('click', function() {
        popupContainer.style.display = 'none';
      });

      // Close the popup if the user clicks outside the content
      window.addEventListener('click', function(event) {
        if (event.target === popupContainer) {
          popupContainer.style.display = 'none';
        }
      });
    });
  </script>
</body>

</html>