package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class MissingMandatoryUpdateOption extends UpdateFileExeption {
    public MissingMandatoryUpdateOption(String option_name) {
        super("Missing mandatory update option: " + option_name);
    }
}
