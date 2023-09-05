package com.paulbrancieq.configupdatehandler.exeptions.Backup;

import com.paulbrancieq.configupdatehandler.exeptions.CUHExeption;

public class CantApplyBackup extends CUHExeption {
    public CantApplyBackup(String backup_name) {
        super("Can't apply backup: " + backup_name);
    }
    public CantApplyBackup(String backup_name, Throwable cause) {
        super("Can't apply backup: " + backup_name, cause);
    }
}
