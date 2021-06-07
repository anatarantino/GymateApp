package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.adapter.RoutinesAdapter;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;

public class HomeFragment extends Fragment implements RoutinesAdapter.ItemClickListener {

    RecyclerView recyclerRoutine;
    ArrayList<RoutineData> routineList;

    public HomeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        routineList = new ArrayList<>();
        recyclerRoutine = view.findViewById(R.id.userRecyclerView);
        recyclerRoutine.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerRoutine.getContext(), LinearLayoutManager.VERTICAL);
        recyclerRoutine.addItemDecoration(mDividerItemDecoration);

        seedRoutines(); //aca pondriamos las ultimas rutinas globales
        RoutinesAdapter adapter = new RoutinesAdapter(routineList,this);

        recyclerRoutine.setAdapter(adapter);
        return view;

    }

    private void seedRoutines() {
        RoutineData.Category c1 = new RoutineData.Category(1,"cat 1", "det cat 1");
        RoutineData.Category c2 = new RoutineData.Category(2,"cat 2", "det cat 2");
        routineList.add(new RoutineData(1,"Titulo 1", "creador 1","Esta es la desc de la rutina numero  1.", 1,c1));
        routineList.add(new RoutineData(2,"Titulo 2", "creador 2","Esta es la desc de la rutina numero  2.", 2,c1));
        routineList.add(new RoutineData(3,"Titulo 3", "creador 3","Esta es la desc de la rutina numero  3.", 3,c2));
        routineList.add(new RoutineData(4,"Titulo 4", "creador 2","Esta es la desc de la rutina numero  4.", 4,c1));
        routineList.add(new RoutineData(5,"Titulo 5", "creador 2","Esta es la desc de la rutina numero  5.", 5,c2));
        routineList.add(new RoutineData(6,"Titulo 6", "creador 1","Esta es la desc de la rutina numero  6.", 4,c2));
        routineList.add(new RoutineData(7,"Titulo 7", "creador 1","Esta es la desc de la rutina numero  7.", 3,c1));
    }

    @Override
    public void onItemClick(RoutineData routineData) {
        Fragment fragment = RoutineDetailFragment.newInstance(routineData);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_home, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
