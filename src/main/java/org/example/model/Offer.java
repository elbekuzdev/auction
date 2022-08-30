package org.example.model;

import java.util.Date;

public class Offer {
    private int id;
    private Lot lot;
    private User user;
    private double price;
    private Date date;

    public Offer(int id, Lot lot, User user, double price, Date date) {
        this.id = id;
        this.lot = lot;
        this.user = user;
        this.price = price;
        this.date = date;
    }

    public Offer(Lot lot, User user, double price, Date date) {
        this.lot = lot;
        this.user = user;
        this.price = price;
        this.date = date;
    }

    public Offer(Lot lot, User user, double price) {
        this.lot = lot;
        this.user = user;
        this.price = price;
    }

    public Offer(int id, Lot lot, User user, double price) {
        this.id = id;
        this.lot = lot;
        this.user = user;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
