package com.paulbrancieq.configupdatehandler.exeptions.InvalidUpdateFile;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class UpdateFileExeption extends CUHExeption {

    public UpdateFileExeption(String message) {
        super(message);
    }
    
    public UpdateFileExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
