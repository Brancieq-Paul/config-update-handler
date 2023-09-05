package com.paulbrancieq.configupdatehandler.valuesystem.consumers.paths;

import com.paulbrancieq.configupdatehandler.exeptions.ConsumerAlreadyFinished;

public abstract class PathConsumer {
    protected String sep;
    protected String path;
    protected String[] pathParts;
    protected String[] pathPartsLeft;

    // Constructor
    protected PathConsumer(String sep, String path) {
        this.sep = sep;
        this.path = path;
        this.separePathParts();
        this.pathPartsLeft = this.pathParts.clone();
    }

    // Copy constructor
    protected PathConsumer(PathConsumer pathConsumer, Boolean resetPathPartsLeft) {
        this.sep = pathConsumer.sep;
        this.path = pathConsumer.path;
        this.pathParts = pathConsumer.pathParts.clone();
        if (resetPathPartsLeft) {
            this.pathPartsLeft = pathConsumer.pathParts.clone();
        } else {
            this.pathPartsLeft = pathConsumer.pathPartsLeft.clone();
        }
    }

    protected abstract void separePathParts();

    public Boolean isFinished() {
        return this.pathPartsLeft.length == 0;
    }

    public String consume() throws ConsumerAlreadyFinished {
        if (this.isFinished()) {
            throw new ConsumerAlreadyFinished();
        }
        String result = this.pathPartsLeft[0];
        String[] temp = new String[this.pathPartsLeft.length - 1];
        System.arraycopy(this.pathPartsLeft, 1, temp, 0, this.pathPartsLeft.length - 1);
        this.pathPartsLeft = temp;
        return result;
    }

    // Getters

    // get path
    public String getPath() {
        return this.path;
    }
}
