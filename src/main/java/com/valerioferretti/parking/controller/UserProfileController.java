package com.valerioferretti.parking.controller;

import com.valerioferretti.parking.exceptions.AccountAlreadyExistsException;
import com.valerioferretti.parking.model.UserProfile;
import com.valerioferretti.parking.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class UserProfileController {

    private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);

    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @RequestMapping(value = "/admin/signup" ,method = RequestMethod.POST)
    public ResponseEntity<?> insertAdmin(@RequestBody @Valid UserProfile admin) throws AccountAlreadyExistsException {

        log.info("Creating admin account {}...", admin.getEmail());
        admin = userProfileService.insertAdmin(admin);
        log.info("Account {} created!", admin.getEmail());

        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/signup" ,method = RequestMethod.POST)
    public ResponseEntity<?> insertUser(@RequestBody @Valid UserProfile user) throws AccountAlreadyExistsException {

        log.info("Creating user account {}...", user.getEmail());
        user = userProfileService.insertUser(user);
        log.info("Account {} created!", user.getEmail());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        List<UserProfile> accounts;

        log.info("Retrieving all accounts...");
        accounts = userProfileService.getAll();
        log.info("List of accounts ready!");

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> delete(@PathVariable String email) throws UsernameNotFoundException {

        log.info("Delete account {}}...", email);
        userProfileService.delete(email);
        log.info("Account deleted!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
