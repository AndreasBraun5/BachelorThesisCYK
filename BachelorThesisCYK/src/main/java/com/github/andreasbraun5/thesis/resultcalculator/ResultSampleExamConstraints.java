package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
@Setter
class ResultSampleExamConstraints {

    private boolean examConstraints;
    private boolean rightCellCombinationsForced;
    private boolean maxSumOfProductions;
    private boolean maxSumOfVarsInPyramid;
    private int RightCellCombinationsForcedCount;
    private int maxSumOfVarsInPyramidCount;
    private int maxSumOfProductionsCount;
    private CellSimple[][] markedRightCellCombinationForced;

	@Override
	public String toString() {
		return "\n\nResultSampleExamConstraints{" +
				"\n		examConstraints=" + examConstraints +
				"\n		rightCellCombinationsForced=" + rightCellCombinationsForced +
				"\n		maxSumOfProductionsCount=" + maxSumOfProductions +
				"\n		maxSumOfVarsInPyramidCount=" + maxSumOfVarsInPyramid +
				"\n}";
	}
}
