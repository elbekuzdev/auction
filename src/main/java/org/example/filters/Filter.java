package org.example.filters;

import com.google.gson.Gson;
import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.model.Message;
import org.example.model.User;
import org.example.util.Utils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
    Gson gson = new Gson();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("application/json");
        UserDao userDao = UserDaoImpl.getUserDao();

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String uri = req.getRequestURI();
        String key = req.getHeader("key");

        if (uri.startsWith("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (key == null || key.isEmpty() || !Utils.keyIsValid(key)) {
                String json = gson.toJson(new Message(7, "key is invalid"));
                Utils.response(resp, json);
            } else {
                Optional<User> user = Utils.getUser(key, userDao);
                if (user.isPresent()){
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user.get());
                    filterChain.doFilter(servletRequest, servletResponse);
                }else{
                    String json = gson.toJson(new Message(9, "user not found"));
                    Utils.response(resp, json);
                }
            }
        }
    }
}
