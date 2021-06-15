package ar.edu.itba.example.gymateapp.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.FragmentRoutinesBinding;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.view.adapter.RoutinesAdapter;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;
import ar.edu.itba.example.gymateapp.viewModel.RoutinesViewModel;

public class MyRoutinesFragment extends Fragment implements RoutinesAdapter.ItemClickListener{
    private RoutinesViewModel viewModel;
    private FragmentRoutinesBinding binding;

    private RecyclerView recyclerView;
    private RoutinesAdapter routinesAdapter;

    private View view;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentRoutinesBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        recyclerView = binding.userRecyclerView;

        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);

        Spinner spinner = (Spinner) view.findViewById(R.id.sortby_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinner_adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RoutinesViewModel.class);
        viewModel.updateUserRoutines();

        routinesAdapter = new RoutinesAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routinesAdapter);

        viewModel.getUserRoutines().observe(getViewLifecycleOwner(), routines -> {
            if(routines != null){
                routinesAdapter.updateRoutines(routines);
            }
        });
    }

    @Override
    public void onItemClick(RoutineCredentials routineCredentials) {
        MyRoutinesFragmentDirections.ActionNavigationMyRoutinesToRoutineDetailFragment action1 = MyRoutinesFragmentDirections.actionNavigationMyRoutinesToRoutineDetailFragment();
        action1.setRoutineId(routineCredentials.getId());
        Navigation.findNavController(view).navigate(action1);
    }
}
