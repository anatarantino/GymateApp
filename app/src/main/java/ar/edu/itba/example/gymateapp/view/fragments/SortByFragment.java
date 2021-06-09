package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;

public class SortByFragment extends Fragment {

    private MainActivity main;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_by,container,false);
        main = (MainActivity) getActivity();
        main.showUpButton();
        main.setNavigationVisibility(false);
        return view;
    }
}
