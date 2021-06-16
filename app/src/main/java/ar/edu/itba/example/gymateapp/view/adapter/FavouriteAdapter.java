package ar.edu.itba.example.gymateapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private List<RoutineCredentials> favouriteList;
    private ItemClickListener listener;

    public FavouriteAdapter(List<RoutineCredentials> favouriteList, ItemClickListener listener) {
        this.favouriteList = favouriteList;
        this.listener = listener;
    }

    public void updateFavouriteList(List<RoutineCredentials> newRoutinesList) {
        favouriteList.clear();
        favouriteList.addAll(newRoutinesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card, null, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavouriteViewHolder holder, int position) {
        RoutineCredentials routine = favouriteList.get(position);

        int id = routine.getCategory().getId();
        switch (id) {
            //a cada categoria se le asigna una imagen
            case 1:
                holder.img.setImageResource(R.drawable.c1);
                routine.setImage(String.valueOf(R.drawable.c1));
                break;
            case 2:
                holder.img.setImageResource(R.drawable.c2);
                routine.setImage(String.valueOf(R.drawable.c2));
                break;
            case 3:
                holder.img.setImageResource(R.drawable.c3);
                routine.setImage(String.valueOf(R.drawable.c3));
                break;
            case 4:
                holder.img.setImageResource(R.drawable.c4);
                routine.setImage(String.valueOf(R.drawable.c4));
                break;
        }

        holder.id.setText(String.valueOf(favouriteList.get(position).getId()));
        holder.txtTitle.setText(favouriteList.get(position).getName());
//        holder.txtCreator.setText(favouriteList.get(position).getUser().getUsername());
        holder.txtCreator.setText("aca iria el usuario");
        holder.rating.setRating(favouriteList.get(position).getAverageRating());
        holder.category.setText(favouriteList.get(position).getCategory().getName());
        // holder.itemView.setRoutineCredentials(favouriteList.get(position));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(favouriteList.get(position)));

        //holder.itemView.setClickOnRoutine(new ClickOnRoutine(routinesViewModel,));
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        //public RoutineCardBinding itemView;
        TextView id, txtTitle, txtCreator;
        ImageView img;
        RatingBar rating;
        Chip category;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.itemView = itemView;
            id = (TextView) itemView.findViewById(R.id.routineId);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtCreator = (TextView) itemView.findViewById(R.id.creator);
            img = (ImageView) itemView.findViewById(R.id.image);
            rating = (RatingBar) itemView.findViewById(R.id.routineRatingBar);
            category = (Chip) itemView.findViewById(R.id.categoryChip);
        }
    }

    public interface ItemClickListener {
        public void onItemClick(RoutineCredentials routineCredentials);
    }

}
