package com.paulbrancieq.configupdatehandler.old.jsonAdapters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictionaryAdapter extends CustomTypeAdapterFactory<Map<String, Object>> {
    public DictionaryAdapter(Gson gson) {
        super(gson);
    }

    @Override
    public void write(JsonWriter out, Map<String, Object> value) throws IOException {
        out.beginObject();
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            out.name(entry.getKey());
            gson.toJson(entry.getValue(), entry.getValue().getClass(), out);
        }
        out.endObject();
    }

    @Override
    public Map<String, Object> read(JsonReader in) throws IOException {
        Map<String, Object> map = new HashMap<>();
        in.beginObject();
        while (in.hasNext()) {
            map.put(in.nextName(), gson.fromJson(in, Object.class));
        }
        in.endObject();
        return map;
    }
}
