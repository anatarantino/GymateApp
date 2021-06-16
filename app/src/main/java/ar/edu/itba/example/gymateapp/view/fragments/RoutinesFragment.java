package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ToggleButton;

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
import ar.edu.itba.example.gymateapp.view.adapter.SortAdapter;
import ar.edu.itba.example.gymateapp.viewModel.RoutinesViewModel;


public class RoutinesFragment extends Fragment implements RoutinesAdapter.ItemClickListener {

    private View view;
    private RoutinesViewModel viewModel;
    private RoutinesAdapter routinesAdapter;
    private FragmentRoutinesBinding binding;
    private ScrollView scrollView;
    private RecyclerView recyclerView;
    boolean noMoreEntries = false;
    private Spinner sortSpinner;
    private ToggleButton orderBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoutinesBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        scrollView = binding.scrollView;
        recyclerView = binding.userRecyclerView;

        DividerItemDecoration itemDecorator = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);

        ((MainActivity) requireActivity()).setNavigationVisibility(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RoutinesViewModel.class);

        setSortOrder(view);

        routinesAdapter = new RoutinesAdapter(new ArrayList<>(), this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routinesAdapter);

        viewModel.getRoutinesFirstLoad().observe(getViewLifecycleOwner(), firstLoad -> {
            if (firstLoad != null) {
                if (firstLoad) {
                    viewModel.updateData();
                    viewModel.setRoutinesFirstLoad(false);
                }
            }
        });

        viewModel.getRoutineCards().observe(getViewLifecycleOwner(), routines -> {
            if (routines != null) {
                routinesAdapter.updateRoutines(routines);
            }
        });

        viewModel.getNoMoreEntries().observe(getViewLifecycleOwner(), value -> {
            if (value != null) {
                noMoreEntries = value;
            }
        });

        scrollView.setOnScrollChangeListener(
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (!noMoreEntries && !scrollView.canScrollVertically(1)) {
                        viewModel.updateData();
                    }
                }
        );
    }

    @Override
    public void onItemClick(RoutineCredentials routineCredentials) {
        RoutinesFragmentDirections.ActionNavigationRoutinesToRoutineDetailFragment action = RoutinesFragmentDirections.actionNavigationRoutinesToRoutineDetailFragment();
        action.setRoutineId(routineCredentials.getId());
        Navigation.findNavController(view).navigate(action);
    }

    private void setSortOrder(View view) {
        sortSpinner = view.findViewById(R.id.sortby_spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_array, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(spinner_adapter);
        sortSpinner.setSelection(viewModel.getOrderById(), false);
        sortSpinner.setOnItemSelectedListener(new SortAdapter(viewModel));

        orderBtn = view.findViewById(R.id.toggleButton);
        orderBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                viewModel.orderRoutines(1);
            }else{
                viewModel.orderRoutines(0);
            }
        });

    }


}


