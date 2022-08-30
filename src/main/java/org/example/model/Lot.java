package org.example.model;

import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;

import java.util.Date;

public class Lot {
    private int id;
    private String model;
    private String description;
    private double startPrice;
    private Date startDate;
    private User user;

    private boolean isActive;

    public Lot(int id, String model, String description, double startPrice, Date startDate, User user) {
        this.id = id;
        this.model = model;
        this.description = description;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.user = user;
    }

    public Lot(int id, String model, String description, double startPrice, Date startDate, int userId) {
        UserDao userDao = UserDaoImpl.getUserDao();
        this.id = id;
        this.model = model;
        this.description = description;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.user = userDao.findById(userId).get();
    }

    public Lot(String model, String description, double startPrice, Date startDate, User user) {
        this.model = model;
        this.description = description;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.user = user;
    }

    public Lot(String model, String description, double startPrice, User user) {
        this.model = model;
        this.description = description;
        this.startPrice = startPrice;
        this.user = user;
    }

    public Lot(String model, String description, double startPrice, Date startDate, User user, boolean isActive) {
        this.model = model;
        this.description = description;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.user = user;
        this.isActive = isActive;
    }

    public Lot(int id, String model, String description, double startPrice, Date startDate, User user, boolean isActive) {
        this.id = id;
        this.model = model;
        this.description = description;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.user = user;
        this.isActive = isActive;
    }



    public Lot() {


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
