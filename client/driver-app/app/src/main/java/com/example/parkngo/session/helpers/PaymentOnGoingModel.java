package com.example.parkngo.session.helpers;

import java.io.Serializable;

public class PaymentOnGoingModel  implements Serializable {
    String paymentID;
    String parking_name;
    String parking_rate;
    String start_time;
    String end_time;
    String vehicle_number;
    String vehicle_type;
    String officer_id;
    String officer_name;
    String hours;
    String minutes;
    String amount;

    public PaymentOnGoingModel(String paymentID, String parking_name, String parking_rate, String start_time, String end_time, String vehicle_number, String vehicle_type, String officer_id, String officer_name, String hours, String minutes, String amount) {
        this.paymentID = paymentID;
        this.parking_name = parking_name;
        this.parking_rate = parking_rate;
        this.start_time = start_time;
        this.end_time = end_time;
        this.vehicle_number = vehicle_number;
        this.vehicle_type = vehicle_type;
        this.officer_id = officer_id;
        this.officer_name = officer_name;
        this.hours = hours;
        this.minutes = minutes;
        this.amount = amount;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public String getParking_name() {
        return parking_name;
    }

    public String getParking_rate() {
        return parking_rate;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public String getOfficer_id() {
        return officer_id;
    }

    public String getOfficer_name() {
        return officer_name;
    }

    public String getHours() {
        return hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getAmount() {
        return amount;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public void setParking_rate(String parking_rate) {
        this.parking_rate = parking_rate;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public void setOfficer_name(String officer_name) {
        this.officer_name = officer_name;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
