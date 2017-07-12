package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.pyramid.Pyramid;
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
        Word word = SS13Exercise.SS13_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = SS13Exercise.SS13_SET_VARK;
        Pyramid pyramid = new Pyramid(setVarKMatrix.getSetV(), word);
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        WriteToTexFile.writeToMainTexFile("pyramidLatex");
        WriteToTexFile.writeToTexFile("pyramidLatex", CenteredTikzPicture.beginToString() +
                pyramidLatex.pyramidToTex() + CenteredTikzPicture.endToString());

    }

    @Test
    public void TexFileTest2() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("WriteToTexFileTest2: Generating latex code directory structure.");
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        WriteToTexFile.writeToMainTexFile("pyramidLatex");
        WriteToTexFile.writeToTexFile("pyramidLatex", CenteredTikzPicture.beginToString() +
                pyramidLatex.pyramidToTex() + CenteredTikzPicture.endToString());
    }

    @Test
    public void TexFileTest3() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator CYK: algorithmAdvanced with input Grammar from the TI1 script");
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        System.out.println(setVarKMatrix.getStringToPrintAsUpperTriangularMatrix());
        System.out.println(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        System.out.println(pyramidLatex.toString());
        WriteToTexFile.writeToMainTexFile("scriptPyramid");
        WriteToTexFile.writeToTexFile("scriptPyramid", CenteredTikzPicture.beginToString() +
                pyramidLatex.pyramidToTex() + CenteredTikzPicture.endToString());

    }

}