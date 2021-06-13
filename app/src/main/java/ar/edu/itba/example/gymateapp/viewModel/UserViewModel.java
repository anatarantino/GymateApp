package ar.edu.itba.example.gymateapp.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import ar.edu.itba.example.gymateapp.model.ApiClient;
import ar.edu.itba.example.gymateapp.model.ApiError;
import ar.edu.itba.example.gymateapp.model.ApiResponse;
import ar.edu.itba.example.gymateapp.model.AppPreferences;
import ar.edu.itba.example.gymateapp.model.AuthToken;
import ar.edu.itba.example.gymateapp.model.UserApi;
import ar.edu.itba.example.gymateapp.model.UserCredentials;
import ar.edu.itba.example.gymateapp.model.UserInfo;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
    private MutableLiveData<AuthToken> token = new MutableLiveData<>();

    private MutableLiveData<ApiError> loginError = new MutableLiveData<>();

    private UserApi userApi;

    private Application app;
    private CompositeDisposable disposable = new CompositeDisposable();

    public UserViewModel(Application application) {
        super(application);
        userApi = new UserApi(application);
        app = application;
    }

    public void setUserData() {
        disposable.add(userApi.getCurrentUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserInfo>() {
                    @Override
                    public void onSuccess(@NonNull UserInfo info) {
                        userInfo.setValue(info);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    public void tryLogin(String username, String password){
        UserCredentials credentials = new UserCredentials(username,password);
        disposable.add(userApi.login(credentials)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AuthToken>(){
                    @Override
                    public void onSuccess(@NonNull AuthToken authToken) {
                        token.setValue(authToken);
                        ApiClient.setAuthToken(authToken.getToken());
                        AppPreferences preferences = new AppPreferences(app);
                        preferences.setAuthToken(authToken.getToken());
                        loginError.setValue(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if(e instanceof HttpException){
                            HttpException httpException = (HttpException) e;
                            try {
                                Gson gson = new Gson();
                                ApiError error;
                                error = gson.fromJson(httpException.response().errorBody().string(), new TypeToken<ApiError>() {
                                }.getType());
                                loginError.setValue(error);
                            }catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        e.printStackTrace();
                    }
                })
        );
    }


    public MutableLiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public MutableLiveData<UserInfo> getUserData() {
        return userInfo;
    }

    public MutableLiveData<AuthToken> getToken() {
        return token;
    }

    public MutableLiveData<ApiError> getLoginError() {
        return loginError;
    }

    public void setLoginErrorCode(ApiError error) {
        loginError.setValue(error);
    }
}
