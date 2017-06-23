package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class CellLatexTest {

    @Test
    public void cellToTexTest() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("CellLatexTest: Generating LaTeX code for the cells.");
        CellLatex cellLatex = new CellLatex(2, 2);
        cellLatex.centerX = 2;
        cellLatex.centerY = -0.5;
        Set<VariableK> setvk = new HashSet<>();
        setvk.add(new VariableK(new Variable("A"), 1));
        setvk.add(new VariableK(new Variable("B"), 2));
        setvk.add(new VariableK(new Variable("C"), 3));
        setvk.add(new VariableK(new Variable("D"), 4));
        setvk.add(new VariableK(new Variable("E"), 5));
        cellLatex.addVar(setvk);
        System.out.println(cellLatex.cellToTex());
    }


    @Test
    public void cellToTexTest2() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("CellLatexTest: Generating LaTeX code for the cells.");
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
        System.out.print(pyramidLatex.pyramidToTex());
    }

}