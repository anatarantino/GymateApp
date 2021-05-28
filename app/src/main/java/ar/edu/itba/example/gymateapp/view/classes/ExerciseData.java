package ar.edu.itba.example.gymateapp.view.classes;

public class ExerciseData {
    private String exName;
    private String exDuration;

    public ExerciseData(String exName, String exDuration) {
        this.exName = exName;
        this.exDuration = exDuration;
    }

    public String getExName() {
        return exName;
    }

    public String getExDuration() {
        return exDuration;
    }
}
