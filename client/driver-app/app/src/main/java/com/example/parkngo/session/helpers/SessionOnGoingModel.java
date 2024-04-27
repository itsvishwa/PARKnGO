package com.example.parkngo.session.helpers;

import org.json.JSONObject;

import java.io.Serializable;

public class SessionOnGoingModel implements Serializable {

    String sessionID;
    String parking_name;
    String parking_rate;
    String start_time;
    String vehicle_number;
    String vehicle_type;
    String officer_id;
    String officer_name;
    String hours;
    String minutes;

    public SessionOnGoingModel(String sessionID, String parking_name, String parking_rate, String start_time, String vehicle_number, String vehicle_type, String officer_id, String officer_name, String hours, String minutes) {
        this.sessionID = sessionID;
        this.parking_name = parking_name;
        this.parking_rate = parking_rate;
        this.start_time = start_time;
        this.vehicle_number = vehicle_number;
        this.vehicle_type = vehicle_type;
        this.officer_id = officer_id;
        this.officer_name = officer_name;
        this.hours = hours;
        this.minutes = minutes;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getParking_rate() {
        return parking_rate;
    }

    public void setParking_rate(String parking_rate) {
        this.parking_rate = parking_rate;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getOfficer_name() {
        return officer_name;
    }

    public void setOfficer_name(String officer_name) {
        this.officer_name = officer_name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
}
