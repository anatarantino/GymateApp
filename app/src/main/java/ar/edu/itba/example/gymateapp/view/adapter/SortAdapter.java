package ar.edu.itba.example.gymateapp.view.adapter;

import android.view.View;
import android.widget.AdapterView;

import ar.edu.itba.example.gymateapp.viewModel.RoutinesViewModel;

public class SortAdapter implements AdapterView.OnItemSelectedListener{

    private RoutinesViewModel viewModel;

    public SortAdapter(RoutinesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        viewModel.sortRoutines(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
