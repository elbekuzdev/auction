package org.example.rest;

import com.google.gson.Gson;
import org.example.dao.LotDao;
import org.example.dao.OfferDao;
import org.example.dao.UserDao;
import org.example.dao.impl.LotDaoImpl;
import org.example.dao.impl.OfferDaoImpl;
import org.example.dao.impl.UserDaoImpl;
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

@WebServlet("/offer")
public class OfferController extends HttpServlet {
    OfferDao offerDao = OfferDaoImpl.getOfferDao();
    LotDao lotDao = LotDaoImpl.getLotDao();
    Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int lotId = Integer.parseInt(req.getParameter("lot_id"));
        String price1 = req.getParameter("price");
        double price = Double.parseDouble(price1);
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        Optional<Lot> optionalLot = lotDao.findById(lotId);
        Lot lot = new Lot();
        if (optionalLot.isPresent()){
            lot = optionalLot.get();
        }
        try {
            boolean append = offerDao.append(new Offer(lot, user, price));
            if (append) {
                String json = gson.toJson(new Message(112, "successfully saved"));
                Utils.response(resp, json);
            } else {
                String json = gson.toJson(new Message(113, "something went wrong"));
                Utils.response(resp, json);
            }
        }catch (Exception e){
            String json = gson.toJson(new Message(115, "lot_id is wrong"));
            Utils.response(resp, json);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Offer> offers = offerDao.getAll();
        String json = gson.toJson(new Message(200, "ok", offers));
        Utils.response(resp, json);
    }


}
