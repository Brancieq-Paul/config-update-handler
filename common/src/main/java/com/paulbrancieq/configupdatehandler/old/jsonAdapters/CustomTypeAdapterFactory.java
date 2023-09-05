package com.paulbrancieq.configupdatehandler.old.jsonAdapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.Dictionary;
import java.util.List;

public abstract class CustomTypeAdapterFactory<T extends Object> extends TypeAdapter<T> {
    
    protected final Gson gson;

    public CustomTypeAdapterFactory(Gson gson) {
        this.gson = gson;
    }

    public static class Factory implements TypeAdapterFactory {

        @Override
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            Class<?> rawType = typeToken.getRawType();
            if (Number.class.isAssignableFrom(rawType)) {
                return (TypeAdapter<T>) new NumberTypeAdapter(gson);
            }
            else if (String.class.isAssignableFrom(rawType)) {
                return (TypeAdapter<T>) new StringTypeAdapter(gson);
            }
            else if (Boolean.class.isAssignableFrom(rawType)) {
                return (TypeAdapter<T>) new BooleanTypeAdapter(gson);
            }
            else if (List.class.isAssignableFrom(rawType)) {
                return (TypeAdapter<T>) new ListTypeAdapater(gson);
            }
            else if (Dictionary.class.isAssignableFrom(rawType)) {
                return (TypeAdapter<T>) new DictionaryAdapter(gson);
            }
            return null;
        }
    }
}
