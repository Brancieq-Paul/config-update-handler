package com.paulbrancieq.configupdatehandler.exeptions;

public class CUHExeption extends Exception {
    public CUHExeption(String message) {
        super(message);
    }
    
    public CUHExeption(String message, Throwable e) {
        super(message, e);
    }
}
