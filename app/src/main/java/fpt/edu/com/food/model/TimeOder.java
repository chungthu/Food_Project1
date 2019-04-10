package fpt.edu.com.food.model;

public class TimeOder {
    String addres;
    String total;
    String phone;
    String satus;
    String date;

    public TimeOder() {
    }

    public TimeOder(String addres, String total, String phone, String satus, String date) {
        this.addres = addres;
        this.total = total;
        this.phone = phone;
        this.satus = satus;
        this.date = date;
    }

    public String getLocation() {
        return addres;
    }

    public void setLocation(String location) {
        this.addres = location;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSatus() {
        return satus;
    }

    public void setSatus(String satus) {
        this.satus = satus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
