package com.paulbrancieq.configupdatehandler.exeptions.ConfigFile;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class FaildedFileCreation extends CUHExeption {
    // Constructor just take a message
    public FaildedFileCreation(String message) {
        super(message);
    }

    // Constructor take a message and a cause
    public FaildedFileCreation(String message, Throwable cause) {
        super(message, cause);
    }
}
