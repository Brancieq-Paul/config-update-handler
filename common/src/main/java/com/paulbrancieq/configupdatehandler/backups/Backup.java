package com.paulbrancieq.configupdatehandler.backups;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import com.paulbrancieq.configupdatehandler.exeptions.Backup.BackupAlreadyExist;
import com.paulbrancieq.configupdatehandler.exeptions.Backup.BackupDirectoryDoesNotExist;
import com.paulbrancieq.configupdatehandler.exeptions.Backup.CantApplyBackup;
import com.paulbrancieq.configupdatehandler.exeptions.Backup.CantCopyFileToBackup;

public class Backup {
    // Backup identifiers
    private String backup_name;
    private String backups_directory;
    private String path;
    private String backup_mcdirectory_path;

    // Backup var
    ArrayList<String> files_to_remove = new ArrayList<String>();

    // Init methods

    public Backup(String backups_directory, String backup_name) throws BackupDirectoryDoesNotExist, BackupAlreadyExist {
        this.backup_name = backup_name;
        this.backups_directory = backups_directory;
        generateBackupPath();
        // Backups directory should exist
        if (!Path.of(backups_directory).toFile().exists()) {
            throw new BackupDirectoryDoesNotExist("Backup directory does not exist: " + backups_directory);
        }
        // If backup directory does not exist, create it
        if (!(backupWithSameNameAlreadyExists())) {
            Path.of(this.path).toFile().mkdirs();
            Path.of(this.backup_mcdirectory_path).toFile().mkdirs();
        } else {
            throw new BackupAlreadyExist("Backup already exist: " + backup_name);
        }
    }

    public void generateBackupPath() {
        this.path = Path.of(backups_directory, backup_name).toString();
        this.backup_mcdirectory_path = Path.of(backups_directory, backup_name, "mcdir").toString();
    }

    public Boolean backupWithSameNameAlreadyExists() {
        if (backupExists(backups_directory, backup_name)) {
            return true;
        }
        return false;
    }

    // Getters

    public String getBackupName() {
        return backup_name;
    }

    public String getBackupsDirectory() {
        return backups_directory;
    }

    // Setters

    public void renameBackup(String new_backup_name) {
        // Rename directory
        Path.of(backups_directory, this.backup_name).toFile()
                .renameTo(Path.of(backups_directory, new_backup_name).toFile());
        // Set new backup name
        this.backup_name = new_backup_name;
        // Generate new backup path
        generateBackupPath();
    }

    // Backup methods

    public void addFileToRemove(String mc_relative_file_path) {
        files_to_remove.add(mc_relative_file_path);
    }

    // WARNING: Adding a directory does not add its content
    public void addToBackup(String absolute_file_path, String mc_relative_file_path) throws CantCopyFileToBackup {
        try {
            // If absolute file path is not a directory, copy it to the backup
            if (!Path.of(absolute_file_path).toFile().isDirectory()) {
                Files.copy(Path.of(absolute_file_path), Path.of(this.backup_mcdirectory_path, mc_relative_file_path),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new CantCopyFileToBackup(absolute_file_path, mc_relative_file_path, e);
        }
    }

    public void applyBackup(String source_mc_directory_path) throws CantApplyBackup {
        // For each file to remove
        for (String file_to_remove : files_to_remove) {
            // Remove file
            Path.of(source_mc_directory_path, file_to_remove).toFile().delete();
        }
        // Copy all files of this.backup_mcdirectory_path and subdirectories to
        // source_mc_directory_path
        try {
            Files.walk(Path.of(this.backup_mcdirectory_path)).forEach(backup_file_path -> {
                try {
                    // Get backup_file_path relative to this.backup_mcdirectory_path
                    String backup_file_path_relative = Path.of(this.backup_mcdirectory_path)
                            .relativize(backup_file_path).toString();
                    Files.copy(backup_file_path, Path.of(source_mc_directory_path, backup_file_path_relative),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            throw new CantApplyBackup(backup_name, e);
        }
    }

    // Static methods

    public static Boolean backupExists(String backups_directory, String backup_name) {
        if (Path.of(backups_directory, backup_name).toFile().exists()) {
            return true;
        }
        return false;
    }

    public static void removeBackup(String backups_directory, String backup_name) {
        Path.of(backups_directory, backup_name).toFile().delete();
    }
}
