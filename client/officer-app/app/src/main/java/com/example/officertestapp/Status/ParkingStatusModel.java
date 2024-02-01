package com.example.officertestapp.Status;

public class ParkingStatusModel {
    String vehicleNumber;
    String vehicleType;
    String dateTime;
    String parkingStatus;


    public ParkingStatusModel(String vehicleNumber, String vehicleType, String dateTime, String parkingStatus) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.dateTime = dateTime;
        this.parkingStatus = parkingStatus;
    }

    public String getVehicleNumber() {return vehicleNumber;}

    public String getVehicleType() {
        return vehicleType;
    }

    public String getDateTime() {return dateTime;}

    public String getParkingStatus() {
        return parkingStatus;
    }


}
