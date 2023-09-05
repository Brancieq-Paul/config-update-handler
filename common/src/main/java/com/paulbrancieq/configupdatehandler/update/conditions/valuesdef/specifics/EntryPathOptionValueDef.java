package com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.specifics;

import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionValue;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.basics.StringOptionValueDef;

import java.util.regex.Pattern;

public class EntryPathOptionValueDef extends StringOptionValueDef {
    public EntryPathOptionValueDef() {
        super();
    }

    @Override
    public void customVerifyValue(String value) throws InvalidUpdateOptionValue {
        // Verify that path is a valid entry path (should correspond to this regex: (.+\.)*[^\.]+ )
        if (!Pattern.matches("(.+\\.)*[^\\.]+", value)) {
            throw new InvalidUpdateOptionValue(value, "Entry names separated by points (Example: name1.name2.name3)");
        }
    }
}
