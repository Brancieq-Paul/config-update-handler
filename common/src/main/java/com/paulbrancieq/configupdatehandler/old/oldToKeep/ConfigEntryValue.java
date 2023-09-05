package com.paulbrancieq.configupdatehandler.old.oldToKeep;

import java.util.Dictionary;
import java.util.List;

// Contain all ConfigEntry related code
// This class is used to regroup the GenericConfigEntry class and all the
// subclasses
// and more general ConfigEntry related methods
public class ConfigEntryValue {
    // GenericConfigEntry class
    // This class contain the features that are common to all ConfigEntry subclasses
    public class GenericConfigEntryValue<T extends GenericConfigEntryValue<?>> {
        // The entry_type attribute is used to identify the subclass of the object
        private final Class<T> entry_type;

        // Constructor
        public GenericConfigEntryValue(Class<T> entry_type) {
            this.entry_type = entry_type;
        }

        // Getter for the entry_type attribute
        public Class<T> getEntryType() {
            return entry_type;
        }

        // Getter for the value attribute
        public Object getValue() {
            return null;
        }
    }

    // BooleanConfigEntry class
    // This class is a subclass of GenericConfigEntry and is used to represent a
    // boolean entry
    public class BooleanConfigEntry extends GenericConfigEntryValue<BooleanConfigEntry> {
        private final boolean value;

        public BooleanConfigEntry(String key, boolean value) {
            super(BooleanConfigEntry.class);
            this.value = value;
        }

        @Override
        public Boolean getValue() {
            return value;
        }
    }

    // IntegerConfigEntry class
    // This class is a subclass of GenericConfigEntry and is used to represent an
    // integer entry
    public class IntegerConfigEntry extends GenericConfigEntryValue<IntegerConfigEntry> {
        private final int value;

        public IntegerConfigEntry(String key, int value) {
            super(IntegerConfigEntry.class);
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }
    }

    // StringConfigEntry class
    // This class is a subclass of GenericConfigEntry and is used to represent a
    // string entry
    public class StringConfigEntry extends GenericConfigEntryValue<StringConfigEntry> {
        private final String value;

        public StringConfigEntry(String key, String value) {
            super(StringConfigEntry.class);
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    // DoubleConfigEntry class
    // This class is a subclass of GenericConfigEntry and is used to represent a
    // double entry
    public class DoubleConfigEntry extends GenericConfigEntryValue<DoubleConfigEntry> {
        private final double value;

        public DoubleConfigEntry(String key, double value) {
            super(DoubleConfigEntry.class);
            this.value = value;
        }

        @Override
        public Double getValue() {
            return value;
        }
    }

    // ListConfigEntry class
    // This class is a subclass of GenericConfigEntry and is used to represent a
    // list entry
    public class ListConfigEntry extends GenericConfigEntryValue<ListConfigEntry> {
        private final List<GenericConfigEntryValue<?>> value;

        public ListConfigEntry(String key, List<GenericConfigEntryValue<?>> value) {
            super(ListConfigEntry.class);
            this.value = value;
        }

        @Override
        public List<GenericConfigEntryValue<?>> getValue() {
            return value;
        }
    }

    // ObjectConfigEntry class
    // This class is a subclass of GenericConfigEntry and is used to represent an
    // object entry
    public class ObjectConfigEntry extends GenericConfigEntryValue<ObjectConfigEntry> {
        private final Dictionary<String, GenericConfigEntryValue<?>> value;

        public ObjectConfigEntry(String key, Dictionary<String, GenericConfigEntryValue<?>> value) {
            super(ObjectConfigEntry.class);
            this.value = value;
        }

        @Override
        public Dictionary<String, GenericConfigEntryValue<?>> getValue() {
            return value;
        }
    }
}
