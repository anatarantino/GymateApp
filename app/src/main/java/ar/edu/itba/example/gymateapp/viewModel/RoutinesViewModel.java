package ar.edu.itba.example.gymateapp.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.model.PagedList;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;
import ar.edu.itba.example.gymateapp.model.RoutinesApi;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoutinesViewModel extends AndroidViewModel {
    private MutableLiveData<List<RoutineCredentials>> routineCards = new MutableLiveData<>();
    private MutableLiveData<List<RoutineCredentials>> userRoutines = new MutableLiveData<>();
    private MutableLiveData<RoutineCredentials> currentRoutine = new MutableLiveData<>();
    private MutableLiveData<Boolean> noMoreEntries = new MutableLiveData<>();
    private RoutinesApi routinesApi;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> routinesFirstLoad = new MutableLiveData<>(true);


    private boolean isLastPage = false;
    private String direction = "desc";
    private int directionId = 0;
    private String orderBy = "date";
    private int orderById = 0;
    private int currentPage = 0;
    private int totalPages = 0;
    private int itemsPerRequest = 15;

    public RoutinesViewModel(@NonNull @NotNull Application application) {
        super(application);
        routinesApi = new RoutinesApi(application);
    }

    public void resetData() {
        routineCards.setValue(new ArrayList<>());
        updateData();
    }

    public void updateData() {
        if (!isLastPage) {
            fetchFromRemote();
        }
    }

    public void getRoutineById(int id) {
        disposable.add(routinesApi.getRoutineById(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RoutineCredentials>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RoutineCredentials routineCredentials) {
                        int id = routineCredentials.getCategory().getId();
                        switch (id) {
                            case 1:
                                routineCredentials.setImage(String.valueOf(R.drawable.c1));
                                break;
                            case 2:
                                routineCredentials.setImage(String.valueOf(R.drawable.c2));
                                break;
                            case 3:
                                routineCredentials.setImage(String.valueOf(R.drawable.c3));
                                break;
                            case 4:
                                routineCredentials.setImage(String.valueOf(R.drawable.c4));
                                break;
                        }
                        currentRoutine.setValue(routineCredentials);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    public void orderRoutines(int option) {
        directionId = option;
        switch (option) {
            case 0:
                direction = "desc";
                break;
            case 1:
                direction = "asc";
                break;
        }
        applyChanges();
    }

    public void sortRoutines(int option) {
        orderById = option;
        switch (option) {
            case 0:
                orderBy = "dateCreated";
                break;
            case 1:
                orderBy = "averageRating";
                break;
            case 2:
                orderBy = "categoryId";
                break;
            case 3:
                orderBy = "difficulty";
                break;
            case 4:
                orderBy = "name";
                break;
        }
        applyChanges();
    }

    private void applyChanges() {
        routineCards.setValue(new ArrayList<>());
        currentPage = 0;
        isLastPage = false;
        totalPages = 0;
        fetchFromRemote();
    }

    private void fetchFromRemote() {
        Map<String, String> options = new HashMap<>();
        options.put("page", String.valueOf(currentPage));
        options.put("orderBy", orderBy);
        options.put("direction", direction);
        options.put("size", String.valueOf(itemsPerRequest));

        disposable.add(routinesApi.getRoutines(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PagedList<RoutineCredentials>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineCredentials> routinesEntries) {
                        isLastPage = routinesEntries.getLastPage();
                        noMoreEntries.setValue(isLastPage);
                        currentPage++;
                        Log.e("RoutinesViewModel", "on success: " + routinesEntries.toString());
                        List<RoutineCredentials> aux = new ArrayList<>();
                        if (routineCards.getValue() != null) {
                            aux = routineCards.getValue();
                        }
                        //comento esta linea porque me crashea la app pues ES NULL SIEMPRE NO SE POR QUEE
                        aux.addAll(routinesEntries.getEntries());
                        routineCards.setValue(aux);
                        totalPages = (int) Math.ceil(routinesEntries.getTotalCount() / (double) itemsPerRequest);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e("on error", "mal ahi hubo error");
                        e.printStackTrace();
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public MutableLiveData<List<RoutineCredentials>> getRoutineCards() {
        return routineCards;
    }

    public MutableLiveData<Boolean> getNoMoreEntries() {
        return noMoreEntries;
    }

    public MutableLiveData<RoutineCredentials> getCurrentRoutine() {
        return currentRoutine;
    }

    public String getDirection() {
        return direction;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setOrderById(int option) {
        orderById = option;
        switch (option) {
            case 0:
                orderBy = "dateCreated";
                break;

            case 1:
                orderBy = "averageRating";
                break;

            case 2:
                orderBy = "categoryId";
                break;

            case 3:
                orderBy = "difficulty";
                break;

            case 4:
                orderBy = "name";
                break;
        }
    }

    public int getOrderById() {
        return orderById;
    }

    public int getDirectionId() {
        return directionId;
    }

    public MutableLiveData<Boolean> getRoutinesFirstLoad() {
        return routinesFirstLoad;
    }

    public void setRoutinesFirstLoad(Boolean firstLoad) {
        routinesFirstLoad.setValue(firstLoad);
    }

    public void updateUserRoutines() {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("orderBy", orderBy);
        options.put("direction", direction);
        options.put("size", String.valueOf(1000));
        disposable.add(
                routinesApi.getUserRoutines(options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineCredentials>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineCredentials> routinesEntries) {
                                userRoutines.setValue(routinesEntries.getEntries());
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );

    }

    public MutableLiveData<List<RoutineCredentials>> getUserRoutines() {
        return userRoutines;
    }


}
