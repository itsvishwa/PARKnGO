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
// define('DB_NAME', $_ENV['DB_NAME']);

// App Root
define('APPROOT', dirname(dirname(__FILE__)));

// URL Root
define('URLROOT', 'http://localhost/PARKnGO/server/mobile/');

// Site Name
define('SITENAME', 'PARKnGO');

// Token Secret Key
define("TOKEN_KEY",  $_ENV['TOKEN_KEY']);

// Payment secret key
define("ID_KEY",  $_ENV['ID_KEY']);

// session secret key
define("SESSION_KEY", $_ENV['SESSION_KEY']);

// google API KEY
define("G_API_KEY", $_ENV['G_API_KEY']);

// textit KEY
define("TEXTIT_KEY", $_ENV['TEXTIT_KEY']);

// setting time zone
date_default_timezone_set('Asia/Kolkata');