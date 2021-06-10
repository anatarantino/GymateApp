package ar.edu.itba.example.gymateapp.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.view.adapter.RoutinesAdapter;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;

public class MyRoutinesFragment extends Fragment implements RoutinesAdapter.ItemClickListener{

   private RecyclerView recyclerRoutine;
   private ArrayList<RoutineData> myRoutineList;
   private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_routines,container,false);
        myRoutineList = new ArrayList<>();
        recyclerRoutine = view.findViewById(R.id.userRecyclerView);
        recyclerRoutine.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider));
        recyclerRoutine.addItemDecoration(itemDecorator);
        seedRoutines();
        RoutinesAdapter adapter = new RoutinesAdapter(myRoutineList,this);
        recyclerRoutine.setAdapter(adapter);
        Button sortBtn = view.findViewById(R.id.button5);
        sortBtn.setOnClickListener(v -> Navigation.findNavController(view).navigate(MyRoutinesFragmentDirections.actionNavigationMyRoutinesToSortByFragment()));
        ((MainActivity) getActivity()).setNavigationVisibility(true);
        return view;
    }

    private void seedRoutines(){
        RoutineData.Category c1 = new RoutineData.Category(1,"cat 1", "det cat 1");
        RoutineData.Category c2 = new RoutineData.Category(2,"cat 2", "det cat 2");
        myRoutineList.add(new RoutineData(1,"Titulo","creador 1","Esta es la desc de la rutina numero  1.", 3, c1));
        myRoutineList.add(new RoutineData(2,"Titulo 2","creador 2","Esta es la desc de la rutina numero 2." , 4, c2));
        myRoutineList.add(new RoutineData(3,"Titulo 3","creador 3","Esta es la desc de la rutina numero  3.", 4, c1));
        myRoutineList.add(new RoutineData(4,"Titulo 4","creador 2","Esta es la desc de la rutina numero  4.", 4, c1));
        myRoutineList.add(new RoutineData(5,"Titulo 5","creador 2","Esta es la desc de la rutina numero 5.", 4, c2));
        myRoutineList.add(new RoutineData(6,"Titulo 6","creador 1","Esta es la desc de la rutina numero  6.", 4, c2));
        myRoutineList.add(new RoutineData(7,"Titulo 7","creador 1","Esta es la desc de la rutina numero  7 .", 4, c1));

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemClick(RoutineData routineData) {
        MyRoutinesFragmentDirections.ActionNavigationMyRoutinesToRoutineDetailFragment action1 = MyRoutinesFragmentDirections.actionNavigationMyRoutinesToRoutineDetailFragment();
        action1.setRoutineId(routineData.id);
        Navigation.findNavController(view).navigate(action1);
    }
}
