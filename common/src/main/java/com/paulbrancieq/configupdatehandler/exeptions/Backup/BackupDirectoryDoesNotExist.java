package com.paulbrancieq.configupdatehandler.exeptions.Backup;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class BackupDirectoryDoesNotExist extends CUHExeption {
    public BackupDirectoryDoesNotExist(String backup_directory) {
        super("Backup directory does not exist: " + backup_directory);
    }
}
