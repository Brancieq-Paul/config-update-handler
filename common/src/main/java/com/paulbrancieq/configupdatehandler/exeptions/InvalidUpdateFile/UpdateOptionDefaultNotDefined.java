package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class UpdateOptionDefaultNotDefined extends UpdateFileExeption {
    public UpdateOptionDefaultNotDefined(String option_name) {
        super("Invalid update option: " + option_name);
    }
}
