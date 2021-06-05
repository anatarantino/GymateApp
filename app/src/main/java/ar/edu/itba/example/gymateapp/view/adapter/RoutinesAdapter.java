package ar.edu.itba.example.gymateapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> implements View.OnClickListener {

    ArrayList<RoutineData> routinesList;
    private View.OnClickListener listener;

    public RoutinesAdapter(ArrayList<RoutineData> routinesList) {
        this.routinesList = routinesList;
    }

    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card,null,false);
        view.setOnClickListener(this);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoutinesAdapter.RoutineViewHolder holder, int position) {
        //aca  van los datos que hay en routineData
        holder.txtTitle.setText(routinesList.get(position).title);
        holder.txtCreator.setText(routinesList.get(position).creator);
        holder.rating.setRating(routinesList.get(position).rating);
        //holder.img.setImageBitmap(getImage(data.get(position).getImg())); //ver como setear la img
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtCreator;
        ImageView img;
        RatingBar rating;

        public RoutineViewHolder(View itemView) {

            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtCreator = (TextView) itemView.findViewById(R.id.creator);
            img = (ImageView) itemView.findViewById(R.id.image);
            rating = (RatingBar) itemView.findViewById(R.id.routineRatingBar);

        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }
}
