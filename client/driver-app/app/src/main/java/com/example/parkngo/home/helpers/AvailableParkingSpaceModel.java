package com.example.parkngo.home.helpers;

public class AvailableParkingSpaceModel {
    String parkingName;
    int freeSlots;
    String totalSlots;
    int rate;
    String parkingType;
    int noOfStars;
    String noOfReviews;
    Double distance;
    String latitude;
    String longitude;
    String parkingID;
    String location;

    public AvailableParkingSpaceModel(String parkingID, String parkingName, String location, int freeSlots, String totalSlots, int rate, String parkingType, int noOfStars, String noOfReviews, Double distance, String latitude, String longitude) {
        this.parkingID = parkingID;
        this.parkingName = parkingName;
        this.freeSlots = freeSlots;
        this.totalSlots = totalSlots;
        this.rate = rate;
        this.parkingType = parkingType;
        this.noOfStars = noOfStars;
        this.noOfReviews = noOfReviews;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public String getParkingID(){
        return parkingID;
    }

    public String getLocation(){
        return  location;
    }

    public String getParkingName() {
        return parkingName;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public String getTotalSlots() {
        return totalSlots;
    }

    public int getRate() {
        return rate;
    }

    public String getParkingType() {
        return parkingType;
    }

    public int getNoOfStars() {
        return noOfStars;
    }

    public String getNoOfReviews() {
        return noOfReviews;
    }

    public Double getDistance() {
        return distance;
    }

    public String getLatitude(){ return latitude;}

    public String getLongitude(){return longitude;}
}
