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
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.RatingDialogBinding;
import ar.edu.itba.example.gymateapp.viewModel.ExercisesViewModel;

public class RateDialog extends AppCompatDialogFragment {

    private int routineId;
    private RatingBar ratingBar;
    private RatingDialogBinding binding;
    private ExercisesViewModel viewModel;

    public RateDialog(int routineId, FragmentActivity activity) {
        this.routineId = routineId;
        viewModel=new ViewModelProvider(activity).get(ExercisesViewModel.class);
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.PopUp);
        binding = RatingDialogBinding.inflate(getActivity().getLayoutInflater());
        ratingBar = binding.ratingBar;
        View view = binding.getRoot();
        builder.setView(view).setTitle(getString(R.string.RateRoutineDialog).toUpperCase()).setNegativeButton(getString(R.string.Close), (dialog, which) -> {
        }).setPositiveButton(getString(R.string.Rate), (dialog, which) -> viewModel.rateRoutine(routineId,(int)ratingBar.getRating()));
        this.setCancelable(false);
        return builder.create();
    }
}
