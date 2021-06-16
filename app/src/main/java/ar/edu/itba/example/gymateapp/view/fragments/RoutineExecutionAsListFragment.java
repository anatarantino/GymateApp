package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.databinding.FragmentRoutinerunAsListBinding;
import ar.edu.itba.example.gymateapp.model.ExerciseCredentials;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.view.adapter.ExercisesAdapter;
import ar.edu.itba.example.gymateapp.viewModel.ExercisesViewModel;

public class RoutineExecutionAsListFragment extends Fragment {

    private FragmentRoutinerunAsListBinding binding;
    private ExercisesViewModel viewModel;

    private ExercisesAdapter warmupAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter mainAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter cooldownAdapter = new ExercisesAdapter(new ArrayList<>());

    private TextView title;

    private static final int WARMUP_CYCLE = 0;
    private static final int MAIN_CYCLE = 1;
    private static final int COOLDOWN_CYCLE = 2;

    private static final int PLAYING = 0;
    private static final int PAUSED = 1;
    private static final int NOTRUNNING = 2;

    private ExercisesAdapter[] adapters = new ExercisesAdapter[3];

    private RecyclerView recyclerViewWarmup;
    private RecyclerView recyclerViewMain;
    private RecyclerView recyclerViewCooldown;

    private View view;

    private int currentCycle;
    private int currentExercise;
    private ExercisesAdapter currentAdapter;
    private boolean finished;
    private int status;
    private boolean executed;

    private MainActivity mainActivity;

    public RoutineExecutionAsListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoutinerunAsListBinding.inflate(getLayoutInflater());

        recyclerViewWarmup = binding.warmupExercises;
        recyclerViewMain = binding.mainExercises;
        recyclerViewCooldown = binding.cooldownExercises;

        title = binding.title;

        binding.executionBar.play.setOnClickListener(v -> playExecution());
        binding.executionBar.pause.setOnClickListener(v -> pauseExecution());
        binding.executionBar.next.setOnClickListener(v -> nextExecution());
        binding.executionBar.previous.setOnClickListener(v -> previousExecution());
        binding.executionBar.previous.setVisibility(View.GONE);
        binding.executionBar.next.setVisibility(View.GONE);

        adapters[0] = warmupAdapter;
        adapters[1] = mainAdapter;
        adapters[2] = cooldownAdapter;

        view = binding.getRoot();

        mainActivity = (MainActivity) getActivity();
        if(mainActivity != null){
            mainActivity.setNavigationVisibility(false);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            title.setText(RoutineExecutionAsListFragmentArgs.fromBundle(getArguments()).getTitle());
        }

        viewModel = new ViewModelProvider(requireActivity()).get(ExercisesViewModel.class);

        Log.e("As List","viewModel es: " + viewModel.toString());
        recyclerViewWarmup.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWarmup.setAdapter(warmupAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMain.setAdapter(mainAdapter);

        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCooldown.setAdapter(cooldownAdapter);

        if(!viewModel.getIsFirstTime()) {
            currentExercise = viewModel.getCurrentExercise();
            currentCycle = viewModel.getCurrentCycle();
            executed = viewModel.isExecuted();
            status = viewModel.getStatus();
        }else {
            currentCycle = 0;
            currentExercise = 0;
            executed = false;
            viewModel.setFirstTime(false);
        }

        observeExerciseViewModel();

        mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        mainActivity.setNavigationVisibility(false);
    }

    private void observeExerciseViewModel() {
        viewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmupAdapter.updateExercises(warmupExercises);
            }
        });

        viewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                mainAdapter.updateExercises(mainExercises);
            }
        });

        viewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldownAdapter.updateExercises(cooldownExercises);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(executed){
            binding.executionBar.previous.setVisibility(View.VISIBLE);
            binding.executionBar.next.setVisibility(View.VISIBLE);

            if(status == PLAYING) {
                binding.executionBar.play.setVisibility(View.INVISIBLE);
                binding.executionBar.pause.setVisibility(View.VISIBLE);
                viewModel.getCountDownTimer().resume();
            }else if(status == PAUSED) {
                binding.executionBar.play.setVisibility(View.VISIBLE);
                binding.executionBar.pause.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void playExecution() {
        binding.executionBar.play.setVisibility(View.INVISIBLE);
        binding.executionBar.pause.setVisibility(View.VISIBLE);
        status = PLAYING;
        finished = false;
        if(executed) {
            viewModel.getCountDownTimer().resume();
        }else{
            binding.executionBar.previous.setVisibility(View.VISIBLE);
            binding.executionBar.next.setVisibility(View.VISIBLE);
            executed = true;
            ExerciseCredentials ex = getNextExercise();
            viewModel.getCountDownTimer().start((ex.getDuration() * 1000), 1000);
            viewModel.getCountDownTimer().getStatus().observe(getViewLifecycleOwner(), countdown -> {
                if(!finished) {
                    if(countdown.isFinished()){
                        ExerciseCredentials exercise;
                        currentAdapter.getExercise(currentExercise).setRunning(false);
                        currentAdapter.notifyItemChanged(currentExercise);
                        currentExercise++;
                        if((exercise = getNextExercise()) != null) {
                            viewModel.getCountDownTimer().start(exercise.getDuration() * 1000,1000);
                        }
                    }
                }else {
                    openFinishedRoutineDialog();
                }
            });
        }
    }

    private void pauseExecution() {
        binding.executionBar.play.setVisibility(View.VISIBLE);
        binding.executionBar.pause.setVisibility(View.INVISIBLE);
        viewModel.getCountDownTimer().pause();
        status = PAUSED;
    }

    private void nextExecution() {
        viewModel.getCountDownTimer().stop();
        currentAdapter.getExercise(currentExercise).setRunning(false);
        currentAdapter.notifyItemChanged(currentExercise);
        currentExercise++;
        ExerciseCredentials exercise = getNextExercise();
        if(exercise != null) {
            viewModel.getCountDownTimer().start(exercise.getDuration() * 1000,1000);
            if(status == PAUSED) {
                viewModel.getCountDownTimer().pause();
            }
        }else {
            openFinishedRoutineDialog();
        }
    }

    private ExerciseCredentials getNextExercise() {
        ExerciseCredentials exercise = null;
        currentAdapter = getCurrentAdapter();
        if(currentAdapter != null) {
            exercise = currentAdapter.getExercise(currentExercise);
            exercise.setRunning(true);
            currentAdapter.notifyItemChanged(currentExercise);
        }
        return exercise;
    }

    public ExercisesAdapter getCurrentAdapter() {
        if(currentExercise >= adapters[currentCycle].getExerciseList().size()) {
            if(currentCycle == COOLDOWN_CYCLE) {
                finished = true;
                return null;
            }
            currentCycle++;
            currentExercise = 0;
        }
        return adapters[currentCycle];
    }

    private void previousExecution() {
        viewModel.getCountDownTimer().stop();
        currentAdapter.getExercise(currentExercise).setRunning(false);
        currentAdapter.notifyItemChanged(currentExercise);
        currentExercise--;
        ExerciseCredentials exercise = getPrevExercise();
        if(exercise != null) {
            viewModel.getCountDownTimer().start(exercise.getDuration() * 1000, 1000);
            if(status == PAUSED) {
                viewModel.getCountDownTimer().pause();
            }
        }
    }

    private ExerciseCredentials getPrevExercise() {
        ExerciseCredentials exercise;
        currentAdapter = getPrevAdapter();
        if(currentAdapter == null) {
            currentAdapter = warmupAdapter;
            currentExercise = 0;
        }
        exercise = currentAdapter.getExercise(currentExercise);
        exercise.setRunning(true);
        currentAdapter.notifyItemChanged(currentExercise);
        return exercise;
    }

    private ExercisesAdapter getPrevAdapter() {
        if(currentExercise == -1){
            if(currentCycle == WARMUP_CYCLE) {
                return null;
            }
            currentCycle--;
            currentExercise = adapters[currentCycle].getExerciseList().size() - 1;
            return adapters[currentCycle];
        }
        return adapters[currentCycle];
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setStatus(status);
        viewModel.setCurrentExercise(currentExercise);
        viewModel.setCurrentCycle(currentCycle);

        viewModel.setExecuted(executed);
        if(executed) {
            viewModel.getCountDownTimer().pause();
        }
    }

    public void openFinishedRoutineDialog() {
        FinishRoutine finishRoutine = new FinishRoutine(view, FinishRoutine.LIST_EXEC);
//        finishRoutine.show(getParentFragmentManager(), "example"); //?? borrar?
        finished = true;
        viewModel.setFirstTime(true);
    }
}
