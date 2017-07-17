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

    // Pyramid constraints values
    private int RightCellCombinationsForcedCount;
    private int maxSumOfVarsInPyramidCount;
    private int maxNumberOfVarsPerCellCount;

    // Grammar constraints values
    int maxSumOfProductionsCount;

}
