package com.github.andreasbraun5.thesis.grammarproperties;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class ExamConstraints {

    // Exam constraints
	public int minRightCellCombinationsForced = 1; // optional, default = 1
    public int maxNumberOfVarsPerCell = 3; // optional, default = 3
    public int sizeOfWord = 10; // optional, default = 10
    public int maxSumOfVarsInPyramid = 100; // optional, default = 100

	@Override
	public String toString() {
		return "\nExamConstraints{" +
				"\n		minRightCellCombinationsForced=" + minRightCellCombinationsForced +
				"\n		maxNumberOfVarsPerCell=" + maxNumberOfVarsPerCell +
				"\n		maxSumOfVarsInPyramid=" + maxSumOfVarsInPyramid +
                "\n		sizeOfWord=" + sizeOfWord +
                "\n}";
	}
}
