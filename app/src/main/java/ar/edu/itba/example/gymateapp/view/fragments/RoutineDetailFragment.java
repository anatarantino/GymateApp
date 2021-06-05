package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;

public class RoutineDetailFragment extends Fragment implements View.OnClickListener {

    private RoutineData routineData;
    public static RoutineDetailFragment newInstance(RoutineData routineData) {
        RoutineDetailFragment fragment = new RoutineDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Routine",routineData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        FloatingActionButton playBtn = view.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment_routinerun_as_list = new RoutineExecutionAsListFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.routine_detail, fragment_routinerun_as_list);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
