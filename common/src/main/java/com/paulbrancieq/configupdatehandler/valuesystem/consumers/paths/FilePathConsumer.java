package com.paulbrancieq.configupdatehandler.valuesystem.consumers.paths;

import com.paulbrancieq.configupdatehandler.Constants;

public class FilePathConsumer extends PathConsumer {
    public FilePathConsumer(String path) {
        super(Constants.PATH_SEPARATOR, path);
    }

    public FilePathConsumer(FilePathConsumer pathConsumer, Boolean resetPathPartsLeft) {
        super(pathConsumer, resetPathPartsLeft);
    }

    @Override
    protected void separePathParts() {
        this.pathParts = this.path.split(this.sep);
    }
}
