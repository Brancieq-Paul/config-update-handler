package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class TypeMisMatch extends CUHExeption {
    public TypeMisMatch(String option_type, String expected_type) {
        super(option_type + " should be " + expected_type);
    }
    public TypeMisMatch(String option_type, String expected_type, Throwable cause) {
        super(option_type + " should be " + expected_type, cause);
    }
}
