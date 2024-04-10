<?php

// accessing the content of the .ENV file
// Load the content of the .env file
$envFile = 'config/.ENV';

if (file_exists($envFile)) {
  $envContent = file_get_contents($envFile);

  // Parse the lines of the .env file
  $lines = explode("\n", $envContent);

  foreach ($lines as $line) {
    // Skip empty lines and comments
    if (empty($line) || strpos($line, '#') === 0) {
      continue;
    }

    // Split the line into key and value
    list($key, $value) = explode('=', $line, 2);

    // Trim whitespace and set the environment variable
    $_ENV[trim($key)] = trim($value);
  }
}


// Load Config
require_once 'config/config.php';
require_once 'vendor/autoload.php';

// Load Helpers
require_once 'helpers/url_helper.php';
require_once 'helpers/session_helper.php';

// Autoload Core libraries
spl_autoload_register(function ($className) {
  require_once 'libraries/' . $className . '.php';
});
