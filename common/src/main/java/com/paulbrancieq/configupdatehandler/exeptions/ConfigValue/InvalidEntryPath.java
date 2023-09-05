package com.paulbrancieq.configupdatehandler.exeptions.ConfigValue;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class InvalidEntryPath extends CUHExeption {
    public InvalidEntryPath(String path) {
        super("Invalid entry path: " + path);
    }

    public InvalidEntryPath(String path, Throwable cause) {
        super("Invalid entry path: " + path, cause);
    }
}
