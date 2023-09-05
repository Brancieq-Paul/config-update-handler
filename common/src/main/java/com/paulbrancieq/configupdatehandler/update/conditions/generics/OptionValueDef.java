package com.paulbrancieq.configupdatehandler.update.conditions.generics;

import java.util.List;
import java.util.Map;

import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionValue;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.TypeMisMatch;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.UpdateOptionGroupGetConstructorError;

public abstract class OptionValueDef<T> {
    // Value type
    private Class<T> type;
    // Default value
    private T defaultValue = null;
    // Enum des authorized values
    private List<T> authorizedValues = null;

    public OptionValueDef(Class<T> type) {
        this.type = type;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void addAuthorizedValues(T authorizedValues) {
        this.authorizedValues.add(authorizedValues);
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void customVerifyValue(T value) throws InvalidUpdateOptionValue {
        return;
    }

    public T validateAndCastOptionValue(Object value) throws TypeMisMatch, UpdateOptionGroupGetConstructorError, InvalidUpdateOptionValue {
        if (value == null) {
            return null;
        }
        if (authorizedValues != null) {
            try {
            } catch (Exception e) {
                throw new InvalidUpdateOptionValue(value.toString(), authorizedValues.getClass().getName(), e);
            }
        }
        if (ConditionsGroup.class.isAssignableFrom(type)) {
            if (!(value instanceof Map)) {
                throw new TypeMisMatch(value.getClass().getName(), Map.class.getName());
            }
            try {
                return type.getConstructor(Map.class).newInstance(value);
            } catch (Exception e) {
                throw new UpdateOptionGroupGetConstructorError(type.getName(), e);
            }
        }
        if (type.isInstance(value)) {
            customVerifyValue(type.cast(value));
            return type.cast(value);
        } else {
            throw new TypeMisMatch(value.getClass().getName(), type.getName());
        }
    }
}
