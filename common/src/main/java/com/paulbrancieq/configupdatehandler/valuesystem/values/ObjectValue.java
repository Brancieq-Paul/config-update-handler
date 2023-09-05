package com.paulbrancieq.configupdatehandler.valuesystem.values;

import java.util.HashMap;
import java.util.Map;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;
import com.paulbrancieq.configupdatehandler.exeptions.ConsumerAlreadyFinished;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.IncoherentValueUpdate;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.InvalidEntryPath;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.InvalidSubConfigKey;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.NoRecursivePossible;
import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.NoSubConfigValue;
import com.paulbrancieq.configupdatehandler.valuesystem.consumers.RecursiveConsumer;
import com.paulbrancieq.configupdatehandler.valuesystem.consumers.paths.PathConsumer;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.IterableValue;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.ValueContainer;

public abstract class ObjectValue extends IterableValue<Map<Object, ValueContainer>> {

    // Default constructor
    public ObjectValue() {
        super();
    }

    public ObjectValue(Map<Object, Object> value) {
        super();
        this.setValue(value);
    }

    protected void setValue(Map<Object, Object> value) {
        // Create Map of ConfigValueContainer
        Map<Object, ValueContainer> container_map = new HashMap<Object, ValueContainer>();
        for (Map.Entry<Object, Object> entry : value.entrySet()) {
            Value<?> subvalue = createSubValueFromRawValue(entry.getValue());
            ValueContainer container = new ValueContainer(subvalue);
            container_map.put(entry.getKey(), container);
        }
        // Set value
        super.setValue(container_map);
    }

    protected abstract Value<?> createSubValueFromRawValue(Object raw_value);

    @Override
    protected void _replaceSubConfigValues(Value<Map<Object, ValueContainer>> other, RecursiveConsumer recursive_level)
            throws NoRecursivePossible, IncoherentValueUpdate {
        // Verify if recursive is possible
        if (!recursive_level.isInfinite() && recursive_level.isFinished()) {
            throw new NoRecursivePossible();
        }
        // Consume recursive level
        try {
            recursive_level.consume();
        } catch (Exception e) {
            throw new NoRecursivePossible(e);
        }
        // Replace sub config values
        for (Map.Entry<Object, ValueContainer> entry : other.getValue().entrySet()) {
            // Verify coherence. If not coherent, throw IncoherentValueUpdate. Will be
            // caught by the precedent _replaceSubConfigValues
            verifyCategoryCoherence(other.getValue().get(entry.getKey()).getValue());
            // If does not exist, create it
            if (!this.getValue().containsKey(entry.getKey())) {
                this.getValue().put(entry.getKey(), entry.getValue());
            } else {
                // Else, try to replace recursively
                try {
                    // Will try to replace recursively if both value contains subvalues and type
                    // match
                    this.getValue().get(entry.getKey()).getValue().replaceSubConfigValues(entry.getValue().getValue(),
                            recursive_level);
                } catch (NoRecursivePossible e) {
                    // If the recursive replace was not possible (because of type mismatch or the
                    // values does not contain subvalues), replace the value
                    this.getValue().get(entry.getKey()).replaceValue(other.getValue().get(entry.getKey()).getValue());
                } catch (IncoherentValueUpdate e) {
                    // In replacesubvalues of the actual entry, the category is not coherent
                    // (example: try to put a directory in a file). It means the subvalue from
                    // "other" should not be in a value of the type and category of "this" and
                    // "other". It should never happen with the current implementation because of
                    // precedent checks.
                    throw new RuntimeException(
                            "Incoherent value update. This error should never show up. Contact the developper of ConfigUpdateHandler",
                            e);
                }
            }
        }
    }

    @Override
    protected void _defaultSubConfigValues(Value<Map<Object, ValueContainer>> other, RecursiveConsumer recursive_level)
            throws CUHExeption {
        // Verify if recursive is possible
        if (!recursive_level.isInfinite() && recursive_level.isFinished()) {
            throw new NoRecursivePossible();
        }
        // Consume recursive level
        try {
            recursive_level.consume();
        } catch (Exception e) {
            throw new NoRecursivePossible(e);
        }
        for (Map.Entry<Object, ValueContainer> entry : other.getValue().entrySet()) {
            // Verify coherence. If not coherent, throw IncoherentValueUpdate. Will be
            // caught by the precedent _replaceSubConfigValues
            verifyCategoryCoherence(other.getValue().get(entry.getKey()).getValue());
            // If does not exist, create it
            if (!this.getValue().containsKey(entry.getKey())) {
                this.getValue().put(entry.getKey(), entry.getValue());
            } else {
                // Else, try to default recursively
                try {
                    // Will try to default recursively if both value contains subvalues and type
                    // match
                    this.getValue().get(entry.getKey()).getValue().defaultSubConfigValues(entry.getValue().getValue(),
                            recursive_level);
                } catch (NoRecursivePossible e) {
                    // If the recursive default was not possible (because of type mismatch or the
                    // values does not contain subvalues), default the value, do nothing
                } catch (IncoherentValueUpdate e) {
                    // In defaultsubvalues of the actual entry, the category is not coherent
                    // (example: try to put a directory in a file). It means the subvalue from
                    // "other" should not be in a value of the type and category of "this" and
                    // "other". It should never happen with the current implementation because of
                    // precedent checks.
                    throw new IncoherentValueUpdate(
                            "Incoherent value update. This error should never show up. Contact the developper of ConfigUpdateHandler",
                            e);
                }
            }
        }
    }

    @Override
    public Map<Object, Object> getWritableValue() {
        Map<Object, Object> writable_value = new HashMap<Object, Object>();
        for (Map.Entry<Object, ValueContainer> entry : this.getValue().entrySet()) {
            writable_value.put(entry.getKey(), entry.getValue().getValue().getWritableValue());
        }
        return writable_value;
    }

    @Override
    public Value<?> getSubConfigValue(String key) throws InvalidSubConfigKey {
        if (!this.getValue().containsKey(key)) {
            throw new InvalidSubConfigKey(key);
        }
        return this.getValue().get(key).getValue();
    }

    @Override
    public Value<?> getSubConfigValue(PathConsumer entry_path) throws InvalidEntryPath {
        try {
            Value<?> value = this.getSubConfigValue(entry_path.consume());
            if (entry_path.isFinished()) {
                return value;
            } else {
                return value.getSubConfigValue(entry_path);
            }
        } catch (InvalidSubConfigKey e) {
            throw new InvalidEntryPath(entry_path.getPath(), e);
        } catch (ConsumerAlreadyFinished e) {
            throw new RuntimeException("This should never happen, report to developer", e);
        } catch (NoSubConfigValue e) {
            throw new InvalidEntryPath(entry_path.getPath(), e);
        }
    }
}
