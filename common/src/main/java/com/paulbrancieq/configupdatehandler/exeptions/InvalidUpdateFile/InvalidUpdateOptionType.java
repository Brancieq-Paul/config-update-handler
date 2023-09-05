package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class InvalidUpdateOptionType extends UpdateFileExeption {
    public InvalidUpdateOptionType(String option_name) {
        super("Invalid update option type for option: " + option_name);
    }

    public InvalidUpdateOptionType(String option_name, Throwable e) {
        super("Invalid update option type for option: " + option_name, e);
    }
}
