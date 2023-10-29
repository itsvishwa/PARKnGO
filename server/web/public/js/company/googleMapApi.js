let map;
let geocoder;

function initMap() {
  map = new google.maps.Map(document.getElementById('map'), {
    center: {
      lat: 6.9271,
      lng: 79.8612,
    },
    zoom: 10,
  });

  geocoder = new google.maps.Geocoder();
}

let marker;

function autoMarkLocation() {
  const address = document.getElementById('address').value;

  geocoder.geocode(
    {
      address: address,
    },
    function (results, status) {
      if (status === 'OK') {
        const location = results[0].geometry.location;
        map.setCenter(location);
        marker = new google.maps.Marker({
          map: map,
          position: location,
        });

        // Update latitude and longitude fields
        document.getElementById('latitude').value = location.lat();
        document.getElementById('longitude').value = location.lng();
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    },
  );
}

// Declare a global marker variable

function addMarkerOnClick() {
  google.maps.event.addListener(map, 'click', function (event) {
    var clickedLocation = event.latLng;

    // Clear the previous marker (if it exists)
    if (marker) {
      marker.setMap(null); // Remove the marker from the map
    }

    marker = new google.maps.Marker({
      position: clickedLocation,
      map: map,
      draggable: true, // Allow the user to move the marker
    });

    // Update latitude and longitude fields with the clicked location
    document.getElementById('latitude').value = clickedLocation.lat();
    document.getElementById('longitude').value = clickedLocation.lng();

    // Add an event listener to update latitude and longitude as the marker is dragged
    google.maps.event.addListener(marker, 'dragend', function (event) {
      document.getElementById('latitude').value = event.latLng.lat();
      document.getElementById('longitude').value = event.latLng.lng();
    });
  });
}

// Call the addMarkerOnClick function to enable marker placement when the page loads
window.onload = function () {
  initMap();
  addMarkerOnClick();
};
