package com.valerioferretti.parking.service;

import com.valerioferretti.parking.exceptions.AccountAlreadyExistsException;
import com.valerioferretti.parking.model.UserProfile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserProfileService {
    UserProfile initializeAdmin(UserProfile admin);
    UserProfile insertAdmin(UserProfile admin) throws AccountAlreadyExistsException;
    UserProfile insertUser(UserProfile user) throws AccountAlreadyExistsException;
    UserProfile update(String email, UserProfile update) throws UsernameNotFoundException;
    void delete(String email) throws UsernameNotFoundException;
    UserProfile get(String email) throws UsernameNotFoundException;
    List<UserProfile> getAll();
}
