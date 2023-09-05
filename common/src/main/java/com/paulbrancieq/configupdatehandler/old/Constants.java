package com.paulbrancieq.configupdatehandler.old;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	// Mod identity
	public static final String MOD_ID = "configupdatehandler";
	public static final String MOD_NAME = "Config Update Handler";

	// Java utilities
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static final String PATH_SEPARATOR = System.getProperty("file.separator");

	// Minecraft
	// Names
	public static final String CONFIG_DIRECTORY_NAME = "config";
	// Paths
	public static final String CONFIG_DIRECTORY_R_PATH = CONFIG_DIRECTORY_NAME;

	// ConfigHandler
	// Names
	public static final String CONFIGHUPDATEANDLER_BASE_DIRECTORY_NAME = "base_config";
	public static final String CONFIGHUPDATEANDLER_VERSIONS_UPDATES_DIRECTORY_NAME = "versions_updates";
	public static final String CONFIGHUPDATEANDLER_CONFIG_FILE_NAME = "configupdatehandler.json";
	public static final String CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_NAME = "configupdatehandler_actual_version.json";
	// Paths
	public static final String CONFIGHUPDATEANDLER_CONFIG_DIRECTORY_R_PATH = CONFIG_DIRECTORY_R_PATH + PATH_SEPARATOR
			+ MOD_ID;
	public static final String CONFIGHUPDATEANDLER_BASE_DIRECTORY_R_PATH = CONFIG_DIRECTORY_R_PATH + PATH_SEPARATOR
			+ MOD_ID + PATH_SEPARATOR + CONFIGHUPDATEANDLER_BASE_DIRECTORY_NAME;
	public static final String CONFIGHUPDATEANDLER_VERSIONS_UPDATES_DIRECTORY_R_PATH = CONFIG_DIRECTORY_R_PATH
			+ PATH_SEPARATOR + MOD_ID + PATH_SEPARATOR + CONFIGHUPDATEANDLER_VERSIONS_UPDATES_DIRECTORY_NAME;
	public static final String CONFIGHUPDATEANDLER_CONFIG_FILE_R_PATH = CONFIGHUPDATEANDLER_CONFIG_DIRECTORY_R_PATH
			+ PATH_SEPARATOR + CONFIGHUPDATEANDLER_CONFIG_FILE_NAME;
	public static final String CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_R_PATH = CONFIG_DIRECTORY_R_PATH + PATH_SEPARATOR
			+ CONFIGHUPDATEANDLER_ACTUAL_VERSION_FILE_NAME;

	// ConfigHandler general
	// Field names
	public static final String CONFIGHUPDATEANDLER_FILE_VERSION_FIELD_NAME = "fversion";

	// ConfigHandler config
	// Field names
	public static final String CONFIGHUPDATEANDLER_CONFIG_VERSIONS_FIELD_NAME = "modpack_versions";
	public static final String CONFIGHUPDATEANDLER_CONFIG_VERSION_NAME_FIELD_NAME = "name";

	// ConfigHandler actual version
	// Field names
	public static final String CONFIGHUPDATEANDLER_ACTUAL_VERSION_FIELD_NAME = "actual_version_name";

	// ConfigHandler update config
	// Field names
	public static final String CONFIGHUPDATEANDLER_UPDATE_LIST_FIELD_NAME = "update";
	public static final String CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_PATH_FIELD_NAME = "entry_path";
	public static final String CONFIGHUPDATEANDLER_UPDATE_LIST_FILE_PATH_FIELD_NAME = "file_path";
	public static final String CONFIGHUPDATEANDLER_UPDATE_LIST_FILE_TYPE_FIELD_NAME = "file_type";
	public static final String CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_TYPE_FIELD_NAME = "entry_type";
	public static final String CONFIGHUPDATEANDLER_UPDATE_LIST_ENTRY_UPDATE_RULE_FIELD_NAME = "update_rule";
	// Entry types
	public enum EntryType {
		OBJECT("object"), LIST("list"), BOOLEAN("boolean"), INTEGER("integer"), DOUBLE("double"), NUMBER("number"),
		STRING("string"), ANY("any");

		private final String name;

		private EntryType(String name) {
			this.name = name;
		}

		public String getValue() {
			return name;
		}
	}
	// Map of entry types as key, and Java classes as value
	public static final Map<EntryType, Class<?>> ENTRY_TYPE_TO_CLASS_MAP = new HashMap<EntryType, Class<?>>() {
		{
			put(EntryType.OBJECT, Map.class);
			put(EntryType.LIST, List.class);
			put(EntryType.BOOLEAN, Boolean.class);
			put(EntryType.INTEGER, Integer.class);
			put(EntryType.DOUBLE, Double.class);
			put(EntryType.NUMBER, Number.class);
			put(EntryType.STRING, String.class);
			put(EntryType.ANY, Object.class);
		}
	};
	// File types
	public enum FileType {
		JSON("json"), PROPERTIES("properties"), ANY("any");

		private final String name;

		private FileType(String name) {
			this.name = name;
		}

		public String getValue() {
			return name;
		}
	}
	// Map of file types as key, and file class as value
	public static final Map<FileType, Class<?>> FILE_TYPE_TO_CLASS_MAP = new HashMap<FileType, Class<?>>() {
		{
			put(FileType.JSON, FileSystem.JsonConfigFile.class);
			put(FileType.ANY, Object.class);
		}
	};
	// Update rules
	public enum UpdateRule {
		DEFAULT("default"), MERGE("merge"), OVERRIDE("override");

		private final String name;

		private UpdateRule(String name) {
			this.name = name;
		}

		public String getValue() {
			return name;
		}
	}
}