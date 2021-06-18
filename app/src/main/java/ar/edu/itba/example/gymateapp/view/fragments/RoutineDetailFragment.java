package ar.edu.itba.example.gymateapp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.FragmentDetailBinding;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.view.adapter.ExercisesAdapter;
import ar.edu.itba.example.gymateapp.viewModel.ExercisesViewModel;
import ar.edu.itba.example.gymateapp.viewModel.FavouritesRoutinesViewModel;
import ar.edu.itba.example.gymateapp.viewModel.RoutinesViewModel;


public class RoutineDetailFragment extends Fragment {

    private RoutinesViewModel routinesViewModel;
    private FavouritesRoutinesViewModel favViewModel;
    private ExercisesViewModel exercisesViewModel;

    private ExercisesAdapter warmupAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter mainAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter cooldownAdapter = new ExercisesAdapter(new ArrayList<>());

    private RecyclerView recyclerViewWarmup;
    private RecyclerView recyclerViewMain;
    private RecyclerView recyclerViewCooldown;

    private RoutineCredentials routineCredentials;
    private TextView title, creator, desc;
    private ImageView img;
    private int routineId;
    private FragmentDetailBinding binding;
    private View view;

    private MenuItem fav;
    private MenuItem unfav;

    private MainActivity main;
    private Button listBtn, detailBtn;

    public RoutineDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(getLayoutInflater());

        title = binding.title;
        creator = binding.creator;
        desc = binding.detailReadAll;
        img = binding.imageView;

        recyclerViewWarmup = binding.warmupExercises;
        recyclerViewMain = binding.mainExercises;
        recyclerViewCooldown = binding.cooldownExercises;

        listBtn = binding.listBtn;
        detailBtn = binding.detailBtn;

        view = binding.getRoot();

        main = (MainActivity) getActivity();
        if(main != null){
            main.showUpButton();
            main.setNavigationVisibility(false);
        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            routineId = Integer.parseInt(getArguments().getString("RoutineId"));
        }

        routinesViewModel = new ViewModelProvider(requireActivity()).get(RoutinesViewModel.class);
        routinesViewModel.getRoutineById(routineId);

        routinesViewModel.getCurrentRoutine().observe(getViewLifecycleOwner(), routine -> {
            if (routine != null) {
                this.routineCredentials = routine;
                img.setImageResource(Integer.parseInt(routine.getImage()));
                title.setText(routine.getName());
                creator.setText(routine.getUser().getUsername());
                desc.setText(routine.getDetail());
            }
        });

        favViewModel = new ViewModelProvider(requireActivity()).get(FavouritesRoutinesViewModel.class);

        Button listBtn = view.findViewById(R.id.listBtn);
        listBtn.setOnClickListener(v -> {
            RoutineDetailFragmentDirections.ActionRoutineDetailFragmentToRoutineExecutionAsListFragment action = RoutineDetailFragmentDirections.actionRoutineDetailFragmentToRoutineExecutionAsListFragment(routineCredentials.getName(), routineId);
            routinesViewModel.addRoutineExecution(routineId);
            Navigation.findNavController(view).navigate(action);
        });

        Button detailBtn = view.findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(v -> {
            RoutineDetailFragmentDirections.ActionRoutineDetailFragmentToRoutineExecutionExercise action = RoutineDetailFragmentDirections.actionRoutineDetailFragmentToRoutineExecutionExercise(routineId);
            routinesViewModel.addRoutineExecution(routineId);
            Navigation.findNavController(view).navigate(action);
        });

        exercisesViewModel = new ViewModelProvider(requireActivity()).get(ExercisesViewModel.class);
        exercisesViewModel.refresh(routineId);

        recyclerViewWarmup.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWarmup.setAdapter(warmupAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMain.setAdapter(mainAdapter);

        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCooldown.setAdapter(cooldownAdapter);

        observeExerciseViewModel();

    }

    private void observeExerciseViewModel() {
        exercisesViewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmupAdapter.updateExercises(warmupExercises);
            }
        });

        exercisesViewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                mainAdapter.updateExercises(mainExercises);
            }
        });

        exercisesViewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldownAdapter.updateExercises(cooldownExercises);
            }
        });
    }

    @Override
    public void onDestroyView() {
        main.hideUpButton();
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.app_bar_share).setVisible(true);
        menu.findItem(R.id.app_bar_rate).setVisible(true);
        menu.findItem(R.id.app_bar_favorite_filled).setVisible(true);
        menu.findItem(R.id.app_bar_favorite_outlined).setVisible(false);

        fav = menu.findItem(R.id.app_bar_favorite_filled);
        unfav = menu.findItem(R.id.app_bar_favorite_outlined);

        favViewModel.getFavouriteRoutines().observe(getViewLifecycleOwner(), favourites -> {

            boolean isFavourite = false;
            for (RoutineCredentials routine : favourites) {
                if (routine.getId() == routineId) {
                    isFavourite = true;
                    break;
                }
            }
            if (isFavourite) {
                favRoutine();
            }else {
                unfavRoutine();
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_favorite_filled) {
            favViewModel.unfavRoutine(routineId);
            unfavRoutine();
        } else if (id == R.id.app_bar_favorite_outlined) {
            favViewModel.favRoutine(routineId);
            favRoutine();
        } else if (id == R.id.app_bar_rate) {
            openRateDialog();
        } else if (id == R.id.app_bar_share) {
            share();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void favRoutine() {
        fav.setVisible(true);
        unfav.setVisible(false);
    }

    public void unfavRoutine() {
        fav.setVisible(false);
        unfav.setVisible(true);
    }

    public void openRateDialog() {
        RateDialog rateDialog = new RateDialog(routineId, getActivity());
        rateDialog.show(getParentFragmentManager(), "Example dialog");
    }

    public void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, routineCredentials.getName());
        sharingIntent.putExtra("RoutineId", routineId);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.subject) + ": http://www.gymate.com/routines/" + routineId);
        startActivity(Intent.createChooser(sharingIntent, "Share Routine"));
    }
}

