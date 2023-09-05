package com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.commons;

import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.basics.StringOptionValueDef;

public class StringOptionValueUniqueDef extends StringOptionValueDef {

    public StringOptionValueUniqueDef(String unique_possibility) {
        super();
        this.addAuthorizedValues(unique_possibility);
    }
}
