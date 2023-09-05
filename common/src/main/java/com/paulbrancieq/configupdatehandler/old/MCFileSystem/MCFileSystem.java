package com.paulbrancieq.configupdatehandler.MCFileSystem;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.paulbrancieq.configupdatehandler.old.ConfigHandlerExeptions;
import com.paulbrancieq.configupdatehandler.old.Constants;
import com.paulbrancieq.configupdatehandler.old.FileSystem;
import com.paulbrancieq.configupdatehandler.old.FileSystem.GenericConfigFile;

public class MCFileSystem {

    String path;
    Map<String, GenericConfigFile<?>> files = new HashMap<String, GenericConfigFile<?>>();
    String[] excludeList = { Constants.CONFIGHUPDATEANDLER_BASE_DIRECTORY_R_PATH };
    String[] includeList = { Constants.CONFIG_DIRECTORY_R_PATH };

    public MCFileSystem(String path) throws ConfigHandlerExeptions.InvalidMCFileSystem {
        // Make sure the path is an existing directory
        if (!Paths.get(path).toFile().isDirectory()) {
            throw new ConfigHandlerExeptions.InvalidMCFileSystem("The path provided is not a directory");
        }
        // Set the canonical path
        try {
            this.path = Paths.get(path).toFile().getCanonicalPath();
        } catch (Exception e) {
            throw new ConfigHandlerExeptions.InvalidMCFileSystem("The path provided is not a valid path");
        }
        parseDirectory(path);
    }

    public void parseDirectory(String d_path) {
        File directory = new File(d_path);
        for (File file : directory.listFiles()) {
            // Verify if the file is in the exclude list
            boolean excluded = false;
            for (String excludePath : this.excludeList) {
                if (file.toPath().toString().contains(this.path + Constants.PATH_SEPARATOR + excludePath)) {
                    excluded = true;
                    break;
                }
            }
            if (excluded) {
                continue;
            }
            // Verify if the file is in the include list
            boolean included = false;
            for (String includePath : this.includeList) {
                if (file.toPath().toString().contains(this.path + Constants.PATH_SEPARATOR + includePath)) {
                    included = true;
                    break;
                }
            }
            if (!included) {
                continue;
            }
            // If the file is a directory, create a new MCFileSystem object with the path of the directory
            if (file.isDirectory()) {
                parseDirectory(d_path + Constants.PATH_SEPARATOR + file.getName());
            }
            // If the file is a file, create a new ConfigFile object with the path of the file
            else if (file.isFile()) {
                GenericConfigFile<?> configFile = FileSystem.createConfigFile(d_path + Constants.PATH_SEPARATOR + file.getName());
                if (configFile != null) {
                    // Put the path relative to the MCFileSystem path as key and the ConfigFile object as value in the files dictionary
                    this.files.put((d_path + Constants.PATH_SEPARATOR + file.getName()).replace(this.path + d_path + Constants.PATH_SEPARATOR, ""), configFile);
                }
            }
        }
    }

    // Get files
    public Map<String, GenericConfigFile<?>> getFiles() {
        return this.files;
    }
}
