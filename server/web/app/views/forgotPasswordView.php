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
      <form action="<?php echo URLROOT; ?>users/forgotPasswordView" method="POST" enctype="multipart/form-data" class="login-form">
        <div>
          <?php flash('register_success') ?>
          <h2 class="heading">Reset Password</h2>
          <p class="sub-heading">Please enter your email to send verfication email.</p>

          <label for="email" class="form-label">Email</label><br />
          <input type="email" id="email" name="email" required class="input-field" placeholder="Enter user email" value="<?php echo $data['email'] ?>" /><br />
          <span class="f-12 text-red"><?php if (!empty($data['email_err'])) {
                                        echo $data['email_err'] . '<br />';
                                      } ?></span>
        </div>
        <input type="submit" value="Send Email Verfication" class="primary-button" />
      </form>
      <div class="sub-link-div">
        <p class="sub-link-label">
          If you remember your password,
          <a href="./loginView" class="sub-link font-semibold">Login here</a>
        </p>
      </div>
</body>