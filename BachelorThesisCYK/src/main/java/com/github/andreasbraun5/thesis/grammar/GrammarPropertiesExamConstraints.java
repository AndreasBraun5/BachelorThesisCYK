package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarPropertiesExamConstraints {

	private int minRightCellCombinationsForced = 1; // optional, default = 1
	private int maxSumOfProductions = 10; // optional, default = 10
	private int maxSumOfVarsInPyramid = 100; // optional, default = 50

	public static GrammarPropertiesExamConstraints buildGrammarExamConstraintsWrapper() {
		return new GrammarPropertiesExamConstraints();
	}

	public GrammarPropertiesExamConstraints setMinRightCellCombinationsForced(int minRightCellCombinationsForced) {
		this.minRightCellCombinationsForced = minRightCellCombinationsForced;
		return this;
	}

	public GrammarPropertiesExamConstraints setMaxSumOfProductions(int maxSumOfProductions) {
		this.maxSumOfProductions = maxSumOfProductions;
		return this;
	}

	public GrammarPropertiesExamConstraints setMaxSumOfVarsInPyramid(int maxSumOfVarsInPyramid) {
		this.maxSumOfVarsInPyramid = maxSumOfVarsInPyramid;
		return this;
	}

	public int getMinRightCellCombinationsForced() {
		return minRightCellCombinationsForced;
	}

	public int getMaxSumOfProductions() {
		return maxSumOfProductions;
	}

	public int getMaxSumOfVarsInPyramid() {
		return maxSumOfVarsInPyramid;
	}

	@Override
	public String toString() {
		return "\nGrammarPropertiesExamConstraints{" +
				"\nminRightCellCombinationsForced=" + minRightCellCombinationsForced +
				"\nmaxSumOfProductions=" + maxSumOfProductions +
				"\nmaxSumOfVarsInPyramid=" + maxSumOfVarsInPyramid +
				"\n";
	}
}
