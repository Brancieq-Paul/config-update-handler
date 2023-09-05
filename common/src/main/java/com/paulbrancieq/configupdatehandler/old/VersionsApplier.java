package com.paulbrancieq.configupdatehandler.old;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.paulbrancieq.configupdatehandler.MCFileSystem.MCFileSystem;
import com.paulbrancieq.configupdatehandler.MCFileSystem.MCFileSystemApplier;

public class VersionsApplier {

    // mainMCFileSystem
    private MCFileSystem mainMCFileSystem;
    // baseMCFileSystem
    private MCFileSystem baseMCFileSystem;

    // Actual version name
    private String actual_version_name;
    // Versions list
    private JsonArray versions_list;
    // Versions names
    private ArrayList<String> versions_names;
    // Versions to apply
    private ArrayList<String> versions_to_apply;


    public VersionsApplier(MCFileSystem mainMCFileSystem, MCFileSystem baseMCFileSystem, String actual_version_name, JsonArray versions_list) {
        // set mainMCFileSystem
        this.mainMCFileSystem = mainMCFileSystem;
        // set baseMCFileSystem
        this.baseMCFileSystem = baseMCFileSystem;
        // Set actual_version_name
        this.actual_version_name = actual_version_name;
        // Set versions_list
        this.versions_list = versions_list;
        // Validate versions list
        validateVersionsList();
        // Set versions to apply
        setVersionsToApply();
    }

    public void apply() {
        // For each version to apply
        for (String version_name : versions_to_apply) {
            // Get version update file path
            String version_update_file_path = Constants.CONFIGHUPDATEANDLER_VERSIONS_UPDATES_DIRECTORY_R_PATH + Constants.PATH_SEPARATOR + version_name + ".json";
            // Create MCFileSystemApplier
            MCFileSystemApplier mcfs_applier = new MCFileSystemApplier(version_update_file_path, mainMCFileSystem, baseMCFileSystem);
            // Apply MCFileSystemApplier
            mcfs_applier.apply();
        }
    }

    private void setVersionsToApply() {
        Boolean actual_version_found = false;
        versions_to_apply = new ArrayList<String>();
        for (String potential_actual_version_name : this.versions_names) {
            if (potential_actual_version_name.equals(this.actual_version_name)) {
                actual_version_found = true;
            }
            if (actual_version_found) {
                versions_to_apply.add(potential_actual_version_name);
            }
        }
        if (!actual_version_found) {
            versions_to_apply = this.versions_names;
        }
    }

    private void validateVersionsList() {
        // Verify that each version is a JsonObject containing a name field with a string value
        this.versions_names = new ArrayList<String>();
        for (Object version : versions_list) {
            if (!(version instanceof JsonObject)) {
                Constants.LOG.error("Invalid version in versions list: " + version);
                throw new ConfigHandlerExeptions.InvalidConfigEntryUpdate("Invalid version in versions list: " + version + " (JsonObject with a name field expected)");
            }
            if (!((JsonObject) version).has(Constants.CONFIGHUPDATEANDLER_CONFIG_VERSION_NAME_FIELD_NAME)) {
                Constants.LOG.error("Invalid version in versions list: " + version);
                throw new ConfigHandlerExeptions.InvalidCUHConfig("Invalid version in versions list: " + version + " (JsonObject with a name field expected)");
            }
            if (!((JsonObject) version).get(Constants.CONFIGHUPDATEANDLER_CONFIG_VERSION_NAME_FIELD_NAME).getAsJsonPrimitive().isString()) {
                Constants.LOG.error("Invalid version in versions list: " + version);
                throw new ConfigHandlerExeptions.InvalidCUHConfig("Invalid version in versions list: " + version + " (JsonObject with a name field expected)");
            }
            if (this.versions_names.contains(((JsonObject) version).get(Constants.CONFIGHUPDATEANDLER_CONFIG_VERSION_NAME_FIELD_NAME).getAsString())) {
                Constants.LOG.error("Invalid version in versions list: " + version);
                throw new ConfigHandlerExeptions.InvalidCUHConfig("Invalid version in versions list: " + version + " (Two versions with the same name found)");
            }
            this.versions_names.add(((JsonObject) version).get(Constants.CONFIGHUPDATEANDLER_CONFIG_VERSION_NAME_FIELD_NAME).getAsString());
        }
    }
}
