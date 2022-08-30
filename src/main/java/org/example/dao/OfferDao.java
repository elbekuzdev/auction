package org.example.dao;

import org.example.model.Offer;

import java.util.List;

public interface OfferDao extends BasicDao<Offer>{
    List<Offer> findByLotId(int lotId);
}
