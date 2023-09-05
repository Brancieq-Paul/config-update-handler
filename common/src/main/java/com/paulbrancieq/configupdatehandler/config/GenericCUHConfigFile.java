package com.paulbrancieq.configupdatehandler.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class GenericCUHConfigFile {

    private String path;
    private Map<String, Object> config;
    private Map<String, Object> generatedFileContent;

    // Constructor: only create the object with the path
    public GenericCUHConfigFile(String path) throws FileNotFoundException, IOException {
        this.path = path;
    }

    // Read config file: read the config file and store it in the config variable
    @SuppressWarnings("unchecked")
    protected void readConfigFile() throws FileNotFoundException, IOException {
        // Read config file
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            Gson gson = new GsonBuilder().create();
            this.config = gson.fromJson(br, Map.class);
        }
    }

    // Generate default config file
    public void generateDefaultConfigFile() {
        // Create gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Open and write file
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(this.path))) {
            // Write json object to file
            gson.toJson(this.generatedFileContent, Map.class, bw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Get config
    protected Map<String, Object> getConfig() {
        return this.config;
    }

    // Get config value
    protected Object getConfigValue(String key) {
        return this.config.get(key);
    }

    // File exists
    public boolean exists() {
        return (new File(path)).exists();
    }

    // Set default config
    protected void setGeneratedFileContent(Map<String, Object> defaultConfig) {
        this.generatedFileContent = defaultConfig;
    }

    // Get default config
    protected Map<String, Object> getGeneratedFileContent() {
        return this.generatedFileContent;
    }
}
