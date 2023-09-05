package com.paulbrancieq.configupdatehandler.update.conditions.valuesdef.basics;

import java.util.List;

import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.InvalidUpdateOptionValue;
import com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile.TypeMisMatch;
import com.paulbrancieq.configupdatehandler.update.conditions.generics.OptionValueDef;

@SuppressWarnings("rawtypes")
public class ListOptionValueDef<T> extends OptionValueDef<List> {

    Class<T> listElemlType;

    public ListOptionValueDef(Class<T> listElemlType) {
        super(List.class);
        this.listElemlType = listElemlType;
    }

    @Override
    public void customVerifyValue(List value) throws InvalidUpdateOptionValue {
        for (Object elem : value) {
            try {
                if (!listElemlType.isInstance(elem)) {
                    throw new TypeMisMatch(value.getClass().getName(), listElemlType.getName());
                }
            } catch (TypeMisMatch e) {
                throw new InvalidUpdateOptionValue(elem.toString(), listElemlType.getName(), e);
            }
        }
    }
}
