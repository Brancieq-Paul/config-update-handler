package com.paulbrancieq.configupdatehandler.exeptions.ConfigFile;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class BaseFileDoesNotExist extends CUHExeption {
    public BaseFileDoesNotExist(String path) {
        super("Base file does not exist: " + path);
    }
}
