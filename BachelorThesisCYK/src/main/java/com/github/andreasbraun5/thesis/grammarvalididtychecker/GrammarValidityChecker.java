package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckMaxNumberOfVarsPerCellResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckMaxSumOfVarsInPyramidResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckRightCellCombinationsForcedResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckSumOfProductionsResultWrapper;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.*;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.*;

/**
 * Created by Andreas Braun on 05.01.2017.
 * https://github.com/AndreasBraun5/
 * <p>
 * All validity tests of GrammarValidityChecker are based onto the simple setV = setV<Variable>.
 */
public class GrammarValidityChecker { // implements GrammarValidityCheckerInterface{

    /**
     * True if the starting symbol is contained at the bottom of the pyramid.
     */
    public static boolean checkProducibilityCYK(
            Pyramid pyramid,
            Grammar grammar,
            GrammarProperties grammarProperties) {
        return CYK.algorithmAdvanced(pyramid, grammar, grammarProperties);
    }

    /**
     * True if numberOfVarsPerCell is smaller than maxNumberOfVarsPerCell. Does not ignore cell after the diagonal.
     */
    public static CheckMaxNumberOfVarsPerCellResultWrapper checkMaxNumberOfVarsPerCell(
            Pyramid pyramid,
            int maxNumberOfVarsPerCell) {
        CellSimple[][] cellsSimple = pyramid.getCellsSimple();
        if (maxNumberOfVarsPerCell == 0) {
            throw new GrammarPropertiesRuntimeException("maxNumberOfVarsPerCell is zero.");
        }
        int tempMaxNumberOfVarsPerCell = 0;
        for (int i = 0; i < cellsSimple[0].length; i++) {
            for (int j = 0; j < cellsSimple[i].length; j++) {
                if (cellsSimple[i][j].getCellElements().size() > tempMaxNumberOfVarsPerCell) {
                    tempMaxNumberOfVarsPerCell = cellsSimple[i][j].getCellElements().size();
                }
            }
        }
        return CheckMaxNumberOfVarsPerCellResultWrapper.buildCheckMaxNumberOfVarsPerCellResultWrapper().
                setMaxNumberOfVarsPerCell(tempMaxNumberOfVarsPerCell).
                setMaxNumberOfVarsPerCell(tempMaxNumberOfVarsPerCell <= maxNumberOfVarsPerCell);
    }

    /**
     * Exam relevant restriction. The upper two rows of the pyramid aren't checked. For each cell of the pyramid it is
     * checked wether it forces.
     */
    public static CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationForced(
            Pyramid pyramid, int minCountRightCellCombinationsForced, Grammar grammar) {
        int rightCellCombinationsForced = 0;
        Word word = pyramid.getWord();
        Set<VariableK>[][] markedRightCellCombinationForced =
                Util.getInitialisedHashSetArray(word.getWordLength(), VariableK.class);
        CellK[][] cells = pyramid.getCellsK();
        // for each cell in pyramid call checkRightCellCombinationForcedForCell
        {
            for (int i = 2; i < cells[0].length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    CellK cellDown = cells[i][j];
                    CellK cellRight = cells[i - 1][j];
                    CellK cellLeft = cells[i - 1][j + 1];
                    List<VariableK> tempVarKsThatForce =
                            checkRightCellCombinationForcedForCell(cellDown, cellRight, cellLeft, grammar);
                    rightCellCombinationsForced += tempVarKsThatForce.size();
                    markedRightCellCombinationForced[i][j].addAll(tempVarKsThatForce);
                }
            }
        }

        Pyramid pyramidMarked = SetVarKMatrix.matrixToPyramid(markedRightCellCombinationForced, word);
        // returns which variables force in which cell of the pyramid, if it the restriciton is valid and how often it forces
        return CheckRightCellCombinationsForcedResultWrapper.buildRightCellCombinationsForcedWrapper().
                setCountRightCellCombinationForced(rightCellCombinationsForced).
                setRightCellCombinationForced(rightCellCombinationsForced >= minCountRightCellCombinationsForced).
                setMarkedRightCellCombinationForced(pyramidMarked);
    }


    /**
     * For one cell of the pyramid is checked if it forces. It returns the variables that force.
     */
    private static List<VariableK> checkRightCellCombinationForcedForCell(
            CellK cellDown, CellK cellRight, CellK cellLeft, Grammar grammar) {
        List<VariableK> varKsThatForce = new ArrayList<>();
        // varCompMistake compound variables aren't allowed to be an rhse of the variable of cellDown.
        @SuppressWarnings("SuspiciousNameCombination")
        Set<VariableCompound> varCompMistake = Util.calculateVariablesCompound(new Tuple<>(cellLeft, cellRight));
        for (VariableK vark : cellDown.getCellElements()) {
            List<Production> prodsForVar = grammar.getProductionsMap().get(vark.getVariable());
            // If at least one of the varCompMistake variables is element of only rhse of the prodsForVar, then the
            // vark does not Force
            for (Production prod : prodsForVar) {
                if (varCompMistake.contains(prod.getRightHandSideElement())) {
                    // If one element is found, then the varK must not be added to varKsThatForce.
                    break;
                } else {
                    varKsThatForce.add(vark);
                }

            }

        }
        return varKsThatForce;
    }


    public static CheckSumOfProductionsResultWrapper checkSumOfProductions(Grammar grammar, int maxSumOfProductions) {
        return CheckSumOfProductionsResultWrapper.buildCheckSumOfProductionsResultWrapper().
                setMaxSumOfProductions(grammar.getProductionsAsList().size()).
                setSumOfProductions(grammar.getProductionsAsList().size() <= maxSumOfProductions);
    }

    /**
     * checkMaxSumOfVarsInPyramid is tested only on the setV simple.
     */
    public static CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramid(
            Pyramid pyramid,
            int maxSumOfVarsInPyramid) {
        CellSimple[][] cellsSimple = pyramid.getCellsSimple();
        // put all vars of the matrix into one list and use its length.
        List<Variable> tempVars = new ArrayList<>();
        for (int i = 0; i < cellsSimple.length; i++) {
            for (int j = 0; j < cellsSimple[i].length; j++) {
                tempVars.addAll(cellsSimple[i][j].getCellElements());
            }
        }
        return CheckMaxSumOfVarsInPyramidResultWrapper.buildCheckMaxSumOfVarsInPyramidResultWrapper().
                setMaxSumOfVarsInPyramid(tempVars.size()).
                setMaxSumOfVarsInPyramid(tempVars.size() <= maxSumOfVarsInPyramid);
    }
}
