package com.paulbrancieq.configupdatehandler.old.jsonAdapters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class NumberTypeAdapter extends CustomTypeAdapterFactory<Number> {

    public NumberTypeAdapter(Gson gson) {
        super(gson);
    }

    @Override
    public void write(JsonWriter out, Number value) throws IOException {
        if (value instanceof Integer) {
            out.value((Integer) value);
        } else if (value instanceof Double) {
            out.value((Double) value);
        } else if (value instanceof Long) {
            out.value((Long) value);
        } else {
            out.value(value);
        }
    }

    @Override
    public Number read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NUMBER) {
            double doubleValue = in.nextDouble();
            int intValue = (int) doubleValue;
            long longValue = (long) doubleValue;
            if (doubleValue == intValue) {
                return intValue;
            } else if (doubleValue == longValue) {
                return longValue;
            } else {
                return doubleValue;
            }
        }
        return null;
    }
}