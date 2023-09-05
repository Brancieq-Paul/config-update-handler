package com.paulbrancieq.configupdatehandler.valuesystem.files;

import java.nio.file.Path;

public class ComposedPath {
    // source mc directory
    private Path sourceDirectory;
    // result mc directory (equals to source by default, should be modified only in mod or modpack dev context)
    private Path resultDirectory;

    // path relative to MC directory
    private String relativePath;

    // Constructor
    public ComposedPath(String relativePath, Path sourceDirectory, Path resultDirectory) {
        setRelativePath(relativePath);
        setSourceDirectory(sourceDirectory);
        setResultDirectory(resultDirectory);
    }

    // Setter for the relative path
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    // Getters for the path
    // Relative to MC directory
    public String getRelativePath() {
        return relativePath;
    }

    // Absolute source path
    public String getSourcePath() {
        return sourceDirectory.resolve(relativePath).toString();
    }

    // Absolute result path
    public String getResultPath() {
        return resultDirectory.resolve(relativePath).toString();
    }

    // Setters for the MC directories
    // Source
    public void setSourceDirectory(Path sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    // Result
    public void setResultDirectory(Path resultDirectory) {
        this.resultDirectory = resultDirectory;
    }
}
