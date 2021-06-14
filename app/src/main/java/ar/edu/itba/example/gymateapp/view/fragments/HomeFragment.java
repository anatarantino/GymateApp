package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.FragmentHomeBinding;
import ar.edu.itba.example.gymateapp.databinding.FragmentRoutinesBinding;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.view.adapter.RoutinesAdapter;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;
import ar.edu.itba.example.gymateapp.viewModel.RoutinesViewModel;

public class HomeFragment extends Fragment implements RoutinesAdapter.ItemClickListener {

    private RecyclerView recyclerRoutine;

    private View view;
    private RoutinesViewModel viewModel;
    private RoutinesAdapter routinesAdapter;
    private FragmentHomeBinding binding;
    private ScrollView scrollView;
    private RecyclerView recyclerView;
    boolean noMoreEntries = false;

    public HomeFragment(){ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_home,container,false);
//        routineList = new ArrayList<>();
//        recyclerRoutine = view.findViewById(R.id.userRecyclerView);
//        recyclerRoutine.setLayoutManager(new LinearLayoutManager(getContext()));
//        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
//        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider));
//        recyclerRoutine.addItemDecoration(itemDecorator);
//
//        seedRoutines(); //aca pondriamos las ultimas rutinas globales
//        RoutinesAdapter adapter = new RoutinesAdapter(routineList,this);
//
//        recyclerRoutine.setAdapter(adapter);
//
//        ((MainActivity) getActivity()).setNavigationVisibility(true);
//
//        return view;
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        scrollView = binding.scrollView;
        recyclerView = binding.userRecyclerView;

        ((MainActivity) getActivity()).setNavigationVisibility(true);
        return view;
    }

//    private void seedRoutines() {
//        RoutineData.Category c1 = new RoutineData.Category(1,"cat 1", "det cat 1");
//        RoutineData.Category c2 = new RoutineData.Category(2,"cat 2", "det cat 2");
//        routineList.add(new RoutineData(1,"Titulo 1", "creador 1","Esta es la desc de la rutina numero  1.", 1,c1));
//        routineList.add(new RoutineData(2,"Titulo 2", "creador 2","Esta es la desc de la rutina numero  2.", 2,c1));
//        routineList.add(new RoutineData(3,"Titulo 3", "creador 3","Esta es la desc de la rutina numero  3.", 3,c2));
//        routineList.add(new RoutineData(4,"Titulo 4", "creador 2","Esta es la desc de la rutina numero  4.", 4,c1));
//        routineList.add(new RoutineData(5,"Titulo 5", "creador 2","Esta es la desc de la rutina numero  5.", 5,c2));
//        routineList.add(new RoutineData(6,"Titulo 6", "creador 1","Esta es la desc de la rutina numero  6.", 4,c2));
//        routineList.add(new RoutineData(7,"Titulo 7", "creador 1","Esta es la desc de la rutina numero  7.", 3,c1));
//    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RoutinesViewModel.class);

        routinesAdapter = new RoutinesAdapter(new ArrayList<>(),this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routinesAdapter);

        viewModel.getRoutinesFirstLoad().observe(getViewLifecycleOwner(), firstLoad -> {
            if(firstLoad != null){
                if(firstLoad) {
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
                (View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if(!noMoreEntries && !scrollView.canScrollVertically(1)){
                        viewModel.updateData();
                    }
                }
        );
    }

    @Override
    public void onItemClick(RoutineCredentials routineCredentials) {
        HomeFragmentDirections.ActionNavigationHomeToRoutineDetailFragment action = HomeFragmentDirections.actionNavigationHomeToRoutineDetailFragment();
        action.setRoutineId(routineCredentials.getId());
        Navigation.findNavController(view).navigate(action);
    }
}
