package ar.edu.itba.example.gymateapp.view.fragments;

import android.util.Log;
import android.view.View;

public class ClickOnRoutine implements View.OnClickListener {

    public ClickOnRoutine() {

    }

    @Override
    public void onClick(View v) {
        Log.i("ClickOnRoutine","Se hizo un click!!!");
        //Aca vamos a tener que llamar a la rutina segun su id.
    }
}
