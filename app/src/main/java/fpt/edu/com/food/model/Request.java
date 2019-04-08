package fpt.edu.com.food.model;

import java.util.List;

public class Request {
    private String phone;
    private String addres;
    private String total;
    private List<Order> foods;

    public Request() {
    }

    public Request(String phone, String addres, String total, List<Order> foods) {
        this.phone = phone;
        this.addres = addres;
        this.total = total;
        this.foods = foods;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
