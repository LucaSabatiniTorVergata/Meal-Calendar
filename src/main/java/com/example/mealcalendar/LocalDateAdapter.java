package com.example.mealcalendar;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.format(formatter));
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String str = in.nextString();
        if (str == null || str.isEmpty()) {
            return null;
        }
        return LocalDate.parse(str, formatter);
    }
}
