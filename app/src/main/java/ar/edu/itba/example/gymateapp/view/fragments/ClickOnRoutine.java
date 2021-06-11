package ar.edu.itba.example.gymateapp.view.fragments;

import android.view.View;
import android.widget.TextView;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.viewModel.RoutinesViewModel;

public class ClickOnRoutine implements View.OnClickListener {

    private RoutinesViewModel routinesViewModel;
    private int from;
    public static final int ROUTINES_ID = 1;
    public static final int FAVOURITES_ID = 2;

    public ClickOnRoutine(RoutinesViewModel routinesViewModel, int from) { //el from para definir que lista se obtiene
        this.routinesViewModel = routinesViewModel;
        this.from = from;
    }

    @Override
    public void onClick(View v) {
        int routineId = Integer.parseInt(((TextView) v.findViewById(R.id.routineId)).getText().toString());
        routinesViewModel.getRoutineById(routineId);
//        switch (from){
//            case ROUTINES_ID:
//                RoutinesFragment;
//                break;
//            case FAVOURITES_ID:
//
//                break;
//        }

    }
}
