package com.paulbrancieq.configupdatehandler.old;

import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.paulbrancieq.configupdatehandler.MCFileSystem.MCFileSystem;

public class ConfigUpdateHandlerCommon {

    // The two MCFileSystem objects
    static MCFileSystem mainMCFileSystem;
    static MCFileSystem baseMCFileSystem;

    // List of all the modpack versions
    static JsonArray modpackVersions;

    // Actual modpack config version
    static JsonObject actualConfigVersion;

    public static void init() {
        // Log the mod initialization
        Constants.LOG.info("ConfigUpdateHandlerCommon initialization started");
        // Generate ConfigUpdateHandler files
        generateCUHFiles();
        // Read ConfigUpdateHandler config file
        readCUHConfigFile();
        // Read ConfigUpdateHandler actual version file
        readCUHActualVersionFile();

        // Initialize mcFileSystem
        Constants.LOG.info("Initializing Minecraft Directory FileSystem");
        mainMCFileSystem = new MCFileSystem(Services.PLATFORM.getGameDirectory().toString());
        // Initialize defaultFileSystem
        Constants.LOG.info("Initializing Base Minecraft Directory FileSystem");
        baseMCFileSystem = new MCFileSystem(Services.PLATFORM.getGameDirectory().toString()
                + Constants.PATH_SEPARATOR + Constants.CONFIGHUPDATEANDLER_BASE_DIRECTORY_R_PATH);
    }

    // Create needed directories and files if they don't exist
    public static void generateCUHFiles() {
        // needVersionUpdate file init
        Boolean needVersionUpdateInit = false;
        // Create the confighandler config directory if it does not exist
        if (!Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_CONFIG_DIRECTORY_R_PATH).toFile()
                .exists()) {
            // Create the confighandler config directory
            try {
                Files.createDirectory(
                        Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_CONFIG_DIRECTORY_R_PATH));
            } catch (Exception e) {
                throw new ConfigHandlerExeptions.InvalidConfigHandlerConfig(
                        "Failed to create the configupdatehandler config directory ("
                                + Constants.CONFIGHUPDATEANDLER_CONFIG_DIRECTORY_R_PATH + ")");
            }
        }
        // Create the confighandler base config directory if it does not exist
        if (!Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_BASE_DIRECTORY_R_PATH).toFile()
                .exists()) {
            // Create the confighandler base config directory
            try {
                Files.createDirectory(
                        Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_BASE_DIRECTORY_R_PATH));
            } catch (Exception e) {
                throw new ConfigHandlerExeptions.InvalidConfigHandlerConfig(
                        "Failed to create the configupdatehandler base config directory ("
                                + Constants.CONFIGHUPDATEANDLER_BASE_DIRECTORY_R_PATH + ")");
            }
        }
        // Create the confighandler versions updates directory if it does not exist
        if (!Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_VERSIONS_UPDATES_DIRECTORY_R_PATH)
                .toFile().exists()) {
            // Create the confighandler versions overwrites directory
            try {
                Files.createDirectory(Services.PLATFORM.getGameDirectory()
                        .resolve(Constants.CONFIGHUPDATEANDLER_VERSIONS_UPDATES_DIRECTORY_R_PATH));
            } catch (Exception e) {
                throw new ConfigHandlerExeptions.InvalidConfigHandlerConfig(
                        "Failed to create the configupdatehandler versions overwrites directory ("
                                + Constants.CONFIGHUPDATEANDLER_VERSIONS_UPDATES_DIRECTORY_R_PATH + ")");
            }
        }
        // Default JSON content for the confighandler config file
        JsonObject defaultConfigFileContent = new JsonObject();
        defaultConfigFileContent.addProperty(Constants.CONFIGHUPDATEANDLER_FILE_VERSION_FIELD_NAME, 1);
        JsonArray modpackVersions = new JsonArray();
        JsonObject initialVersion = new JsonObject();
        initialVersion.addProperty(Constants.CONFIGHUPDATEANDLER_CONFIG_VERSION_NAME_FIELD_NAME, "0.0");
        modpackVersions.add(initialVersion);
        defaultConfigFileContent.add(Constants.CONFIGHUPDATEANDLER_CONFIG_VERSIONS_FIELD_NAME, modpackVersions);
        // Create the confighandler config file if it does not exist
        if (!Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_CONFIG_FILE_R_PATH).toFile()
                .exists()) {
            // Create the confighandler config file
            try {
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                Files.createFile(
                        Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_CONFIG_FILE_R_PATH));
                // Write JSONObject to the confighandler config file
                BufferedWriter writer = new BufferedWriter(new FileWriter(Services.PLATFORM.getGameDirectory()
                        .resolve(Constants.CONFIGHUPDATEANDLER_CONFIG_FILE_R_PATH).toFile()));
                writer.write(gson.toJson(defaultConfigFileContent));
                writer.close();
            } catch (Exception e) {
                throw new ConfigHandlerExeptions.InvalidConfigHandlerConfig(
                        "Failed to create the configupdatehandler config file ("
                                + Constants.CONFIGHUPDATEANDLER_CONFIG_FILE_R_PATH + ")");
            }
        }
        // Default JSON content for the confighandler actual version file
        JsonObject defaultActualVersionFileContent = new JsonObject();
        defaultActualVersionFileContent.addProperty(Constants.CONFIGHUPDATEANDLER_FILE_VERSION_FIELD_NAME, 1);
        defaultActualVersionFileContent.addProperty(Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FIELD_NAME, "0.0");
        // Create the confighandler actual version file if it does not exist
        if (!Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH).toFile()
                .exists()) {
            // Create the confighandler actual version file
            try {
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                Files.createFile(
                        Services.PLATFORM.getGameDirectory().resolve(Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH));
                // Write JSONObject to the confighandler actual version file
                BufferedWriter writer = new BufferedWriter(new FileWriter(Services.PLATFORM.getGameDirectory()
                        .resolve(Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH).toFile()));
                writer.write(gson.toJson(defaultActualVersionFileContent));
                writer.close();
            } catch (Exception e) {
                throw new ConfigHandlerExeptions.InvalidConfigHandlerConfig(
                        "Failed to create the configupdatehandler actual version file ("
                                + Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH + ")");
            }
        }
    }

    // Read confighandler config file
    public static void readCUHConfigFile() {
        try {
            Gson gson = new Gson();
            String configFileContent = new String(Files.readAllBytes(Services.PLATFORM.getGameDirectory()
                    .resolve(Constants.CONFIGHUPDATEANDLER_CONFIG_FILE_R_PATH)));
            modpackVersions = gson.fromJson(configFileContent, JsonObject.class).getAsJsonArray(Constants.CONFIGHUPDATEANDLER_CONFIG_VERSIONS_FIELD_NAME);
        } catch (Exception e) {
            throw new ConfigHandlerExeptions.InvalidConfigHandlerConfig(
                    "Failed to read the configupdatehandler config file ("
                            + Constants.CONFIGHUPDATEANDLER_CONFIG_FILE_R_PATH + ")");
        }
    }

    // Read confighandler actual version file
    public static void readCUHActualVersionFile() {
        try {
            Gson gson = new Gson();
            String actualVersionFileContent = new String(Files.readAllBytes(Services.PLATFORM.getGameDirectory()
                    .resolve(Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH)));
            actualConfigVersion = gson.fromJson(actualVersionFileContent, JsonObject.class);
        } catch (Exception e) {
            throw new ConfigHandlerExeptions.InvalidConfigHandlerConfig(
                    "Failed to read the configupdatehandler actual version file ("
                            + Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH + ")");
        }
    }

    // Get last modpack version (base version)
    public static String getLastModpackVersion() {
        return modpackVersions.get(modpackVersions.size() - 1).getAsJsonObject().get(Constants.CONFIGHUPDATEANDLER_CONFIG_VERSION_NAME_FIELD_NAME).getAsString();
    }

    // Get actual config version
    public static String getActualConfigVersion() {
        return actualConfigVersion.get(Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FIELD_NAME).getAsString();
    }

    // Set actual config version
    public static void setActualConfigVersion(String version) {
        actualConfigVersion.addProperty(Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_NAME, version);
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            // Write JSONObject to the confighandler actual version file
            BufferedWriter writer = new BufferedWriter(new FileWriter(Services.PLATFORM.getGameDirectory()
                    .resolve(Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH).toFile()));
            writer.write(gson.toJson(actualConfigVersion));
            writer.close();
        } catch (Exception e) {
            throw new ConfigHandlerExeptions.InvalidConfigHandlerConfig(
                    "Failed to write the configupdatehandler actual version file ("
                            + Constants.CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH + ")");
        }
    }
}
