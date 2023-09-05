package com.paulbrancieq.configupdatehandler.exeptions.ConfigValue;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class NoSubConfigValue extends CUHExeption {
    public NoSubConfigValue() {
        super("No sub config value found");
    }
}
