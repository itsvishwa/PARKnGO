package com.example.parkngo.home;

public class AvailableParkingSpaceModel {
    String parkingName;
    int freeSlots;
    int totalSlots;
    int rate;
    String parkingType;
    int noOfStars;
    int noOfReviews;
    int distance;

    public AvailableParkingSpaceModel(String parkingName, int freeSlots, int totalSlots, int rate, String parkingType, int noOfStars, int noOfReviews, int distance) {
        this.parkingName = parkingName;
        this.freeSlots = freeSlots;
        this.totalSlots = totalSlots;
        this.rate = rate;
        this.parkingType = parkingType;
        this.noOfStars = noOfStars;
        this.noOfReviews = noOfReviews;
        this.distance = distance;
    }

    public String getParkingName() {
        return parkingName;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public int getTotalSlots() {
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

    public int getNoOfReviews() {
        return noOfReviews;
    }

    public int getDistance() {
        return distance;
    }
}
