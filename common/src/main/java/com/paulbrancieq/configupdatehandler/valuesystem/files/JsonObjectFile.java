package com.paulbrancieq.configupdatehandler.valuesystem.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigFile.InvalidFileFormat;
import com.paulbrancieq.configupdatehandler.valuesystem.values.ObjectFileValue;

public class JsonObjectFile extends ObjectFileValue implements IFile {

    public ComposedPath path;

    public JsonObjectFile(ComposedPath path) throws InvalidFileFormat {
        super();
        // Set path
        this.path = path;
        // Map
        Map<Object, Object> json_obj;
        // Create gson
        Gson gson = new GsonBuilder().create();
        // Open and read file
        try (BufferedReader br = new BufferedReader(new FileReader(path.getSourcePath()))) {
            // Get json object from file
            json_obj = MapToObjectMapUnchecked(gson.fromJson(br, Map.class));
            // Set value
        } catch (IOException e) {
            throw new InvalidFileFormat(path.getSourcePath(), e);
        }
        this.setValue(json_obj);
    }

    @Override
    public ComposedPath getPath() {
        return this.path;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Map<Object, Object> MapToObjectMapUnchecked(Map map) {
        return map;
    }

    @Override
    public void _write() {
        // Create gson
        Gson gson = new GsonBuilder().create();
        // Open and write file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.path.getResultPath()))) {
            // Write json object to file
            gson.toJson(this.getValue(), Map.class, bw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
