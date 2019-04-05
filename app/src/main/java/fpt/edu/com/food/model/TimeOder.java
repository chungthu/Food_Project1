package fpt.edu.com.food.model;

public class TimeOder {
    String time;
    String location;
    double total;
    String phone;

    public TimeOder() {
    }

    public TimeOder(String time, String location, double total, String number) {
        this.time = time;
        this.location = location;
        this.total = total;
        this.phone = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
