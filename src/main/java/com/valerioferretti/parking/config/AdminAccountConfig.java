package com.valerioferretti.parking.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AdminAccountConfig {
    @Value("${accounts.admins.emails}")
    private String[] emails;

    @Value("${accounts.admins.passwords}")
    private String[] passwords;
}
