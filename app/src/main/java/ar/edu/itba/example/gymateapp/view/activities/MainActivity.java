package ar.edu.itba.example.gymateapp.view.activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.viewModel.UserViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.mainNavFragment);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNav,navHostFragment.getNavController());

        setSupportActionBar(findViewById(R.id.appbar));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setUserData();

        NavController navController = navHostFragment.getNavController();
        Uri uri = getIntent().getData();
        if(uri != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("routineId",Integer.parseInt(uri.getLastPathSegment()));
            navController.navigate(R.id.action_navigation_home_to_routineDetailFragment,bundle);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,R.id.mainNavFragment);
        return navController.navigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        getMenuInflater().inflate(R.menu.appbar,menu);
        return true;
    }

    public void setNavigationVisibility(boolean b){
        if(b){
            bottomNav.setVisibility(View.VISIBLE);
        }else{
            bottomNav.setVisibility(View.GONE);
        }
    }

    public void showUpButton(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideUpButton(){
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}

