package ar.edu.itba.example.gymateapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutineHistory {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("date")
    private Long date;

    @Expose
    @SerializedName("duration")
    private Integer duration;

    @Expose
    @SerializedName("wasModified")
    private boolean wasModified;

    @Expose
    @SerializedName("routine")
    private RoutineCredentials routine;

    public RoutineHistory(Integer id, Long date, Integer duration, boolean wasModified, RoutineCredentials routine) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.wasModified = wasModified;
        this.routine = routine;
    }

    public Integer getId() {
        return id;
    }

    public Long getDate() {
        return date;
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean isWasModified() {
        return wasModified;
    }

    public RoutineCredentials getRoutine() {
        return routine;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setWasModified(boolean wasModified) {
        this.wasModified = wasModified;
    }

    public void setRoutine(RoutineCredentials routine) {
        this.routine = routine;
    }
}
