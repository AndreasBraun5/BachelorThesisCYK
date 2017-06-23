package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSampleExamConstraints {

	private boolean isExamConstraints;
	private boolean isRightCellCombinationsForced;
	private boolean isMaxSumOfProductionsCount;
	private boolean isMaxSumOfVarsInPyramidCount;
	private int countRightCellCombinationsForced;
	private int maxSumOfVarsInPyramid;
	private int maxSumOfProductions;
	private Pyramid markedRightCellCombinationForced;

	public boolean isExamConstraints() {
		return isExamConstraints;
	}

	public ResultSampleExamConstraints setExamConstraints(boolean examConstraints) {
		isExamConstraints = examConstraints;
		return this;
	}

	public int getMaxSumOfProductions() {
		return maxSumOfProductions;
	}

	public ResultSampleExamConstraints setMaxSumOfProductions(int maxSumOfProductions) {
		this.maxSumOfProductions = maxSumOfProductions;
		return this;
	}

	public int getMaxSumOfVarsInPyramid() {
		return maxSumOfVarsInPyramid;
	}

	public ResultSampleExamConstraints setMaxSumOfVarsInPyramid(int maxSumOfVarsInPyramid) {
		this.maxSumOfVarsInPyramid = maxSumOfVarsInPyramid;
		return this;
	}

	public boolean isRightCellCombinationsForced() {
		return isRightCellCombinationsForced;
	}

	public ResultSampleExamConstraints setRightCellCombinationsForced(boolean rightCellCombinationsForced) {
		isRightCellCombinationsForced = rightCellCombinationsForced;
		return this;
	}

	public boolean isMaxSumOfProductionsCount() {
		return isMaxSumOfProductionsCount;
	}

	public ResultSampleExamConstraints setMaxSumOfProductionsCount(boolean maxSumOfProductionsCount) {
		isMaxSumOfProductionsCount = maxSumOfProductionsCount;
		return this;
	}

	public boolean isMaxSumOfVarsInPyramidCount() {
		return isMaxSumOfVarsInPyramidCount;
	}

	public ResultSampleExamConstraints setMaxSumOfVarsInPyramidCount(boolean maxSumOfVarsInPyramidCount) {
		isMaxSumOfVarsInPyramidCount = maxSumOfVarsInPyramidCount;
		return this;
	}

	public int getCountRightCellCombinationsForced() {
		return countRightCellCombinationsForced;
	}

	public ResultSampleExamConstraints setCountRightCellCombinationsForced(int countRightCellCombinationsForced) {
		this.countRightCellCombinationsForced = countRightCellCombinationsForced;
		return this;
	}

	public Pyramid getMarkedRightCellCombinationForced() {
		return markedRightCellCombinationForced;
	}

	public ResultSampleExamConstraints setMarkedRightCellCombinationForced(Pyramid markedRightCellCombinationForced) {
		this.markedRightCellCombinationForced = markedRightCellCombinationForced;
		return this;
	}

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
