package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class MissingExpectedType extends UpdateFileExeption {
    public MissingExpectedType() {
        super("Missing expected type for update definition");
    }
}
