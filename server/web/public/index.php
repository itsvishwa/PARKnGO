<!-- Main file  -->
<?php 
    // require_once "./../app/bootloader.php";
    if(isset($_GET["url"])){
        echo $_GET["url"];
    }else{
        echo "welcome";
    }
?>