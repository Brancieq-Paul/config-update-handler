package com.paulbrancieq.configupdatehandler.valuesystem.values.bases;

public class ValueContainer {
    Value<?> value;

    public ValueContainer(Value<?> value) {
        this.value = value;
    }

    public Value<?> getValue() {
        return value;
    }

    public Class<?> getRawType() {
        return value.getRawType();
    }

    public void replaceValue(Value<?> other) {
        this.value = other;
    }
}
