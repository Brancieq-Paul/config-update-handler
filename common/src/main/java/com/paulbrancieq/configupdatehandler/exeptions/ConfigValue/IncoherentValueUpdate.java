package com.paulbrancieq.configupdatehandler.exeptions.ConfigValue;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class IncoherentValueUpdate extends CUHExeption {
    // Constructor just take a message
    public IncoherentValueUpdate(String message) {
        super(message);
    }

    // Constructor take a message and a cause
    public IncoherentValueUpdate(String message, Throwable cause) {
        super(message, cause);
    }
}
