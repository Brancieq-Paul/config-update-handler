package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class InvalidUpdateOptionName extends UpdateFileExeption {

    public InvalidUpdateOptionName(String option_name) {
        super("Invalid update option name: " + option_name);
    }

    public InvalidUpdateOptionName(String option_name, Throwable e) {
        super("Invalid update option name: " + option_name, e);
    }
}
