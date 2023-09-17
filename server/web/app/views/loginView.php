<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="style.css" rel="stylesheet" />
    <title>Login Page</title>
  </head>
  <body>
    <div class="container">
      <div class="left-container">
        <div class="bg-div">
          <img src="./assets/logo-black.png" alt="logo" width="100%" />
        </div>
      </div>
      <div class="right-container">
        <form
          action="registration_process.php"
          method="POST"
          enctype="multipart/form-data"
          class="login-form"
        >
          <div>
            <h2 class="heading">Welcome Back</h2>
            <p class="sub-heading">Please enter your details to sign in.</p>

            <label for="email" class="form-label">Email</label><br />
            <input
              type="email"
              id="email"
              name="email"
              required
              class="input-field"
              placeholder="Enter company email"
            /><br />

            <label for="password" class="form-label">Password</label><br />
            <input
              type="password"
              id="password"
              name="password"
              required
              class="input-field"
              placeholder="Enter password"
            /><br />

            <div
              style="
                display: flex;
                justify-content: space-between;
                align-items: center;
              "
            >
              <div>
                <input type="checkbox" id="remember" name="remember" />
                <label
                  for="remember"
                  class="sub-link-label"
                  style="vertical-align: middle"
                  >Remember me</label
                >
              </div>

              <a href="#" class="sub-link">Forgot password?</a>
            </div>

            <input type="submit" value="Log in" class="primary-button" />
            <div class="sub-link-div">
              <p class="sub-link-label">
                Don't have an account?
                <a href="./company/registrationPage.php" class="sub-link"
                  >Register here</a
                >
              </p>
            </div>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
