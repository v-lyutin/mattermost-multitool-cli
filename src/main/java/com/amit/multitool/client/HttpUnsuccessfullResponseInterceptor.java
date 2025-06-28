package com.amit.multitool.client;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class HttpUnsuccessfullResponseInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUnsuccessfullResponseInterceptor.class);

    @NotNull
    @Override
    public Response intercept(@NotNull final Chain chain) throws IOException {
        final Request request = chain.request();
        final Response response = chain.proceed(request);
        if (!response.isSuccessful()) {
            try (ResponseBody body = response.peekBody(1024)) {
                LOGGER.error("Request {} {} failed with code {}: {}",
                        request.method(),
                        request.url(),
                        response.code(),
                        body.string());
            }
        }
        return response;
    }

}
