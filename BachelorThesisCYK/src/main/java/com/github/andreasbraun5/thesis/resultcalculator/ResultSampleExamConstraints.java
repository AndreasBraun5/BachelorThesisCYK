package com.github.andreasbraun5.thesis.resultcalculator;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSampleExamConstraints {

	private boolean isExamConstraints;
	private boolean isRightCellCombinationsForced;
	private boolean isMaxSumOfProductionsCount;
	private boolean isMaxSumOfVarsInPyramidCount;
	private int maxVarsPerCell;

	public static ResultSampleExamConstraints buildResultSampleExamConstraints(){
		return new ResultSampleExamConstraints();
	}

	public boolean isExamConstraints() {
		return isExamConstraints;
	}

	public ResultSampleExamConstraints setExamConstraints(boolean examConstraints) {
		isExamConstraints = examConstraints;
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

	public int getMaxVarsPerCell() {
		return maxVarsPerCell;
	}

	public ResultSampleExamConstraints setMaxVarsPerCell(int maxVarsPerCell) {
		this.maxVarsPerCell = maxVarsPerCell;
		return this;
	}

	@Override
	public String toString() {
		return "\n\nResultSampleExamConstraints{" +
				"\nisExamConstraints=" + isExamConstraints +
				"\nisRightCellCombinationsForced=" + isRightCellCombinationsForced +
				"\nisMaxSumOfProductionsCount=" + isMaxSumOfProductionsCount +
				"\nisMaxSumOfVarsInPyramidCount=" + isMaxSumOfVarsInPyramidCount +
				"\n";
	}
}
