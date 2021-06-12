package ar.edu.itba.example.gymateapp.model;

import android.content.Context;


import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class RoutinesApi implements RoutinesApiService{

    private RoutinesApi routinesApi;

    public RoutinesApi(Context context){
        this.routinesApi=ApiClient.create(context,RoutinesApi.class);
    }

    @Override
    public Single<PagedList<RoutineCredentials>> getRoutines(Map<String, String> options) {
        return routinesApi.getRoutines(options);
    }

    @Override
    public Single<PagedList<RoutineCredentials>> getUserRoutines(Map<String, String> options) {
        return routinesApi.getUserRoutines(options);
    }

    @Override
    public Single<PagedList<RoutineCredentials>> getUserHistory(Map<String, String> options) {
        return routinesApi.getUserHistory(options);
    }

    @Override
    public Single<RoutineCredentials> getRoutineById(Integer routineId) {
        return routinesApi.getRoutineById(routineId);
    }

    @Override
    public Single<PagedList<RoutineCycleCredentials>> getRoutineCycles(Integer routineId, Map<String, String> options) {
        return routinesApi.getRoutineCycles(routineId,options);
    }

    @Override
    public Single<PagedList<ExerciseCredentials>> getExercises(Integer cycleId, Map<String, String> options) {
        return routinesApi.getExercises(cycleId,options);
    }

    @Override
    public Single<PagedList<ExerciseCredentials>> getExercisesById(Integer cycleId, Integer exerciseId, Map<String, String> options) {
        return routinesApi.getExercisesById(cycleId,exerciseId,options);
    }

    @Override
    public Single<PagedList<RoutineCredentials>> getFavouriteRoutines(Map<String, String> options) {
        return routinesApi.getFavouriteRoutines(options);
    }

    @Override
    public Single<ApiResponse<Void>> favRoutine(Integer routineId) {
        return routinesApi.favRoutine(routineId);
    }

    @Override
    public Single<RoutineCredentials> addRoutineExecution(Integer routineId, RoutineCredentials routineExecution) {
        return routinesApi.addRoutineExecution(routineId,routineExecution);
    }

    @Override
    public Single<ApiResponse<Void>> unfavRoutine(Integer routineId) {
        return routinesApi.unfavRoutine(routineId);
    }
}
