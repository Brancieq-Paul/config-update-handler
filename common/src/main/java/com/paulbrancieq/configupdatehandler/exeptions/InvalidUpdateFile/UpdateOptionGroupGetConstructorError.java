package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

public class UpdateOptionGroupGetConstructorError extends UpdateFileExeption {
    public UpdateOptionGroupGetConstructorError(String group_type) {
        super("Error getting constructor for option group: " + group_type);
    }

    public UpdateOptionGroupGetConstructorError(String group_type, Throwable e) {
        super("Error getting constructor for option group: " + group_type, e);
    }
}
