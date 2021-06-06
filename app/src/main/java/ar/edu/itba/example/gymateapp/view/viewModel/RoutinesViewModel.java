package ar.edu.itba.example.gymateapp.view.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ar.edu.itba.example.gymateapp.view.classes.RoutineData;

public class RoutinesViewModel extends AndroidViewModel {
    private MutableLiveData<List<RoutineData>> routines = new MutableLiveData<>();

    public RoutinesViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void getRoutineById(int id){

    }
}
