package com.paulbrancieq.configupdatehandler.update.conditions;

import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidOptionContainer;
import com.paulbrancieq.configupdatehandler.update.conditions.generics.ConditionsGroup;

public class ConditionsContainer {
    public Class<? extends ConditionsGroup> type;
    public ConditionsGroup group;

    public ConditionsContainer(Class<? extends ConditionsGroup> type, ConditionsGroup group) {
        this.type = type;
        this.group = group;
    }

    public static ConditionsContainer create(Class<? extends ConditionsGroup> type, ConditionsGroup group) throws InvalidOptionContainer {
        // Verify that group is instance of type
        if (!type.isInstance(group)) {
            throw new InvalidOptionContainer(group.getClass().getName(), type.getName());
        }
        // Create new ConditionsContainer
        return new ConditionsContainer(type, group);
    }
}
