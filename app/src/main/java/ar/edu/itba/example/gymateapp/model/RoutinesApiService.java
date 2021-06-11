package ar.edu.itba.example.gymateapp.model;

import androidx.lifecycle.LiveData;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RoutinesApiService {

    @GET("routines")
    LiveData<PagedList<RoutineCredentials>> getRoutines(@QueryMap Map<String, String> options);

    @GET("users/current/routines/")
    LiveData<PagedList<RoutineCredentials>> getUserRoutines(@QueryMap Map<String, String> options);

    @GET("users/current/executions")
    LiveData<PagedList<RoutineCredentials>> getUserHistory(@QueryMap Map<String, String> options);

    @GET("routines/{routineId}")
    LiveData<RoutineCredentials> getRoutineById(@Path("routineId") Integer routineId);

    @GET("routines/{routineId}/cycles")
    LiveData<PagedList<RoutineCycleCredentials>> getRoutineCycles(@Path("routineId") Integer routineId, @QueryMap Map<String, String> options);

    @GET("cycles/{cycleId}/exercises")
    LiveData<PagedList<ExerciseCredentials>> getExercises(@Path("cycleId") Integer cycleId, @QueryMap Map<String, String> options);

    @GET("cycles/{cycleId}/exercises/{exerciseId}")
    LiveData<PagedList<ExerciseCredentials>> getExercisesById(@Path("cycleId") Integer cycleId, @Path("exerciseId") Integer exerciseId,@QueryMap Map<String, String> options);

    @GET("favourites")
    LiveData<PagedList<RoutineCredentials>> getFavouriteRoutines(@QueryMap Map<String, String> options);

    @POST("favourites/{routineId}")
    LiveData<ApiResponse<Void>> favRoutine(@Path("routineId") Integer routineId);

    @POST("executions/{routineId}")
    LiveData<RoutineCredentials> addRoutineExecution(@Path("routineId") Integer routineId, @Body RoutineCredentials routineExecution);

    @DELETE("favourites/{routineId}")
    LiveData<ApiResponse<Void>> unfavRoutine(@Path("routineId") Integer routineId);
}
