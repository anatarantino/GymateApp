package ar.edu.itba.example.gymateapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.example.gymateapp.R;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_login);
//        loginBtn = findViewById(R.id.login);
//        loginBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

}