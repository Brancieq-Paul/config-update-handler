package com.paulbrancieq.configupdatehandler.old;

public class ConfigHandlerExeptions {
    public static class ConfigHandlerException extends RuntimeException {
        public ConfigHandlerException(String message) {
            super(message);
        }
    }

    public static class IncompatibleFileFormat extends ConfigHandlerException {
        public IncompatibleFileFormat(String message) {
            super(message);
        }
    }

    public static class InvalidMCFileSystem extends ConfigHandlerException {
        public InvalidMCFileSystem(String message) {
            super(message);
        }
    }

    public static class InvalidConfigHandlerConfig extends ConfigHandlerException {
        public InvalidConfigHandlerConfig(String message) {
            super(message);
        }
    }

    public static class InvalidConfigEntryUpdate extends ConfigHandlerException {
        public InvalidConfigEntryUpdate(String message) {
            super(message);
        }
    }

    public static class InvalidCUHConfig extends ConfigHandlerException {
        public InvalidCUHConfig(String message) {
            super(message);
        }
    }
}
