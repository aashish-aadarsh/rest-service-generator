package com.devop.aashish.java.myapplication.configuration.audit;

import com.devop.aashish.java.myapplication.application.utility.SecurityUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
public class AppAuditConfiguration implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String userId = SecurityUtil.loggedInUser().getUserId();
        return Optional.of(userId);
    }
}
