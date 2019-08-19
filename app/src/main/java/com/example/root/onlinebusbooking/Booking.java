package com.example.root.onlinebusbooking;

/**
 * Created by root on 2018/07/19.
 */

public class Booking {
    String seat;
    String time;
    int bus_id;

    public Booking(String seat, String time, int bus_id) {
        this.seat = seat;
        this.time = time;
        this.bus_id = bus_id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }
}
