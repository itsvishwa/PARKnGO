<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <title>Registration Page</title>
</head>

<body>
  <div class="container">
    <div class="left-container">
      <div class="bg-div">
        <a href="../"><img src="<?php echo URLROOT; ?>/css/assets/logo-black.png" alt="logo" width="100%" /></a>
      </div>
    </div>
    <div class="right-container mt-150">
      <div class="reg-submission">
        <h2 class="heading">PARKnGO Rejected your request to register.</h2>
        <p class="message">
        <?php echo $data; ?>
        </p>
      </div>
    </div>
  </div>
</body>

</html>