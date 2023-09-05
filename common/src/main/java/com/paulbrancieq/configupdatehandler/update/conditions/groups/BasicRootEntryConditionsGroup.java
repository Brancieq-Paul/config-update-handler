package com.paulbrancieq.configupdatehandler.update.conditions.groups;

import java.util.Map;

import com.paulbrancieq.configupdatehandler.Constants;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.generics.ConditionsGroupDef;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.commons.StringOptionValueUniqueDef;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.specifics.EntryPathOptionValueDef;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.specifics.FilePathOptionValueDef;

public class BasicRootEntryConditionsGroup extends BasicReplaceableDefaultableEntryConditionsGroup {
    protected static ConditionsGroupDef def = new ConditionsGroupDef(BasicReplaceableDefaultableEntryConditionsGroup.def) {
        {
            // Expected type (should always be overridden by subclasses because each one have unique expected type)
            set(Constants.CUH.UpdateFile.ConditionsFields.Name.EXPECTED_ENTRY_TYPE, new StringOptionValueUniqueDef(""));
            // File path
            set(Constants.CUH.UpdateFile.ConditionsFields.Name.FILE_PATH, new FilePathOptionValueDef());
            // Entry path
            set(Constants.CUH.UpdateFile.ConditionsFields.Name.ENTRY_PATH, new EntryPathOptionValueDef());
        }
    };

    public BasicRootEntryConditionsGroup(ConditionsGroupDef def, Map<String, Object> conditions) throws InvalidUpdateOptionGroup {
        super(def, conditions);
    }

    // Getters
    public String getFilePath() {
        return (String) get(Constants.CUH.UpdateFile.ConditionsFields.Name.FILE_PATH);
    }

    public String getEntryPath() {
        return (String) get(Constants.CUH.UpdateFile.ConditionsFields.Name.ENTRY_PATH);
    }
}
