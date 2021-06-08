package ar.edu.itba.example.gymateapp.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.databinding.ActivityMainBinding;
import ar.edu.itba.example.gymateapp.view.fragments.HomeFragment;
import ar.edu.itba.example.gymateapp.view.fragments.MyRoutinesFragment;
import ar.edu.itba.example.gymateapp.view.fragments.ProfileFragment;
import ar.edu.itba.example.gymateapp.view.fragments.RoutinesFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

//    Fragment currentFragment = null;
//    FragmentTransaction ft;

//    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch(item.getItemId()){
//                case R.id.navigation_home:
//                    currentFragment = new HomeFragment();
//                    ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content,currentFragment);
//                    ft.commit();
//                    return true;
//                case R.id.navigation_routines:
//                    currentFragment = new RoutinesFragment();
//                    ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content,currentFragment);
//                    ft.commit();
//                    return true;
//                case R.id.navigation_my_routines:
//                    currentFragment = new MyRoutinesFragment();
//                    ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content,currentFragment);
//                    ft.commit();
//                    return true;
//                case R.id.navigation_profile:
//                    currentFragment = new ProfileFragment();
//                    ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content,currentFragment);
//                    ft.commit();
//                    return true;
//            }
//            return false;
//        }
//    };

    private BottomNavigationView bottomNav;

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

