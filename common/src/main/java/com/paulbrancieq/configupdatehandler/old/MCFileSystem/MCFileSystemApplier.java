package com.paulbrancieq.configupdatehandler.MCFileSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.paulbrancieq.configupdatehandler.old.ConfigEntryUpdate;
import com.paulbrancieq.configupdatehandler.old.Constants;
import com.paulbrancieq.configupdatehandler.old.ConfigHandlerExeptions.InvalidConfigEntryUpdate;
import com.paulbrancieq.configupdatehandler.old.FileSystem.GenericConfigFile;

public class MCFileSystemApplier {
    // String entry update
    public List<ConfigEntryUpdate> entryUpdateList;
    // mainMCFileSystem
    public MCFileSystem mainMCFileSystem;
    // baseMCFileSystem
    public MCFileSystem baseMCFileSystem;

    // Constructor
    // Take a file path "version_update_file_path" as parameter
    public MCFileSystemApplier(String version_update_file_path, MCFileSystem mainMCFileSystem, MCFileSystem baseMCFileSystem) {
        // Set mainMCFileSystem
        this.mainMCFileSystem = mainMCFileSystem;
        // Set baseMCFileSystem
        this.baseMCFileSystem = baseMCFileSystem;
        // Create entryUpdateList from version_update_file_path
        createEntryUpdateList(version_update_file_path);
    }

    // create entryUpdateList from version_update_file_path
    private void createEntryUpdateList(String version_update_file_path) {
        // Create gson object
        Gson gson = new GsonBuilder().create();
        try (BufferedReader br = new BufferedReader(new FileReader(version_update_file_path))) {
            String entry_path = null;
            String file_path = null;
            // Get json object from file
            JsonObject json_obj = gson.fromJson(br, JsonObject.class);
            // Verify that update field is a list
            if (!(json_obj.get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FIELD_NAME) instanceof List)) {
                Constants.LOG.error("Invalid update field in version update file: " + json_obj.get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FIELD_NAME));
                throw new InvalidConfigEntryUpdate("Invalid update field in version update file: " + json_obj.get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FIELD_NAME) + " (List expected) in " + version_update_file_path);
            }
            // For each entry in the update list
            for (Object value : (List<?>) json_obj.get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FIELD_NAME)) {
                // Verify that the value is a JsonObject
                if (!(value instanceof JsonObject)) {
                    Constants.LOG.error("Invalid value in version update file: " + value);
                    throw new InvalidConfigEntryUpdate("Invalid value in version update file: " + value + " (JsonObject expected) in " + version_update_file_path);
                }
                // Get entry path if entry path exist, else do nothing
                if (((JsonObject) value).has(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_PATH_FIELD_NAME)) {
                    // Verify that the entry path is a string
                    if (!((JsonObject) value).get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_PATH_FIELD_NAME).getAsJsonPrimitive().isString()) {
                        Constants.LOG.error("Invalid entry path in version update file: " + value);
                        throw new InvalidConfigEntryUpdate("Invalid entry path in version update file: " + value + " (String expected) in " + version_update_file_path);
                    }
                    // Get entry path
                    entry_path = ((JsonObject) value).get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_PATH_FIELD_NAME).getAsString();
                }
                
                // Get file path if file path exist, else throw an exception
                if (((JsonObject) value).has(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FILE_PATH_FIELD_NAME)) {
                    // Verify that the file path is a string
                    if (!((JsonObject) value).get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FILE_PATH_FIELD_NAME).getAsJsonPrimitive().isString()) {
                        Constants.LOG.error("Invalid file path in version update file: " + value);
                        throw new InvalidConfigEntryUpdate("Invalid file path in version update file: " + value + " (String expected) in " + version_update_file_path);
                    }
                    // Get file path
                    file_path = ((JsonObject) value).get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FILE_PATH_FIELD_NAME).getAsString();
                } else {
                    Constants.LOG.error("File path not found in version update file: " + value);
                    throw new InvalidConfigEntryUpdate("File path not found in version update file: " + value + " (String expected) in " + version_update_file_path);
                }
                // Create a new ConfigEntryUpdate object with the key and the value
                ConfigEntryUpdate entry_update = new ConfigEntryUpdate((String) file_path, (String) entry_path, (JsonObject) gson.fromJson(br, Map.class).get(entry_path));
                // Add the ConfigEntryUpdate object to the entryUpdateList
                entryUpdateList.add(entry_update);
            }
            // Sort entry update list
            sortEntryUpdateList();
        } catch (Exception e) {
            Constants.LOG.error("Failed to read version update file", e);
            throw new RuntimeException(e);
        }
    }

    // Sort entry update list by file path length and entry path length
    private void sortEntryUpdateList() {
        Collections.sort(this.entryUpdateList, new Comparator<ConfigEntryUpdate>() {
            @Override
            public int compare(ConfigEntryUpdate entry_update_1, ConfigEntryUpdate entry_update_2) {
                // Compare file path length
                int file_path_length_comparison = entry_update_1.getFileFileSystemRelativePath().length() - entry_update_2.getFileFileSystemRelativePath().length();
                if (file_path_length_comparison != 0) {
                    return file_path_length_comparison;
                }
                // Compare entry path length
                return entry_update_1.getStringEntryPath().length() - entry_update_2.getStringEntryPath().length();
            }
        });
    }

    // Apply file entry update
    private void applyFile(Map<String, GenericConfigFile<?>> mainMCFileSystemFiles, Map<String, GenericConfigFile<?>> baseMCFileSystemFiles, ConfigEntryUpdate entry_update) {
        Boolean fileExistInMainMCFileSystem = mainMCFileSystemFiles.containsKey(entry_update.getFileFileSystemRelativePath());
    }

    // Apply entry update list
    public void apply() {
        // mainMCFileSystem files
        Map<String, GenericConfigFile<?>> mainMCFileSystemFiles = mainMCFileSystem.getFiles();
        // baseMCFileSystem files
        Map<String, GenericConfigFile<?>> baseMCFileSystemFiles = baseMCFileSystem.getFiles();
        // For each entry update
        for (ConfigEntryUpdate entry_update : this.entryUpdateList) {
            // Verify that the file exist in the base file system, else throw an exception
            if (!baseMCFileSystemFiles.containsKey(entry_update.getFileFileSystemRelativePath())) {
                Constants.LOG.error("File " + entry_update.getFileFileSystemRelativePath() + " not found in base file system");
                throw new InvalidConfigEntryUpdate("File " + entry_update.getFileFileSystemRelativePath() + " not found in base file system");
            }
            // Verify if the file is a directory, if it is, throw not yet supported exception
            if ((new File(baseMCFileSystemFiles.get(entry_update.getFileFileSystemRelativePath()).getPath())).isDirectory()) {
                Constants.LOG.error("Directory update not yet supported");
                throw new UnsupportedOperationException("Directory update not yet supported");
                // TODO: Integrate directory update replace, and default and merge using a sub MCFileSystem
            }
        }
    }
}
