package ar.edu.itba.example.gymateapp.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.edu.itba.example.gymateapp.model.ApiClient.BASE_URL;

public class UserApi implements UserApiService {

    private UserApiService userApi;

    public UserApi(Context context){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthInterceptor(context))
                .build();

        userApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(UserApiService.class);
    }

    @Override
    public Single<AuthToken> login(UserCredentials credentials) {
        return userApi.login(credentials);
    }

    @Override
    public Single<Response<Void>> logout() {
        return userApi.logout();
    }

    @Override
    public Single<UserCredentials> getCurrentUser() {
        return userApi.getCurrentUser();
    }
}
