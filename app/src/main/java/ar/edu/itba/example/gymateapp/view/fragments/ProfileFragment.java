package ar.edu.itba.example.gymateapp.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.FragmentProfileBinding;
import ar.edu.itba.example.gymateapp.view.activities.MainActivity;
import ar.edu.itba.example.gymateapp.view.adapter.RoutinesAdapter;
import ar.edu.itba.example.gymateapp.view.classes.RoutineData;
import ar.edu.itba.example.gymateapp.viewModel.UserViewModel;

public class ProfileFragment extends Fragment implements RoutinesAdapter.ItemClickListener {

    private RecyclerView recyclerRoutine;
    private ArrayList<RoutineData> routineList;
    private View view;
    private FragmentProfileBinding binding;
    private TextView username;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        username = binding.username;

//        view = inflater.inflate(R.layout.fragment_profile,container,false);
//        routineList = new ArrayList<>();
//        recyclerRoutine = view.findViewById(R.id.userRecyclerView);
//        recyclerRoutine.setLayoutManager(new LinearLayoutManager(getContext()));
//        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
//        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.divider));
//        recyclerRoutine.addItemDecoration(itemDecorator);

//        seedRoutines(); //aca hay que solo cargar las favoritas
//        RoutinesAdapter adapter = new RoutinesAdapter(routineList,this);
//
//        recyclerRoutine.setAdapter(adapter);
        ((MainActivity) requireActivity()).setNavigationVisibility(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       //favRoutinesViewModel = new ViewModelProvider(getActivity()).get(FavouritesRoutinesViewModel.class);
       //favRoutinesViewModel.updateData();

        //favoriteAdapter = new FavoriteAdapter(new ArrayList<>(), new ViewModelProvider(getActivity()).get(RoutinesViewModel.class));

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        //favoriteCardsList.setLayoutManager(new LinearLayoutManager(getContext()));
        //favoriteCardsList.setAdapter(favoriteAdapter);

        seedProfile();
    }

    private void seedProfile() {
        userViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            if (userInfo != null) {
                binding.setUserInfo(userInfo);
//                if (!userInfo.getAvatarUrl().equals("")) {
//                    Glide.with(binding.getRoot()).load(userInfo.getAvatarUrl()).into(binding.profileImage);
//                }
            }
        });
    }

//    private void seedRoutines() {
//        RoutineData.Category c1 = new RoutineData.Category(1,"cat 1", "det cat 1");
//        RoutineData.Category c2 = new RoutineData.Category(2,"cat 2", "det cat 2");
//        routineList.add(new RoutineData(1,"Titulo 1", "creador 1","Esta es la desc de la rutina numero  1.", 1,c1));
//        routineList.add(new RoutineData(2,"Titulo 2", "creador 2","Esta es la desc de la rutina numero  2.", 2,c1));
//        routineList.add(new RoutineData(3,"Titulo 3", "creador 3","Esta es la desc de la rutina numero  3.", 3,c2));
//        routineList.add(new RoutineData(4,"Titulo 4", "creador 2","Esta es la desc de la rutina numero  4.", 4,c1));
//        routineList.add(new RoutineData(5,"Titulo 5", "creador 2","Esta es la desc de la rutina numero  5.", 5,c2));
//        routineList.add(new RoutineData(6,"Titulo 6", "creador 1","Esta es la desc de la rutina numero  6.", 4,c2));
//        routineList.add(new RoutineData(7,"Titulo 7", "creador 1","Esta es la desc de la rutina numero  7.", 3,c1));
//    }

    @Override
    public void onItemClick(RoutineData routineData) {
        ProfileFragmentDirections.ActionNavigationProfileToRoutineDetailFragment action = ProfileFragmentDirections.actionNavigationProfileToRoutineDetailFragment();
        action.setRoutineId(routineData.id);
        Navigation.findNavController(view).navigate(action);
    }
}
