let map;
let geocoder;
let marker;

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
        updateMarker(location);
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    },
  );
}

function addMarkerOnClick() {
  google.maps.event.addListener(map, 'click', function (event) {
    const clickedLocation = event.latLng;
    updateMarker(clickedLocation);
  });
}

function updateMarker(location) {
  // Check if marker exists, and remove it if it does
  if (marker) {
    marker.setMap(null);
  }

  // Create a new marker at the specified location
  marker = new google.maps.Marker({
    map: map,
    position: location,
    draggable: true, // Allow the user to move the marker
  });

  // Update latitude and longitude fields with the new values
  document.getElementById('latitude').value = location.lat();
  document.getElementById('longitude').value = location.lng();

  // Add an event listener to update latitude and longitude as the marker is dragged
  google.maps.event.addListener(marker, 'dragend', function (event) {
    document.getElementById('latitude').value = event.latLng.lat();
    document.getElementById('longitude').value = event.latLng.lng();
  });
}

function updateMapWithCoordinates() {
  const latitudeInput = document.getElementById('latitude');
  const longitudeInput = document.getElementById('longitude');

  // Get latitude and longitude values from the input fields
  const latitude = parseFloat(latitudeInput.value);
  const longitude = parseFloat(longitudeInput.value);

  // Check if the values are valid numbers
  if (!isNaN(latitude) && !isNaN(longitude)) {
    const location = new google.maps.LatLng(latitude, longitude);
    map.setCenter(location);
    updateMarker(location);
  }
}

// Call the addMarkerOnClick function to enable marker placement when the page loads
window.onload = function () {
  initMap();
  addMarkerOnClick();
  updateMapWithCoordinates();
};
