package com.example.officertestapp.Status;

public class ParkingStatusModel {
    String vehicleNumber;
    String vehicleType;
    String dateTime;
    String parkingStatus;
    String _id;

    public ParkingStatusModel(String _id, String vehicleNumber, String vehicleType, String dateTime, String parkingStatus) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.dateTime = dateTime;
        this.parkingStatus = parkingStatus;
        this._id = _id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType.toUpperCase();
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getParkingStatus() {
        return parkingStatus;
    }

    public String getID(){
        return _id;
    }
}
