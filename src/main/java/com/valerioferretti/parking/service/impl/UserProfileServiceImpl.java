package com.valerioferretti.parking.service.impl;

import com.valerioferretti.parking.exceptions.AccountAlreadyExistsException;
import com.valerioferretti.parking.model.UserProfile;
import com.valerioferretti.parking.model.enums.RoleTypes;
import com.valerioferretti.parking.repository.UserProfileDao;
import com.valerioferretti.parking.service.UserProfileService;
import com.valerioferretti.parking.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserProfileServiceImpl implements UserProfileService, UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private UserProfileDao userProfileDao;

    @Autowired
    public UserProfileServiceImpl(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserProfile userProfile;
        Set authorities;

        userProfile = userProfileDao.findById(email);
        authorities = Utils.getAuthoritiesFromRoles(userProfile.getRoles());
        return new User(userProfile.getEmail(), userProfile.getPassword(), authorities);
    }

    @Override
    public UserProfile initializeAdmin(UserProfile admin) {
        UserProfile adminDb;

        adminDb = userProfileDao.findById(admin.getEmail());
        if (adminDb != null) {
            return adminDb;
        }
        return userProfileDao.insert(admin);
    }

    @Override
    public UserProfile insertAdmin(UserProfile admin) throws AccountAlreadyExistsException {
        UserProfile adminDb;
        String encodedPassword;

        adminDb = userProfileDao.findById(admin.getEmail());
        if (adminDb != null) {
            throw new AccountAlreadyExistsException(admin.getEmail());
        }

        //Password managament
        encodedPassword = new BCryptPasswordEncoder().encode(admin.getPassword());
        log.info("Password encoding: {} -> {}", admin.getPassword(), encodedPassword);
        admin.setPassword(encodedPassword);

        //Roles management
        admin.getRoles().remove(RoleTypes.USER);
        admin.getRoles().add(RoleTypes.ADMIN);

        return userProfileDao.insert(admin);
    }

    @Override
    public UserProfile insertUser(UserProfile user) throws AccountAlreadyExistsException {
        UserProfile userDb;
        String encodedPassword;

        userDb = userProfileDao.findById(user.getEmail());
        if (userDb != null) {
            throw new AccountAlreadyExistsException(user.getEmail());
        }

        //Password managament
        encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        log.info("Password encoding: {} -> {}", user.getPassword(), encodedPassword);
        user.setPassword(encodedPassword);

        //Roles management
        user.getRoles().remove(RoleTypes.ADMIN);
        user.getRoles().add(RoleTypes.USER);

        return userProfileDao.insert(user);
    }

    @Override
    public UserProfile update(String email, UserProfile update) throws UsernameNotFoundException {
        UserProfile userProfile;
        String encodedPassword;

        userProfile = userProfileDao.findById(email);
        if(userProfile == null) {
            throw new UsernameNotFoundException(email);
        }

        if(update.getPassword() != null) {
            encodedPassword = new BCryptPasswordEncoder().encode(update.getPassword());
            log.info("Password encoding: {} -> {}", update.getPassword(), encodedPassword);
            userProfile.setPassword(encodedPassword);
        }
        return userProfileDao.update(userProfile);
    }

    @Override
    public void delete(String email) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileDao.findById(email);
        if(userProfile == null) {
            throw new UsernameNotFoundException(email);
        }

        userProfileDao.delete(email);
    }

    @Override
    public UserProfile get(String email) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileDao.findById(email);
        if(userProfile == null) {
            throw new UsernameNotFoundException(email);
        }

        return userProfile;
    }

    @Override
    public List<UserProfile> getAll() {
        return userProfileDao.findAll();
    }
}
