package com.paulbrancieq.configupdatehandler.valuesystem.values;

import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.ValuesCategory;

public class IntegerEntryValue extends Value<Integer> {

    public IntegerEntryValue(Integer value) {
        super(value);
    }

    @Override
    protected void setValueCategory() {
        this.category = ValuesCategory.ENTRY;
    }
}
