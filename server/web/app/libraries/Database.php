<?php

/**
 * PDO Database Class
 * Connect to database
 * Create prepared statements
 * Bind values
 * Return rows and results
 */

class Database
{
  private $host = DB_HOST;
  private $user = DB_USER;
  private $pass = DB_PASS;
  private $dbname = DB_NAME;

  private $dbh; // Database handler
  private $stmt; // Statement
  private $error;

  public function __construct()
  {
    // Set DSN
    $dsn = 'mysql:host=' . $this->host . ';dbname=' . $this->dbname;
    $options = array(
      // PDO::MYSQL_ATTR_SSL_CA     => 'C:/certificate/DigiCertGlobalRootCA.crt.pem',

      PDO::ATTR_PERSISTENT => true, // Persistent connection
      PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION // Throw exceptions
    );

    // Create PDO instance
    try {
      $this->dbh = new PDO($dsn, $this->user, $this->pass, $options);
    } catch (PDOException $e) {
      $this->error = $e->getMessage();
      echo $this->error;
    }
  }

  // Prepare statement with query
  public function query($sql)
  {
    $this->stmt = $this->dbh->prepare($sql);
  }

  // Bind values
  public function bind($param, $value, $type = null)
  {
    if (is_null($type)) {
      switch (true) {
        case is_int($value):
          $type = PDO::PARAM_INT;
          break;
        case is_bool($value):
          $type = PDO::PARAM_BOOL;
          break;
        case is_null($value):
          $type = PDO::PARAM_NULL;
          break;
        default:
          $type = PDO::PARAM_STR;
      }
    }

    $this->stmt->bindValue($param, $value, $type);
  }

  // Execute the prepared statement
  public function execute()
  {
    return $this->stmt->execute();
  }

  // Get result set as array of objects
  public function resultSet()
  {
    $this->execute();
    return $this->stmt->fetchAll(PDO::FETCH_OBJ);
  }

  // Get single record as object
  public function single()
  {
    $this->execute();
    return $this->stmt->fetch(PDO::FETCH_OBJ);
  }

  // Get row count
  public function rowCount()
  {
    return $this->stmt->rowCount();
  }

  public function calcData()
  {
    $this->execute();
    return $this->stmt->fetch(PDO::FETCH_ASSOC);
  }

  public function fetch()
  {
    return $this->stmt->fetch(PDO::FETCH_OBJ);
  }

  // Begin a transaction
  public function beginTransaction()
  {
    return $this->dbh->beginTransaction();
  }

  // Commit a transaction
  public function commit()
  {
    return $this->dbh->commit();
  }

  // Roll back a transaction
  public function rollBack()
  {
    return $this->dbh->rollBack();
  }

  // Returns the ID of the last inserted row or sequence value
  public function lastInsertId()
  {
    return $this->dbh->lastInsertId();
  }
}
