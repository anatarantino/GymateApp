package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.databinding.FragmentRoutinerunBinding;
import ar.edu.itba.example.gymateapp.model.ExerciseCredentials;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.view.adapter.ExercisesAdapter;
import ar.edu.itba.example.gymateapp.view.classes.ExerciseData;
import ar.edu.itba.example.gymateapp.viewModel.ExercisesViewModel;

public class RoutineExecutionFragment extends Fragment {

    private FragmentRoutinerunBinding binding;

    private ExercisesViewModel viewModel;

    private ArrayList<ExerciseCredentials> warmUp;
    private ArrayList<ExerciseCredentials> main;
    private ArrayList<ExerciseCredentials> cooldown;

    private static final int WARMUP_CYCLE = 0;
    private static final int MAIN_CYCLE = 1;
    private static final int COOLDOWN_CYCLE = 2;
    private static final int PLAYING = 0;
    private static final int PAUSED = 1;
    private static final int NOTRUNNING = 2;

    private static final String[] titles = new String[3];
    private static final String WARMUP_TITLE = "WARM UP";
    private static final String MAIN_TITLE = "MAIN EXERCISES";
    private static final String COOLDOWN_TITLE = "COOLDOWN";

    private int currentCycle;
    private int currentExerciseIndex;
    private boolean finished;
    private ExerciseData currentExercise;

    private View view;
    private MainActivity mainActivity;

    private TextView time;
    private ProgressBar progressBar;
    private TextView exName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentRoutinerunBinding.inflate(getLayoutInflater());
        time = binding.exTime;
        view = binding.getRoot();

        titles[0] = WARMUP_TITLE;
        titles[1]=MAIN_TITLE;
        titles[2]=COOLDOWN_TITLE;

        progressBar = binding.progressBar;
        mainActivity = (MainActivity) getActivity();
        mainActivity.setNavigationVisibility(false);

        return view;
    }
}
