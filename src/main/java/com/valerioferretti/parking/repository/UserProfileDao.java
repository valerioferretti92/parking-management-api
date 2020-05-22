package com.valerioferretti.parking.repository;

import com.valerioferretti.parking.model.UserProfile;

import java.util.List;

public interface UserProfileDao {
    UserProfile insert(UserProfile userProfile);
    UserProfile update(UserProfile userProfile);
    void delete(String email);
    UserProfile findById(String email);
    List<UserProfile> findAll();
}
