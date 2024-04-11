<?php

// DB Params - local server
define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_NAME', 'parkngo');

// Azure server
// define('DB_HOST', $_ENV['DB_HOST']);
// define('DB_USER', $_ENV['DB_USER']);
// define('DB_PASS', $_ENV['DB_PASSWORD']);
// define('DB_NAME', 'parkngo');

// App Root
define('APPROOT', dirname(dirname(__FILE__)));
// URL Root
define('URLROOT', 'http://localhost/PARKnGO/server/mobile/');
// Site Name
define('SITENAME', 'PARKnGO');

// Twilio Configurations
define("SID", "");
define("TOKEN", "");

// Token Secret Key
define("TOKEN_KEY", "81g135w");

// Payment secret key
define("ID_KEY", "1h3sh65");

// session secret key
define("SESSION_KEY", "21nbgfh");

// google API KEY
define("G_API_KEY", "AIzaSyBWhETkAddp84ekkS0aJ9Wceg5LURUfJpo");

// setting time zone
date_default_timezone_set('Asia/Kolkata');
