package ar.edu.itba.example.gymateapp.model;

import androidx.lifecycle.LiveData;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RoutinesApiService {

    @GET("routines")
    Single<PagedList<RoutineCredentials>> getRoutines(@QueryMap Map<String, String> options);

    @GET("users/current/routines/")
    Single<PagedList<RoutineCredentials>> getUserRoutines(@QueryMap Map<String, String> options);

    @GET("users/current/executions")
    Single<PagedList<RoutineCredentials>> getUserHistory(@QueryMap Map<String, String> options);

    @GET("routines/{routineId}")
    Single<RoutineCredentials> getRoutineById(@Path("routineId") Integer routineId);

    @GET("routines/{routineId}/cycles")
    Single<PagedList<RoutineCycleCredentials>> getRoutineCycles(@Path("routineId") Integer routineId, @QueryMap Map<String, String> options);

    @GET("cycles/{cycleId}/exercises")
    Single<PagedList<ExerciseCredentials>> getExercises(@Path("cycleId") Integer cycleId, @QueryMap Map<String, String> options);

    @GET("cycles/{cycleId}/exercises/{exerciseId}")
    Single<PagedList<ExerciseCredentials>> getExercisesById(@Path("cycleId") Integer cycleId, @Path("exerciseId") Integer exerciseId,@QueryMap Map<String, String> options);

    @GET("favourites")
    Single<PagedList<RoutineCredentials>> getFavouriteRoutines(@QueryMap Map<String, String> options);

    @POST("favourites/{routineId}")
    Single<Response<Void>> favRoutine(@Path("routineId") Integer routineId);

    @POST("executions/{routineId}")
    Single<RoutineCredentials> addRoutineExecution(@Path("routineId") Integer routineId, @Body RoutineCredentials routineExecution);

    @DELETE("favourites/{routineId}")
    Single<Response<Void>> unfavRoutine(@Path("routineId") Integer routineId);

    @POST("reviews/{routineId}")
    Single<RoutineCredentials> rateRoutine(Integer routineId, RoutineRating rating);
}
