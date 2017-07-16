package com.github.andreasbraun5.thesis.score;

import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by AndreasBraun on 16.07.2017.
 * Each one of the properties of the ResultSample will be scored.
 */
@Getter
@Setter
@Builder
public class ScoringMatrix {

    // General info
    private boolean isValid; // - 10000
    private boolean isWordProducible; // -1000

    // Exam constraints
    private boolean examConstraints; // -100
    private boolean rightCellCombinationsForced;
    private boolean maxSumOfProductions;
    private boolean maxSumOfVarsInPyramid;
    private int RightCellCombinationsForcedCount;
    private int maxSumOfVarsInPyramidCount;
    private int maxSumOfProductionsCount;

    // Grammar constraints
    private boolean grammarRestrictions; // -100
    private boolean maxNumberOfVarsPerCell;
    int maxNumberOfVarsPerCellCount;
}
