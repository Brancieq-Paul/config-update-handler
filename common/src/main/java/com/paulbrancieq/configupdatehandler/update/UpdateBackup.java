package com.paulbrancieq.configupdatehandler.update;

import com.paulbrancieq.configupdatehandler.Constants;
import com.paulbrancieq.configupdatehandler.Services;
import com.paulbrancieq.configupdatehandler.backups.Backup;
import com.paulbrancieq.configupdatehandler.exeptions.Backup.BackupAlreadyExist;
import com.paulbrancieq.configupdatehandler.exeptions.Backup.BackupDirectoryDoesNotExist;
import com.paulbrancieq.configupdatehandler.exeptions.Backup.CantApplyBackup;
import com.paulbrancieq.configupdatehandler.exeptions.Backup.CantCopyFileToBackup;

public class UpdateBackup {
    // Update backup
    private static Backup backup;
    // Bacup created
    private static boolean backup_created = false;
    
    // Init update backup
    public static void initUpdateBackup() throws BackupDirectoryDoesNotExist, BackupAlreadyExist {
        backup = new Backup(Constants.CUH.Paths.Absolutes.BACKUPS_DIRECTORY.toString(), "latest");
        backup_created = true;
    }

    // Backup actions
    public static void addFileToRemove(String mc_relative_path) {
        backup.addFileToRemove(mc_relative_path);
    }

    public static void addToBackup(String absolute_file_path, String mc_relative_path) throws CantCopyFileToBackup {
        backup.addToBackup(absolute_file_path, mc_relative_path);
    }

    public static Boolean backupCreated() {
        return backup_created;
    }

    // Apply backup
    public static void applyBackup() throws CantApplyBackup {
        backup.applyBackup(Services.PLATFORM.getGameDirectory().toString());
    }

    // Rename backup
    public static void renameBackup(String new_backup_name) {
        backup.renameBackup(new_backup_name);
    }
}
