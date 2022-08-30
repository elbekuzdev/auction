package org.example.util;

import org.example.dao.UserDao;
import org.example.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class Utils {
    public static void response(HttpServletResponse resp, String json) {
        resp.setContentType("application/json");
        try {
            resp.getWriter().print(json);
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String makeKey(User user) {
        try {
            return Base64.getEncoder().encodeToString((user.getUsername() + "&" + user.getPassword()).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean keyIsValid(String key) {
        try {
            String s = new String(Base64.getDecoder().decode(key.getBytes()));
            return s.split("&").length == 2;
        } catch (Exception e) {
            return false;
        }
    }

    public static Optional<User> getUser(String key, UserDao userDao) {
        String k = new String(Base64.getDecoder().decode(key.getBytes()));
        String[] keys = k.split("&");
        return userDao.findByUsernameAndPassword(keys[0], keys[1]);
    }
}
