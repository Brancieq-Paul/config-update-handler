package com.paulbrancieq.configupdatehandler;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final Logger LOG = LoggerFactory.getLogger(CUH.MOD_NAME);
    public static final String PATH_SEPARATOR = System.getProperty("file.separator");

    public class Minecraft {
        public class Paths {
            public class Names {
                public static final String CONFIG_DIRECTORY = "config";
            }
        }

        public class Relatives {
            public static Path CONFIG_DIRECTORY = java.nio.file.Paths.get(Paths.Names.CONFIG_DIRECTORY);
        }

        public class Absolutes {
            public static Path CONFIG_DIRECTORY = Services.PLATFORM.getGameDirectory().resolve(Relatives.CONFIG_DIRECTORY);
        }
    }

    public class CUH {
        public static final String MOD_ID = "configupdatehandler";
        public static final String MOD_NAME = "Config Update Handler";

        public class UpdateFile {
            public class ConditionsFields {
                public class Name {
                    // Commmon
                    public static final String FILE_PATH = "file_path";
                    public static final String ENTRY_PATH = "entry_path";
                    public static final String EXPECTED_ENTRY_TYPE = "expected_entry_type";
                    public static final String ENTRY_UPDATE_RULE = "entry_update_rule";

                    // Object entry
                    public static final String RECURSIVE_LEVEL = "recursive_level";
                }

                public class PossibleValues {
                    // Common
                    public static final String STRING_ENTRY_TYPE = "string";
                    public static final String INTEGER_ENTRY_TYPE = "integer";
                    public static final String DOUBLE_ENTRY_TYPE = "double";
                    public static final String BOOLEAN_ENTRY_TYPE = "boolean";
                    public static final String OBJECT_ENTRY_TYPE = "object";
                    public static final String LIST_ENTRY_TYPE = "list";
                    public static final String JSON_FILE_OBJECT_ENTRY_TYPE = "json_file_object";
                    public static final String JSON_FILE_LIST_ENTRY_TYPE = "json_file_list";
                    public static final String DIRECTORY_ENTRY_TYPE = "directory";

                    public static final String REPLACE_RULE = "replace";
                    public static final String DEFAULT_RULE = "default";
                    protected static final String JSON_FILE_OBJECT_TYPE = null;
                }
            }
        }

        public class Paths {
            public class Names {
                public static final class Formats {
                    public static final String VERSION_UPDATE_FILE_NAME_FORMAT = "%s.json";
                    // Backup directory name format: <clear date>_<timestamp>
                    public static final String BACKUP_DIRECTORY_NAME_FORMAT = "%s_%s";
                }
                public static final String BASE_DIRECTORY = "base_config";
	            public static final String VERSIONS_UPDATES_DIRECTORY = "versions_updates";
	            public static final String CONFIG_FILE = "configupdatehandler.json";
	            public static final String ACTUAL_VERSION_FILE = "configupdatehandler_actual_version.json";
                public static final String BACKUPS_DIRECTORY = "backups";
                public static final String DEV_JSON = "dev.json";
            }

            public class Absolutes {
                public static Path CONFIG_DIRECTORY = Minecraft.Absolutes.CONFIG_DIRECTORY.resolve(MOD_ID);
                public static Path BASE_DIRECTORY = CONFIG_DIRECTORY.resolve(Names.BASE_DIRECTORY);
                public static Path VERSIONS_UPDATES_DIRECTORY = CONFIG_DIRECTORY.resolve(Names.VERSIONS_UPDATES_DIRECTORY);
                public static Path CONFIG_FILE = CONFIG_DIRECTORY.resolve(Names.CONFIG_FILE);
                public static Path ACTUAL_VERSION_FILE = CONFIG_DIRECTORY.resolve(Names.ACTUAL_VERSION_FILE);
                public static Path BACKUPS_DIRECTORY = CONFIG_DIRECTORY.resolve(Names.BACKUPS_DIRECTORY);
                public static Path DEV_JSON = CONFIG_DIRECTORY.resolve(Names.DEV_JSON);
            }
        }
    }
}
