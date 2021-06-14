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
import java.util.List;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.RoutineCardBinding;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;
import ar.edu.itba.example.gymateapp.view.fragments.ClickOnRoutine;

//public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> {
//
//    ArrayList<RoutineData> routinesList;
//    private ItemClickListener listener;
//
//    public RoutinesAdapter(ArrayList<RoutineData> routinesList, ItemClickListener listener) {
//        this.routinesList = routinesList;
//        this.listener = listener;
//    }
//
//    @Override
//    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card,null,false);
//        return new RoutineViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull @NotNull RoutinesAdapter.RoutineViewHolder holder, int position) {
//        RoutineData routine = routinesList.get(position);
//        int id = routine.cat.id;
//        switch(id) {
//            //aca vamos a tener que elegir una img para cada cat
//            case 1:
//                holder.img.setImageResource(R.drawable.fit);
//                routine.setImg(String.valueOf(R.drawable.fit));
//                break;
//            case 2:
//                holder.img.setImageResource(R.drawable.fit2);
//                routine.setImg(String.valueOf(R.drawable.fit2));
//                break;
//        }
//        holder.id.setText(String.valueOf(routinesList.get(position).id));
//        holder.txtTitle.setText(routinesList.get(position).title);
//        holder.txtCreator.setText(routinesList.get(position).creator);
//        holder.rating.setRating(routinesList.get(position).rating);
//
//        //listener de la rutina clickeada
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClick(routinesList.get(position));
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return routinesList.size();
//    }
//
//    public class RoutineViewHolder extends RecyclerView.ViewHolder {
//        TextView id,txtTitle,txtCreator;
//        ImageView img;
//        RatingBar rating;
//
//        public RoutineViewHolder(@NonNull View itemView) {
//            super(itemView);
//            id = (TextView) itemView.findViewById(R.id.routineId);
//            txtTitle = (TextView) itemView.findViewById(R.id.title);
//            txtCreator = (TextView) itemView.findViewById(R.id.creator);
//            img = (ImageView) itemView.findViewById(R.id.image);
//            rating = (RatingBar) itemView.findViewById(R.id.routineRatingBar);
//
//        }
//    }
//
//    public interface ItemClickListener{
//        public void onItemClick(RoutineData routineData);
//    }
//
//}

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> {

    List<RoutineCredentials> routinesList;

    private ItemClickListener listener;

    public RoutinesAdapter(List<RoutineCredentials> routinesList, ItemClickListener listener) {
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
        RoutineCredentials routine = routinesList.get(position);
        int id = routine.getCategory().getId();
        switch(id) {
            //aca vamos a tener que elegir una img para cada cat
            case 1:
                holder.img.setImageResource(R.drawable.fit);
                routine.setImage(String.valueOf(R.drawable.fit));
                break;
            case 2:
                holder.img.setImageResource(R.drawable.fit2);
                routine.setImage(String.valueOf(R.drawable.fit2));
                break;
        }
        holder.id.setText(String.valueOf(routinesList.get(position).getId()));
        holder.txtTitle.setText(routinesList.get(position).getName());
        //silencie lo del usuario porque la api no lo estaba devolviendo
//        holder.txtCreator.setText(routinesList.get(position).getUser().getUsername());
        holder.txtCreator.setText("aca iria el creator");
        holder.rating.setRating(routinesList.get(position).getAverageRating());

        //listener de la rutina clickeada

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

    public void updateRoutines(List<RoutineCredentials> routineCards){
        routinesList.clear();
        routinesList.addAll(routineCards);
        notifyDataSetChanged();
    }

    public void resetRoutines(){
        routinesList.clear();
        notifyDataSetChanged();
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
        public void onItemClick(RoutineCredentials routineCredentials);
    }

}