package ar.edu.itba.example.gymateapp.model;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;

import ar.edu.itba.example.gymateapp.R;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter implements CallAdapter<R, LiveData<ApiResponse<R>>> {

    private final Type responseType;

    public LiveDataCallAdapter(Type responseType){
        this.responseType=responseType;
    }
    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            @Override
            protected void onActive() { //solo se va a ejecutar si hay alguien observando el LiveData, si nadie está consumiendo datos no hay que hacer una solicitud a retrofit
                super.onActive();
                call.enqueue(new Callback<R>() {//call es la solicitud de retrofit, entonces aca encolo la solicitud a retrofit
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        postValue(new ApiResponse(response));
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable throwable) {
                        postValue(new ApiResponse(throwable));
                    }
                });
            }
        };
    }
}
