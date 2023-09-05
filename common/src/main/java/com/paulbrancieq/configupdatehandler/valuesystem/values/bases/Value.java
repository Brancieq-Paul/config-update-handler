package com.paulbrancieq.configupdatehandler.valuesystem.values.bases;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.IncoherentValueUpdate;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.InvalidEntryPath;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.InvalidSubConfigKey;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.NoDefaultableContent;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.NoRecursivePossible;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.NoReplaceableContent;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.NoSubConfigValue;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.TypeMisMatch;
import com.paulbrancieq.configupdatehandler.valuesystem.consumers.RecursiveConsumer;
import com.paulbrancieq.configupdatehandler.valuesystem.consumers.paths.PathConsumer;

public abstract class Value<T> {

    private T value;

    // isAlreadySet
    private Boolean isAlreadySet = false;

    // category
    protected ValuesCategory category;

    public Value() {
        setValueCategory();
    }

    public Value(T value) {
        setValueCategory();
        setValue(value);
    }

    // Should only be used for initialization !
    protected void setValue(T value) {
        if (isAlreadySet) {
            throw new RuntimeException("ConfigValue already set");
        }
        this.value = value;
        isAlreadySet = true;
    }

    // Abstract setValueCategory
    protected abstract void setValueCategory();

    // getValueCategory
    public ValuesCategory getValueCategory() {
        return category;
    }

    public T getValue() {
        return value;
    }

    public Object getWritableValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getRawType() {
        return (Class<T>) value.getClass();
    }

    private boolean useSameRawTypeAndCategory(Value<?> other) {
        return getRawType() == other.getRawType() && getValueCategory() == other.getValueCategory();
    }

    // To override to implement replacing
    protected void _replaceSubConfigValues(Value<T> other, RecursiveConsumer recursive_level)
            throws NoRecursivePossible, IncoherentValueUpdate {
        throw new NoReplaceableContent();
    }

    @SuppressWarnings("unchecked")
    public final void replaceSubConfigValues(Value<?> other, RecursiveConsumer recursive_level)
            throws NoRecursivePossible, IncoherentValueUpdate {
        try {
            if (useSameRawTypeAndCategory(other)) {
                _replaceSubConfigValues((Value<T>) other, recursive_level);
            } else {
                throw new TypeMisMatch(other.getRawType().getName(), getRawType().getName());
            }
        } catch (TypeMisMatch e) {
            throw new NoRecursivePossible(e);
        }
    }

    // To override to implement defaulting
    protected void _defaultSubConfigValues(Value<T> other, RecursiveConsumer recursive_level) throws CUHExeption {
        throw new NoDefaultableContent();
    }

    @SuppressWarnings("unchecked")
    public final void defaultSubConfigValues(Value<?> other, RecursiveConsumer recursive_level) throws CUHExeption {
        try {
            if (useSameRawTypeAndCategory(other)) {
                _defaultSubConfigValues((Value<T>) other, recursive_level);
            } else {
                throw new TypeMisMatch(other.getRawType().getName(), getRawType().getName());
            }
        } catch (TypeMisMatch e) {
            throw new NoRecursivePossible(e);
        }
    }

    public Value<?> getSubConfigValue(String key) throws NoSubConfigValue, InvalidSubConfigKey {
        throw new NoSubConfigValue();
    }

    public Value<?> getSubConfigValue(PathConsumer entry_path) throws NoSubConfigValue, InvalidEntryPath {
        throw new NoSubConfigValue();
    }
}
