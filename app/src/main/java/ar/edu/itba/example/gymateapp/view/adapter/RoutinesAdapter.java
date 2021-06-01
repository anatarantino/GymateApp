package ar.edu.itba.example.gymateapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> {

    ArrayList<RoutineData> routinesList;

    public RoutinesAdapter(ArrayList<RoutineData> routinesList) {
        this.routinesList = routinesList;
    }

    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card,null,false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoutinesAdapter.RoutineViewHolder holder, int position) {
        //aca  van los datos que hay en routineData
        holder.txtTitle.setText(routinesList.get(position).title);
        holder.txtCreator.setText(routinesList.get(position).creator);
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtCreator;
        ImageView img;
        Integer rating;

        public RoutineViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtCreator = (TextView) itemView.findViewById(R.id.creator);
            img = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}
