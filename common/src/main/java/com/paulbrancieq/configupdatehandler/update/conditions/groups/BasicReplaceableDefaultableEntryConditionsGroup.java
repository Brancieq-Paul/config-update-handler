package com.paulbrancieq.configupdatehandler.update.conditions.groups;

import java.util.Map;

import com.paulbrancieq.configupdatehandler.Constants;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.generics.ConditionsGroup;
import com.paulbrancieq.configupdatehandler.update.conditions.generics.ConditionsGroupDef;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.basics.StringOptionValueDef;
import com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.specifics.RecursiveOptionValueDef;

public class BasicReplaceableDefaultableEntryConditionsGroup extends ConditionsGroup {

    protected static ConditionsGroupDef def = new ConditionsGroupDef() {
        {
            // Expected type not overriden because each subclass have unique expected type
            // Entry update rule
            StringOptionValueDef entryUpdateRule = new StringOptionValueDef();
            entryUpdateRule.addAuthorizedValues(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.REPLACE_RULE);
            entryUpdateRule.addAuthorizedValues(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.DEFAULT_RULE);
            entryUpdateRule.setDefaultValue(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.REPLACE_RULE);
            set(Constants.CUH.UpdateFile.ConditionsFields.Name.ENTRY_UPDATE_RULE, entryUpdateRule);
            // Recursive level
            RecursiveOptionValueDef recursiveLevel = new RecursiveOptionValueDef();
            recursiveLevel.setDefaultValue(-1);
            set(Constants.CUH.UpdateFile.ConditionsFields.Name.RECURSIVE_LEVEL, recursiveLevel);
        }
    };

    public BasicReplaceableDefaultableEntryConditionsGroup(ConditionsGroupDef def, Map<String, Object> conditions) throws InvalidUpdateOptionGroup {
        super(def, conditions);
    }

    public boolean updateRuleIsReplace() {
        return get(Constants.CUH.UpdateFile.ConditionsFields.Name.ENTRY_UPDATE_RULE).equals(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.REPLACE_RULE);
    }

    public boolean updateRuleIsDefault() {
        return get(Constants.CUH.UpdateFile.ConditionsFields.Name.ENTRY_UPDATE_RULE).equals(Constants.CUH.UpdateFile.ConditionsFields.PossibleValues.DEFAULT_RULE);
    }

    public Integer getRecursiveLevel() {
        return (Integer) get(Constants.CUH.UpdateFile.ConditionsFields.Name.RECURSIVE_LEVEL);
    }
}
