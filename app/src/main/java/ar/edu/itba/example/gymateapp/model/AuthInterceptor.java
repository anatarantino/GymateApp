package ar.edu.itba.example.gymateapp.model;

import android.app.Application;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final AppPreferences preferences;

    public AuthInterceptor(Context context){
        this.preferences = new AppPreferences(context);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (preferences.getAuthToken() != null) {
            request.addHeader("Authorization", "Bearer" + preferences.getAuthToken());
        }
        return chain.proceed(request.build());
    }
}
