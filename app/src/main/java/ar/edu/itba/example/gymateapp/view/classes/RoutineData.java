package ar.edu.itba.example.gymateapp.view.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoutineData implements Serializable {

    @Expose
    @SerializedName("id2")
    public Integer id;
    @Expose
    @SerializedName("title2")
    public String title;
    @Expose
    @SerializedName("creator2")
    public String creator;
    @Expose
    @SerializedName("desc2")
    public String desc;
    @Expose
    @SerializedName("img2")
    public String img;
    @Expose
    @SerializedName("rating2")
    public Integer rating;
    @Expose
    @SerializedName("cat2")
    public Category cat;
    //meter toda la data necesaria

    public RoutineData(Integer id, String title, String creator, String desc, Integer rating,Category cat) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.rating = rating;
        this.cat = cat;
        this.desc = desc;
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
