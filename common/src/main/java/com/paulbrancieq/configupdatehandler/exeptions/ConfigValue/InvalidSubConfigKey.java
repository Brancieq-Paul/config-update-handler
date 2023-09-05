package com.paulbrancieq.configupdatehandler.exeptions.ConfigValue;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class InvalidSubConfigKey extends CUHExeption {
    public InvalidSubConfigKey(String key) {
        super("Invalid sub config key: " + key);
    }
}
