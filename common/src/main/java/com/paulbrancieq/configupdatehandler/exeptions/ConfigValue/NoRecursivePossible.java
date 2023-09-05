package com.paulbrancieq.configupdatehandler.exeptions.ConfigValue;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class NoRecursivePossible extends CUHExeption {
    public NoRecursivePossible() {
        super("No recursive possible");
    }

    public NoRecursivePossible(Throwable cause) {
        super("No recursive possible", cause);
    }
}
