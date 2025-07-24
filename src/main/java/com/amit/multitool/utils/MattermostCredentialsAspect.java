package com.amit.multitool.utils;

import com.amit.multitool.client.MattermostClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public final class MattermostCredentialsAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MattermostCredentialsAspect.class);

    private final MattermostClient mattermostClient;

    @Autowired
    public MattermostCredentialsAspect(final MattermostClient mattermostClient) {
        this.mattermostClient = mattermostClient;
    }

    @Around(value = "@annotation(com.amit.multitool.utils.RequireMattermostCredentials)")
    public Object checkMattermostCredentials(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (this.mattermostClient == null || !this.mattermostClient.isMattermostCredentialsPresent()) {
            LOGGER.warn("The environment for Mattermost is not set up");
            return null;
        }
        return proceedingJoinPoint.proceed();
    }

}
