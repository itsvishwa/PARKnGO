<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/dashboardView.css" />
	<link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/parkingSpaceView.css" />
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
					<h3>Add Parking Space</h3>
				</div>
				<div class="profile">
					<a href="./dashboardView" class="company-name"><?php echo $_SESSION['user_name']; ?></a>
					<a href="../../users/logout" class="logout">Log out</a>
				</div>
			</div>
			<div class="header text-md">
				<p>Fill the following details to add a new parking space</p>
			</div>
			<form action="parkingSpaceSaveView" method="POST" class="parking-form" onsubmit="saveFormData()">
				<div class="form-left">
					<label for="name" class="p-form-label ">Parking Name *</label>
					<input type="text" name="name" class="p-form-input width-65" placeholder="Display name of the parking space" required>

					<label for="address" class="p-form-label">Address *</label>
					<input type="text" name="address" id="address" placeholder="Enter Parking Space Address" class="p-form-input width-75" required>

					<div id="parkingSlotBatches">
						<p class="p-form-label">Parking Slots *</p>
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

							<button type="button" onclick="removeParkingSlotBatch(this.parentNode)" class="p-form-btn p-form-label">
								<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="button-icon text-red p-form-icon">
									<path fill-rule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 013.878.512.75.75 0 11-.256 1.478l-.209-.035-1.005 13.07a3 3 0 01-2.991 2.77H8.084a3 3 0 01-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 01-.256-1.478A48.567 48.567 0 017.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 013.369 0c1.603.051 2.815 1.387 2.815 2.951zm-6.136-1.452a51.196 51.196 0 013.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 00-6 0v-.113c0-.794.609-1.428 1.364-1.452zm-.355 5.945a.75.75 0 10-1.5.058l.347 9a.75.75 0 101.499-.058l-.346-9zm5.48.058a.75.75 0 10-1.498-.058l-.347 9a.75.75 0 001.5.058l.345-9z" clip-rule="evenodd" />
								</svg>
								Remove
							</button>
						</div>
					</div>

					<button type="button" onclick="addParkingSlotBatch()" class="p-form-btn width-65 mb-10 mt-10">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="menu-logo text-green p-form-icon">
							<path fill-rule="evenodd" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zM12.75 9a.75.75 0 00-1.5 0v2.25H9a.75.75 0 000 1.5h2.25V15a.75.75 0 001.5 0v-2.25H15a.75.75 0 000-1.5h-2.25V9z" clip-rule="evenodd" />
						</svg>
						Add Another Parking Slot Batch
					</button>

					<label for="parkingType" class="p-form-label mt-10">Parking Type (Public/Customers Only) *</label>
					<select name="parkingType" class="p-form-dropdown width-30">
						<option value="" disabled selected>Type</option>
						<option value="public">Public</option>
						<option value="customers">Customers Only</option>
					</select>
					<!-- image -->
					<label for="parkingImage" class="p-form-label">Parking Image</label>
					<input type="file" name="parkingImage[]" accept="image/*" class="mt-10" id="parkingImageInput" />

				</div>
				<div class="form-right">
					<div class="p-location">
						<div class="p-latitude mr-10">
							<label for="latitude" class="p-form-label">Location Latitude *</label>
							<input type="text" id="latitude" name="latitude" class="p-form-input width-90" placeholder="Latitude" readonly>
						</div>
						<div class="p-longitude">
							<label for="longitude" class="p-form-label">Location Longitude *</label>
							<input type="text" id="longitude" name="longitude" class="p-form-input width-90" placeholder="Longitude" readonly>
						</div>
					</div>
					<!-- <button type="button" onclick="autoMarkLocation()" class="p-form-btn mb-10 ">
						<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="button-icon mr-5">
							<path fill-rule="evenodd" d="M11.54 22.351l.07.04.028.016a.76.76 0 00.723 0l.028-.015.071-.041a16.975 16.975 0 001.144-.742 19.58 19.58 0 002.683-2.282c1.944-1.99 3.963-4.98 3.963-8.827a8.25 8.25 0 00-16.5 0c0 3.846 2.02 6.837 3.963 8.827a19.58 19.58 0 002.682 2.282 16.975 16.975 0 001.145.742zM12 13.5a3 3 0 100-6 3 3 0 000 6z" clip-rule="evenodd" />
						</svg>
						Auto Mark Location
					</button> -->
					<div>
						<div id="map" class="map"></div>
					</div>
					<div class="p-btn-section">
						<input type="button" value="Discard" class="p-btn bg-black40" onclick="confirmDiscard()">
						<input type="submit" value="Continue" class="p-btn bg-black">
					</div>
				</div>
			</form>
		</div>
	</div>


	<script src="<?php echo URLROOT; ?>/js/company/parkingSpaceFormView.js"></script>
	<!-- Google Map API -->
	<script async src="https://maps.googleapis.com/maps/api/js?key=<?php echo G_API_KEY ?>&callback=initMap">
	</script>
	<script src="<?php echo URLROOT; ?>/js/company/googleMapApi.js"></script>
	<script>
		function confirmDiscard() {
			if (
				confirm(
					'Are you sure you want to discard? All the field data will not be saved.',
				)
			) {
				window.location.href = './parkingSpaceView';
			}
		}
	</script>

</body>

</html>