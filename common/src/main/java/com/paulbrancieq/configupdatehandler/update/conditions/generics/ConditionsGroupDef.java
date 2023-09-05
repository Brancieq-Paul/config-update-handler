package com.paulbrancieq.configupdatehandler.update.conditions.generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConditionsGroupDef {
    private Map<String, OptionValueDef<?>> defMap = new HashMap<String, OptionValueDef<?>>();

    // Default constructor
    public ConditionsGroupDef() {
    }

    // Constructor from existing OptionsGroupDef
    public ConditionsGroupDef(ConditionsGroupDef def) {
        for (String optionName : def.getOptionNames()) {
            set(optionName, def.get(optionName));
        }
    }

    public void set(String name, OptionValueDef<?> value) {
        defMap.put(name, value);
    }

    public OptionValueDef<?> get(String name) {
        return defMap.get(name);
    }

    public List<String> getOptionNames() {
        return new ArrayList<String>(defMap.keySet());
    }
}
