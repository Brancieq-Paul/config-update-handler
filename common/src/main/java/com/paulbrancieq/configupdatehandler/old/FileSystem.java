package com.paulbrancieq.configupdatehandler.old;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.paulbrancieq.configupdatehandler.old.jsonAdapters.CustomTypeAdapterFactory;

public class FileSystem {

    // The GenericConfigEntry class is used to represent a config file of any type
    public abstract class GenericConfigFile<T extends GenericConfigFile<?>> {
        private final String path;
        private final Class<T> file_type;

        // Constructor
        // The path made canonical then stored in the path attribute
        public GenericConfigFile(String path, Class<T> file_type) {
            try {
                this.path = new File(path).getCanonicalPath();
            } catch (IOException e) {
                Constants.LOG.error("Failed to make path canonical: " + path, e);
                throw new RuntimeException(e);
            }
            this.file_type = file_type;
        }

        // Getter for the path attribute
        public String getPath() {
            return path;
        }

        // Getter for the file_type attribute
        public Class<T> getFileType() {
            return file_type;
        }

        // Abstract method used to read the config file
        public abstract void updateFromFile();

        // Abstract method used to write the edited config file
        public abstract void updateFile();

        // Get entry value
        // This method is used to get the value of an entry
        // It take an entry path string as parameter
        // It return the value of the entry
        public abstract Object getEntryValue(ConfigEntryUpdate.EntryPath entry_path_obj);

        // Apply entry value
        // This method is used to apply a value to an entry
        // It take an entry path string and a value (Object) as parameters
        public abstract void applyEntryValue(ConfigEntryUpdate.EntryPath entry_path_obj, Object value);

        // Get list of entry ref
    }

    // JsonConfigFile class
    // This class is a subclass of GenericConfigFile and is used to represent a json
    public class JsonConfigFile extends GenericConfigFile<JsonConfigFile> {

        // Dictionary of entries
        private Map<Object, Object> entries;

        // Constructor
        public JsonConfigFile(String path) throws ConfigHandlerExeptions.IncompatibleFileFormat {
            super(path, JsonConfigFile.class);
            this.updateFromFile();
        }

        // Read static method
        // Read will try to read the config file and return a JsonConfigFile object
        // If failed, it will log on the info level and return null
        @Override
        @SuppressWarnings("unchecked")
        public void updateFromFile() throws ConfigHandlerExeptions.IncompatibleFileFormat {
            try {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(new CustomTypeAdapterFactory.Factory()).create();
                // Read file content and use toJson method
                try (BufferedReader br = new BufferedReader(new FileReader(this.getPath()))) {
                    JsonObject json = gson.fromJson(br, JsonObject.class);
                    this.entries = gson.fromJson(json, Map.class);
                }
            } catch (IOException e) {
                Constants.LOG.info("Failed to read config file", e);
                // Throw a IncompatibleFileFormat exception
                throw new ConfigHandlerExeptions.IncompatibleFileFormat("Failed to read config file");
            }
        }

        // write method
        public void updateFile() {
            try {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(new CustomTypeAdapterFactory.Factory()).create();
                // Write file content and use toJson method
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.getPath()))) {
                    bw.write(gson.toJson(this.entries));
                }
            } catch (IOException e) {
                Constants.LOG.error("Failed to write config file", e);
            }
        }

        // getEntryValue method
        // This method is used to get the value of an entry
        // It take an entry path string as parameter
        // It return the value of the entry
        @Override
        @SuppressWarnings("unchecked")
        public Object getEntryValue(ConfigEntryUpdate.EntryPath entry_path_obj) throws ConfigHandlerExeptions.InvalidConfigEntryUpdate {
            Object current_value = this.entries;
            Map<Object, Object> current_map = null;
            String current_entry_name = null;
            // Debug log
            Constants.LOG.debug("Getting entry value for path " + entry_path_obj.getPath() + " in file " + this.getPath());
            while ((current_entry_name = entry_path_obj.getNextEntryName()) != null) {
                if (!(current_value instanceof Map)) {
                    throw new ConfigHandlerExeptions.InvalidConfigEntryUpdate("Failed to get entry value, value does not exist");
                }
                current_map = (Map<Object, Object>) current_value;
                // Verify that the current map contains the entry name, if not, throw
                if (!current_map.containsKey(current_entry_name)) {
                    throw new ConfigHandlerExeptions.InvalidConfigEntryUpdate("Failed to get entry value, entry does not exist");
                }
                // Value in the current map
                current_value = current_map.get(current_entry_name);
            }
            // Return the value associated with the entry path
            return current_value;
        }

        // applyEntryValue method
        // This method is used to apply a value to an entry
        // It take an entry path string and a value (Object) as parameters
        @Override
        @SuppressWarnings("unchecked")
        public void applyEntryValue(ConfigEntryUpdate.EntryPath entry_path_obj, Object value) {
            Object current_value = this.entries;
            Map<Object, Object> current_map = null;
            String current_entry_name = null;
            // Debug log
            Constants.LOG.debug("Applying entry value for path " + entry_path_obj.getPath() + " in file " + this.getPath());
            while ((current_entry_name = entry_path_obj.getNextEntryName()) != null) {
                if (!(current_value instanceof Map)) {
                    throw new ConfigHandlerExeptions.InvalidConfigEntryUpdate("Failed to set entry value, path to value differs from path to existing value");
                }
                current_map = (Map<Object, Object>) current_value;
                // Verify that the current map contains the entry name, else, create sub map (will be replaced by the value if it's the last entry name)
                if (!current_map.containsKey(current_entry_name)) {
                    current_map.put(current_entry_name, new HashMap<Object, Object>());
                }
                // Value in the current map
                current_value = current_map.get(current_entry_name);
            }
            // Set the value associated with the entry path to the value parameter
            if (current_map != null) {
                current_map.put(current_entry_name, value);
            } else {
                Constants.LOG.error("Failed to apply entry value, current map is null");
                throw new RuntimeException("Failed to apply entry value, current map is null");
            }
        }
    }

    public static GenericConfigFile<?> createConfigFile(String path) {
        // List of supported file types (all subclasses of GenericConfigFile) in the
        // correct order of priority
        Class<?>[] supported_file_types = { JsonConfigFile.class };
        // Log debug message
        Constants.LOG.debug("Creating config file for path " + path);
        // For each supported file type
        for (Class<?> file_type : supported_file_types) {
            // Try to create a new instance of the file type
            try {
                // Log debug message
                Constants.LOG.debug("Trying to create config file of type " + file_type.getName());
                // Return the new instance if no exception was thrown
                return (GenericConfigFile<?>) file_type.getConstructor(FileSystem.class, String.class).newInstance(new FileSystem(), path);
            } catch (Exception e) {
                Constants.LOG.debug("Failed to create config file of type " + file_type.getName(), e);
            }
        }
        // If no supported file type was found, log error and return null
        Constants.LOG.debug("Failed to create config file, no supported file type was found");
        return null;
    }
}