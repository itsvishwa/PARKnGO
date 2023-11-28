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

  public function getCardDetailsFromParkingOfficer($company_id)
  {
    $this->db->query('SELECT
                        ps._id AS parking_id,
                        ps.name AS parking_name,
                        ps.address AS parking_address,
                        ps.no_of_slots AS parking_total_slots,
                        ps.is_public AS parking_is_public,
                        ps.is_closed AS parking_is_closed,
                        ps.avg_star_count AS parking_avg_star_count,
                        ps.total_review_count AS parking_total_review_count,
                        ps.company_id AS parking_space_company_id,
                        po.first_name AS officer_first_name,
                        po.last_name AS officer_last_name
                      FROM
                        parking_spaces ps
                      LEFT JOIN
                        parking_officer po ON ps._id = po.parking_id
                      WHERE ps.company_id = :company_id');
    $this->db->bind(':company_id', $company_id);

    $row = $this->db->resultSet();
    return $row;
  }

  public function getCardDetailsFromParkingSpaceStatus($company_id)
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
                      WHERE ps.company_id = :company_id');
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }
}
