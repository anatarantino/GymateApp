package ar.edu.itba.example.gymateapp.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.Map;

public class RoutinesApi implements RoutinesApiService{

    private RoutinesApi routinesApi;

    public RoutinesApi(Context context){
        this.routinesApi=ApiClient.create(context,RoutinesApi.class);
    }

    @Override
    public LiveData<PagedList<RoutineCredentials>> getRoutines(Map<String, String> options) {
        return routinesApi.getRoutines(options);
    }

    @Override
    public LiveData<PagedList<RoutineCredentials>> getUserRoutines(Map<String, String> options) {
        return routinesApi.getUserRoutines(options);
    }

    @Override
    public LiveData<PagedList<RoutineCredentials>> getUserHistory(Map<String, String> options) {
        return routinesApi.getUserHistory(options);
    }

    @Override
    public LiveData<RoutineCredentials> getRoutineById(Integer routineId) {
        return routinesApi.getRoutineById(routineId);
    }

    @Override
    public LiveData<PagedList<RoutineCycleCredentials>> getRoutineCycles(Integer routineId, Map<String, String> options) {
        return routinesApi.getRoutineCycles(routineId,options);
    }

    @Override
    public LiveData<PagedList<ExerciseCredentials>> getExercises(Integer cycleId, Map<String, String> options) {
        return routinesApi.getExercises(cycleId,options);
    }

    @Override
    public LiveData<PagedList<ExerciseCredentials>> getExercisesById(Integer cycleId, Integer exerciseId, Map<String, String> options) {
        return routinesApi.getExercisesById(cycleId,exerciseId,options);
    }

    @Override
    public LiveData<PagedList<RoutineCredentials>> getFavouriteRoutines(Map<String, String> options) {
        return routinesApi.getFavouriteRoutines(options);
    }

    @Override
    public LiveData<ApiResponse<Void>> favRoutine(Integer routineId) {
        return routinesApi.favRoutine(routineId);
    }

    @Override
    public LiveData<RoutineCredentials> addRoutineExecution(Integer routineId, RoutineCredentials routineExecution) {
        return routinesApi.addRoutineExecution(routineId,routineExecution);
    }

    @Override
    public LiveData<ApiResponse<Void>> unfavRoutine(Integer routineId) {
        return routinesApi.unfavRoutine(routineId);
    }
}
