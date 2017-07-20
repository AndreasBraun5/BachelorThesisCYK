package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.resultcalculator.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by AndreasBraun on 04.06.2017.
 */
public enum ThesisDirectory {
    LOGS("logs"),
    EXERCISE("Exercise"),
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

    public static void initBatFiles() {
        File test = new File(ThesisDirectory.EXERCISE.path, "CreateExercise.bat");
        try (PrintWriter out = new PrintWriter(test)) {
            out.println("cd C:\\\\Users\\\\AndreasBraun\\\\Documents\\\\BachelorThesis\\\\BachelorThesisCYK\\\\Exercise\\\\\n" +
                    "pdflatex.exe exerciseLatex.tex");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
