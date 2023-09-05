package com.paulbrancieq.configupdatehandler.update.conditions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.paulbrancieq.configupdatehandler.Constants;
import com.paulbrancieq.configupdatehandler.Services;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigFile.BaseFileDoesNotExist;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.IncoherentValueUpdate;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.NoRecursivePossible;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.MissingExpectedType;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.UpdateFileExeption;
import com.paulbrancieq.configupdatehandler.update.conditions.generics.ConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.BasicReplaceableDefaultableEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.BasicRootEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.BooleanEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.DoubleEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.IntegerEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.JsonFileListEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.JsonFileObjectEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.ListEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.ObjectEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.groups.StringEntryConditionsGroup;
import com.paulbrancieq.configupdatehandler.valuesystem.consumers.paths.EntryPathConsumer;
import com.paulbrancieq.configupdatehandler.valuesystem.files.ComposedPath;
import com.paulbrancieq.configupdatehandler.valuesystem.files.IFile;
import com.paulbrancieq.configupdatehandler.valuesystem.files.FileFactory;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;

public class UpdateFile {

    public static Map<String, Class<? extends ConditionsGroup>> conditionsTypes = new HashMap<>() {
        {
            put(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.STRING_ENTRY_TYPE,
                    StringEntryConditionsGroup.class);
            put(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.INTEGER_ENTRY_TYPE,
                    IntegerEntryConditionsGroup.class);
            put(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.DOUBLE_ENTRY_TYPE,
                    DoubleEntryConditionsGroup.class);
            put(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.BOOLEAN_ENTRY_TYPE,
                    BooleanEntryConditionsGroup.class);
            put(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.OBJECT_ENTRY_TYPE,
                    ObjectEntryConditionsGroup.class);
            put(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.LIST_ENTRY_TYPE,
                    ListEntryConditionsGroup.class);
            put(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.JSON_FILE_OBJECT_ENTRY_TYPE,
                    JsonFileObjectEntryConditionsGroup.class);
            put(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.JSON_FILE_LIST_ENTRY_TYPE,
                    JsonFileListEntryConditionsGroup.class);
        }
    };

    List<ConditionsContainer> conditions;

    // Constructor taking a version name as parameter
    public UpdateFile(String version) throws UpdateFileExeption {
        // Get file name from version name and format from Constants
        String fileName = String.format(Constants.CUH.Paths.Names.Formats.VERSION_UPDATE_FILE_NAME_FORMAT, version);
        // Get path to file
        Path filePath = Constants.CUH.Paths.Absolutes.VERSIONS_UPDATES_DIRECTORY.resolve(fileName);
        // Generic list
        List<?> generic_list;
        // Open as a jsonArray
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toString()))) {
            Gson gson = new GsonBuilder().create();
            JsonArray jsonArray = new Gson().fromJson(br, JsonArray.class);
            generic_list = gson.fromJson(jsonArray, List.class);
        } catch (IOException e) {
            Constants.LOG.error("Failed to open file: " + filePath);
            throw new UpdateFileExeption("Failed to open file: " + filePath, e);
        }
        // Verify that each element of the list is a Map, and that each key of the map
        // is a String, then cast the list to a list of Map<String, Object>
        for (Object value : generic_list) {
            if (!(value instanceof Map)) {
                Constants.LOG.error(
                        "Invalid element in list of updates: " + value.toString() + " (Map expected) in " + filePath);
                throw new UpdateFileExeption(
                        "Invalid value in version update file: " + value.toString() + " (Map expected) in " + filePath);
            }
            for (Object key : ((Map<?, ?>) value).keySet()) {
                if (!(key instanceof String)) {
                    Constants.LOG.error("Invalid key in version update file: " + key.toString()
                            + " (String expected) in " + filePath);
                    throw new UpdateFileExeption("Invalid key in version update file: " + key.toString()
                            + " (String expected) in " + filePath);
                }
            }
        }
        // Cast the list to a list of Map<String, Object>
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) generic_list;
        // Iterate over the list
        try {
            for (Map<String, Object> map : list) {
                // Verify that the map has the expected type field
                if (!map.containsKey(Constants.CUH.UpdateFile.ConditionsFields.Name.EXPECTED_ENTRY_TYPE)) {
                    Constants.LOG.error("Missing expected entry type field in version update definition: "
                            + map.toString() + " in " + filePath);
                    throw new MissingExpectedType();
                }
                // Get the associated type
                Class<? extends ConditionsGroup> type = conditionsTypes
                        .get(map.get(Constants.CUH.UpdateFile.ConditionsFields.Name.EXPECTED_ENTRY_TYPE));
                // Get constructor taking the map as parameter
                java.lang.reflect.Constructor<? extends ConditionsGroup> constructor = type
                        .getConstructor(map.getClass());
                // Create a new ConditionsGroup object
                ConditionsGroup group = constructor.newInstance(map);
                // Create a new ConditionsContainer object
                ConditionsContainer container = ConditionsContainer.create(type, group);
                this.conditions.add(container);
            }
        } catch (Exception e) {
            throw new UpdateFileExeption("Invalid update file", e);
        }
    }

    // Apply update
    public void apply() throws BaseFileDoesNotExist, IncoherentValueUpdate {
        for (ConditionsContainer container : this.conditions) {
            // cats container.group to BasicReplaceableDefaultableEntryConditionsGroup
            if (!(container.group instanceof BasicReplaceableDefaultableEntryConditionsGroup)) {
                throw new RuntimeException("Update file error, report to developer");
            }
            BasicRootEntryConditionsGroup group = (BasicRootEntryConditionsGroup) container.group;
            // Get relative path
            String relative_file_path = group.getFilePath();
            // Get base path
            ComposedPath base_path = new ComposedPath(relative_file_path, Constants.CUH.Paths.Absolutes.BASE_DIRECTORY,
                    Constants.CUH.Paths.Absolutes.BASE_DIRECTORY);
            // Get source path
            ComposedPath source_path = new ComposedPath(relative_file_path, Services.PLATFORM.getGameDirectory(),
                    Services.PLATFORM.getGameDirectory());
            // Verify that base path exists
            if (!(new File(base_path.getSourcePath()).exists())) {
                throw new BaseFileDoesNotExist(base_path.getSourcePath());
            }
            // Verify that source path exists
            if (!(new File(source_path.getSourcePath()).exists())) {
                // If does not exist, must be created from base path
                // Copy base path to source path
                try {
                    FileUtils.copyFile(new File(base_path.getSourcePath()), new File(source_path.getResultPath()));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to copy base file to source file", e);
                }
            }
            // Both files exist, so the update should be applied
            IFile base_file;
            IFile source_file;
            try {
                // Get base file
                base_file = FileFactory.create(base_path);
                // Get source file
                source_file = FileFactory.create(source_path);
            } catch (Exception e) {
                // Log info skip
                Constants.LOG.info("Skipping update of file: " + relative_file_path);
                continue;
            }
            // All Ifile should be a Value
            if (!(base_file instanceof Value)) {
                throw new RuntimeException("Update file error, report to developer");
            }
            if (!(source_file instanceof Value)) {
                throw new RuntimeException("Update file error, report to developer");
            }
            // Unchecked casts (should be safe)
            Value<?> base_value = (Value<?>) base_file;
            Value<?> source_value = (Value<?>) source_file;
            // Get value aimed by entry path, if no entry path, the value is the file
            try {
                if (!group.getEntryPath().isEmpty()) {
                    base_value = base_value.getSubConfigValue(new EntryPathConsumer(group.getEntryPath()));
                    source_value = source_value.getSubConfigValue(new EntryPathConsumer(group.getEntryPath()));
                }
            } catch (Exception e) {
                throw new RuntimeException("Update file error, report to developer", e);
            }
            // Get entry update rule
            if (group.updateRuleIsReplace()) {
                // Replace entry
                try {
                    source_value.replaceSubConfigValues(source_value, null);
                } catch (NoRecursivePossible e) {
                    // If can't replace configs in the file, copy the base file to the source file
                    File newFile = new File(source_path.getResultPath());
                    newFile.getParentFile().mkdirs();
                    try {
                        FileUtils.copyFile(new File(base_path.getSourcePath()), new File(source_path.getResultPath()));
                    } catch (IOException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            } else if (group.updateRuleIsDefault()) {
                // Set entry to default value
            } else {
                throw new RuntimeException("Update file error, report to developer");
            }
        }
    }
}
