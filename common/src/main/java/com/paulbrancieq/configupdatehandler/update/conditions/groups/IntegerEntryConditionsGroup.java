package com.paulbrancieq.configupdatehandler.update.conditions.groups;

import java.util.Map;

import com.paulbrancieq.configupdatehandler.Constants;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.generics.ConditionsGroupDef;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.commons.StringOptionValueUniqueDef;

public class IntegerEntryConditionsGroup extends BasicRootEntryConditionsGroup {
    protected static ConditionsGroupDef def = new ConditionsGroupDef(BasicRootEntryConditionsGroup.def) {
        {
            // Expected type
            set (Constants.CUH.UpdateFile.ConditionsFields.Name.EXPECTED_ENTRY_TYPE, new StringOptionValueUniqueDef(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.INTEGER_ENTRY_TYPE));
        }
    };

    public IntegerEntryConditionsGroup(Map<String, Object> conditions) throws InvalidUpdateOptionGroup {
        super(def, conditions);
    }
}
