package ar.edu.itba.example.gymateapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExerciseCredentials {
    @SerializedName("exercise")
    @Expose
    private Exercise exercise;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("duration")
    @Expose
    private Integer duration;

    private boolean isRunning;

    public ExerciseCredentials(Exercise exercise, String detail, String type, Integer duration) {
        this.exercise = exercise;
        this.detail = detail;
        this.type = type;
        this.duration = duration;
        isRunning = false;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setRunning(boolean state) {
        isRunning = state;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public class Exercise {
        private Integer id;
        private String name;
        private String detail;

        public Exercise(Integer id, String name, String detail) {
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

        public void setId(Integer id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
