package com.paulbrancieq.configupdatehandler.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DevConfigFile extends GenericCUHConfigFile {

    private static final String path = "config.json";

    private Map<String, Object> generatedContent = new HashMap<String, Object>() {
        {
            put("version", 1);
            // Boolean: apply backup on shutdown
            put("applyBackupOnShutdown", true);
        }
    };

    public DevConfigFile(String path) throws FileNotFoundException, IOException {
        super(path);
    }
}
