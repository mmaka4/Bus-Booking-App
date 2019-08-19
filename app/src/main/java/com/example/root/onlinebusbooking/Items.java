package com.example.root.onlinebusbooking;

/**
 * Created by root on 2018/07/20.
 */

public class Items {
    String bId;
    String bName;
    int numOfSeats;
    int bPrice;
    String bClass;

    public Items(String regId, String bName, int numOfSeats, int bPrice, String bClass){
        this.bId = regId;
        this.bName = bName;
        this.numOfSeats = numOfSeats;
        this.bPrice = bPrice;
        this.bClass = bClass;
    }

    public String getId() {
        return bId;
    }

    public void setBusId(String busId) {
        this.bId = busId;
    }

    public String getBusName() {
        return bName;
    }

    public void setBusName(String busName) {
        this.bName = busName;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int seats) {
        this.numOfSeats = seats;
    }

    public int getBPrice() {
        return bPrice;
    }

    public void setBPrice(int bPrice) {
        this.bPrice = bPrice;
    }

    public String getBClass() {
        return bClass;
    }

    public void setBClass(String bClass) {
        this.bClass = bClass;
    }
}
