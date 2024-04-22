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
    const parkingRate = form.querySelector('input[name="parkingRate[]"]').value;

    // Create an object for each parking slot batch
    const parkingSlotBatch = {
      noOfSlots,
      vehicleType,
      parkingRate,
    };

    // Push the object to the formDataArray
    formDataArray.push(parkingSlotBatch);
  });

  const formData = {
    name: document.querySelector('input[name="name"]').value,
    address: document.querySelector('input[name="address"]').value,
    parking_id: null,
    parkingSlotBatches: formDataArray,
    latitude: document.querySelector('input[name="latitude"]').value,
    longitude: document.querySelector('input[name="longitude"]').value,
    parkingType: document.querySelector('select[name="parkingType"]').value,
  };

  const parkingImageFile =
    document.getElementById('parkingImageInput').files[0];
  if (parkingImageFile) {
    const reader = new FileReader();
    reader.onload = function (e) {
      const parkingImageBase64 = e.target.result;
      localStorage.setItem('parkingImage', parkingImageBase64);

      console.log(formData);

      // Save the updated array back to localStorage
      localStorage.setItem('formData', JSON.stringify(formData));
    };
    reader.readAsDataURL(parkingImageFile);
  }

  console.log(formData);

  // Save the updated array back to localStorage
  localStorage.setItem('formData', JSON.stringify(formData));
}

function addParkingSlotBatch() {
  // Create a new div element
  var newParkingSlotBatch = document.createElement('div');

  // Set the innerHTML of the new div to the parkingSlotBatchForm string
  newParkingSlotBatch.innerHTML = `
				<div id="parkingSlotBatchForm" class="p-slot-batch">
          <label for="noOfSlots" class="p-form-label">Slots* </label>
					<input type="number" name="noOfSlots[]" class="p-form-input width-20" placeholder="No of slots" required>

          <label for="vehicleType" class="p-form-label ml-10">Vehicle Type* </label>
					<select name="vehicleType[]" class="p-form-dropdown" required>
            <option value="" disabled selected>Vehicle Type</option>
            <option value="A">Car|Tuktuk|Mini Van</option>
            <option value="B">Bicycle</option>
            <option value="C">Van|Lorry|Mini Bus</option>
            <option value="D">Long Vehicles</option>
					</select><br>

          <br><label for="parkingRate" class="p-form-label">Parking Rate *</label><br>
							<span class="p-form-label">Rs. <input type="number" name="parkingRate[]" placeholder="Price" class="p-form-input width-20" required> per Hour</span>

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
