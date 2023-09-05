package com.paulbrancieq.configupdatehandler.old;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ConfigEntryUpdate {
    // File path attribute
    private String file_path;
    // String entry path attribute
    private String entry_path;
    // Entry options attribute
    public EntryUpdateOptions entry_update_options;

    // EntryPath is a protected class used to represent a path to an entry
    public class EntryPath {
        // Path attribute
        private String path;

        // Path left attribute (used to store the remaining path)
        private String path_left;

        // Constructor
        public EntryPath(String path) {
            this.path = path;
            this.path_left = path;
        }

        // Getter for the path attribute
        public String getPath() {
            return path;
        }

        // getNextEntryName method
        public String getNextEntryName() {
            if (this.path_left == null) {
                return null;
            }
            String[] path_left_split = this.path_left.split(".");
            String entry_name = path_left_split[0].replaceAll("\\\\", "\\");
            while (path_left_split[0].endsWith("\\")) {
                entry_name += "." + path_left_split[1].replaceAll("\\\\", "\\");
                // Remove first element of the array
                String[] new_path_left_split = new String[path_left_split.length - 1];
                System.arraycopy(path_left_split, 1, new_path_left_split, 0, path_left_split.length - 1);
                // Update path_left_split
                path_left_split = new_path_left_split;
                // Replace first element of the array with the new entry name
                path_left_split[0] = entry_name;
            }
            // Set path_left to the remaining path (without the first element)
            if (path_left_split.length == 1) {
                this.path_left = null;
            }
            String[] new_path_left_split = new String[path_left_split.length - 1];
            System.arraycopy(path_left_split, 1, new_path_left_split, 0, path_left_split.length - 1);
            path_left_split = new_path_left_split;
            this.path_left = String.join(".", path_left_split);
            // Return the entry name
            return entry_name;
        }

        // reset method
        // Reset the path_left to the original path
        public void reset() {
            this.path_left = this.path;
        }
    }

    // Class entry options
    public class EntryUpdateOptions {
        // General options
        private Constants.EntryType expected_field_type = Constants.EntryType.ANY;
        private Constants.FileType expected_file_type = Constants.FileType.ANY;
        private Constants.UpdateRule update_rule = Constants.UpdateRule.OVERRIDE;
        // Merge options for objects (if the update rule is merge)
        private boolean replaceExistingEntries = true; // If all existing entries should be replaced by the new ones
        private String[] entriesToDeleteInObjects = null; // Entries that has to be deleted in the existing object
        // Merge options for lists (if the update rule is merge)
        // TODO: Add options for lists merge

        // Constructor
        public EntryUpdateOptions(JsonObject entry_update_config) {
            // General options
            if (entry_update_config.has(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_TYPE_FIELD_NAME)) {
                this.expected_field_type = Constants.EntryType
                        .valueOf(entry_update_config.get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_TYPE_FIELD_NAME).getAsString());
            }
            if (entry_update_config.has(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FILE_TYPE_FIELD_NAME)) {
                this.expected_file_type = Constants.FileType
                        .valueOf(entry_update_config.get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_FILE_TYPE_FIELD_NAME).getAsString());
            }
            if (entry_update_config.has(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_UPDATE_RULE_FIELD_NAME)) {
                this.update_rule = Constants.UpdateRule.valueOf(entry_update_config.get(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_UPDATE_RULE_FIELD_NAME).getAsString());
            }
            // Merge options for objects (if the update rule is merge)
            if (entry_update_config.has("replaceExistingEntries")) {
                this.replaceExistingEntries = entry_update_config.get("replaceExistingEntries").getAsBoolean();
            }
            if (entry_update_config.has("entriesToDeleteInObjects")) {
                JsonArray tempArray = entry_update_config.get("entriesToDeleteInObjects").getAsJsonArray();
                this.entriesToDeleteInObjects = new String[tempArray.size()];
                for (int i = 0; i < tempArray.size(); i++) {
                    this.entriesToDeleteInObjects[i] = tempArray.get(i).getAsString();
                }
            }
        }

        // Getter for the expected_field_type attribute
        public Constants.EntryType getExpectedEntryType() {
            return this.expected_field_type;
        }

        // Getter for the expected_file_type attribute
        public Constants.FileType getExpectedFileType() {
            return this.expected_file_type;
        }

        // Getter for the update_rule attribute
        public Constants.UpdateRule getUpdateRule() {
            return this.update_rule;
        }

        // Getter for the replaceExistingEntries attribute
        public boolean getReplaceExistingEntries() {
            return this.replaceExistingEntries;
        }

        // Getter for the entriesToDeleteInObjects attribute
        public String[] getEntriesToDeleteInObjects() {
            return this.entriesToDeleteInObjects;
        }
    }

    // Constructor
    public ConfigEntryUpdate(String file_path, String entry_path, JsonObject entry_update_config) {
        // Get entry path
        this.entry_path = entry_path;
        // Get entry options
        entry_update_options = new EntryUpdateOptions(entry_update_config);
    }

    // Get new entry path
    public EntryPath getNewEntryPath() {
        return new EntryPath(this.entry_path);
    }

    // Get String entry path
    public String getStringEntryPath() {
        return this.entry_path;
    }

    // Get file path
    public String getFileFileSystemRelativePath() {
        return this.file_path;
    }

    // Get jsonObject
    public JsonObject getAJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_TYPE_FIELD_NAME, this.entry_update_options.getExpectedEntryType().name());
        jsonObject.addProperty(Constants.CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_UPDATE_RULE_FIELD_NAME, this.entry_update_options.getUpdateRule().name());
        jsonObject.addProperty("replaceExistingEntries", this.entry_update_options.getReplaceExistingEntries());
        // TODO: Finir la méthode   
        return jsonObject;
    }
}
