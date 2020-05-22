package com.valerioferretti.parking.repository.impl;

import com.valerioferretti.parking.model.UserProfile;
import com.valerioferretti.parking.repository.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDaoImpl implements UserProfileDao {

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserProfileDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public UserProfile insert(UserProfile userProfile) {
        return mongoTemplate.insert(userProfile);
    }

    @Override
    public UserProfile update(UserProfile userProfile) {
        return mongoTemplate.save(userProfile);
    }

    @Override
    public void delete(String email) {
        Query query;

        query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        mongoTemplate.remove(query, UserProfile.class);
    }

    @Override
    public UserProfile findById(String email) {
        return mongoTemplate.findById(email, UserProfile.class);
    }

    @Override
    public List<UserProfile> findAll() {
        return mongoTemplate.findAll(UserProfile.class);
    }
}
