package ar.edu.itba.example.gymateapp.view.classes;

public class RoutineData {

    public String title;
    public String creator;
    public int imgId;
    public int rating;
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
