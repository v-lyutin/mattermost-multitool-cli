package com.amit.multitool.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
