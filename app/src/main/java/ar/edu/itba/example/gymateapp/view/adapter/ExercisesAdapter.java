package ar.edu.itba.example.gymateapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.classes.ExerciseData;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {

    ArrayList<ExerciseData> exerciseList;

    public ExercisesAdapter(ArrayList<ExerciseData> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card, null, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExercisesAdapter.ExerciseViewHolder holder, int position) {
        //aca los datos de ExerciseData
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView name,duration;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.exName);
            duration = (TextView) itemView.findViewById(R.id.exDuration);
        }
    }


}
