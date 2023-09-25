const formData = JSON.parse(localStorage.getItem('formData'));

if (formData) {
  createCard(formData);
}

function createCard(data) {
  const card = document.createElement('div');

  let totalSlots = 0;

  if (data.parkingSlotBatches && data.parkingSlotBatches.length > 0) {
    totalSlots = data.parkingSlotBatches.reduce(
      (acc, batch) => acc + parseInt(batch.noOfSlots),
      0,
    );
  }

  card.innerHTML = `
        <div class="confirmation-card">
						<div class="confirmation-card-line mb-10">
							<h3 class="b-600">${data.parkingName}</h3>
            	<p class="b-600">${data.address}</p>
						</div>
						<div class="confirmation-card-line mb-10">
					 		<p class="f-14">Total Slots <span class="b-500">${totalSlots}</span></p>
            	${
                data.parkingRate == 0
                  ? '<p class="parking-type bg-blue text-white">Free</p>'
                  : `<p class="b-500 f-14">Rs.${data.parkingRate}/ 1H</p>`
              }
					 	</div>
						<div class="confirmation-card-line">
							<h3 class="f-14">Parking Slots</h3>
							<p class="parking-type bg-green text-white">${
                data.parkingType == 'public' ? 'Public' : 'Private'
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
        				${
                  data.parkingSlotBatches && data.parkingSlotBatches.length > 0
                    ? data.parkingSlotBatches
                        .map(
                          (batch) => `
            			<tr class="tr-b">
                		<td>PS${batch.vehicleType[0]}${batch.startNumber}</td>
                		<td>PS${batch.vehicleType[0]}${
                            parseInt(batch.startNumber) +
                            parseInt(batch.noOfSlots) -
                            1
                          }</td>
                		<td>${batch.vehicleType}</td>
                		<td>${batch.noOfSlots}</td>
            			</tr>

        				`,
                        )
                        .join('')
                    : '<tr><td colspan="4">No parking slot batches available</td></tr>'
                }
    					</tbody>
						</table>
        </div>
    `;

  document.getElementById('card-container').appendChild(card);
}

function cancel() {
  if (
    confirm('Are you sure you want to cancel? All the data will not be saved.')
  ) {
    window.location.href = './parkingSpaceView.php';
  }
}
