package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.main.ThesisDirectory;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.util.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas Braun on 14.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class WriteToTexFileTest {

    @Test
    public void TexFileTestExercise() throws IOException {
        System.out.println("");
        System.out.println("############################");
        System.out.println("TexFileTestExercise: TexFileTestExercise");
        GrammarPyramidWrapper wrapper = SS12Exercise.SS12_GRAMMAR_PYRAMID_WRAPPER;
        ExerciseLatex exerciseLatex = new ExerciseLatex(wrapper.getGrammar(), wrapper.getPyramid());
        System.out.println(exerciseLatex.toString());
        WriteToTexFile.writeToTexFile("exerciseLatex", exerciseLatex.toString());
        ThesisDirectory.initPaths();
        ThesisDirectory.initBatFiles();
        String execString = "cmd /c start " +
                "C:\\Users\\AndreasBraun\\Documents\\BachelorThesis\\BachelorThesisCYK\\exercise\\CreateExercise.bat";
        Runtime runtime = Runtime.getRuntime();
        runtime.exec(execString);
    }

}