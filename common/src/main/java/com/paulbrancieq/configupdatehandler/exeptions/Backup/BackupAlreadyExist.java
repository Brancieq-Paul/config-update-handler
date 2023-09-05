package com.paulbrancieq.configupdatehandler.exeptions.Backup;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class BackupAlreadyExist extends CUHExeption {
    public BackupAlreadyExist(String backup_name) {
        super("Backup already exist: " + backup_name);
    }
}
