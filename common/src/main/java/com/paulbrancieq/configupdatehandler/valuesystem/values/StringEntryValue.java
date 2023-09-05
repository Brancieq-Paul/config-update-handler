package com.paulbrancieq.configupdatehandler.valuesystem.values;

import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.Value;
import com.paulbrancieq.configupdatehandler.valuesystem.values.bases.ValuesCategory;

public class StringEntryValue extends Value<String> {
    
    public StringEntryValue(String value) {
        super(value);
    }

    @Override
    protected void setValueCategory() {
        this.category = ValuesCategory.ENTRY;
    }
}
