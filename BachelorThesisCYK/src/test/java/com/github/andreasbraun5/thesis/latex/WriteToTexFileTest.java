package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.*;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

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
        GrammarPyramidWrapper wrapper = SS12Exercise.SS12_GRAMMAR_PYRAMID_WRAPPER;
        int wordLenght = wrapper.getPyramid().getWord().getWordLength();
        TreeLatex treeLatex = TreeLatex.generateRandomTree(wrapper.getPyramid(), wrapper.getPyramid().getCellK(wordLenght-1, 0));
        ExerciseLatex exerciseLatex = new ExerciseLatex(wrapper.getGrammar(), wrapper.getPyramid(), treeLatex);
        //ExerciseLatex exerciseLatex = new ExerciseLatex(wrapper.getGrammar(), wrapper.getPyramid(), SS12Exercise.SS12_TREE);
        System.out.println(exerciseLatex.toString());
        WriteToTexFile.writeToTexFile("exerciseLatex", exerciseLatex.toString());
    }

}