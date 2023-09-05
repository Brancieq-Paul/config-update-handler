package com.paulbrancieq.configupdatehandler.platform.services;

import java.nio.file.Path;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Gets the path to the game directory.
     * 
     * @return The path to the game directory.
     */
    public Path getGameDirectory();

    /**
     * Verify that path is a relative path.
     * 
     * @param path The path to verify.
     * 
     * @return True if the path is relative, false otherwise.
     */
    default boolean isRelativePath(String path) {
        Path pathObj = Path.of(path);
        return !pathObj.isAbsolute();
    }

    /**
     * Transform a path to an os specific path.
     * 
     * @param path The path to transform.
     * 
     * @return The transformed path.
     */
    default String transformPath(String path) {
        // Verify if the OS is Unix or Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // If the OS is Windows, verify that the path only contains '\'
            if (path.contains("/")) {
                // If the path contains '/', replace all the '/' with '\'
                return path.replace("/", "\\");
            } else {
                // If the path doesn't contain '/', return the path
                return path;
            }
        } else {
            // If the OS is Unix, verify that the path only contains '/'
            if (path.toString().contains("\\")) {
                // If the path contains '\', replace all the '\' with '/'
                return path.replace("\\", "/");
            } else {
                // If the path doesn't contain '\', return the path
                return path;
            }
        }
    }
}