package org.example.dao.impl;

import org.example.PostgresConnection;
import org.example.dao.LotDao;
import org.example.dao.OfferDao;
import org.example.dao.UserDao;
import org.example.model.Lot;
import org.example.model.Offer;
import org.example.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OfferDaoImpl implements OfferDao {

    Connection connection = PostgresConnection.getConnection();
    UserDao userDao = UserDaoImpl.getUserDao();
    LotDao lotDao = LotDaoImpl.getLotDao();

    private static OfferDao offerDao;

    public static OfferDao getOfferDao(){
        if (offerDao == null){
            offerDao = new OfferDaoImpl();
        }
        return offerDao;
    }


    @Override
    public boolean append(Offer offer) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into offer(lot_id, user_id, price) values (?, ?, ?)");
            statement.setInt(1, offer.getLot().getId());
            statement.setInt(2, offer.getUser().getId());
            statement.setDouble(3, offer.getPrice());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Offer offer) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Optional<Offer> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Offer> getAll() {
        List<Offer> offers = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select id, lot_id, user_id, price, date from offer order by price desc");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int lotId = resultSet.getInt("lot_id");
                int userId = resultSet.getInt("user_id");
                double price = resultSet.getDouble("price");
                Date date = resultSet.getDate("date");
                Optional<Lot> optionalLot = lotDao.findById(lotId);
                Optional<User> optionalUser = userDao.findById(userId);
                User user = new User();
                Lot lot = new Lot();
                if (optionalLot.isPresent()){
                    lot = optionalLot.get();
                }
                if (optionalUser.isPresent()){
                    user = optionalUser.get();
                }

                offers.add(new Offer(id, lot, user, price, date));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offers;
    }

    @Override
    public List<Offer> findByLotId(int lotId) {
        List<Offer> offers = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select id, lot_id, user_id, price, date from offer where lot_id = ? order by price desc");
            statement.setInt(1, lotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                double price = resultSet.getDouble("price");
                Date date = resultSet.getDate("date");

                offers.add(new Offer(id, lotDao.findById(lotId).get(), userDao.findById(userId).get(), price, date));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offers;
    }
}
