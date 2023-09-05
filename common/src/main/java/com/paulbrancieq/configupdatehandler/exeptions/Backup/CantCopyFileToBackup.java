package com.paulbrancieq.configupdatehandler.exeptions.Backup;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class CantCopyFileToBackup extends CUHExeption {
    public CantCopyFileToBackup(String file_name, String backup_name) {
        super("Can't copy file " + file_name + " to backup " + backup_name);
    }
    public CantCopyFileToBackup(String file_name, String backup_name, Throwable cause) {
        super("Can't copy file " + file_name + " to backup " + backup_name, cause);
    }
}
