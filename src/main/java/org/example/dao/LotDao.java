package org.example.dao;

import org.example.model.Lot;
import org.example.model.Offer;
import org.example.model.User;

import java.util.List;
import java.util.Optional;

public interface LotDao extends BasicDao<Lot>{
        Optional<Offer> setNoActive(int id);
}
