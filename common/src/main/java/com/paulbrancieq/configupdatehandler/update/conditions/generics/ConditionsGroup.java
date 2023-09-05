package com.paulbrancieq.configupdatehandler.update.conditions.generics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.UpdateFileExeption;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionGroup;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionName;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionType;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.MissingMandatoryUpdateOption;

public abstract class ConditionsGroup {
    private Map<String, Object> options = new HashMap<String, Object>();
    protected static ConditionsGroupDef def = null;

    public ConditionsGroup(ConditionsGroupDef def, Map<String, Object> options) throws InvalidUpdateOptionGroup {
        // Get list of option names
        List<String> remaniningOptionNames = def.getOptionNames();
        try {
            // Parse options by key
            for (String optionName : options.keySet()) {
                // Check if option name is valid
                if (!remaniningOptionNames.contains(optionName)) {
                    throw new InvalidUpdateOptionName(optionName);
                }
                // Set option value
                try {
                    this.options.put(optionName, def.get(optionName).validateAndCastOptionValue(options.get(optionName)));
                } catch (Exception e) {
                    throw new InvalidUpdateOptionType(optionName, e);
                }
                // Remove optionName from remaniningOptionNames
                remaniningOptionNames.remove(optionName);
            }
            // For all remaining option names, set default value
            for (String optionName : remaniningOptionNames) {
                this.options.put(optionName, def.get(optionName).getDefaultValue());
                if (this.options.get(optionName) == null) {
                    throw new MissingMandatoryUpdateOption(optionName);
                }
            }
        } catch (UpdateFileExeption e) {
            throw new InvalidUpdateOptionGroup(def.getClass().getName(), e);
        }
    }

    protected Object get(String name) {
        return options.get(name);
    }
}
