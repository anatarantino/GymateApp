package ar.edu.itba.example.gymateapp.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.adapter.RoutinesAdapter;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;

public class RoutinesFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerRoutine;
    ArrayList<RoutineData> routineList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routines, container, false);
        routineList = new ArrayList<>();
        recyclerRoutine = view.findViewById(R.id.userRecyclerView);
        recyclerRoutine.setLayoutManager(new LinearLayoutManager(getContext()));
        seedRoutines();
        RoutinesAdapter adapter = new RoutinesAdapter(routineList);
        recyclerRoutine.setAdapter(adapter);
        Button sortBtn = view.findViewById(R.id.button5);
        sortBtn.setOnClickListener(this);
        return view;
    }

    private void seedRoutines() {
        routineList.add(new RoutineData("Titulo", "creador 1", R.drawable.fit, 3));
        routineList.add(new RoutineData("Titulo 2", "creador 2", R.drawable.fit, 4));
        routineList.add(new RoutineData("Titulo 3", "creador 3", R.drawable.fit, 4));
        routineList.add(new RoutineData("Titulo 4", "creador 2", R.drawable.fit, 4));
        routineList.add(new RoutineData("Titulo 5", "creador 2", R.drawable.fit, 4));
        routineList.add(new RoutineData("Titulo 6", "creador 1", R.drawable.fit, 4));
        routineList.add(new RoutineData("Titulo 7", "creador 1", R.drawable.fit, 4));
    }

    @Override
    public void onClick(View v) {
        Fragment fragment_sort_by = new SortByFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_routines, fragment_sort_by);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
