package ar.edu.itba.example.gymateapp.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.view.fragments.HomeFragment;
import ar.edu.itba.example.gymateapp.view.fragments.MyRoutinesFragment;
import ar.edu.itba.example.gymateapp.view.fragments.ProfileFragment;
import ar.edu.itba.example.gymateapp.view.fragments.RoutinesFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    Fragment currentFragment = null;
    FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.navigation_home:
                    currentFragment = new HomeFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content,currentFragment);
                    ft.commit();
                    return true;
                case R.id.navigation_routines:
                    currentFragment = new RoutinesFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content,currentFragment);
                    ft.commit();
                    return true;
                case R.id.navigation_my_routines:
                    currentFragment = new MyRoutinesFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content,currentFragment);
                    ft.commit();
                    return true;
                case R.id.navigation_profile:
                    currentFragment = new ProfileFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content,currentFragment);
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }


//    public void setUpBottomNavigation() {
//        bottomNavigationView = findViewById(R.id.nav_view);
//
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainNavFragment);
//        assert navHostFragment != null;
//        NavigationUI.setupWithNavController(bottomNavigationView,
//                navHostFragment.getNavController());
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.mainNavFragment);
//        return navController.navigateUp();
//    }




}

