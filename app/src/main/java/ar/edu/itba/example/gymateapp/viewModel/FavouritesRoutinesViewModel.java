package ar.edu.itba.example.gymateapp.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ar.edu.itba.example.gymateapp.model.ApiResponse;
import ar.edu.itba.example.gymateapp.model.PagedList;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;
import ar.edu.itba.example.gymateapp.model.RoutinesApi;
import ar.edu.itba.example.gymateapp.model.UserApi;
import ar.edu.itba.example.gymateapp.model.UserInfo;
import ar.edu.itba.example.gymateapp.view.fragments.LoginFragment;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class FavouritesRoutinesViewModel extends AndroidViewModel {
    private MutableLiveData<List<RoutineCredentials>> favouriteRoutines = new MutableLiveData<>();
    private RoutinesApi routinesApi;
    private CompositeDisposable disposable = new CompositeDisposable();
    private UserApi userApi;
    private CompositeDisposable userDisposable = new CompositeDisposable();

    public FavouritesRoutinesViewModel(@androidx.annotation.NonNull @NotNull Application application) {
        super(application);
        routinesApi = new RoutinesApi(application);
        userApi = new UserApi(application);
    }

    public void favRoutine(int routineId) {
        disposable.add(routinesApi.favRoutine(routineId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>(){
                    @Override
                    public void onSuccess(@NotNull Response<Void> voidResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }

    public void unfavRoutine(int routineId) {
        disposable.add(routinesApi.unfavRoutine(routineId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidApiResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }

    public void updateData(){
        fetchFromRemote();
    }

    private void fetchFromRemote(){
        Map<String,String> options = new HashMap<>();
        options.put("page", String.valueOf(0));
        options.put("size", String.valueOf(100));
        options.put("orderBy", "averageRating");
        options.put("direction", "desc");

        disposable.add(routinesApi.getFavouriteRoutines(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PagedList<RoutineCredentials>>() {
                    @Override
                    public void onSuccess(@NonNull PagedList<RoutineCredentials> favourites) {
                        userDisposable.add(userApi.getCurrentUser()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableSingleObserver<UserInfo>() {
                                    @Override
                                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull UserInfo userInfo) {
                                        for(RoutineCredentials r : favourites.getEntries()){
                                            r.setUser(new RoutineCredentials.User(userInfo.getUsername()));
                                        }
                                        favouriteRoutines.setValue(favourites.getEntries());
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                        e.printStackTrace();
                                    }
                                }));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public MutableLiveData<List<RoutineCredentials>> getFavouriteRoutines() {
        return favouriteRoutines;
    }

}
