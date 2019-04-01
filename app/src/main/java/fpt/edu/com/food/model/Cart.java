package fpt.edu.com.food.model;

import java.util.Date;

public class Cart {
    String namefood;
    double price;
    double amount;
    double total;
    String image;

    public Cart() {
    }

    public Cart(String namefood, double price, double amount, double total, String image) {
        this.namefood = namefood;
        this.price = price;
        this.amount = amount;
        this.total = total;
        this.image = image;
    }

    public Cart(double total) {
        this.total = total;
    }

    public String getNamefood() {
        return namefood;
    }

    public void setNamefood(String namefood) {
        this.namefood = namefood;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
