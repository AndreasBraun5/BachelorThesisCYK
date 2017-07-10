package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.*;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Andreas Braun on 14.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class WriteToTexFileTest {

    @Test
    public void TexFileTest() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("WriteToTexFileTest: Generating latex code into the directory structure.");

        int wordLength = 6;

        Set<VariableK>[][] setVTemp = Util.getInitialisedHashSetArray(wordLength, VariableK.class);
        // reconstructing example matrix
        setVTemp[0][0].add(new VariableK(new Variable("A"), 1));
        setVTemp[0][0].add(new VariableK(new Variable("B"), 1));
        setVTemp[0][1].add(new VariableK(new VariableStart("S"), 1));
        setVTemp[0][1].add(new VariableK(new Variable("B"), 1));
        setVTemp[0][3].add(new VariableK(new VariableStart("S"), 1));
        setVTemp[0][3].add(new VariableK(new Variable("C"), 1));
        setVTemp[0][3].add(new VariableK(new Variable("A"), 1));
        setVTemp[0][3].add(new VariableK(new Variable("A"), 2));
        setVTemp[0][4].add(new VariableK(new VariableStart("S"), 4));
        setVTemp[0][5].add(new VariableK(new VariableStart("S"), 1));
        setVTemp[0][5].add(new VariableK(new VariableStart("S"), 4));
        setVTemp[0][5].add(new VariableK(new Variable("C"), 1));
        setVTemp[0][5].add(new VariableK(new Variable("C"), 4));
        setVTemp[0][5].add(new VariableK(new Variable("A"), 1));

        setVTemp[1][1].add(new VariableK(new Variable("A"), 2));
        setVTemp[1][1].add(new VariableK(new Variable("B"), 2));
        setVTemp[1][2].add(new VariableK(new VariableStart("S"), 2));
        setVTemp[1][3].add(new VariableK(new Variable("C"), 2));
        setVTemp[1][3].add(new VariableK(new VariableStart("S"), 2));
        setVTemp[1][3].add(new VariableK(new Variable("A"), 2));
        setVTemp[1][4].add(new VariableK(new VariableStart("S"), 4));
        setVTemp[1][5].add(new VariableK(new Variable("C"), 4));
        setVTemp[1][5].add(new VariableK(new VariableStart("S"), 4));

        setVTemp[2][2].add(new VariableK(new Variable("A"), 3));
        setVTemp[2][3].add(new VariableK(new VariableStart("S"), 3));
        setVTemp[2][3].add(new VariableK(new Variable("C"), 3));
        setVTemp[2][4].add(new VariableK(new VariableStart("S"), 4));

        setVTemp[3][3].add(new VariableK(new Variable("C"), 4));
        setVTemp[3][4].add(new VariableK(new VariableStart("S"), 4));

        setVTemp[4][4].add(new VariableK(new Variable("A"), 5));
        setVTemp[4][4].add(new VariableK(new Variable("B"), 5));
        setVTemp[4][5].add(new VariableK(new Variable("C"), 5));
        setVTemp[4][5].add(new VariableK(new VariableStart("S"), 5));
        setVTemp[4][5].add(new VariableK(new Variable("A"), 5));

        setVTemp[5][5].add(new VariableK(new Variable("C"), 6));

        Word word = new Word("bbacbc");
        SetVarKMatrix setVarKMatrixSolution = new SetVarKMatrix(wordLength, word).setSetV(setVTemp);
        Pyramid pyramid = setVarKMatrixSolution.getAsPyramid();
        CenteredTikzPicture tikz = new CenteredTikzPicture();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        WriteToTexFile.writeToMainTexFile("pyramidLatex");
        WriteToTexFile.writeToTexFile("pyramidLatex", tikz.beginToString() + pyramidLatex.pyramidToTex() + tikz.endToString());

    }

    @Test
    public void TexFileTest2() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("WriteToTexFileTest2: Generating latex code directory structure.");
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        CenteredTikzPicture tikz = new CenteredTikzPicture();
        WriteToTexFile.writeToMainTexFile("pyramidLatex");
        WriteToTexFile.writeToTexFile("pyramidLatex", tikz.beginToString() + pyramidLatex.pyramidToTex() + tikz.endToString());
    }

    @Test
    public void TexFileTest3() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator CYK: algorithmAdvanced with input Grammar from the TI1 script");
        Grammar grammar = SS13Exercise.SS13_GRAMMAR;
        Word word = SS13Exercise.SS13_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = SS13Exercise.SS13_SET_VARK;
        System.out.println(setVarKMatrix.getStringToPrintAsUpperTriangularMatrix());
        System.out.println(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        System.out.println(pyramidLatex.toString());
        CenteredTikzPicture tikz = new CenteredTikzPicture();
        WriteToTexFile.writeToMainTexFile("scriptPyramid");
        WriteToTexFile.writeToTexFile("scriptPyramid", tikz.beginToString() + pyramidLatex.pyramidToTex() + tikz.endToString());

    }

}