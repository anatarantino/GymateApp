package ar.edu.itba.example.gymateapp.model;

import android.app.Application;

public class App extends Application {

    private AppPreferences preferences;

    public AppPreferences getPreferences(){
        return preferences;
    }

    @Override
    public void onCreate() { //lo va a ejecutar android cuando se cree el contexto de mi aplicación
        super.onCreate();
        preferences=new AppPreferences(this);
    }
}
