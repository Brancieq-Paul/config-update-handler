package com.paulbrancieq.configupdatehandler.valuesystem.files;

import com.paulbrancieq.configupdatehandler.exeptions.Backup.CantCopyFileToBackup;
import com.paulbrancieq.configupdatehandler.update.UpdateBackup;

public interface IFile {

    public abstract void _write();

    // Get path
    public abstract ComposedPath getPath();

    // Write the file with generic comportement
    default void write() throws CantCopyFileToBackup {
        // Backup file
        UpdateBackup.addToBackup(this.getPath().getResultPath(), this.getPath().getRelativePath());
        // Write file
        this._write();
    }
}
