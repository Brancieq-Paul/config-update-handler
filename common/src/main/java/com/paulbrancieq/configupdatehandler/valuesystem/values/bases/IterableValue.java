package com.paulbrancieq.configupdatehandler.valuesystem.values.bases;

import java.util.List;
import java.util.Map;

import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.IncoherentValueUpdate;
import com.paulbrancieq.configupdatehandler.valuesystem.values.BooleanEntryValue;
import com.paulbrancieq.configupdatehandler.valuesystem.values.DoubleEntryValue;
import com.paulbrancieq.configupdatehandler.valuesystem.values.IntegerEntryValue;
import com.paulbrancieq.configupdatehandler.valuesystem.values.ListEntryValue;
import com.paulbrancieq.configupdatehandler.valuesystem.values.ObjectEntryValue;
import com.paulbrancieq.configupdatehandler.valuesystem.values.StringEntryValue;

public abstract class IterableValue<T> extends Value<T> {
    public IterableValue() {
        super();
    }

    public IterableValue(T value) {
        super(value);
    }

    @SuppressWarnings("unchecked")
    protected Value<?> createSubValueFromRawValue(Object raw_value, ValuesCategory type) {
        if (type == ValuesCategory.ENTRY) {
            // If the value is a Integer, then create and return a IntegerConfigValue
            if (raw_value instanceof Integer) {
                return new IntegerEntryValue((Integer) raw_value);
            }
            // If the value is a String, then create and return a StringConfigValue
            if (raw_value instanceof String) {
                return new StringEntryValue((String) raw_value);
            }
            // If the value is a Boolean, then create and return a BooleanConfigValue
            if (raw_value instanceof Boolean) {
                return new BooleanEntryValue((Boolean) raw_value);
            }
            // If the value is a Double, then create and return a DoubleConfigValue
            if (raw_value instanceof Double) {
                return new DoubleEntryValue((Double) raw_value);
            }
            // If the value is a List, then create and return a ListConfigValue
            if (raw_value instanceof List) {
                return new ListEntryValue((List<Object>) raw_value);
            }
            // If the value is a Map, then create and return a ObjectConfigValue
            if (raw_value instanceof Map) {
                return new ObjectEntryValue((Map<Object, Object>) raw_value);
            }
            throw new RuntimeException("Unknown config value type: " + raw_value.getClass().getName());
        } else if (type == ValuesCategory.FILE) {
            // TODO: implement: File creator (try to create with each type until it work)
            throw new RuntimeException("Not implemented yet");
        } else if (type == ValuesCategory.DIRECTORY) {
            // TODO: implement: Directory creator
            throw new RuntimeException("Not implemented yet");
        }
        throw new RuntimeException("Unknown config value type: " + type);
    }

    // To use when applying replace update
    protected abstract void verifyCategoryCoherence(Value<?> other_subValue) throws IncoherentValueUpdate;
}
