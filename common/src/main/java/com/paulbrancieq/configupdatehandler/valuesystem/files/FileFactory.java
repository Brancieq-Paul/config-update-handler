package com.paulbrancieq.configupdatehandler.valuesystem.files;

import java.util.HashMap;
import java.util.Map;

import com.paulbrancieq.configupdatehandler.Constants;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigFile.FaildedFileCreation;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;

public class FileFactory<T extends Value<?> & IFile> {
    public static Map<String, Class<? extends IFile>> files = new HashMap<String, Class<? extends IFile>>() {
        {
            put(".json", JsonObjectFile.class);
        }
    };

    public static IFile create(ComposedPath path) throws FaildedFileCreation {
        // Get file class
        Class<? extends IFile> file_class = null;
        // Get file extension
        for (Map.Entry<String, Class<? extends IFile>> entry : files.entrySet()) {
            if (path.getRelativePath().endsWith(entry.getKey())) {
                file_class = entry.getValue();
                break;
            }
        }
        // Check if file class is found
        if (file_class == null) {
            throw new RuntimeException();
        }
        // Create file
        try {
            return file_class.getConstructor(ComposedPath.class).newInstance(path);
        } catch (Exception e) {
            Constants.LOG.error("Failed to handle file: " + path.getRelativePath());
            throw new FaildedFileCreation("Failed to handle file: " + path.getRelativePath(), e);
        }
    }
}
