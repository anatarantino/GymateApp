package ar.edu.itba.example.gymateapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.itba.example.gymateapp.model.ExerciseCredentials;
import ar.edu.itba.example.gymateapp.model.PagedList;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;
import ar.edu.itba.example.gymateapp.model.RoutineCycleCredentials;
import ar.edu.itba.example.gymateapp.model.RoutineRating;
import ar.edu.itba.example.gymateapp.model.RoutinesApi;
import ar.edu.itba.example.gymateapp.view.fragments.CountDownTimer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExercisesViewModel extends AndroidViewModel {
    private MutableLiveData<List<ExerciseCredentials>> warmupExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseCredentials>> mainExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseCredentials>> cooldownExercises = new MutableLiveData<>();

    private RoutinesApi routinesApi;
    private CompositeDisposable disposable = new CompositeDisposable();

    private boolean executed;
    private int currentCycle;
    private String cycleTitle;
    private int currentExercise;
    private boolean isFirstTime = true;
    private boolean finished;
    private int status;
    private ArrayList<ExerciseCredentials> currCycle;

    private CountDownTimer countDownTimer = new CountDownTimer();

    public ExercisesViewModel(@NonNull @NotNull Application application) {
        super(application);
        routinesApi = new RoutinesApi(application);
    }

    public void refresh(int routineId) {
        fetchFromRemote(routineId);
    }

    private void fetchFromRemote(int routineId) {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("size", "100");
        List<RoutineCycleCredentials> routineCycle = new ArrayList<>();

        disposable.add(routinesApi.getRoutineCycles(routineId,options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PagedList<RoutineCycleCredentials>>(){
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineCycleCredentials> cycles) {
                        routineCycle.addAll(cycles.getEntries());
                        for (RoutineCycleCredentials cycle : routineCycle) {
                            disposable.add(routinesApi.getExercises(cycle.getId(), options)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableSingleObserver<PagedList<ExerciseCredentials>>(){
                                        @Override
                                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<ExerciseCredentials> cycleExercises) {
                                            switch(cycle.getType()){
                                                case "warmup":
                                                    warmupExercises.setValue(cycleExercises.getEntries());
                                                    break;
                                                case "exercise":
                                                    mainExercises.setValue(cycleExercises.getEntries());
                                                    break;
                                                case "cooldown":
                                                    cooldownExercises.setValue(cycleExercises.getEntries());
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                            e.printStackTrace();
                                        }
                                    }));
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public MutableLiveData<List<ExerciseCredentials>> getWarmupExercises() {
        return warmupExercises;
    }

    public MutableLiveData<List<ExerciseCredentials>> getMainExercises() {
        return mainExercises;
    }

    public MutableLiveData<List<ExerciseCredentials>> getCooldownExercises() {return cooldownExercises;}

    public void setFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }

    public boolean getIsFirstTime(){
        return isFirstTime;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public int getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentCycle(int currentCycle) {
        this.currentCycle = currentCycle;
    }

    public void setCurrentExercise(int currentExercise) {
        this.currentExercise = currentExercise;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }

    public void rateRoutine(int routineId, int value) {
        RoutineRating rating = new RoutineRating(value, "");
        disposable.add(routinesApi.rateRoutine(routineId, rating)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RoutineCredentials>() {

                    @Override
                    public void onSuccess(@NonNull RoutineCredentials routineCredentials) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

}
