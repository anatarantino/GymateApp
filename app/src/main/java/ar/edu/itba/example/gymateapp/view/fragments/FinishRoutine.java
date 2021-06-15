package ar.edu.itba.example.gymateapp.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import org.jetbrains.annotations.NotNull;

import ar.edu.itba.example.gymateapp.R;

public class FinishRoutine extends AppCompatDialogFragment {
    private View mainView;
    public static final int LIST_EXEC = 1;
    public static final int DETAIL_EXEC = 0;
    private int execType;

    public FinishRoutine(View mainView, int execType) {
        this.mainView = mainView;
        this.execType = execType;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.PopUp);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.finish_routine, null);

        builder.setView(view).setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (execType == LIST_EXEC) {
                    Navigation.findNavController(mainView).navigate(RoutineExecutionAsListFragmentDirections.actionRoutineExecutionAsListFragmentToRoutineDetailFragment());
                } else if (execType == DETAIL_EXEC) {
                    Navigation.findNavController(mainView).navigate(RoutineExecutionFragmentDirections.actionRoutineExecutionExerciseToRoutineDetailFragment());
                }
            }
        });
        this.setCancelable(false);
        return builder.create();
    }
}
