package ar.edu.itba.example.gymateapp.model;

import androidx.lifecycle.LiveData;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {

    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body UserCredentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("users/current")
    LiveData<ApiResponse<User>> getCurrent();

}
