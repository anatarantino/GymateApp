package ar.edu.itba.example.gymateapp.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class ApiDateTypeConverter extends TypeAdapter<Date> {


    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        if (date == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(date.getTime());
        }
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        if (jsonReader != null) {
            return new Date(jsonReader.nextLong());
        } else {
            return null;
        }
    }
}
