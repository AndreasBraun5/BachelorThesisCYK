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
class ResultSamplePyramidConstraints {

    private boolean examConstraints;
    private boolean rightCellCombinationsForced;
    private boolean maxSumOfVarsInPyramid;
    private boolean maxNumberOfVarsPerCell;
    int maxNumberOfVarsPerCellCount;
    private int RightCellCombinationsForcedCount;
    private int maxSumOfVarsInPyramidCount;
    private CellSimple[][] markedRightCellCombinationForced;




    @Override
	public String toString() {
		return "\n\nResultSampleExamConstraints{" +
				"\n		examConstraints=" + examConstraints +
				"\n		rightCellCombinationsForced=" + rightCellCombinationsForced +
				"\n		maxNumberOfVarsPerCell=" + maxNumberOfVarsPerCell +
				"\n		maxSumOfVarsInPyramidCount=" + maxSumOfVarsInPyramid +
				"\n}";
	}
}
