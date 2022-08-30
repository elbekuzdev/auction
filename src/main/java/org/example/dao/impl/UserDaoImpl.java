package org.example.dao.impl;

import org.example.PostgresConnection;
import org.example.dao.UserDao;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    Connection connection = PostgresConnection.getConnection();
    private static UserDao userDao = null;

    public static UserDao getUserDao(){
        if (userDao == null){
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    @Override
    public boolean append(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(int id) {
       try {
           PreparedStatement statement = connection.prepareStatement("delete from users where id = ?");
           statement.setInt(1, id);
           return statement.execute();
       }catch (Exception e){
           e.printStackTrace();
       }
       return false;
    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> user = Optional.empty();
        try {
            PreparedStatement statement = connection.prepareStatement("select id, firstname, lastname, username, password, is_admin from users where id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                user = Optional.of(new User(id, firstname, lastname, username, password, isAdmin));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select id, firstname, lastname, username, password, is_admin from users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                users.add(new User(id, firstname, lastname, username, password, isAdmin));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        Optional<User> user = Optional.empty();
        try {
            PreparedStatement statement = connection.prepareStatement("select id, firstname, lastname, username, password, is_admin from users where username = ? and password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                user = Optional.of(new User(id, firstname, lastname, username, password, isAdmin));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = Optional.empty();
        try {
            PreparedStatement statement = connection.prepareStatement("select id, firstname, lastname, username, password, is_admin from users where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                user = Optional.of(new User(id, firstname, lastname, username, password, isAdmin));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
