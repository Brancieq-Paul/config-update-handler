package com.paulbrancieq.configupdatehandler.exeptions.ConfigFile;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class InvalidFileFormat extends CUHExeption {
    // Constructor just take a message
    public InvalidFileFormat(String message) {
        super(message);
    }

    // Constructor take a message and a cause
    public InvalidFileFormat(String message, Throwable cause) {
        super(message, cause);
    }
}
