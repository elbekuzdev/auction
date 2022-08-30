package org.example.dao.impl;

import org.example.PostgresConnection;
import org.example.dao.LotDao;
import org.example.dao.UserDao;
import org.example.model.Lot;
import org.example.model.Offer;
import org.example.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LotDaoImpl implements LotDao {


    Connection connection = PostgresConnection.getConnection();
    private static LotDao lotDao = null;

    public static LotDao getLotDao(){
        if (lotDao == null){
            lotDao = new LotDaoImpl();
        }
        return lotDao;
    }
    UserDao userDao = UserDaoImpl.getUserDao();

    @Override
    public boolean append(Lot lot) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into lot(model, description, start_price, user_id) values (?, ?, ?, ?)");
            statement.setString(1, lot.getModel());
            statement.setString(2, lot.getDescription());
            statement.setDouble(3, lot.getStartPrice());
            statement.setInt(4, lot.getUser().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Lot lot) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Optional<Lot> findById(int id) {
        Optional<Lot> lot = Optional.empty();
        try {
            PreparedStatement statement = connection.prepareStatement("select model, description, start_price, start_date, user_id, is_active from lot where id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String model = resultSet.getString("model");
                String description = resultSet.getString("description");
                double startPrice = resultSet.getDouble("start_price");
                Date startDate = resultSet.getDate("start_date");
                int userId = resultSet.getInt("user_id");
                boolean isActive = resultSet.getBoolean("is_active");
                User user = new User();
                Optional<User> optionalUser = userDao.findById(userId);
                if (optionalUser.isPresent()){
                    user = optionalUser.get();
                }

                if (isActive){
                    lot = Optional.of(new Lot(id, model, description, startPrice, startDate, user));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lot;
    }

    @Override
    public List<Lot> getAll() {
        List<Lot> lots = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select id, model, description, start_price, start_date, user_id, is_active from lot");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String model = resultSet.getString("model");
                String description = resultSet.getString("description");
                double startPrice = resultSet.getDouble("start_price");
                Date startDate = resultSet.getDate("start_date");
                int userId = resultSet.getInt("user_id");
                boolean isActive = resultSet.getBoolean("is_active");
                if (isActive){
                    lots.add(new Lot(id, model, description, startPrice, startDate, userDao.findById(userId).get(), isActive));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lots;
    }

    @Override
    public Optional<Offer> setNoActive(int id)  {
        Optional<Offer> offer = Optional.empty();

        try {
            PreparedStatement statement = connection.prepareStatement("update lot set is_active = false where id = ? ");
            statement.setInt(1, id);
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement("select id, lot_id, user_id, price, date from offer where lot_id = ? order by price desc");
            statement1.setInt(1, id);
            ResultSet resultSet = statement1.executeQuery();
            if (resultSet.next()){
                int id1 = resultSet.getInt("id");
                int lotId = resultSet.getInt("lot_id");
                int userId = resultSet.getInt("user_id");
                double price = resultSet.getDouble("price");
                Date date = resultSet.getDate("date");
                Optional<Lot> optionalLot = lotDao.findById(lotId);
                Optional<User> optionalUser = userDao.findById(userId);
                Lot lot = new Lot();
                User user = new User();
                if (optionalLot.isPresent()){
                    lot = optionalLot.get();
                }
                if (optionalUser.isPresent()){
                    user = optionalUser.get();
                }



                offer = Optional.of(new Offer(id1, lot, user, price, date));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offer;
    }
}
