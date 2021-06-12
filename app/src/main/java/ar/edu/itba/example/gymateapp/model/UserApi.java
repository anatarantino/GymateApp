package ar.edu.itba.example.gymateapp.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import io.reactivex.rxjava3.core.Single;

public class UserApi implements UserApiService {

    private UserApi userApi;

    public UserApi(Context context){
        this.userApi= ApiClient.create(context,UserApi.class);
    }

    @Override
    public Single<AuthToken> login(UserCredentials credentials) {
        return userApi.login(credentials);
    }

    @Override
    public Single<Void> logout() {
        return userApi.logout();
    }

    @Override
    public Single<UserCredentials> getCurrentUser() {
        return userApi.getCurrentUser();
    }
}
