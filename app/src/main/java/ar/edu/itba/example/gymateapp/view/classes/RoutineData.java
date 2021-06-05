package ar.edu.itba.example.gymateapp.view.classes;

public class RoutineData {

    public String id;
    public String title;
    public String creator;
    public String img;
    public Integer rating;
    public Category cat;
    //meter toda la data necesaria

    public RoutineData(String id, String title, String creator, Integer rating,Category cat) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.rating = rating;
        this.cat = cat;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static class Category{
        public Integer id;
        public String name,detail;

        public Category(Integer id, String name, String detail) {
            this.id = id;
            this.name = name;
            this.detail = detail;
        }
    }


    //hacer getters y setters
}
