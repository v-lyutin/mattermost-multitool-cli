package com.amit.multitool.client;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class AuthorizationInterceptor implements Interceptor {

    private final String accessToken;

    public AuthorizationInterceptor(final String accessToken) {
        this.accessToken = accessToken;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull final Chain chain) throws IOException {
        final Request original = chain.request();
        final Request.Builder builder = original.newBuilder();
        if (this.accessToken != null) {
            builder.header("Authorization", "Bearer " + this.accessToken);
        }
        return chain.proceed(builder.build());
    }

}
