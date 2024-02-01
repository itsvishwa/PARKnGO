<?php
    class OfficerActivityModel{
        private $db;

        public function __construct() {
            $this->db = new Database;
        }

        public function add_officer_activity($activity_data) {
            $this->db->query("INSERT INTO officer_activity (type, time_stamp, session_id, officer_id) VALUES (:type, :time_stamp, :session_id, :officer_id)");

            $this->db->bind(":type", "start");
            $this->db->bind(":time_stamp", $activity_data["start_time"]);
            $this->db->bind(":session_id", $activity_data["session_id"]);
            $this->db->bind(":officer_id", $activity_data["officer_id"]);

            $this->db->execute();
        }

        public function end_officer_activity($_id, $token_data, $end_timestamp) {
            $session_id = $_id;
            $officer_id = $token_data["user_id"];

            $this->db->query("INSERT INTO officer_activity (type, time_stamp, session_id, officer_id) VALUES (:type, :time_stamp, :session_id, :officer_id)");

            $this->db->bind(":type", "end");
            $this->db->bind(":time_stamp", $end_timestamp);
            $this->db->bind(":session_id", $session_id);
            $this->db->bind(":officer_id", $officer_id);

            $this->db->execute();
        }

        public function get_session_id($officer_id) {
            $this->db->query("SELECT DISTINCT session_id FROM officer_activity WHERE type = 'end' AND officer_id = :officer_id");

            $this->db->bind(":officer_id", $officer_id);

            $results = $this->db->resultSet();

            if ($results === []) {
                return false;
            } else {
                return $results;
            }
        }
    }
?>