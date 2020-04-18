package com.valerioferretti.parking.repository.impl;

import com.valerioferretti.parking.model.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.valerioferretti.parking.repository.ParkingDao;

import java.util.List;

@Repository
public class ParkingDaoImpl implements ParkingDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Parking insert(Parking parking) {
        mongoTemplate.insert(parking);
        return parking;
    }

    public void delete(String parkingId) {
        Query query;

        query = new Query();
        query.addCriteria(Criteria.where("parkingId").is(parkingId));
        mongoTemplate.remove(query, Parking.class);
    }

    public Parking findById(String parkingId) {
        return mongoTemplate.findById(parkingId, Parking.class);
    }

    public List<Parking> findAll() {
        return mongoTemplate.findAll(Parking.class);
    }

    public Parking update(Parking parking) {
        mongoTemplate.save(parking);
        return parking;
    }
}
