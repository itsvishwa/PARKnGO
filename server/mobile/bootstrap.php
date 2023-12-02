<?php

<<<<<<< HEAD
    //Load Config
    require_once "config/config.php";

    //Autoload libraries
    spl_autoload_register(function ($className){
        require_once "./libraries/" . $className . ".php";
    });
?>
=======
// Load Config
require_once 'config/config.php';
require_once "helpers/twilio-php-main/twilio-php-main/src/Twilio/autoload.php";
// set timezone to unix time stamp

// Autoload Core libraries
spl_autoload_register(function ($className) {
    require_once './libraries/' . $className . '.php';
});
>>>>>>> a8b31085702c9871b643b87521f605f432cb3fb7
