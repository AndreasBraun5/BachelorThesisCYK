package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.*;

/**
 * Created by Andreas Braun on 05.01.2017.
 * https://github.com/AndreasBraun5/
 * <p>
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
        Set<Variable>[][] tempSetV = pyramid.getAsVarKMatrix().getSimpleSetDoubleArray();
        if (maxNumberOfVarsPerCell == 0) {
            throw new GrammarPropertiesRuntimeException("maxNumberOfVarsPerCell is zero.");
        }
        int tempMaxNumberOfVarsPerCell = 0;
        int wordLength = tempSetV[0].length;
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                if (tempSetV[i][j].size() > tempMaxNumberOfVarsPerCell) {
                    tempMaxNumberOfVarsPerCell = tempSetV[i][j].size();
                }
            }
        }
        return CheckMaxNumberOfVarsPerCellResultWrapper.buildCheckMaxNumberOfVarsPerCellResultWrapper().
                setMaxNumberOfVarsPerCell(tempMaxNumberOfVarsPerCell).
                setMaxNumberOfVarsPerCell(tempMaxNumberOfVarsPerCell <= maxNumberOfVarsPerCell);
    }

    /**
     * True if more than minCountRightCellCombinationsForced "rightCellCombination" are forced.
     * Exam relevant restriction. The upper two rows of the pyramid aren't checked.
     * Starting from from the upper right index of the matrix setV[0][wL-1] towards the diagonal.
     */
    public static CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationForced(
            Pyramid pyramid, int minCountRightCellCombinationsForced, Grammar grammar) {
        Set<Variable>[][] tempSetV = pyramid.getAsVarKMatrix().getSimpleSetDoubleArray();
        int wordLength = tempSetV[0].length;
        Set<VariableK>[][] markedRightCellCombinationForced = Util.getInitialisedHashSetArray(wordLength, VariableK.class);
        Map<Variable, List<Production>> prodMap = grammar.getProductionsMap();
        int rightCellCombinationsForced = 0;
        // Keep in mind that the setV matrix is a upper right matrix. But descriptions of how the algorithm works
        // is done, as if the setV pyramid points downwards (reflection on the diagonal + rotation to the left).
        // Regarding one cells, its upper left cells and its upper right cells are looked at.
        // setV[i][j] = down
        // setV[i + 1][j] = upper right
        // setV[i][j - 1] = upper left
        for (int i = 0; i < wordLength; i++) { // row
            // here it is restricted that the upper two rows aren't checked. Trivial cases, which would fulfill the
            // restrictions each time.
            for (int j = wordLength - 1; j > i + 2; j--) { // column
                Set<VariableCompound> tempVarCompSet = new HashSet<>();
                // if one., the left or right cell is empty, then rightCellCombinationsForced wont't be incremented.
                if (tempSetV[i][j - 1].size() != 0 && tempSetV[i + 1][j].size() != 0) {
                    // make all tuples of left and right --> tempVariablesCompound = tuples of type
                    // ({varLeft}, {varRight})
                    for (Variable varLeft : tempSetV[i][j - 1]) {
                        for (Variable varRight : tempSetV[i + 1][j]) {
                            tempVarCompSet.add(new VariableCompound(varLeft, varRight));
                        }
                    }
                    // for each var in down check if no combination is rhse of var in its prodMap
                    boolean isRightCellCombinationForced;
                    for (Variable varDown : tempSetV[i][j]) {
                        // As long as the opposite isn't found isRightCellCombinationForced is true
                        isRightCellCombinationForced = true;
                        // Get its map
                        List<Production> varDownProdList = prodMap.get(varDown);
                        // Check for each tempVarComp if it is element of the rhse of the observed variable <-> prodmap
                        for (VariableCompound tempVarComp : tempVarCompSet) {
                            // regarding an empty cell down
                            if (varDownProdList == null) {
                                isRightCellCombinationForced = false;
                                // TODO Martin: Ask for break;
                                break;
                            }
                            for (Production prod : varDownProdList) {
                                if (prod.getRightHandSideElement().equals(tempVarComp)) {
                                    isRightCellCombinationForced = false;
                                }
                                if (!isRightCellCombinationForced) {
                                    break;
                                }
                            }
                            if (!isRightCellCombinationForced) {
                                break;
                            }
                        }
                        if (isRightCellCombinationForced) {
                            rightCellCombinationsForced++;
                            // TODO: not nice here
                            markedRightCellCombinationForced[i][j].add(new VariableK(varDown,0));
                        }
                    }
                }
            }
        }
        Pyramid pyramidMarked = SetVarKMatrix.matrixToPyramid(markedRightCellCombinationForced, pyramid.getWord());
        return CheckRightCellCombinationsForcedResultWrapper.buildRightCellCombinationsForcedWrapper().
                setCountRightCellCombinationForced(rightCellCombinationsForced).
                setRightCellCombinationForced(rightCellCombinationsForced >= minCountRightCellCombinationsForced).
                setMarkedRightCellCombinationForced(pyramidMarked);
    }

    public static CheckSumOfProductionsResultWrapper checkSumOfProductions(Grammar grammar, int maxSumOfProductions) {
        return CheckSumOfProductionsResultWrapper.buildCheckSumOfProductionsResultWrapper().
                setMaxSumOfProductions(grammar.getProductionsAsList().size()).
                setSumOfProductions(grammar.getProductionsAsList().size() <= maxSumOfProductions);
    }

    /**
     * checkMaxSumOfVarsInPyramid is tested only on the setV simple. Does not ignore cell after the diagonal.
     */
    public static CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramid(
            Pyramid pyramid,
            int maxSumOfVarsInPyramid) {
        Set<Variable>[][] tempSetV = pyramid.getAsVarKMatrix().getSimpleSetDoubleArray();
        // put all vars of the matrix into one list and use its length.
        List<Variable> tempVars = new ArrayList<>();
        for (int i = 0; i < tempSetV.length; i++) {
            for (int j = 0; j < tempSetV.length; j++) {
                tempVars.addAll(tempSetV[i][j]);
            }
        }
        return CheckMaxSumOfVarsInPyramidResultWrapper.buildCheckMaxSumOfVarsInPyramidResultWrapper().
                setMaxSumOfVarsInPyramid(tempVars.size()).
                setMaxSumOfVarsInPyramid(tempVars.size() <= maxSumOfVarsInPyramid);
    }
}
