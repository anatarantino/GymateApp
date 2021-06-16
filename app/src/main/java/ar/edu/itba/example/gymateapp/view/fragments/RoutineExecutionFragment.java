package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

    private ArrayList<ExerciseCredentials> currentCycleList;

    private ArrayList<ArrayList<ExerciseCredentials>> cycles = new ArrayList<>();

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
    private String currentExName;
    private String currentCycleTitle;
    private boolean finished;
    private ExerciseCredentials currentExercise;
    private boolean executed;
    private int status;

    private View view;
    private MainActivity mainActivity;

    private TextView time;
    private TextView desc;
    private ProgressBar progressBar;
    private TextView exName;
    private TextView cycleTitle;
    private int routineId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentRoutinerunBinding.inflate(getLayoutInflater());

        exName = binding.exTitle;
        time = binding.exTime;
        desc = binding.exDesc;
        cycleTitle = binding.CycleTitle;

        view = binding.getRoot();

        titles[0] = WARMUP_TITLE;
        titles[1] = MAIN_TITLE;
        titles[2] = COOLDOWN_TITLE;

        binding.executionBar.play.setOnClickListener(v -> playExecution());
        binding.executionBar.pause.setOnClickListener(v -> pauseExecution());
        binding.executionBar.next.setOnClickListener(v -> nextExecution());
        binding.executionBar.previous.setOnClickListener(v -> previousExecution());
        binding.executionBar.previous.setVisibility(View.GONE);
        binding.executionBar.next.setVisibility(View.GONE);

        progressBar = binding.progressBar;


        mainActivity = (MainActivity) requireActivity();
        mainActivity.setNavigationVisibility(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null) {
            cycleTitle.setText(String.valueOf(RoutineExecutionFragmentArgs.fromBundle(getArguments()).getRoutineId()));
        }

        viewModel = new ViewModelProvider(requireActivity()).get(ExercisesViewModel.class);

        if(!viewModel.getIsFirstTime()){
            currentExerciseIndex = viewModel.getCurrentExercise();
            currentCycle = viewModel.getCurrentCycle();
            executed = viewModel.isExecuted();
            cycleTitle.setText(titles[currentCycle]);
            status = viewModel.getStatus();
        }else {
            currentCycle = 0;
            cycleTitle.setText(titles[currentCycle]);
            currentExerciseIndex = 0;
            viewModel.setStarted(true);
            binding.progressBar.setProgress(0);
        }
        currentCycleTitle = titles[currentCycle];
        observeExerciseViewModel();
    }

    private void observeExerciseViewModel() {
        viewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmUp = (ArrayList<ExerciseCredentials>) warmupExercises;
                cycles.add(WARMUP_CYCLE,warmUp);
            }
        });

        viewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                main = (ArrayList<ExerciseCredentials>) mainExercises;
                cycles.add(MAIN_CYCLE,main);
            }
        });

        viewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldown = (ArrayList<ExerciseCredentials>) cooldownExercises;
                cycles.add(COOLDOWN_CYCLE,cooldown);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void playExecution(){
        binding.executionBar.play.setVisibility(View.INVISIBLE);
        binding.executionBar.pause.setVisibility(View.VISIBLE);
        finished = false;
        status = PLAYING;
        if (executed) {
            viewModel.getCountDownTimer().resume();
        } else {
            binding.executionBar.previous.setVisibility(View.VISIBLE);
            binding.executionBar.next.setVisibility(View.VISIBLE);
            executed = true;
            currentExercise = getNextExercise();
            progressBar.setMax(currentExercise.getDuration());
            viewModel.getCountDownTimer().start(currentExercise.getDuration() * 1000, 1000);
            viewModel.getCountDownTimer().getStatus().observe(getViewLifecycleOwner(), countdown -> {
                if(!finished) {
                    if(countdown.isFinished()) {
                        currentExerciseIndex++;
                        if ((currentExercise = getNextExercise()) != null) {
                            viewModel.getCountDownTimer().start(currentExercise.getDuration() * 1000, 1000);
                            progressBar.setProgress(0);
                            progressBar.setMax(currentExercise.getDuration());
                        }
                    }else {
                        long remainingTime = countdown.getRemainingTime();
                        progressBar.setProgress(progressBar.getMax() - (int) remainingTime);
                        time.setText(String.valueOf(remainingTime));
                    }
                }else {
                    finishRoutine();
                }
            });
        }

    }

    private void pauseExecution(){
        binding.executionBar.play.setVisibility(View.VISIBLE);
        binding.executionBar.pause.setVisibility(View.INVISIBLE);
        viewModel.getCountDownTimer().pause();
        status = PAUSED;
    }

    private void nextExecution(){
        viewModel.getCountDownTimer().stop();
        currentExerciseIndex++;
        currentExercise = getNextExercise();
        if (currentExercise != null) {
            progressBar.setProgress(0);
            progressBar.setMax(currentExercise.getDuration());
            viewModel.getCountDownTimer().start(currentExercise.getDuration() * 1000, 1000);
            if (status == PAUSED) {
                viewModel.getCountDownTimer().pause();
            }
        }else {
            finishRoutine();
        }
    }

    private void previousExecution(){
        viewModel.getCountDownTimer().stop();
        currentExerciseIndex--;
        currentExercise = getPrevExercise();
        if (currentExercise != null){
            progressBar.setProgress(0);
            progressBar.setMax(currentExercise.getDuration());
            viewModel.getCountDownTimer().start(currentExercise.getDuration() * 1000,1000);
            if(status == PAUSED) {
                viewModel.getCountDownTimer().pause();
            }
        }
    }

    private ExerciseCredentials getPrevExercise(){
        ExerciseCredentials exercise;
        currentCycleList = getPrevCycle();
        if (currentCycleList == null){
            currentExerciseIndex = 0;
            currentCycleList = cycles.get(currentCycle);
        }
        exercise = currentCycleList.get(currentExerciseIndex);
        assert binding.exTitle != null;
        binding.exTitle.setText(exercise.getExercise().getName());
        assert binding.CycleTitle != null;
        binding.CycleTitle.setText(currentCycleTitle);
        assert binding.exTime != null;
        binding.exTime.setText(String.valueOf(exercise.getDuration()));
        assert binding.exDesc != null;
        binding.exDesc.setText(exercise.getExercise().getDetail());
        return exercise;
    }

    private ArrayList<ExerciseCredentials> getPrevCycle() {
        if(currentExerciseIndex == -1) {
            if(currentCycle == WARMUP_CYCLE){
                return null;
            }
            currentCycle--;
            currentCycleTitle = titles[currentCycle];
            currentExerciseIndex = cycles.get(currentCycle).size() - 1;
        }
        return cycles.get(currentCycle);
    }

    private ExerciseCredentials getNextExercise() {
        ExerciseCredentials exercise = null;
        currentCycleList = getCurrentCycle();
        if (currentCycleList != null) {
            exercise = currentCycleList.get(currentExerciseIndex);
            assert binding.exTitle != null;
            binding.exTitle.setText(exercise.getExercise().getName());
            assert binding.CycleTitle != null;
            binding.CycleTitle.setText(currentCycleTitle);
            assert binding.exTime != null;
            binding.exTime.setText(String.valueOf(exercise.getDuration()));
            assert binding.exDesc != null;
            binding.exDesc.setText(exercise.getExercise().getDetail());
        }
        return exercise;
    }

    private ArrayList<ExerciseCredentials> getCurrentCycle() {
        if(currentExerciseIndex >= cycles.get(currentCycle).size()){
            if(currentCycle == COOLDOWN_CYCLE) {
                finished = true;
                return null;
            }
            currentCycle++;
            currentCycleTitle = titles[currentCycle];
            currentExerciseIndex = 0;
        }
        return cycles.get(currentCycle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setCurrentCycle(currentCycle);
        viewModel.setCurrentExercise(currentExerciseIndex);
        viewModel.setStatus(status);
        viewModel.setExecuted(executed);
        if(executed){
            viewModel.getCountDownTimer().pause();
        }
    }

    public void finishRoutine() {
        FinishRoutine finishRoutine = new FinishRoutine(view, FinishRoutine.DETAIL_EXEC);
    }
}
