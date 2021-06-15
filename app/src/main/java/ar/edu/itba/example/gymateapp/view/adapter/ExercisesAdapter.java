package ar.edu.itba.example.gymateapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.ExerciseCardBinding;
import ar.edu.itba.example.gymateapp.model.ExerciseCredentials;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {

    List<ExerciseCredentials> exerciseList;
    ExerciseCardBinding binding;
    private Context parentContext;

    public ExercisesAdapter(ArrayList<ExerciseCredentials> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void updateExercises(List<ExerciseCredentials> newExercisesList) {
        exerciseList.clear();
        exerciseList.addAll(newExercisesList);
        notifyDataSetChanged();
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);
        binding = DataBindingUtil.inflate(inflater, R.layout.exercise_card, parent, false);
        return new ExerciseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExercisesAdapter.ExerciseViewHolder holder, int position) {
        ExerciseCredentials exercise = exerciseList.get(position);
        holder.itemView.setExerciseCredentials(exercise);
        if (exercise.isRunning()){
            holder.itemView.exerciseCard.setBackgroundColor(parentContext.getColor(R.color.Gymate_green));
            holder.itemView.exName.setTextColor(parentContext.getColor(R.color.title1));
            holder.itemView.exDuration.setTextColor(parentContext.getColor(R.color.title1));
        }
        else{
            holder.itemView.exerciseCard.setBackgroundColor(parentContext.getColor(R.color.exColor));
            holder.itemView.exName.setTextColor(parentContext.getColor(R.color.title1));
            holder.itemView.exDuration.setTextColor(parentContext.getColor(R.color.title1));
        }

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public ExerciseCardBinding itemView;

        public ExerciseViewHolder(ExerciseCardBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }

    public List<ExerciseCredentials> getExerciseList() {
        return exerciseList;
    }

    public ExerciseCredentials getExercise(int index) {
         return exerciseList.get(index);
    }


}
