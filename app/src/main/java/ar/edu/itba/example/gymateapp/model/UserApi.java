package ar.edu.itba.example.gymateapp.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

public class UserApi implements UserApiService {

    private UserApi userApi;

    public UserApi(Context context){
        this.userApi= ApiClient.create(context,UserApi.class);
    }

    @Override
    public LiveData<ApiResponse<AuthToken>> login(UserCredentials credentials) {
        return userApi.login(credentials);
    }

    @Override
    public LiveData<ApiResponse<Void>> logout() {
        return userApi.logout();
    }

    @Override
    public LiveData<ApiResponse<UserCredentials>> getCurrentUser() {
        return userApi.getCurrentUser();
    }
}
