package ar.edu.itba.example.gymateapp.model;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import ar.edu.itba.example.gymateapp.model.LiveDataCallAdapter;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) { //call adapter a live data
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0,(ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if(rawObservableType!=ApiResponse.class){
            throw new IllegalArgumentException("type must be an API response");
        }
        if(!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("API response must be parametrized");
        }
        Type bodyType=getParameterUpperBound(0,(ParameterizedType) observableType);
        return new LiveDataCallAdapter(bodyType);
    }


}
