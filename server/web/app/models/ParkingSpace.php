<?php
class ParkingSpace
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }

  public function parkingSave($data)
  {
    if (isset($data['parking_id'])) {
      return $this->updateParking($data);
    } else {
      return $this->registerParking($data);
    }
  }

  // Register company
  public function registerParking($data)
  {
    // Check if required keys are present
    if (
      isset($data['name'], $data['address'], $data['latitude'], $data['longitude'], $data['parkingType'], $data['parkingSlotBatches'])
    ) {
      // Start a transaction to ensure data consistency
      $this->db->beginTransaction();

      try {
        // Insert into parking_spaces table
        $this->db->query('INSERT INTO parking_spaces (name, address, latitude, longitude, no_of_slots, is_public, company_id) VALUES (:name, :address, :latitude, :longitude, :no_of_slots, :is_public, :company_id)');

        // Bind values
        $this->db->bind(':name', $data['name']);
        $this->db->bind(':address', $data['address']);
        $this->db->bind(':latitude', $data['latitude']);
        $this->db->bind(':longitude', $data['longitude']);
        $this->db->bind(':is_public', ($data['parkingType'] == 'public' ? 1 : 0));
        $this->db->bind(':company_id', $_SESSION['user_id']);

        $totalSlots = 0;

        if ($data['parkingSlotBatches'] && count($data['parkingSlotBatches']) > 0) {
          // Use array_reduce to sum up the noOfSlots
          $totalSlots = array_reduce($data['parkingSlotBatches'], function ($acc, $batch) {
            return $acc + intval($batch['noOfSlots']);
          }, 0);
        }
        $this->db->bind(':no_of_slots', $totalSlots);

        // Execute
        $this->db->execute();

        // Get the last inserted parking space ID
        $parkingSpaceId = $this->db->lastInsertId();

        // Insert into parking_space_status table
        foreach ($data['parkingSlotBatches'] as $batch) {
          $this->db->query('INSERT INTO parking_space_status (vehicle_type, free_slots, total_slots, rate, parking_id) VALUES (:vehicle_type, :free_slots, :total_slots, :rate, :parking_id)');

          // Bind values
          $this->db->bind(':vehicle_type', $batch['vehicleType']);
          $this->db->bind(':free_slots', $batch['noOfSlots']);
          $this->db->bind(':total_slots', $batch['noOfSlots']);
          $this->db->bind(':rate', $batch['parkingRate']);
          $this->db->bind(':parking_id', $parkingSpaceId);

          // Execute for each batch
          $this->db->execute();
        }

        // Commit the transaction
        $this->db->commit();

        return true;
      } catch (PDOException $e) {
        // Rollback the transaction on error
        $this->db->rollBack();
        // Log the error
        error_log('Error saving parking space: ' . $e->getMessage());
        return false;
      }
    } else {
      // Handle missing keys
      error_log('Invalid data format. Missing required keys.');
      return false;
    }
  }

  public function updateParking($data)
  {
    // Check if required keys are present
    if (isset($data['name'], $data['address'], $data['latitude'], $data['longitude'], $data['parkingType'], $data['parkingSlotBatches'])) {
      // Start a transaction to ensure data consistency
      $this->db->beginTransaction();

      try {
        // Check if parking space with the given ID exists
        $existingParkingSpace = $this->getParkingSpaceById($data['parking_id']);

        if (!$existingParkingSpace) {
          // Parking space not found
          error_log('Parking space with ID ' . $data['parking_id'] . ' not found.');
          return false;
        }

        // Update parking_spaces table
        $this->db->query('UPDATE parking_spaces SET name = :name, address = :address, latitude = :latitude, no_of_slots = :no_of_slots, longitude = :longitude, is_public = :is_public WHERE _id = :parking_id');

        // Bind values
        $this->db->bind(':name', $data['name']);
        $this->db->bind(':address', $data['address']);
        $this->db->bind(':latitude', $data['latitude']);
        $this->db->bind(':longitude', $data['longitude']);
        $this->db->bind(':is_public', ($data['parkingType'] == 'public' ? 1 : 0));
        $this->db->bind(':parking_id', $data['parking_id']);

        $totalSlots = 0;

        if ($data['parkingSlotBatches'] && count($data['parkingSlotBatches']) > 0) {
          // Use array_reduce to sum up the noOfSlots
          $totalSlots = array_reduce($data['parkingSlotBatches'], function ($acc, $batch) {
            return $acc + intval($batch['noOfSlots']);
          }, 0);
        }
        $this->db->bind(':no_of_slots', $totalSlots);

        // Execute
        $this->db->execute();

        // Delete existing parking space status entries
        $this->deleteParkingSpaceStatusByParkingId($data['parking_id']);

        // Update parking_space_status table
        foreach ($data['parkingSlotBatches'] as $batch) {
          // Insert new entries
          $this->db->query('INSERT INTO parking_space_status (vehicle_type, free_slots, total_slots, rate, parking_id) VALUES (:vehicle_type, :free_slots, :total_slots, :rate, :parking_id)');

          // Bind values
          $this->db->bind(':vehicle_type', $batch['vehicleType']);
          $this->db->bind(':free_slots', $batch['noOfSlots']);
          $this->db->bind(':total_slots', $batch['noOfSlots']);
          $this->db->bind(':rate', $batch['parkingRate']);
          $this->db->bind(':parking_id', $data['parking_id']);

          // Execute for each batch
          $this->db->execute();
        }

        // Commit the transaction
        $this->db->commit();

        return true;
      } catch (PDOException $e) {
        // Rollback the transaction on error
        $this->db->rollBack();
        // Log the error
        error_log('Error updating parking space: ' . $e->getMessage() . ' ' . $e->getTraceAsString());
        return false;
      }
    } else {
      // Handle missing keys
      error_log('Invalid data format. Missing required keys.');
      return false;
    }
  }

  // Helper function to get parking space by ID
  private function getParkingSpaceById($parking_id)
  {
    $this->db->query('SELECT * FROM parking_spaces WHERE _id = :parking_id');
    $this->db->bind(':parking_id', $parking_id);
    $result = $this->db->single(); // Assuming you have a method like this to retrieve a single row

    return $result;
  }

  // Helper function to delete parking space status entries by parking_id
  private function deleteParkingSpaceStatusByParkingId($parking_id)
  {
    $this->db->query('DELETE FROM parking_space_status WHERE parking_id = :parking_id');
    $this->db->bind(':parking_id', $parking_id);
    $this->db->execute();
  }




  public function getCardDetailsFromParkingOfficer($company_id, $parking_id = null)
  {
    $this->db->query('SELECT
                        ps._id AS parking_id,
                        ps.name AS parking_name,
                        ps.address AS parking_address,
                        ps.no_of_slots AS parking_total_slots,
                        ps.is_public AS parking_is_public,
                        ps.is_closed AS parking_is_closed,
                        ps.closed_start_time AS parking_closed_start_time,
                        ps.closed_end_time AS parking_closed_end_time,
                        ps.latitude AS parking_latitude,
                        ps.longitude AS parking_longitude,
                        ps.avg_star_count AS parking_avg_star_count,
                        ps.total_review_count AS parking_total_review_count,
                        ps.company_id AS parking_space_company_id,
                        po.first_name AS officer_first_name,
                        po.last_name AS officer_last_name
                      FROM
                        parking_spaces ps
                      LEFT JOIN
                        parking_officer po ON ps._id = po.parking_id
                      WHERE ps.company_id = :company_id' . ($parking_id != null ? ' AND ps._id = :parking_id' : ''));
    $this->db->bind(':company_id', $company_id);
    if ($parking_id !== null) {
      $this->db->bind(':parking_id', $parking_id);
      $row = $this->db->single();
    } else {
      $row = $this->db->resultSet();
    }
    return $row;
  }

  public function getCardDetailsFromParkingSpaceStatus($company_id, $parking_id = null)
  {
    $this->db->query('SELECT
                        ps._id AS parking_space_id,
                        pss.vehicle_type AS vehicle_type,
                        pss.free_slots AS each_type_free_slots,
                        pss.total_slots AS each_type_total_slots,
                        pss.rate AS each_type_rate
                      FROM
                        parking_spaces ps
                      LEFT JOIN
                        parking_space_status pss ON ps._id = pss.parking_id
                      WHERE ps.company_id = :company_id' . ($parking_id != null ? ' AND ps._id = :parking_id' : ''));
    $this->db->bind(':company_id', $company_id);
    if ($parking_id !== null) {
      $this->db->bind(':parking_id', $parking_id);
    }
    $row = $this->db->resultSet();

    return $row;
  }

  public function deleteParkingSpace($parking_id)
  {
    $this->db->query(
      'DELETE FROM 
        parking_spaces 
      WHERE _id = :parking_id'
    );

    // Bind values
    $this->db->bind(':parking_id', $parking_id);

    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  public function updateClosedTime($data)
  {
    $this->db->query(
      'UPDATE parking_spaces 
            SET is_closed = 1, closed_start_time = :closed_start_time, closed_end_time = :closed_end_time
            WHERE _id = :parking_id'
    );

    $startTime = new DateTime($data['closed_start_time']);
    $unixStartTime = $startTime->getTimestamp();

    $endTime = new DateTime($data['closed_end_time']);
    $unixEndTime = $endTime->getTimestamp();


    $this->db->bind(':closed_start_time', $unixStartTime);
    $this->db->bind(':closed_end_time', $unixEndTime);
    $this->db->bind(':parking_id', $data['parking_id']);

    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  public function getReviewDetails()
  {
    $this->db->query(
      'SELECT
        review._id AS review_id,
        review.parking_id AS parking_id,
        parking_spaces.name AS parking_name,
        parking_spaces.address AS parking_address,
        driver.first_name AS driver_first_name, 
        driver.last_name AS driver_last_name,
        review.content,
        review.time_stamp,
        review.no_of_stars
      FROM
        review
      LEFT JOIN parking_spaces ON review.parking_id = parking_spaces._id
      LEFT JOIN driver ON review.driver_id = driver._id'
    );

    $row = $this->db->resultSet();

    return $row;
  }
}