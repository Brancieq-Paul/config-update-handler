package com.paulbrancieq.configupdatehandler.old.jsonAdapters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class BooleanTypeAdapter extends CustomTypeAdapterFactory<Boolean> {
    public BooleanTypeAdapter(Gson gson) {
        super(gson);
    }

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        out.value(value);
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        return in.nextBoolean();
    }
}
