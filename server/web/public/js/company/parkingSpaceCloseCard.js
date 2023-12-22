if (parkingData) {
  createCard(parkingData[0]);
}

function createCard(data) {
  const card = document.createElement('div');

  let totalSlots = 0;

  if (data.TotalSlots) {
    totalSlots =
      data.TotalSlots.Cars +
      data.TotalSlots.Vans +
      data.TotalSlots.Buses +
      data.TotalSlots.Bicycles;
  }

  card.innerHTML = `
  <div class="confirmation-card">
    <div class="confirmation-card-line mb-10">
      <h3 class="b-600">${data.ParkingName}</h3>
      <p class="b-600">${data.Address}</p>
    </div>
    <div class="confirmation-card-line mb-10">
      <p class="f-14">Total Slots <span class="b-500">${totalSlots}</span></p>
      ${
        data.PricePerHour == 0
          ? '<p class="parking-type bg-blue text-white">Free</p>'
          : `<p class="b-500 f-14">Rs.${data.PricePerHour}/ 1H</p>`
      }
    </div>
    <div class="confirmation-card-line">
      <h3 class="f-14">Parking Slots</h3>
      <p class="parking-type bg-green text-white">${
        data.IsPublic ? 'Public' : 'Private'
      }</p>
    </div>
    <table class="confirmation-card-table">
      <thead>
        <tr>
          <th>Start</th>
          <th>End</th>
          <th>Type</th>
          <th>Count</th>
        </tr>
      </thead>
      <tbody>
        <tr class="tr-b">
          <td>PSCars1</td>
          <td>PSCars${data.TotalSlots.Cars}</td>
          <td>Cars</td>
          <td>${data.TotalSlots.Cars}</td>
        </tr>
        <tr class="tr-b">
          <td>PSVans1</td>
          <td>PSVans${data.TotalSlots.Vans}</td>
          <td>Vans</td>
          <td>${data.TotalSlots.Vans}</td>
        </tr>
        <tr class="tr-b">
          <td>PSBuses1</td>
          <td>PSBuses${data.TotalSlots.Buses}</td>
          <td>Buses</td>
          <td>${data.TotalSlots.Buses}</td>
        </tr>
        <tr class="tr-b">
          <td>PSBicycles1</td>
          <td>PSBicycles${data.TotalSlots.Bicycles}</td>
          <td>Bicycles</td>
          <td>${data.TotalSlots.Bicycles}</td>
        </tr>
      </tbody>
    </table>
  </div>
`;

  document.getElementById('card-container').appendChild(card);
}

// Function to hide/show buttons based on dropdown selection
function toggleButtons() {
  const parkingDropdown = document.getElementById('parkingDropdown');
  const cancelButton = document.getElementById('cancelButton');
  const deleteButton = document.getElementById('deleteButton');
  const timeSection = document.querySelector('.time-section');

  if (parkingDropdown.value === '') {
    cancelButton.style.display = 'none';
    deleteButton.style.display = 'none';
    timeSection.style.display = 'none'; // Hide the time-section
  } else {
    cancelButton.style.display = 'inline-block'; // Show the buttons
    deleteButton.style.display = 'inline-block';
    timeSection.style.display = 'flex'; // Show the time-section
  }
}

// Call the function when the dropdown selection changes
const parkingDropdown = document.getElementById('parkingDropdown');
parkingDropdown.addEventListener('change', toggleButtons);

// Initial call to set the initial button and time-section state
toggleButtons();

function populateTimeData() {
  const closureTimeInput = document.getElementById('closureTimeInput').value;
  const untilTimeInput = document.getElementById('untilTimeInput').value;

  // Display the input values directly
  const closureTimeElement = document.getElementById('closureTime');
  const untilTimeElement = document.getElementById('untilTime');

  closureTimeElement.textContent = closureTimeInput;
  untilTimeElement.textContent = untilTimeInput;
}

// Call the function to populate the time data
populateTimeData();
