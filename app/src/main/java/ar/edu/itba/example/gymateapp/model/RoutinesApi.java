package ar.edu.itba.example.gymateapp.model;

import android.content.Context;


import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.edu.itba.example.gymateapp.model.ApiClient.BASE_URL;

public class RoutinesApi extends ApiClient implements RoutinesApiService {

    private RoutinesApiService routinesApi;

    public RoutinesApi(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthInterceptor(context))
                .build();

        routinesApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(RoutinesApiService.class);
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
        return routinesApi.getRoutineCycles(routineId, options);
    }

    @Override
    public Single<PagedList<ExerciseCredentials>> getExercises(Integer cycleId, Map<String, String> options) {
        return routinesApi.getExercises(cycleId, options);
    }

    @Override
    public Single<PagedList<ExerciseCredentials>> getExercisesById(Integer cycleId, Integer exerciseId, Map<String, String> options) {
        return routinesApi.getExercisesById(cycleId, exerciseId, options);
    }

    @Override
    public Single<PagedList<RoutineCredentials>> getFavouriteRoutines(Map<String, String> options) {
        return routinesApi.getFavouriteRoutines(options);
    }

    @Override
    public Single<retrofit2.Response<Void>> favRoutine(Integer routineId) {
        return routinesApi.favRoutine(routineId);
    }

    @Override
    public Single<RoutineCredentials> addRoutineExecution(Integer routineId, RoutineCredentials routineExecution) {
        return routinesApi.addRoutineExecution(routineId, routineExecution);
    }

    @Override
    public Single<Response<Void>> unfavRoutine(Integer routineId) {
        return routinesApi.unfavRoutine(routineId);
    }
}
