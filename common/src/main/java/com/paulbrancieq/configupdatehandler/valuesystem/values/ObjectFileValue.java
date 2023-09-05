package com.paulbrancieq.configupdatehandler.valuesystem.values;

import java.util.Map;

import com.paulbrancieq.configupdatehandler.exeptions.ConfigValue.IncoherentValueUpdate;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.ValuesCategory;

public abstract class ObjectFileValue extends ObjectValue {

    public ObjectFileValue() {
        super();
    }

    public ObjectFileValue(Map<Object, Object> value) {
        super(value);
    }

    protected Value<?> createSubValueFromRawValue(Object raw_value) {
        return this.createSubValueFromRawValue(raw_value, ValuesCategory.ENTRY);  
    }

    @Override
    protected void setValueCategory() {
        this.category = ValuesCategory.FILE;
    }

    @Override
    protected void verifyCategoryCoherence(Value<?> other_subValue) throws IncoherentValueUpdate {
        // Value must be an entry
        if (other_subValue.getValueCategory() != ValuesCategory.ENTRY) {
            throw new IncoherentValueUpdate("Value in Object entry must be an entry, not a file or a directory");
        }
    }
}
