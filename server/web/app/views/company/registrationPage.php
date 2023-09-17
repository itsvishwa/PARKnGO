<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="../style.css" rel="stylesheet" />
    <title>Registration Page</title>
  </head>
  <body>
    <div class="container">
      <div class="left-container">
        <div class="bg-div">
          <img src="../assets/logo-black.png" alt="logo" width="100%" />
        </div>
      </div>
      <div class="right-container">
        <form
          action="registration_process.php"
          method="POST"
          enctype="multipart/form-data"
          class="registration-form"
        >
          <div>
            <h2 class="heading">Company Registration</h2>
            <p class="sub-heading">
              Please enter company details and submit the application to
              register
            </p>
            <label for="company_name" class="form-label">Company Name*</label
            ><br />
            <input
              type="text"
              id="company_name"
              name="company_name"
              required
              class="input-field"
              placeholder="Enter company registration name"
            /><br />

            <label for="company_address" class="form-label"
              >Company Address*</label
            ><br />
            <input
              type="text"
              id="company_address"
              name="company_address"
              required
              class="input-field"
              placeholder="Enter company location"
            /><br />
            <div style="display: flex">
              <div style="width: 49%">
                <label for="email" class="form-label">Email*</label><br />
                <input
                  type="email"
                  id="email"
                  name="email"
                  required
                  class="input-field"
                  placeholder="Enter company email"
                /><br />
              </div>
              <div style="width: 48%">
                <label for="phone_number" class="form-label"
                  >Phone Number*</label
                ><br />
                <input
                  type="tel"
                  id="phone_number"
                  name="phone_number"
                  required
                  class="input-field"
                  placeholder="Enter company phone number"
                /><br />
              </div>
            </div>
            <div style="display: flex">
              <div style="width: 49%">
                <label for="password" class="form-label">Password*</label><br />
                <input
                  type="password"
                  id="password"
                  name="password"
                  required
                  class="input-field"
                  placeholder="Enter password"
                /><br />
              </div>
              <div style="width: 48%">
                <label for="confirm_password" class="form-label"
                  >Confirm Password*</label
                ><br />
                <input
                  type="password"
                  id="confirm_password"
                  name="confirm_password"
                  required
                  class="input-field"
                  placeholder="Confirm password"
                /><br />
              </div>
            </div>

            <label for="confirm_password" class="form-label"
              >Company Verification*</label
            ><br />
            <div style="margin: 5px 0px 10px 0px">
              <ol class="sub-link-label">
                <li>
                  Download the form (
                  <a href="#" class="sub-link"> Click here to download </a> )
                </li>
                <li>Fill it (Attach necessary documents that are required)</li>
                <li>Upload the filled form</li>
              </ol>
            </div>

            <input
              type="file"
              id="file_upload"
              name="file_upload"
              placeholder="Add company identification"
            /><br />

            <input type="submit" value="Register Now" class="primary-button" />
            <div class="sub-link-div">
              <p class="sub-link-label">
                Already have an account?
                <a href="../loginView.php" class="sub-link">Log in</a>
              </p>
            </div>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
