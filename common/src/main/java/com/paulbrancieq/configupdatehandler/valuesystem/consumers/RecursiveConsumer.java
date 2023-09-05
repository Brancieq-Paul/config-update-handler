package com.paulbrancieq.configupdatehandler.valuesystem.consumers;

import com.paulbrancieq.configupdatehandler.exeptions.ConsumerAlreadyFinished;

public class RecursiveConsumer {
    Integer level;

    public RecursiveConsumer(Integer level) {
        this.level = level;
    }

    public Boolean isInfinite() {
        return level == -1;
    }

    public boolean isFinished() {
        return level == 0;
    }

    public void consume() throws ConsumerAlreadyFinished {
        if (isFinished()) {
            throw new ConsumerAlreadyFinished();
        }
        if (!this.isInfinite()) {
            this.level--;
        }
    }
}
