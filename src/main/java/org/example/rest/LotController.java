package org.example.rest;

import com.google.gson.Gson;
import org.example.dao.LotDao;
import org.example.dao.impl.LotDaoImpl;
import org.example.model.Lot;
import org.example.model.Message;
import org.example.model.Offer;
import org.example.model.User;
import org.example.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/lot")
public class LotController extends HttpServlet {
    LotDao lotDao = LotDaoImpl.getLotDao();
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String model = req.getParameter("model");
        String description = req.getParameter("description");
        double startPrice = Double.parseDouble(req.getParameter("start_price"));
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user.isAdmin()) {

            boolean append = lotDao.append(new Lot(model, description, startPrice, user));
            String json;
            if (append) {
                json = gson.toJson(new Message(112, "successfully saved"));
            } else {
                json = gson.toJson(new Message(113, "something went wrong"));
            }
            Utils.response(resp, json);
        } else {
            String json = gson.toJson(new Message(111, "no permission"));
            Utils.response(resp, json);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Lot> lots = lotDao.getAll();
        String json = gson.toJson(new Message(200, "ok", lots));
        Utils.response(resp, json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int lotId = Integer.parseInt(req.getParameter("lot_id"));

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user.isAdmin()){
            Optional<Offer> optionalOffer = lotDao.setNoActive(lotId);
            if (optionalOffer.isPresent()){
                Offer offer = optionalOffer.get();
                String json = gson.toJson(new Message(200, "ok", offer));
                Utils.response(resp, json);
            }else{
                String json = gson.toJson(new Message(9, "offer not found"));
                Utils.response(resp, json);
            }
        }else {
            String json = gson.toJson(new Message(111, "no permission"));
            Utils.response(resp, json);
        }
    }
}
