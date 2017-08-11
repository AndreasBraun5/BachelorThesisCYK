package com.github.andreasbraun5.thesis.main;

import java.io.File;

/**
 * Created by AndreasBraun on 04.06.2017.
 */
public enum ThesisDirectory {
    LOGS("logs"),
    EXERCISE("exercise"),
    EXAMPLES("examples"),
    BEST("bestOnes");

    public final String path;

    ThesisDirectory(String path) {
        this.path = path;
    }

    public File fileAsTxt(String fileName) {
        return new File(this.path + "/" + fileName + ".txt");
    }

    public File fileAsTex(String fileName) {
        return new File(this.path + "/" + fileName + ".tex");
    }

    public void initPath() {
        File logDir = new File(path);
        if (logDir.exists()) {
            if (logDir.isFile()) {
                throw new RuntimeException("directory " + this.path + " was a fileAsTxt");
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
