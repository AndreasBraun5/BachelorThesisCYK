package com.github.andreasbraun5.thesis.score;

import com.github.andreasbraun5.thesis.exception.ScoreMatrixRuntimeException;
import com.github.andreasbraun5.thesis.resultcalculator.GrammarConstraintValues;
import com.github.andreasbraun5.thesis.resultcalculator.PyramidConstraintValues;
import com.github.andreasbraun5.thesis.resultcalculator.ResultSample;

/**
 * Created by AndreasBraun on 18.07.2017.
 * Maximum score possible is 1.0.
 * Negative scores also possible.
 */
public class ScoringMatrix {

    public static double scoreResultSample(ResultSample resultSample) {
        int score = 0;
        {   // Scoring the grammar
            GrammarConstraintValues grammarValues = resultSample.getGrammarConstraintValues();
            score += scoreSumProductions(grammarValues.getMaxSumOfProductionsCount());
        }
        {   // Scoring the pyramid
            PyramidConstraintValues pyramidValues = resultSample.getPyramidConstraintValues();
            score += scoreCellCombinationsForced(pyramidValues.getRightCellCombinationsForcedCount());
            score += scoreSumVarsInPyramid(pyramidValues.getMaxSumOfVarsInPyramidCount());
            score += scoreMaxVarsPerCell(pyramidValues.getMaxNumberOfVarsPerCellCount());
        }
        return (double) score / ScoringMatrixWeights.MAX_SUM_SCORE_AVAILABLE;
    }

    private static int scoreCellCombinationsForced(int cellCombinationsForcedCount) {
        if (cellCombinationsForcedCount > 50) {
            return ScoringMatrixWeights.OUT_OF_RANGE_SCORE;
        } else if (0 <= cellCombinationsForcedCount && cellCombinationsForcedCount <= 10) {
            return 2;
        } else if (11 <= cellCombinationsForcedCount && cellCombinationsForcedCount <= 20) {
            return 4;
        } else if (21 <= cellCombinationsForcedCount && cellCombinationsForcedCount <= 30) {
            return 6;
        } else if (41 <= cellCombinationsForcedCount && cellCombinationsForcedCount <= 50) {
            return 8;
        } else if (31 <= cellCombinationsForcedCount && cellCombinationsForcedCount <= 40) {
            return 10;
        } else {
            throw new ScoreMatrixRuntimeException("Value out of bound, scoring not possible.");
        }
    }

    private static double scoreSumVarsInPyramid(int sumVarsInPyramid) {
        if (sumVarsInPyramid > 50) {
            return ScoringMatrixWeights.OUT_OF_RANGE_SCORE;
        } else if (0 <= sumVarsInPyramid && sumVarsInPyramid <= 10) {
            return 2;
        } else if (11 <= sumVarsInPyramid && sumVarsInPyramid <= 20) {
            return 4;
        } else if (21 <= sumVarsInPyramid && sumVarsInPyramid <= 30) {
            return 6;
        } else if (41 <= sumVarsInPyramid && sumVarsInPyramid <= 50) {
            return 8;
        } else if (31 <= sumVarsInPyramid && sumVarsInPyramid <= 40) {
            return 10;
        } else {
            throw new ScoreMatrixRuntimeException("Value out of bound, scoring not possible.");
        }
    }

    private static double scoreMaxVarsPerCell(int maxVarsPerCell) {
        if (maxVarsPerCell > 5) {
            return ScoringMatrixWeights.OUT_OF_RANGE_SCORE;
        } else if (5 <= maxVarsPerCell && maxVarsPerCell <= 5) {
            return 2;
        } else if (4 <= maxVarsPerCell && maxVarsPerCell <= 4) {
            return 4;
        } else if (1 <= maxVarsPerCell && maxVarsPerCell <= 1) {
            return 6;
        } else if (3 <= maxVarsPerCell && maxVarsPerCell <= 3) {
            return 8;
        } else if (2 <= maxVarsPerCell && maxVarsPerCell <= 2) {
            return 10;
        } else {
            throw new ScoreMatrixRuntimeException("Value out of bound, scoring not possible.");
        }
    }

    private static double scoreSumProductions(int sumProductions) {
        if (sumProductions > 10) {
            return ScoringMatrixWeights.OUT_OF_RANGE_SCORE;
        } else if (1 <= sumProductions && sumProductions <= 2) {
            return 2;
        } else if (3 <= sumProductions && sumProductions <= 4) {
            return 4;
        } else if (5 <= sumProductions && sumProductions <= 6) {
            return 6;
        } else if (9 <= sumProductions && sumProductions <= 10) {
            return 8;
        } else if (7 <= sumProductions && sumProductions <= 8) {
            return 10;
        } else {
            throw new ScoreMatrixRuntimeException("Value out of bound, scoring not possible.");
        }
    }
}
