package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.SS13Exercise;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.TiScriptExercise;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Test;

/**
 * Created by Andreas Braun on 14.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class WriteToTexFileTest {

    @Test
    public void TexFileTestExercise() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("TexFileTestExercise: TexFileTestExercise");
        GrammarPyramidWrapper wrapper = SS13Exercise.SS13_GRAMMAR_PYRAMID_WRAPPER;
        ExerciseLatex exerciseLatex = new ExerciseLatex(wrapper.getGrammar(), wrapper.getPyramid());
        System.out.println(exerciseLatex.toString());
        WriteToTexFile.writeToTexFile("exerciseLatex", exerciseLatex.toString());

    }

}