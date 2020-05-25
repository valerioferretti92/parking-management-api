package com.valerioferretti.parking.config;

import com.valerioferretti.parking.exceptions.BadConfigurationException;
import com.valerioferretti.parking.model.UserProfile;
import com.valerioferretti.parking.model.enums.RoleType;
import com.valerioferretti.parking.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BootstrapManager implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(BootstrapManager.class);

    private AdminAccountConfig adminAccountConfig;

    private UserProfileService userProfileService;

    @Autowired
    public BootstrapManager(AdminAccountConfig adminAccountConfig,
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
            admin.getRoles().add(RoleType.ADMIN);
            userProfileService.initializeAdmin(admin);
        }
    }
}
