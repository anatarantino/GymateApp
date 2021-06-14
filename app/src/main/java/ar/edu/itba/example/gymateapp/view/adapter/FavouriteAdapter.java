package ar.edu.itba.example.gymateapp.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.RoutineCardBinding;
import ar.edu.itba.example.gymateapp.model.RoutineCredentials;
import ar.edu.itba.example.gymateapp.view.fragments.ClickOnRoutine;
import ar.edu.itba.example.gymateapp.viewModel.RoutinesViewModel;

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
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        RoutineCardBinding view = DataBindingUtil.inflate(inflater, R.layout.routine_card, parent, false);
//        return new FavouriteAdapter.FavouriteViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card,null,false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavouriteViewHolder holder, int position) {
        RoutineCredentials routine = favouriteList.get(position);

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

        holder.id.setText(String.valueOf(favouriteList.get(position).getId()));
        holder.txtTitle.setText(favouriteList.get(position).getName());
        holder.txtCreator.setText(favouriteList.get(position).getAuthor().getUsername());
        holder.rating.setRating(favouriteList.get(position).getAverageRating());

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
        TextView id,txtTitle,txtCreator;
        ImageView img;
        RatingBar rating;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.itemView = itemView;
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