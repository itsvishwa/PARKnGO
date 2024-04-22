<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <title>Forgot Password</title>
</head>

<body>
  <div class="container">
    <div class="left-container">
      <div class="bg-div">
        <a href="../"><img src="<?php echo URLROOT; ?>/css/assets/logo-black.png" alt="logo" width="100%" /></a>
      </div>
    </div>
    <div class="right-container align-items-center mt-150">
      <form action="<?php echo URLROOT; ?>users/resetPasswordView" method="POST" enctype="multipart/form-data" class="login-form">
        <div>
          <?php flash('register_success') ?>
          <h2 class="heading">Reset Password</h2>
          <p class="sub-heading">Please enter your new password.</p>
          <input type="hidden" id="token" name="token" value="<?php echo $data['token'] ?>" />
          <input type="hidden" id="email" name="email" value="<?php echo $data['email'] ?>" />

          <label for="password" class="form-label">New Password*</label><br />
          <input type="password" id="password" name="password" required class="input-field" placeholder="Enter new password" value="<?php echo $data['password'] ?>" /><br />
          <span class="f-12 text-red"><?php echo $data['password_err']; ?></span>

          <label for="confirm_password" class="form-label">Confirm New Password*</label><br />
          <input type="password" id="confirm_password" name="confirm_password" required class="input-field" placeholder="Confirm new password" value="<?php echo $data['confirm_password'] ?>" /><br />
          <span class="f-12 text-red"><?php echo $data['confirm_password_err']; ?></span>
        </div>
        <input type="submit" value="Change Password" class="primary-button" />
      </form>
      <div class="sub-link-div">
        <p class="sub-link-label">
          If you remember your password,
          <a href="./loginView" class="sub-link font-semibold">Login here</a>
        </p>
      </div>
</body>