package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Andreas Braun on 08.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class CenteredTikzPictureTest {

    @Test
    public void toStringTest() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("CenteredTikzPictureTest: Generating LaTeX code for TikzPicture.");

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
        System.out.print(setVarKMatrixSolution.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrixSolution.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        CenteredTikzPicture tikz = new CenteredTikzPicture();
        System.out.println(tikz.beginToString());
        System.out.print(pyramidLatex.pyramidToTex());
        System.out.println(tikz.endToString());
    }


}