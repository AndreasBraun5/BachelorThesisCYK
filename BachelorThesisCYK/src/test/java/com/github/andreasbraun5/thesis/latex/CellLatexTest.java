package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.util.SS13Exercise;
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
        SetVarKMatrix setVarKMatrix = SS13Exercise.SS13_SET_VARK;
        System.out.print(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        PyramidLatex pyramidLatex = new PyramidLatex(pyramid);
        System.out.print(pyramidLatex.pyramidToTex());
    }

}