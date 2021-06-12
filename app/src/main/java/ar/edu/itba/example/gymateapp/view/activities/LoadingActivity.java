package ar.edu.itba.example.gymateapp.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDeepLinkBuilder;

import java.util.concurrent.Executor;

import ar.edu.itba.example.gymateapp.R;
import ar.edu.itba.example.gymateapp.model.AppPreferences;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        AppPreferences preferences = new AppPreferences(this.getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        },2000);
        new Handler().postDelayed(()-> {
            Intent appLinkIntent = getIntent();
            Uri appLinkData = appLinkIntent.getData();
            if(appLinkData != null) { //inicio desde un link
                String routineId = appLinkData.getLastPathSegment();
                newActivity(preferences,routineId);
            }else{
                Intent intent;
                if(preferences.getAuthToken() != null){
                    intent = new Intent(LoadingActivity.this, MainActivity.class);
                }else {
                    intent = new Intent(LoadingActivity.this, LoginActivity.class);
                }
                startActivity(intent);
            }
        },2000);
    }

    private void newActivity(AppPreferences preferences, String routineId){
        Bundle bundle = new Bundle();
        bundle.putInt("routineId",Integer.parseInt(routineId));
        if(preferences.getAuthToken() != null){
            new NavDeepLinkBuilder(this).setComponentName(MainActivity.class)
                    .setGraph(R.navigation.mobile_navigation).setDestination(R.id.routine_detail)
                    .setArguments(bundle)
                    .createTaskStackBuilder()
                    .startActivities();
            return;
        }
        Intent auxIntent = new Intent(LoadingActivity.this, LoginActivity.class);
        putAndStart(routineId, auxIntent);
    }

    private void putAndStart(String routineId, Intent intent){
        intent.putExtra("RoutineId",routineId);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
