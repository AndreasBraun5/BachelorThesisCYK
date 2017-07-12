package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return CheckMaxNumberOfVarsPerCellResultWrapper.builder()
                .maxNumberOfVarsPerCellCount(tempMaxNumberOfVarsPerCell).
                        maxNumberOfVarsPerCell(tempMaxNumberOfVarsPerCell <= maxNumberOfVarsPerCell).build();
    }

    /**
     * Exam relevant restriction. The upper two rows of the pyramid aren't checked. For each cell of the pyramid it is
     * checked whether it forces. minCountRightCellCombinationsForced is incremented dependent on unique varKs.
     * If there is D4, D5, D6 that force, it is only the D_ that forces.
     */
    public static CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationForcedSimpleCells(
            Pyramid pyramid, int minCountRightCellCombinationsForced, Grammar grammar) {
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
                    rightCellCombinationsForced += tempVarKsThatForce.size();
                    // Now we still need the variableSet instead of the variableKSet
                    Set<Variable> tempVarsThatForce = new HashSet<>();
                    for (VariableK varK : tempVarKsThatForce) {
                        tempVarsThatForce.add(varK.getVariable());
                    }
                    // This can be added now to the markedRightCellCombinationForced.
                    markedRightCellCombinationForced[i][j].addVars(tempVarsThatForce);
                }
            }
        }
        // returns which variables force in which cell of the pyramid, if it the restriction is valid and how often it forces
        return CheckRightCellCombinationsForcedResultWrapper.builder().
                rightCellCombinationForcedCount(rightCellCombinationsForced).
                rightCellCombinationForced(rightCellCombinationsForced >= minCountRightCellCombinationsForced).
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
        CellSimple[][] cellsSimple = pyramid.getCellsSimple();
        // put all vars of the matrix into one list and use its length.
        List<Variable> tempVars = new ArrayList<>();
        for (int i = 0; i < cellsSimple.length; i++) {
            for (int j = 0; j < cellsSimple[i].length; j++) {
                tempVars.addAll(cellsSimple[i][j].getCellElements());
            }
        }
        return CheckMaxSumOfVarsInPyramidResultWrapper.builder().
                maxSumOfVarsInPyramidCount(tempVars.size()).
                maxSumOfVarsInPyramid(tempVars.size() <= maxSumOfVarsInPyramid).build();
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
