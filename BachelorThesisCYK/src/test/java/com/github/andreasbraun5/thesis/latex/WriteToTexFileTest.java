package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.exercise.Exercise;
import com.github.andreasbraun5.thesis.exercise.ExercisesInputReader;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.main.ThesisDirectory;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.util.*;
import org.junit.Assert;
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
        ThesisDirectory.initPaths();
        //Exercise exercise = ExercisesInputReader.read2();

        //ExerciseLatex exerciseLatex = new ExerciseLatex(exercise.getGrammar(), exercise.getPyramid());
        //System.out.println(exerciseLatex.toString());
        //WriteToTexFile.writeToTexFile("exerciseLatex", exerciseLatex.toString());
        //String execString = "cmd /c start " + new File(ThesisDirectory.EXERCISE.path).getAbsolutePath() +
        //        "\\CreateExercise.bat";
        //Runtime runtime = Runtime.getRuntime();
        //runtime.exec(execString);
    }

    /*

        // Same Variable Start
        System.out.println(exercise.getGrammar().getVariableStart());
        System.out.println(TiScriptExercise.SCRIPT_GRAMMAR.getVariableStart());
        Assert.assertTrue(exercise.getGrammar().getVariableStart().equals(TiScriptExercise.SCRIPT_GRAMMAR.getVariableStart()));

        // Same word
        System.out.println(exercise.getWord());
        System.out.println(TiScriptExercise.SCRIPT_EXAMPLE_WORD);
        Assert.assertTrue(exercise.getWord().equals(TiScriptExercise.SCRIPT_EXAMPLE_WORD));

        // Same productions
        System.out.println(exercise.getGrammar());
        System.out.println(TiScriptExercise.SCRIPT_GRAMMAR);
        Grammar grammar = exercise.getGrammar();
        Grammar grammar1 = TiScriptExercise.SCRIPT_GRAMMAR;
        List<Production> prodList = grammar.getProductionsAsList();
        List<Production> prodList1 = grammar1.getProductionsAsList();
        boolean same = false;
        for (Production prod : prodList) {
            same = prodList1.contains(prod);
        }
        for (Production prod : prodList1) {
            same = prodList.contains(prod);
        }
        Assert.assertTrue(same);

        // TODO Different calculated pyramids
        System.out.println(exercise.getPyramid());
        System.out.println(TiScriptExercise.SCRIPT_GRAMMAR_PYRAMID_WRAPPER.getPyramid());

        System.out.println(CYK.calculateSetVAdvanced(TiScriptExercise.SCRIPT_GRAMMAR_PYRAMID_WRAPPER).getPyramid());
        GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder().build();
        grammarPyramidWrapper.setGrammar(exercise.getGrammar());
        grammarPyramidWrapper.setPyramid(exercise.getPyramid());
        System.out.println(CYK.calculateSetVAdvanced(grammarPyramidWrapper).getPyramid());
     */

}