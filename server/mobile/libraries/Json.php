<?php

class Json
{
    // sucess response
    public function make_json_200($msg)
    {
        $response = ["response" => $msg];
        $response = json_encode($response);
        header('Content-Type: application/json');
        return $response;
    }
    // bad request response
    public function make_json_400($msg)
    {
        header('HTTP/1.1 400 Bad Request');
        $response = ["response" => $msg];
        $response = json_encode($response);
        header('Content-Type: application/json');
        return $response;
    }
}
