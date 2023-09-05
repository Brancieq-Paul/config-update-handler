package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class InvalidUpdateOptionValue extends UpdateFileExeption {
    public InvalidUpdateOptionValue(String option_value, String option_possible_values) {
        super("Invalid update option value: " + option_value + " possible values: " + option_possible_values);
    }

    public InvalidUpdateOptionValue(String option_value, String option_possible_values, Throwable e) {
        super("Invalid update option value: " + option_value + " possible values: " + option_possible_values, e);
    }   
}
