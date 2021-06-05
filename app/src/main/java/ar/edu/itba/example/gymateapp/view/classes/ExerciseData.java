package ar.edu.itba.example.gymateapp.view.classes;

public class ExerciseData {
    private String exName;
    private Integer exDuration;

    public ExerciseData(String exName, Integer exDuration) {
        this.exName = exName;
        this.exDuration = exDuration;
    }

    public String getExName() {
        return exName;
    }

    public Integer getExDuration() {
        return exDuration;
    }
}
