package com.paulbrancieq.configupdatehandler.valuesystem.values;

import java.util.ArrayList;
import java.util.List;

import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.IncoherentValueUpdate;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.IterableValue;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.ValueContainer;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.ValuesCategory;

public class ListEntryValue extends IterableValue<List<ValueContainer>> {

    public ListEntryValue(List<Object> value) {
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

    protected Value<?> createConfigValueFromRawValue(Object raw_value) {
        return this.createSubValueFromRawValue(raw_value, ValuesCategory.ENTRY);
    }

    @Override
    protected void setValueCategory() {
        this.category = ValuesCategory.ENTRY;
    }

    @Override
    protected void verifyCategoryCoherence(Value<?> other) throws IncoherentValueUpdate {
        // Value must be an entry
        if (other.getValueCategory() != ValuesCategory.ENTRY) {
            throw new IncoherentValueUpdate("Value in Object entry must be an entry, not a file or a directory");
        }
    }
}
