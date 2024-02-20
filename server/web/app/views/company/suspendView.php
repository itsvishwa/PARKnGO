<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <title>Suspended Page</title>
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
        <h2 class="heading">PARK&apos;n GO Suspended<br />Your Company</h2>
        <p class="message">
          <?php echo $data['suspend_details']->message; ?>
        </p>
        <p>From <b><?php echo htmlspecialchars(date('Y-m-d H:i:s', $data['suspend_details']->start_time)) ?></b> Until <b><?php echo htmlspecialchars(date('Y-m-d H:i:s', $data['suspend_details']->end_time)) ?></b></p>
      </div>
      <a href="../login" class="return-to-login-btn text-primary">Return to Login</a>
    </div>

  </div>
</body>

</html>