package ar.edu.itba.example.gymateapp.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.FragmentDetailBinding;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;
import ar.edu.itba.example.gymateapp.view.viewModel.RoutinesViewModel;


public class RoutineDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "desc";
    private static final String ARG_PARAM3 = "creator";
    private static final String ARG_PARAM4 = "img";
    private static final String ARG_PARAM5 = "id";

    private String titleParam,creatorParam,descParam,imgParam,id;
    private RoutinesViewModel routinesViewModel;
    private RoutineData routineData;
    private TextView title;
    private int routineId;
    private FragmentDetailBinding binding;
    private View view;

    private MenuItem fav;
    private MenuItem unfav;

    private MainActivity main;

    public RoutineDetailFragment() {
    }

    public static RoutineDetailFragment newInstance(RoutineData routineData) {
        RoutineDetailFragment fragment = new RoutineDetailFragment();
        Bundle bundle = new Bundle();
//        bundle.putSerializable("routineId",routineData);
        bundle.putString(ARG_PARAM1,routineData.title);
        bundle.putString(ARG_PARAM2,routineData.desc);
        bundle.putString(ARG_PARAM3,routineData.creator);
        bundle.putString(ARG_PARAM4,routineData.img);
        bundle.putString(ARG_PARAM5, String.valueOf(routineData.id));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            titleParam = getArguments().getString(ARG_PARAM1);
            descParam = getArguments().getString(ARG_PARAM2);
            creatorParam = getArguments().getString(ARG_PARAM3);
            imgParam = getArguments().getString(ARG_PARAM4);
            id = getArguments().getString(ARG_PARAM5);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(getLayoutInflater());
        view = binding.getRoot();

        TextView title = view.findViewById(R.id.title);
        title.setText(titleParam);

        TextView creator = view.findViewById(R.id.creator);
        creator.setText(creatorParam);

        TextView desc = view.findViewById(R.id.detail_read_all);
        desc.setText(descParam);

        ImageView img = view.findViewById(R.id.imageView);

        byte[] encodeByte = Base64.decode(imgParam,Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
        img.setImageBitmap(bm);

        Button listBtn = view.findViewById(R.id.listBtn);
        listBtn.setOnClickListener(v -> {
//            Fragment fragment_routinerun_as_list = new RoutineExecutionAsListFragment();
//            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//
//            transaction.replace(R.id.routine_detail, fragment_routinerun_as_list);
//            transaction.addToBackStack(null);
//            transaction.commit();

            Fragment fragment_routinerun_as_list = new RoutineExecutionAsListFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.routine_detail, fragment_routinerun_as_list);
            transaction.addToBackStack(null);
            transaction.commit();


        });

        Button detailBtn = view.findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(v -> {
            Fragment fragment_routinerun = new RoutineExecutionFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.routine_detail, fragment_routinerun);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        main = (MainActivity) getActivity();
        main.showUpButton();
        main.setNavigationVisibility(false);
        return view;
    }


//    @Override
//    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if(getArguments() != null) {
//            routineId = getArguments().getInt("routineId");
//        }
//
//    }


    @Override
    public void onDestroyView() {
        main.hideUpButton();
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.app_bar_share).setVisible(true);
        menu.findItem(R.id.app_bar_rate).setVisible(true);
        //cambiar a true despues
        menu.findItem(R.id.app_bar_favorite_filled).setVisible(false);
//        menu.findItem(R.id.app_bar_favorite_filled).setVisible(true);
        menu.findItem(R.id.app_bar_favorite_outlined).setVisible(true);

        fav = menu.findItem(R.id.app_bar_favorite_filled);
        unfav = menu.findItem(R.id.app_bar_favorite_outlined);

        //arrancan los dos corazones en true y despues te fijas en la api si esta true o false
        //mandar data a la api

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.app_bar_favorite_filled){
            //llamar api
            unfavRoutine();
        }else if(id == R.id.app_bar_favorite_outlined){
            //llamar api
            favRoutine();
        }else if(id == R.id.app_bar_rate){
            openRateDialog();
        }else if(id == R.id.app_bar_share){
            share();
        }else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void favRoutine(){
        fav.setVisible(true);
        unfav.setVisible(false);
    }

    public void unfavRoutine(){
        fav.setVisible(false);
        unfav.setVisible(true);
    }

    public void openRateDialog(){
        RateDialog rateDialog = new RateDialog(routineId, getActivity());
        rateDialog.show(getParentFragmentManager(),"Example dialog");
    }

    public void share(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, routineData.title);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Titulo prueba");
        sharingIntent.putExtra("RoutineId", routineId);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.subject) + ": http://www.gymate.com/Routines/" + routineId);
        startActivity(Intent.createChooser(sharingIntent, "Share Rutine"));
    }
}

