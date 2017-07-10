package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSampleExamConstraints {

	public boolean isExamConstraints;
    public boolean isRightCellCombinationsForced;
    public boolean isMaxSumOfProductionsCount;
    public boolean isMaxSumOfVarsInPyramidCount;
    public int countRightCellCombinationsForced;
    public int maxSumOfVarsInPyramid;
    public int maxSumOfProductions;
    public Pyramid markedRightCellCombinationForced;

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
