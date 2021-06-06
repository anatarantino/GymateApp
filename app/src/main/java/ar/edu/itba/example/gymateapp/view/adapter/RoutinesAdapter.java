package ar.edu.itba.example.gymateapp.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.RoutineCardBinding;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;
import ar.edu.itba.example.gymateapp.view.fragments.ClickOnRoutine;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> {

    ArrayList<RoutineData> routinesList;
    private ItemClickListener listener;

    public RoutinesAdapter(ArrayList<RoutineData> routinesList, ItemClickListener listener) {
        this.routinesList = routinesList;
        this.listener = listener;
    }

    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card,null,false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoutinesAdapter.RoutineViewHolder holder, int position) {
        RoutineData routine = routinesList.get(position);
        int id = routine.cat.id;
        switch(id) {
            //aca vamos a tener que elegir una img para cada cat
            case 1:
                holder.img.setImageResource(R.drawable.fit);
                routine.setImg(String.valueOf(R.drawable.fit));
                break;
            case 2:
                holder.img.setImageResource(R.drawable.fit2);
                routine.setImg(String.valueOf(R.drawable.fit2));
                break;
        }
        holder.id.setText(String.valueOf(routinesList.get(position).id));
        holder.txtTitle.setText(routinesList.get(position).title);
        holder.txtCreator.setText(routinesList.get(position).creator);
        holder.rating.setRating(routinesList.get(position).rating);

        //listener de la rutina clickeada
        Log.d("Routines Adapter","onBindViewHolder: called");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(routinesList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView id,txtTitle,txtCreator;
        ImageView img;
        RatingBar rating;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.routineId);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtCreator = (TextView) itemView.findViewById(R.id.creator);
            img = (ImageView) itemView.findViewById(R.id.image);
            rating = (RatingBar) itemView.findViewById(R.id.routineRatingBar);

        }
    }

    public interface ItemClickListener{
        public void onItemClick(RoutineData routineData);
    }

}
