<?php
class Post
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }

  public function getAdmin()
  {
    $this->db->query("SELECT * FROM admin");
    $results = $this->db->resultSet();
    return $results;
  }
}
