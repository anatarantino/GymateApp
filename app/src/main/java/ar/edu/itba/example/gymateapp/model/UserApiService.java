package ar.edu.itba.example.gymateapp.model;

import androidx.lifecycle.LiveData;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApiService {

    @POST("users/login")
    Single<ApiResponse<AuthToken>> login(@Body UserCredentials credentials);

    @POST("users/logout")
    Single<ApiResponse<Void>> logout();

    @GET("users/current")
    Single<ApiResponse<UserCredentials>> getCurrentUser();

}
