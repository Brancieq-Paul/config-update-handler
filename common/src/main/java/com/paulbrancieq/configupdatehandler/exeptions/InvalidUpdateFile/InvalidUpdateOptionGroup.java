package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class InvalidUpdateOptionGroup extends UpdateFileExeption {

    public InvalidUpdateOptionGroup(String option_group_name, Throwable e) {
        super("Invalid update option group: " + option_group_name, e);
    }

    public InvalidUpdateOptionGroup(String option_group_name) {
        super("Invalid update option group: " + option_group_name);
    }
}
