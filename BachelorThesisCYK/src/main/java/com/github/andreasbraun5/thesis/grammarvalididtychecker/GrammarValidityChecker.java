package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.*;

/**
 * Created by Andreas Braun on 05.01.2017.
 * https://github.com/AndreasBraun5/
 * All validity tests of GrammarValidityChecker are based onto the simple setV = setV<Variable>.
 */
public class GrammarValidityChecker {

    /**
     * True if the starting symbol is contained at the bottom of the pyramid.
     */
    public static boolean checkProducibilityCYK(
            Pyramid pyramid) {
        return CYK.algorithmAdvanced(pyramid);
    }

    /**
     * True if numberOfVarsPerCell is smaller than maxNumberOfVarsPerCell. Does not ignore cell after the diagonal.
     */
    public static CheckMaxNumberOfVarsPerCellResultWrapper checkMaxNumberOfVarsPerCell(
            Pyramid pyramid,
            int maxNumberOfVarsPerCell) {
        if (maxNumberOfVarsPerCell == 0) {
            throw new GrammarPropertiesRuntimeException("maxNumberOfVarsPerCell is zero.");
        }
        int tempMaxNumberOfVarsPerCell = maxNumberOfVarsPerCellCount(pyramid);
        return CheckMaxNumberOfVarsPerCellResultWrapper.builder()
                .maxNumberOfVarsPerCellCount(tempMaxNumberOfVarsPerCell).
                        maxNumberOfVarsPerCell(tempMaxNumberOfVarsPerCell <= maxNumberOfVarsPerCell).build();
    }

    public static int maxNumberOfVarsPerCellCount(Pyramid pyramid) {
        CellSimple[][] cellsSimple = pyramid.getCellsSimple();
        int tempMaxNumberOfVarsPerCell = 0;
        for (int i = 0; i < cellsSimple[0].length; i++) {
            for (int j = 0; j < cellsSimple[i].length; j++) {
                if (cellsSimple[i][j].getCellElements().size() > tempMaxNumberOfVarsPerCell) {
                    tempMaxNumberOfVarsPerCell = cellsSimple[i][j].getCellElements().size();
                }
            }
        }
        return tempMaxNumberOfVarsPerCell;
    }

    /**
     * Exam relevant restriction. The upper two rows of the pyramid aren't checked. For each cell of the pyramid it is
     * checked whether it forces. minCountRightCellCombinationsForced is incremented dependent on unique varKs.
     * If there is D4, D5, D6 that force, it is only the D that forces.
     * TODO: Cell must not be empty.
     */
    public static CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationForcedSimpleCells(
            Pyramid pyramid, int minCountRightCellCombinationsForced, Grammar grammar) {
        CheckRightCellCombinationsForcedResultWrapper res = checkRightCellCombinationsForcedSimpleCells(pyramid, grammar);
        res.setRightCellCombinationForced(res.getRightCellCombinationForcedCount() >= minCountRightCellCombinationsForced);
        // returns which variables force in which cell of the pyramid, if it the restriction is valid and how often it forces
        return res;

    }

    public static CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedSimpleCells(
            Pyramid pyramid, Grammar grammar) {
        int rightCellCombinationsForced = 0;
        CellSimple[][] markedRightCellCombinationForced = getEmptyCellsSimple(pyramid.getSize());
        CellK[][] cells = pyramid.getCellsK();
        {   // for each cell, starting in row 2 of the pyramid call checkRightCellCombinationForcedForCell
            for (int i = 2; i < cells[0].length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    CellK cellDown = cells[i][j];
                    CellK cellRight = cells[i - 1][j + 1];
                    CellK cellLeft = cells[i - 1][j];
                    Set<VariableK> tempVarKsThatForce =
                            checkRightCellCombinationForcedForCell(cellDown, cellRight, cellLeft, grammar);
                    // Now we still need the variableSet instead of the variableKSet
                    Set<Variable> tempVarsThatForce = new HashSet<>();
                    for (VariableK varK : tempVarKsThatForce) {
                        tempVarsThatForce.add((Variable) varK.getLhse());
                    }
                    rightCellCombinationsForced += tempVarsThatForce.size();
                    // This can be added now to the markedRightCellCombinationForced.
                    markedRightCellCombinationForced[i][j].addVars(tempVarsThatForce);
                }
            }
        }
        return CheckRightCellCombinationsForcedResultWrapper.builder().
                rightCellCombinationForcedCount(rightCellCombinationsForced).
                markedRightCellCombinationForced(markedRightCellCombinationForced).build();
    }


    /**
     * For one cell of the pyramid is checked if it forces. It returns the variables that force.
     * If there is A1, A1, A4 then it will return A1, A4.
     */
    static Set<VariableK> checkRightCellCombinationForcedForCell(
            CellK cellDown, CellK cellRight, CellK cellLeft, Grammar grammar) {
        Set<VariableK> varKsThatForce = new HashSet<>();
        // varCompMistake compound variables aren't allowed to be an rhse of the varK that forces of cellDown.
        Set<VariableCompound> varCompMistake = Util.calculateVariablesCompoundForCellPair(new Tuple<>(cellLeft, cellRight));
        for (VariableK vark : cellDown.getCellElements()) {
            Set<Production> prodsForVar = grammar.getProductionsMap().get(vark.getLhse());
            // If at least one of the varCompMistake variables is element of only rhse of the prodsForVar, then the
            // vark does not Force
            boolean addVar = true;
            for (Production prod : prodsForVar) {
                if (varCompMistake.contains(prod.getRightHandSideElement())) {
                    // If one element is found, then the varK must not be added to varKsThatForce.
                    addVar = false;
                }
            }
            if (addVar) {
                varKsThatForce.add(vark);
            }
        }
        return varKsThatForce;
    }


    public static CheckSumOfProductionsResultWrapper checkSumOfProductions(Grammar grammar, int maxSumOfProductions) {
        return CheckSumOfProductionsResultWrapper.builder().
                maxSumOfProductionsCount(grammar.getProductionsAsList().size()).
                sumOfProductions(grammar.getProductionsAsList().size() <= maxSumOfProductions).build();
    }

    /**
     * checkMaxSumOfVarsInPyramid is tested only on the setV simple.
     */
    public static CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramid(
            Pyramid pyramid,
            int maxSumOfVarsInPyramid) {
        int countVars = countSumOfVarsInPyramid(pyramid);
        return CheckMaxSumOfVarsInPyramidResultWrapper.builder().
                maxSumOfVarsInPyramidCount(countVars).
                maxSumOfVarsInPyramid(countVars <= maxSumOfVarsInPyramid).build();
    }

    public static int countSumOfVarsInPyramid(Pyramid pyramid) {
        CellSimple[][] cellsSimple = pyramid.getCellsSimple();
        // put all vars of the matrix into one list and use its length.
        List<Variable> tempVars = new ArrayList<>();
        for (int i = 0; i < cellsSimple.length; i++) {
            for (int j = 0; j < cellsSimple[i].length; j++) {
                tempVars.addAll(cellsSimple[i][j].getCellElements());
            }
        }
        return tempVars.size();
    }

    private static CellSimple[][] getEmptyCellsSimple(int correspondingPyramidSize) {
        CellSimple[][] cells = new CellSimple[correspondingPyramidSize][];
        { // Now the zero row has length times many cells, the 1st row has length-1 times many cells, ...
            int j = correspondingPyramidSize;
            for (int i = 0; i < correspondingPyramidSize; i++) {
                cells[i] = new CellSimple[j];
                j--;
            }
        }
        {
            for (int i = 0; i < correspondingPyramidSize; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    cells[i][j] = new CellSimple(i, j);
                }
            }
        }
        return cells;
    }
}
