package ar.edu.itba.example.gymateapp.view.classes;

import android.widget.ImageView;

public class RoutineData {
    private String title;
    private String creator;
    private int imgId;
    private int rating;
    //meter toda la data necesaria

    public RoutineData(String title, String creator, int imgId, int rating) {
        this.title = title;
        this.creator = creator;
        this.imgId = imgId;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }


    //hacer getters y setters
}
