package ar.edu.itba.example.gymateapp.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import org.jetbrains.annotations.NotNull;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.RatingDialogBinding;

public class RateDialog extends AppCompatDialogFragment {

    private int routineId;
    private RatingBar ratingBar;
    private RatingDialogBinding binding;

    public RateDialog(int routineId, FragmentActivity activity) {
        this.routineId = routineId;
        //conectar viewModel
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.PopUp);
        binding = RatingDialogBinding.inflate(getActivity().getLayoutInflater());
        ratingBar = binding.ratingBar;
        View view = binding.getRoot();
        //Api
//        builder.setView(view).setTitle(getString(R.string.RateRoutineDialog).toUpperCase()).setNegativeButton(getString(R.string.Close), (dialog, which) -> {
//        }).setPositiveButton(getString(R.string.Rate), (dialog, which) -> viewModel.rateRoutine(routineId,(int)ratingBar.getRating()));
        this.setCancelable(false);
        return builder.create();
    }
}
