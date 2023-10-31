function saveFormData() {
  // Retrieve form data and save it to localStorage

  const formDataArray = [];

  // Get all elements with the class "p-slot-batch"
  const parkingSlotBatchForms = document.querySelectorAll('.p-slot-batch');

  parkingSlotBatchForms.forEach((form) => {
    const noOfSlots = form.querySelector('input[name="noOfSlots[]"]').value;
    const vehicleType = form.querySelector(
      'select[name="vehicleType[]"]',
    ).value;
    const startNumber = form.querySelector('input[name="startNumber[]"]').value;
    const endNumber = form.querySelector('input[name="endNumber[]"]').value;

    // Create an object for each parking slot batch
    const parkingSlotBatch = {
      noOfSlots,
      vehicleType,
      startNumber,
      endNumber,
    };

    // Push the object to the formDataArray
    formDataArray.push(parkingSlotBatch);
  });

  const formData = {
    parkingName: document.querySelector('input[name="parkingName"]').value,
    address: document.querySelector('input[name="address"]').value,
    parkingSlotBatches: formDataArray,
    parkingRate: document.querySelector('input[name="parkingRate"]').value,
    latitude: document.querySelector('input[name="latitude"]').value,
    longitude: document.querySelector('input[name="longitude"]').value,
    parkingType: document.querySelector('select[name="parkingType"]').value,
  };

  // Save the updated array back to localStorage
  localStorage.setItem('formData', JSON.stringify(formData));
}

function confirmDiscard() {
  if (
    confirm(
      'Are you sure you want to discard? All the field data will not be saved.',
    )
  ) {
    window.location.href = './parkingSpaceView.php';
  }
}

function addParkingSlotBatch() {
  // Create a new div element
  var newParkingSlotBatch = document.createElement('div');

  // Set the innerHTML of the new div to the parkingSlotBatchForm string
  newParkingSlotBatch.innerHTML = `
				<div id="parkingSlotBatchForm" class="p-slot-batch">
					<input type="number" name="noOfSlots[]" class="p-form-input width-20" placeholder="No of slots" required>

					<select name="vehicleType[]" class="p-form-dropdown" required>
						<option value="" disabled selected>Vehicle Type</option>
						<option value="Car">Car</option>
						<option value="Van">Van</option>
						<option value="Bus">Bus</option>
						<option value="Bicycle">Bicycle</option>
					</select><br>

					<label for="startNumber" class="p-form-label">Start</label>
					<input type="number" name="startNumber[]" class="p-form-input width-40" placeholder="Parking Slot Start Number" required>

					<label for="endNumber" class="p-form-label">To</label>
					<input type="number" name="endNumber[]" class="p-form-input width-30" placeholder="Parking Slot End Number" disabled required>

					<button type="button" onclick="removeParkingSlotBatch(this.parentNode)" class="p-form-btn">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="button-icon text-red p-form-icon">
							<path fill-rule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 013.878.512.75.75 0 11-.256 1.478l-.209-.035-1.005 13.07a3 3 0 01-2.991 2.77H8.084a3 3 0 01-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 01-.256-1.478A48.567 48.567 0 017.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 013.369 0c1.603.051 2.815 1.387 2.815 2.951zm-6.136-1.452a51.196 51.196 0 013.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 00-6 0v-.113c0-.794.609-1.428 1.364-1.452zm-.355 5.945a.75.75 0 10-1.5.058l.347 9a.75.75 0 101.499-.058l-.346-9zm5.48.058a.75.75 0 10-1.498-.058l-.347 9a.75.75 0 001.5.058l.345-9z" clip-rule="evenodd" />
						</svg>
						Remove
					</button>
				</div>
  		`;

  // Append the new div to the 'parkingSlotBatches' element or any other container
  document
    .getElementById('parkingSlotBatches')
    .appendChild(newParkingSlotBatch);
}

// Function to remove a parking slot batch form
function removeParkingSlotBatch(batch) {
  batch.remove();
}