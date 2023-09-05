package com.paulbrancieq.configupdatehandler.valuesystem.values;

import java.util.ArrayList;
import java.util.List;

import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.IterableValue;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.ValueContainer;

public abstract class ListValue extends IterableValue<List<ValueContainer>> {
    public ListValue(List<Object> value) {
        super();
        // Create list of ConfigValueContainer
        List<ValueContainer> container_list = new ArrayList<ValueContainer>();
        for (Object elem : value) {
            Value<?> subvalue = createConfigValueFromRawValue(elem);
            ValueContainer container = new ValueContainer(subvalue);
            container_list.add(container);
        }
        // Set value
        this.setValue(container_list);
    }

    protected abstract Value<?> createConfigValueFromRawValue(Object raw_value);

    @Override
    public List<Object> getWritableValue() {
        List<Object> writable_value = new ArrayList<Object>();
        for (ValueContainer entry : this.getValue()) {
            writable_value.add(entry.getValue().getWritableValue());
        }
        return writable_value;
    }
}
