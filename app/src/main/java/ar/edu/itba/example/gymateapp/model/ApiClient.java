package ar.edu.itba.example.gymateapp.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://10.0.2.2:8080/api/";
    public static final int CONNECT_TIMEOUT = 60;
    public static final int READ_TIMEOUT = 60;
    public static final int WRITE_TIMEOUT = 60;
    private static String authToken = null;


    public ApiClient() {
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        ApiClient.authToken = authToken;
    }

//    public static <S> S create(Context context, Class<S> serviceClass) {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
//                .setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        //canal de comunicación
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(new AuthInterceptor(context))
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .build();
//
//        //formato de los datos que viajan en el canal de comunicación
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Date.class, new ApiDateTypeConverter())
//                .create();
//
//        //junto todo con retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .build();
//
//        return retrofit.create(serviceClass);
//    }
}
