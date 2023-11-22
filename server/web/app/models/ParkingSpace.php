<?php
class ParkingSpace
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }

  // Register company
  public function registerParking($data)
  {
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
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }
}
