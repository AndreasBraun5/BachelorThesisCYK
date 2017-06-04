package com.github.andreasbraun5.thesis.main;

import java.io.File;

/**
 * Created by AndreasBraun on 04.06.2017.
 */
public enum ThesisDirectory {
    LOGS("logs"),
    TEX("tex"),
    EXAMPLES("examples");

    public final String path;

    ThesisDirectory(String path) {
        this.path = path;
    }

    public File file(String fileName) {
        return new File(this.path + "/" + fileName);
    }

    public void initPath() {
        File logDir = new File(path);
        if (logDir.exists()) {
            if (logDir.isFile()) {
                throw new RuntimeException("directory " + this.path + " was a file");
            }
        } else if (!logDir.mkdirs()) {
            throw new RuntimeException("could not create directory" + this.path);
        }
    }

    public static void initPaths() {
        for (ThesisDirectory dir : ThesisDirectory.values()) {
            dir.initPath();
        }
    }

}
