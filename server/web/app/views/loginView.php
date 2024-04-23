<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link href="<?php echo URLROOT; ?>/css/style.css" rel="stylesheet" />
  <title>Login Page</title>
</head>

<body>
  <div class="container">
    <div class="left-container">
      <div class="bg-div">
        <a href="../"><img src="<?php echo URLROOT; ?>/css/assets/logo-black.png" alt="logo" width="100%" /></a>
      </div>
    </div>
    <div class="right-container align-items-center mt-150">
      <form action="<?php echo URLROOT; ?>users/loginView" method="POST" enctype="multipart/form-data" class="login-form">
        <div>
          <?php flash('register_success') ?>
          <h2 class="heading">Welcome Back</h2>
          <p class="sub-heading">Please enter your details to sign in.</p>

          <label for="email" class="form-label">Email</label><br />
          <input type="email" id="email" name="email" required class="input-field" placeholder="Enter company email" value="<?php echo $data['email'] ?>" /><br />
          <span class="f-12 text-red"><?php if (!empty($data['email_err'])) {
                                        echo $data['email_err'] . '<br />';
                                      } ?></span>

          <label for="password" class="form-label">Password</label><br />
          <input type="password" id="password" name="password" required class="input-field" placeholder="Enter password" value="<?php echo $data['password'] ?>" /><br />
          <span class="f-12 text-red"><?php if (!empty($data['password_err'])) {
                                        echo $data['password_err'] . '<br />';
                                      } ?></span>
          <div style="
                display: flex;
                justify-content: space-between;
                align-items: center;
              ">
            <!-- <div>
              <input type="checkbox" id="remember" name="remember" />
              <label for="remember" class="sub-link-label" style="vertical-align: middle">Remember me</label>
            </div>-->

            <a href="./ForgotPasswordView" class="sub-link">Forgot password?</a>
          </div>

          <input type="submit" value="Log in" class="primary-button" />
          <div class="sub-link-div">
            <p class="sub-link-label">
              Don't have an account?
              <a href="./registrationView" class="sub-link">Register here</a>
            </p>
          </div>
        </div>
      </form>
    </div>
  </div>
</body>

</html>