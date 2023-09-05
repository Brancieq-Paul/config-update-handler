package com.paulbrancieq.configupdatehandler.old.jsonAdapters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListTypeAdapater extends CustomTypeAdapterFactory<List<Object>> {
    public ListTypeAdapater(Gson gson) {
            super(gson);
        }

    @Override
    public void write(JsonWriter out, List<Object> value) throws IOException {
        out.beginArray();
        for (Object object : value) {
            gson.toJson(object, object.getClass(), out);
        }
        out.endArray();
    }

    @Override
    public List<Object> read(JsonReader in) throws IOException {
        List<Object> list = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            list.add(gson.fromJson(in, Object.class));
        }
        in.endArray();
        return list;
    }
}
