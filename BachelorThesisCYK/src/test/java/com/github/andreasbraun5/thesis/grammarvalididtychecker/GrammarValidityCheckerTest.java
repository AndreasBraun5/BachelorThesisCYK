package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.TiScriptExercise;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Andreas Braun on 13.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarValidityCheckerTest {
    @Test
    public void checkRightCellCombinationForced1() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarValidityCheckerTest: checkRightCellCombinationForcedForCell");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        Pyramid pyramid = new Pyramid(setVarKMatrix.getSetV(), word);
        CheckRightCellCombinationsForcedResultWrapper resultWrapper = GrammarValidityChecker.
                checkRightCellCombinationForcedSimpleCells(pyramid, 3, grammar);
        System.out.println(grammar);
        System.out.println(pyramid);
        CellSimple[][] cellsForcing = resultWrapper.getMarkedRightCellCombinationForced();
        System.out.println(Pyramid.printPyramid(cellsForcing));
    }

    @Test
    public void checkRightCellCombinationForcedForCell() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarValidityCheckerTest: checkRightCellCombinationForcedForCell");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        // Random cells are picked from the grammar, for easier construction of an CellK
        // Indexes of the cells are irrelevant for this check
        Pyramid pyramid = new Pyramid(setVarKMatrix.getSetV(), word);
        CellK cellLeft = pyramid.getCellK(0,0); // contains N1, A1
        CellK cellRight = pyramid.getCellK(5, 1); // contains D2
        CellK cellDown = pyramid.getCellK(4,2); // contains B3
        cellRight.addVar(new VariableK(new Variable("A"), 1)); // adding fictional var A1 to cellRight
        cellDown.addVar(new VariableK(new Variable("D_"), 1)); // adding fictional var D1 to cellDown
        cellDown.addVar(new VariableK(new Variable("C"), 1)); // adding fictional var D1 to cellDown
        System.out.println("cellLeft and cellRight:" + cellLeft.toString() + "             "+ cellRight.toString());
        System.out.println("cellDown:                       " + cellDown);
        System.out.println(grammar);
        Set<VariableK> forcingVarsOfCellDown = GrammarValidityChecker.
                checkRightCellCombinationForcedForCell(cellDown, cellRight, cellLeft, grammar);
        System.out.println(forcingVarsOfCellDown);
        Assert.assertTrue(forcingVarsOfCellDown.contains(new VariableK(new Variable("D_"), 1)));
        Assert.assertFalse(forcingVarsOfCellDown.contains(new VariableK(new Variable("B"), 3)));
        Assert.assertFalse(forcingVarsOfCellDown.contains(new VariableK(new Variable("C"), 1)));

        System.out.println("B3 doesn't force because ND is an rhse of one of its productions.");
        System.out.println("C1 doesn't force because AA is an rhse of one of its productions.");
        System.out.println("D1 forces because AD, AA, ND or NA isn't an rhse of one of its productions.");
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
        System.out.println("GrammarValidityCheckerTest: checkRightCellCombinationForcedSimpleCells");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        System.out.println(setVarKMatrix.getStringToPrintAsLowerTriangularMatrix());
        Pyramid pyramid = setVarKMatrix.getAsPyramid();
        CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
                GrammarValidityChecker.checkRightCellCombinationForcedSimpleCells(pyramid, 3, grammar);
        System.out.println(grammar);
        System.out.println("CountForced: " + checkRightCellCombinationsForcedResultWrapper.getRightCellCombinationForcedCount());
        System.out.println(checkRightCellCombinationsForcedResultWrapper.getMarkedRightCellCombinationForced());
    }
}