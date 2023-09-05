package com.paulbrancieq.configupdatehandler.old.jsonAdapters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StringTypeAdapter extends CustomTypeAdapterFactory<String> {
    
    public StringTypeAdapter(Gson gson) {
        super(gson);
    }

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        out.value(value);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        return in.nextString();
    }
}
