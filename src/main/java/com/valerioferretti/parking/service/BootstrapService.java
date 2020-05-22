package com.valerioferretti.parking.service;

import com.valerioferretti.parking.config.AdminAccountConfig;
import com.valerioferretti.parking.exceptions.BadConfigurationException;
import com.valerioferretti.parking.model.UserProfile;
import com.valerioferretti.parking.model.enums.RoleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BootstrapService implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(BootstrapService.class);

    private AdminAccountConfig adminAccountConfig;

    private UserProfileService userProfileService;

    @Autowired
    public BootstrapService(AdminAccountConfig adminAccountConfig,
                            UserProfileService userProfileService) {
        this.adminAccountConfig = adminAccountConfig;
        this.userProfileService = userProfileService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setupAdminUser();
    }

    private void setupAdminUser() throws BadConfigurationException {
        UserProfile admin;

        log.info("Bootstrapping admin users...");

        String[] emails = adminAccountConfig.getEmails();
        String[] passwords = adminAccountConfig.getPasswords();
        if(emails.length != passwords.length) {
            throw new BadConfigurationException("MISMATCHING_ENTERPRISE_LISTS_LENGTH");
        }

        for(int i = 0; i < emails.length; i++){
            admin = new UserProfile();
            admin.setEmail(emails[i]);
            admin.setPassword(passwords[i]);
            admin.getRoles().add(RoleTypes.ADMIN);
            userProfileService.initializeAdmin(admin);
        }
    }
}
