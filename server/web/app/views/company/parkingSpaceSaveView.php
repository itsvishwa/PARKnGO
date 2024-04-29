<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Parking Space Cards</title>
	<link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/dashboardView.css" />
	<link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/confirmationCard.css" />
	<link rel="stylesheet" href="<?php echo URLROOT; ?>/css/company/parkingSpaceView.css" />
</head>

<body>
	<div class="container">
		<div class="left-container">

			<div class="bg-div">
				<img src="<?php echo URLROOT; ?>/css/assets/logo-black.png" alt="logo" width="100%" />
				<div>
					<ul class="menu">
						<li>

							<a href="../dashboardView">
								<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
									<path stroke-linecap="round" stroke-linejoin="round" d="M15.59 14.37a6 6 0 01-5.84 7.38v-4.8m5.84-2.58a14.98 14.98 0 006.16-12.12A14.98 14.98 0 009.631 8.41m5.96 5.96a14.926 14.926 0 01-5.841 2.58m-.119-8.54a6 6 0 00-7.381 5.84h4.8m2.581-5.84a14.927 14.927 0 00-2.58 5.84m2.699 2.7c-.103.021-.207.041-.311.06a15.09 15.09 0 01-2.448-2.448 14.9 14.9 0 01.06-.312m-2.24 2.39a4.493 4.493 0 00-1.757 4.306 4.493 4.493 0 004.306-1.758M16.5 9a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z" />
								</svg>
								Dashboard
							</a>
						</li>
						<li>
							<a href="../updateView">
								<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
									<path stroke-linecap="round" stroke-linejoin="round" d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 002.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 00-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 00.75-.75 2.25 2.25 0 00-.1-.664m-5.8 0A2.251 2.251 0 0113.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25zM6.75 12h.008v.008H6.75V12zm0 3h.008v.008H6.75V15zm0 3h.008v.008H6.75V18z" />
								</svg>
								Updates
							</a>
						</li>
						<li>
							<a href="../forceStoppedSessionView">
								<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
									<path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" />
								</svg>
								Aborted Sessions
							</a>
						</li>
						<li class="active">
							<a href="../parkingSpaceView">
								<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
									<path stroke-linecap="round" stroke-linejoin="round" d="M9 6.75V15m6-6v8.25m.503 3.498l4.875-2.437c.381-.19.622-.58.622-1.006V4.82c0-.836-.88-1.38-1.628-1.006l-3.869 1.934c-.317.159-.69.159-1.006 0L9.503 3.252a1.125 1.125 0 00-1.006 0L3.622 5.689C3.24 5.88 3 6.27 3 6.695V19.18c0 .836.88 1.38 1.628 1.006l3.869-1.934c.317-.159.69-.159 1.006 0l4.994 2.497c.317.158.69.158 1.006 0z" />
								</svg>

								Parking Spaces
							</a>
						</li>
						<li>
							<a href="../parkingOfficerView">
								<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="menu-logo">
									<path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z" />
								</svg>
								Parking Officer
							</a>
						</li>
						<li>
							<a href="../reportGenerateView">
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
					<a href="../../../users/logout" class="logout">Log out</a>
				</div>
			</div>
			<div class="center">
				<div class="ml-20 text-center b-600 f-18">
					Confirm the Details
				</div>
				<div id="card-container"></div>
				<form action="<?php echo URLROOT; ?>companys/parkingSpaceView" class="c-btn-section" onsubmit="saveData()">
					<input type="button" value="Cancel" class="c-btn bg-black40" onclick="cancel()">
					<input type="submit" value="Save Parking Space" class="c-btn bg-black">
				</form>
			</div>

		</div>
	</div>

	<script>
		function saveData() {
			const formData = JSON.parse(localStorage.getItem('formData'));
			const parkingImage = localStorage.getItem('parkingImage');

			<?php if (isset($data)) : ?>
				formData['parking_id'] = <?php echo $data ?>;
			<?php endif; ?>

			if (formData) {
				const apiUrl = '<?php echo URLROOT; ?>/companys/parkingSpaceView';

				formData['parking_image'] = parkingImage;

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
						window.location.href = '<?php echo URLROOT; ?>/companys/parkingSpaceView';
					})
					.catch(error => {
						console.error('Error:', error);
						// Optionally, you can handle errors here, e.g., show an error message
					});
			}
		}

		function cancel() {
			if (
				confirm('Are you sure you want to cancel? All the data will not be saved.')
			) {
				window.location.href = '../parkingSpaceView';
			}
		}
	</script>


	<script src="<?php echo URLROOT; ?>/js/company/confirmationCard.js"></script>
</body>

</html>