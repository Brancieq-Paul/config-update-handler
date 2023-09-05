package com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.specifics;

import com.paulbrancieq.configupdatehandler.Services;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionValue;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.basics.StringOptionValueDef;

public class FilePathOptionValueDef extends StringOptionValueDef {
    public FilePathOptionValueDef() {
        super();
        setDefaultValue("");
    }

    @Override
    public void customVerifyValue(String value) throws InvalidUpdateOptionValue {
        // Transform path for os compatibility
        value = Services.PLATFORM.transformPath(value);
        // Verify that path is a relative path
        if (!Services.PLATFORM.isRelativePath(value)) {
            throw new InvalidUpdateOptionValue(value, "MC directory relative paths only");
        }
    }
}
