package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.TiScriptExercise;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by Andreas Braun on 13.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarValidityCheckerTest { 
    @Test
    public void checkRightCellCombinationForced1() {
        // take a known old generated grammar, reconstruct it and check for the same result.
        // see folder C:\Users\AndreasBraun\Documents\BachelorThesis\BachelorThesisCYK\examples
    }

    @Test
    public void checkRightCellCombinationForcedForCell() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarValidityCheckerTest: checkRightCellCombinationForcedForCell");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        // Random cells are picked from the grammar, for easier construction of the cells.
        // Indexes of the cells are irrelevant for this check
        Pyramid pyramid = new Pyramid(setVarKMatrix.getSetV(), word);
        CellK cellDown = pyramid.getCellK(4,2); // contains B3
        CellK cellRight = pyramid.getCellK(5, 1); // contains D2
        CellK cellLeft = pyramid.getCellK(0,0); // contains N1, A1
        // in context of the grammar add a few Variables to the cells, so that in the end some of the vars in
        // cellDown force and some of the vars in cellDown don't force.
        System.out.println(grammar);
        List<VariableK> forcingVars = GrammarValidityChecker.
                checkRightCellCombinationForcedForCell(cellDown, cellRight, cellLeft, grammar);
    }

    @Test
    public void checkMaxNumberOfVarsPerCell() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarValidityCheckerTest: checkMaxNumberOfVarsPerCell");
        System.out.println("Check to find the proper MaxSumOfVarsPerCell.");
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        System.out.println(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        System.out.println(pyramid.toString());
        CheckMaxNumberOfVarsPerCellResultWrapper result = GrammarValidityChecker.checkMaxNumberOfVarsPerCell(pyramid, 2);
        Assert.assertTrue("", result.isMaxNumberOfVarsPerCell());
        System.out.println(Pyramid.printPyramid(pyramid.getCellsSimple()));
        System.out.println("GrammarValidityCheckerTest: checkMaxSumOfVarsInPyramid was successful." +
                "\nMaxSumOfVarsInPyramid is four.");
    }

    @Test
    public void checkSumOfProductions() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarValidityCheckerTest: checkSumOfProductions");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        System.out.println(grammar);
        Assert.assertTrue(
                GrammarValidityChecker.checkSumOfProductions(grammar, 15).isSumOfProductions()
        );
        Assert.assertFalse(
                GrammarValidityChecker.checkSumOfProductions(grammar, 14).isSumOfProductions()
        );
        System.out.println("The count of productions is more than 14 but less equal than 15 == 15 productions.");
    }


    @Test
    public void checkMaxSumOfVarsInPyramid() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarValidityCheckerTest: checkMaxSumOfVarsInPyramid");
        Word word = new Word("01110100");
        int wordLength = word.getWordLength();
        Set<VariableK>[][] setVTemp = Util.getInitialisedHashSetArray(wordLength, VariableK.class);

        // Constructing a small example matrix
        setVTemp[0][7].add(new VariableK(new VariableStart("P"), 1));
        setVTemp[0][7].add(new VariableK(new VariableStart("P"), 2));
        setVTemp[0][7].add(new VariableK(new VariableStart("P"), 3));
        setVTemp[0][7].add(new VariableK(new VariableStart("P"), 4));
        setVTemp[0][7].add(new VariableK(new VariableStart("S"), 4));

        setVTemp[1][1].add(new VariableK(new Variable("B"), 2));
        setVTemp[1][1].add(new VariableK(new Variable("A"), 2));

        setVTemp[2][7].add(new VariableK(new Variable("B"), 6));
        setVTemp[2][7].add(new VariableK(new Variable("O"), 7));

        setVTemp[3][3].add(new VariableK(new Variable("A"), 4));
        setVTemp[3][3].add(new VariableK(new Variable("C"), 4));

        setVTemp[4][7].add(new VariableK(new Variable("B"), 6));
        setVTemp[4][7].add(new VariableK(new Variable("T"), 7));

        setVTemp[5][5].add(new VariableK(new Variable("A"), 6));
        setVTemp[5][5].add(new VariableK(new Variable("C"), 6));

        SetVarKMatrix setVarKMatrix = new SetVarKMatrix(wordLength, word).setSetV(setVTemp);
        System.out.println(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        System.out.println(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Assert.assertFalse(
                GrammarValidityChecker.checkMaxSumOfVarsInPyramid(pyramid, 11).isMaxSumOfVarsInPyramid()
        );
        Assert.assertTrue(
                GrammarValidityChecker.checkMaxSumOfVarsInPyramid(pyramid, 12).isMaxSumOfVarsInPyramid()
        );
        System.out.println("The count of variables is more than 11 but less equal than 12 == 12 count of variables.");
    }

    @Test
    public void checkRightCellCombinationForced() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarValidityCheckerTest: checkRightCellCombinationForced");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        System.out.println(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
                GrammarValidityChecker.checkRightCellCombinationForced(pyramid, 3, grammar);
        System.out.println(grammar);
        System.out.println("CountForced: " + checkRightCellCombinationsForcedResultWrapper.getCountRightCellCombinationForced());
        System.out.println(checkRightCellCombinationsForcedResultWrapper.getMarkedRightCellCombinationForced());
    }
}