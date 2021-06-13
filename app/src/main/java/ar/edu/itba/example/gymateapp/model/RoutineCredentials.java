package ar.edu.itba.example.gymateapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineCredentials {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("category")
    @Expose
    private Category category;
    @Expose
    @SerializedName("creator")
    private RoutineCreator author;
    @Expose
    @SerializedName("averageRating")
    private float averageRating;
    @SerializedName("metadata")
    @Expose
    private Object metadata;

    private String image;

    public RoutineCredentials(Integer id, String name, String detail, Boolean isPublic, String difficulty, Category category, RoutineCreator author, float averageRating, String image) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.category = category;
        this.author = author;
        this.averageRating = averageRating;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public RoutineCreator getAuthor() {
        return author;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public static class Category {
        private Integer id;
        private String name;
        private String detail;

        public Category(Integer id, String name, String detail) {
            this.id = id;
            this.name = name;
            this.detail = detail;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDetail() {
            return detail;
        }
    }

    public static class RoutineCreator {
        private String username;
        private String gender;
        private String avatarUrl;
        private String dateCreated;
        private Integer id;
        private String dateLastActive;


        public RoutineCreator(String username, String gender, String avatarUrl, String dateCreated, Integer id, String dateLastActive) {
            this.username = username;
            this.gender = gender;
            this.avatarUrl = avatarUrl;
            this.dateCreated = dateCreated;
            this.id = id;
            this.dateLastActive = dateLastActive;
        }

        public String getUsername() {
            return username;
        }

        public String getGender() {
            return gender;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public Integer getid() {
            return id;
        }

        public String getDateLastActive() {
            return dateLastActive;
        }
    }
}
