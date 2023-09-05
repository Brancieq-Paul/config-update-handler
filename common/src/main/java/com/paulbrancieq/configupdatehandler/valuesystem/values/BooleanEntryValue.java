package com.paulbrancieq.configupdatehandler.valuesystem.values;

import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.ValuesCategory;

public class BooleanEntryValue extends Value<Boolean> {

    public BooleanEntryValue(Boolean value) {
        super(value);
    }

    @Override
    protected void setValueCategory() {
        this.category = ValuesCategory.ENTRY;
    }
}
