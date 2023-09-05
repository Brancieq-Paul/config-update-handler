package com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.specifics;

import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionValue;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.basics.IntegerOptionValueDef;

public class RecursiveOptionValueDef extends IntegerOptionValueDef {
    public RecursiveOptionValueDef() {
        super();
    }

    @Override
    public void customVerifyValue(Integer value) throws InvalidUpdateOptionValue {
        if (value < -1) {
            throw new InvalidUpdateOptionValue(value.toString(), "Recursive value must be -1 (infinite) or greater");
        }
    }
}
