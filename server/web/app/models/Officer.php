<?php
class Officer
{
  private $db;

  public function __construct()
  {
    $this->db = new Database;
  }

  // Register Parking Officer
  public function register($data)
  {
    $this->db->query(
      'INSERT INTO 
        parking_officer (nic, mobile_number, first_name, last_name, company_id, officer_id, profile_photo ) 
      VALUES (:nic, :mobile_number, :first_name, :last_name, :company_id, :officer_id, :profile_photo)'
    );
    // Bind values
    $this->db->bind(':nic', $data['nic']);
    $this->db->bind(':mobile_number', $data['mobile_number']);
    $this->db->bind(':first_name', $data['first_name']);
    $this->db->bind(':last_name', $data['last_name']);
    $this->db->bind(':company_id', $data['company_id']);
    $this->db->bind(':officer_id', $data['officer_id']);
    $this->db->bind(':profile_photo', $data['profile_image']);
    //$this->db->bind(':parking_id', $data['parking_id']);

    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  public function update($data)
  {
    $this->db->query(
      'UPDATE 
        parking_officer 
      SET nic = :nic, mobile_number = :mobile_number, first_name = :first_name, last_name = :last_name, profile_photo = :profile_photo
      WHERE officer_id = :officer_id AND company_id = :company_id'
    );

    // Bind values
    $this->db->bind(':nic', $data['nic']);
    $this->db->bind(':mobile_number', $data['mobile_number']);
    $this->db->bind(':first_name', $data['first_name']);
    $this->db->bind(':last_name', $data['last_name']);
    $this->db->bind(':officer_id', $data['officer_id']);
    $this->db->bind(':company_id', $data['company_id']);
    $this->db->bind(':profile_photo', $data['profile_image']);

    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  public function delete($officer_id, $company_id)
  {
    $this->db->query(
      'DELETE FROM 
        parking_officer 
      WHERE officer_id = :officer_id AND company_id = :company_id'
    );

    // Bind values
    $this->db->bind(':officer_id', $officer_id);
    $this->db->bind(':company_id', $company_id);

    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }

  public function assignParkingSpace($officer_id, $parking_id, $company_id)
  {
    $this->db->query(
      'UPDATE 
        parking_officer 
      SET parking_id = :parking_id 
      WHERE officer_id = :officer_id AND company_id = :company_id'
    );

    // Bind values
    $this->db->bind(':parking_id', $parking_id);
    $this->db->bind(':officer_id', $officer_id);
    $this->db->bind(':company_id', $company_id);

    // Execute
    if ($this->db->execute()) {
      return true;
    } else {
      return false;
    }
  }



  // Find Parking Officer by Phone number
  public function findOfficerByPhoneNumber($mobile_number)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE mobile_number = :mobile_number');
    $this->db->bind(':mobile_number', $mobile_number);

    $row = $this->db->single();

    // Check roww
    if ($this->db->rowCount() > 0) {
      return true;
    } else {
      return false;
    }
  }

  //Find parking officer by nic
  public function findOfficerByNic($nic)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE nic = :nic');
    $this->db->bind(':nic', $nic);

    $row = $this->db->single();

    // Check roww
    if ($this->db->rowCount() > 0) {
      return true;
    } else {
      return false;
    }
  }

  //Find parking officer by officer id
  public function findOfficerByOfficerId($officer_id)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE officer_id = :officer_id');
    $this->db->bind(':officer_id', $officer_id);

    $row = $this->db->single();

    // Check roww
    if ($this->db->rowCount() > 0) {
      return $row;
    } else {
      return false;
    }
  }

  public function getOfficerDetailsUsingPhoneNumber($mobile_number)
  {
    $this->db->query('SELECT * FROM parking_officer WHERE mobile_number = :mobile_number');
    $this->db->bind(':mobile_number', $mobile_number);

    $row = $this->db->single();

    return $row;
  }

  //get officers details for parking officer view 
  // public function getAllOfficersDetails($company_id)
  // {
  //   $this->db->query('SELECT po.*, ps.name AS parking_name, ps._id AS parking_id
  //   FROM parking_officer po
  //   LEFT JOIN parking_spaces ps ON po.parking_id = ps._id
  //   WHERE po.company_id = :company_id;');
  //   $this->db->bind(':company_id', $company_id);
  //   $results = $this->db->resultSet();
  //   return $results;
  // }
  public function getAllOfficersDetails($company_id)
  {
    $this->db->query('SELECT po.*, ps.name AS parking_name, ps._id AS parking_id, dr.type AS duty_type, dr.last_duty_timestamp AS last_duty_timestamp
    FROM parking_officer po
    LEFT JOIN parking_spaces ps ON po.parking_id = ps._id
    LEFT JOIN (
        SELECT officer_id, MAX(time_stamp) AS last_duty_timestamp, type
        FROM duty_record
        GROUP BY officer_id
    ) dr ON po._id = dr.officer_id
    WHERE po.company_id = :company_id;');

    $this->db->bind(':company_id', $company_id);
    $results = $this->db->resultSet();

    return $results;
  }


  //get officer details for company dashboard
  public function getOfficerDetails($company_id)
  {
    $this->db->query("SELECT CONCAT(po.first_name, ' ', po.last_name) AS officer_name, 
      IFNULL(CONCAT(ps._id, ' ', ps.name), 'Not Assigned') AS parking_details
      FROM parking_officer po
      LEFT JOIN parking_spaces ps ON po._id = ps._id 
      WHERE po.company_id = :company_id
      LIMIT 10");
    $this->db->bind(':company_id', $company_id);
    $results = $this->db->resultSet();
    return $results;
  }

  //get resently added officer
  public function getRecentlyAddedOfficer()
  {
    $this->db->query("SELECT * FROM parking_officer ORDER BY _id DESC LIMIT 1");
    $row = $this->db->single();
    return $row;
  }

  //Details for parking officer card details using officer id and company id
  public function getOfficerCardDetails($officer_id, $company_id)
  {
    $this->db->query('SELECT po.*, IFNULL(CONCAT(ps._id, " ", ps.name), "Not Assigned") AS parking_space
    FROM parking_officer po
    LEFT JOIN parking_spaces ps ON po.parking_id = ps._id
    WHERE po.company_id = :company_id AND po.officer_id = :officer_id;');
    $this->db->bind(':company_id', $company_id);
    $this->db->bind(':officer_id', $officer_id);
    $results = $this->db->single();
    return $results;
  }

  //   public function getOfficerActivities($officer_id, $company_id)
  //   {
  //     $this->db->query("SELECT
  //     oa.type AS activity_type,
  //     po.first_name AS officer_first_name,
  //     po.last_name AS officer_last_name,
  //     ps.name AS parking_space_name,
  //     sess.start_time AS session_start_time,
  //     sess.end_time AS session_end_time,
  //     sess.vehicle_number AS session_vehicle_number,
  //     sess.parking_id AS session_parking_id,
  //     pss.vehicle_type AS parking_space_status_vehicle_type,
  //     pss.rate AS parking_space_status_rate,
  //     pay.amount AS payment_amount,
  //     pay.is_complete AS payment_is_complete,
  //     pay.payment_method AS payment_method,
  //     pay.time_stamp AS payment_time_stamp
  // FROM
  //     officer_activity oa
  // JOIN
  //     parking_officer po ON oa.officer_id = po._id

  // LEFT JOIN
  //     parking_session sess ON oa.session_id = sess._id
  // LEFT JOIN
  //     parking_spaces ps ON sess.parking_id = ps._id
  // LEFT JOIN
  //     parking_space_status pss ON sess.parking_id = pss.parking_id
  // LEFT JOIN
  //     payment pay ON sess._id = pay.session_id
  // WHERE
  //     po.company_id = :company_id AND po.officer_id = :officer_id
  // ORDER BY
  //     oa.time_stamp DESC;
  // ");
  //     $this->db->bind(':company_id', $company_id);
  //     $this->db->bind(':officer_id', $officer_id);
  //     $row = $this->db->resultSet();
  //     return $row;
  //   }

  public function getOfficerActivities($officer_id, $company_id)
  {
    $this->db->query("SELECT
  oa._id AS officer_activity_id,
  oa.type AS activity_type,
  oa.time_stamp AS activity_time_stamp,
  oa.session_id,
  oa.officer_id,
  ps.vehicle_number AS vehicle_number,
  ps.vehicle_type AS vehicle_type,
  ps.driver_id,
  ps.parking_id,
  pspace.name AS parking_space_name,
  pspace.company_id,
  d.first_name AS first_name,
  d.last_name AS last_name,
  d.mobile_number AS driver_mobile_number
FROM
  officer_activity oa
LEFT JOIN parking_session ps ON oa.session_id = ps._id
LEFT JOIN parking_spaces pspace ON ps.parking_id = pspace._id
LEFT JOIN driver d ON d._id = ps.driver_id
LEFT JOIN parking_officer po ON oa.officer_id = po._id
WHERE
  po.officer_id = :officer_id AND pspace.company_id = :company_id
ORDER BY
  oa._id DESC;
");
    $this->db->bind(':company_id', $company_id);
    $this->db->bind(':officer_id', $officer_id);
    $row = $this->db->resultSet();
    return $row;
  }

  public function getOfficerActivitiesCount($company_id)
  {
    $this->db->query("SELECT 
    po._id AS officer_id,
    po.first_name,
    po.last_name,
    COUNT(oa._id) AS no_of_activities
FROM 
    officer_activity AS oa
LEFT JOIN 
    parking_officer AS po ON oa.officer_id = po._id
WHERE 
    DATE(FROM_UNIXTIME(oa.time_stamp)) = CURDATE() AND po.company_id = :company_id
GROUP BY 
    po._id, po.first_name, po.last_name
ORDER BY 
    no_of_activities DESC;

");
    $this->db->bind(':company_id', $company_id);
    $row = $this->db->resultSet();
    return $row;
  }
}
