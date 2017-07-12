package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.pyramid.CellSimple;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
class ResultSampleExamConstraints {

	boolean isExamConstraints;
    boolean isRightCellCombinationsForced;
    boolean isMaxSumOfProductionsCount;
    boolean isMaxSumOfVarsInPyramidCount;
    int countRightCellCombinationsForced;
    int maxSumOfVarsInPyramid;
    int maxSumOfProductions;
    CellSimple[][] markedRightCellCombinationForced;

	@Override
	public String toString() {
		return "\n\nResultSampleExamConstraints{" +
				"\n		isExamConstraints=" + isExamConstraints +
				"\n		isRightCellCombinationsForced=" + isRightCellCombinationsForced +
				"\n		isMaxSumOfProductionsCount=" + isMaxSumOfProductionsCount +
				"\n		isMaxSumOfVarsInPyramidCount=" + isMaxSumOfVarsInPyramidCount +
				"\n}";
	}
}
