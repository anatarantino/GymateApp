package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.adapter.ExercisesAdapter;
import ar.edu.itba.example.gymateapp.view.classes.ExerciseData;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;

public class RoutineExecutionAsListFragment extends Fragment {

    /*

    //esto lo haria con la bdd ahora solo los declaro y los agrego a mano
//    private ExercisesAdapter warmupAdapter = new ExercisesAdapter(new ArrayList<>());
//    private ExercisesAdapter mainAdapter = new ExercisesAdapter(new ArrayList<>());
//    private ExercisesAdapter cooldownAdapter = new ExercisesAdapter(new ArrayList<>());

    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "id";

    private String titleParam, id;

    private ExercisesAdapter warmupAdapter;
    private ExercisesAdapter mainAdapter;
    private ExercisesAdapter cooldownAdapter;

    private static final int WARMUP_CYCLE = 0;
    private static final int MAIN_CYCLE = 1;
    private static final int COOLDOWN_CYCLE = 2;

    private ExercisesAdapter[] adapters = new ExercisesAdapter[3];

    private RecyclerView recyclerViewWarmup;
    private RecyclerView recyclerViewMain;
    private RecyclerView recyclerViewCooldown;

    public RoutineExecutionAsListFragment() {
    }

    public static RoutineExecutionAsListFragment newInstance(RoutineData routineData){
        RoutineExecutionAsListFragment fragment = new RoutineExecutionAsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1,routineData.title);
        bundle.putString(ARG_PARAM2, String.valueOf(routineData.id));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            titleParam = getArguments().getString(ARG_PARAM1);
            id = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routinerun_as_list,container,false);

        TextView title = view.findViewById(R.id.title);
        title.setText(titleParam);

        //esto lo hacemos ahora porque no tenemos la bdd
        ArrayList<ExerciseData> warmup = new ArrayList<>();
        warmup.add(new ExerciseData("Ej W 1","20"));
        warmup.add(new ExerciseData("Ej W 2", "30"));
        ArrayList<ExerciseData> main = new ArrayList<>();
        main.add(new ExerciseData("Ej M 1","20"));
        main.add(new ExerciseData("Ej M 2", "30"));
        main.add(new ExerciseData("Ej M 3", "30"));
        main.add(new ExerciseData("Ej M 4", "30"));
        ArrayList<ExerciseData> cooldown = new ArrayList<>();
        cooldown.add(new ExerciseData("Ej C 1","20"));
        cooldown.add(new ExerciseData("Ej C 2", "30"));
        cooldown.add(new ExerciseData("Ej C 3", "30"));
        cooldown.add(new ExerciseData("Ej C 4", "30"));

        recyclerViewWarmup = view.findViewById(R.id.warmupExercises);
        recyclerViewMain = view.findViewById(R.id.mainExercises);
        recyclerViewCooldown = view.findViewById(R.id.cooldownExercises);

        //relleno los adapters a mano
        warmupAdapter = new ExercisesAdapter(warmup);
        mainAdapter = new ExercisesAdapter(main);
        cooldownAdapter = new ExercisesAdapter(cooldown);

        adapters[0] = warmupAdapter;
        adapters[1] = mainAdapter;
        adapters[2] = cooldownAdapter;

        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewWarmup.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWarmup.setAdapter(warmupAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMain.setAdapter(mainAdapter);

        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCooldown.setAdapter(cooldownAdapter);
    }

     */
}
