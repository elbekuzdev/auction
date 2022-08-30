package org.example.rest;

import com.google.gson.Gson;
import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.model.Message;
import org.example.model.User;
import org.example.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class UserController extends HttpServlet {
    Gson gson = new Gson();
    UserDao userDao = UserDaoImpl.getUserDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (!(username == null || password == null)){
            Optional<User> optionalUser = userDao.findByUsernameAndPassword(username, password);
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                String key = Utils.makeKey(user);
                String json = gson.toJson(new Message(100, "authorized", key));
                Utils.response(resp, json);
            }else{
                String json = gson.toJson(new Message(101, "unauthorized"));
                Utils.response(resp, json);
            }
        }else{
            String json = gson.toJson(new Message(5, "bad request"));
            Utils.response(resp, json);
        }

    }
}
