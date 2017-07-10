package com.github.andreasbraun5.thesis.grammarproperties;

import com.github.andreasbraun5.thesis.pyramid.Pyramid;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class ExamConstraints {

    // Exam constraints
	public int minRightCellCombinationsForced = 1; // optional, default = 1
    public int maxSumOfProductions = 10; // optional, default = 10
    public int maxSumOfVarsInPyramid = 100; // optional, default = 50

	@Override
	public String toString() {
		return "\nExamConstraints{" +
				"\n		minRightCellCombinationsForced=" + minRightCellCombinationsForced +
				"\n		maxSumOfProductions=" + maxSumOfProductions +
				"\n		maxSumOfVarsInPyramid=" + maxSumOfVarsInPyramid +
				"\n}";
	}
}
