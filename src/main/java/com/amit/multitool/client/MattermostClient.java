package com.amit.multitool.client;


import com.amit.multitool.config.MattermostCredentials;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class MattermostClient {

    private final okhttp3.OkHttpClient okHttpClient;

    private MattermostCredentials mattermostCredentials;

    private MattermostApiV4 mattermostApiV4;

    @Autowired
    public MattermostClient(final okhttp3.OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        this.mattermostCredentials = null;
    }

    public void setMattermostCredentials(final MattermostCredentials mattermostCredentials) {
        this.mattermostCredentials = mattermostCredentials;
        this.refreshFeignClient();
    }

    public MattermostApiV4 getApi() {
        return this.mattermostApiV4;
    }

    private void refreshFeignClient() {
        final okhttp3.OkHttpClient client = this.okHttpClient.newBuilder()
                .addInterceptor(new AuthorizationInterceptor(this.mattermostCredentials.accessToken()))
                .addInterceptor(new HttpUnsuccessfullResponseInterceptor())
                .build();
        this.mattermostApiV4 = Feign.builder()
                .client(new OkHttpClient(client))
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(MattermostApiV4.class))
                .logLevel(Logger.Level.FULL)
                .target(MattermostApiV4.class, this.mattermostCredentials.baseUrl());
    }

}
