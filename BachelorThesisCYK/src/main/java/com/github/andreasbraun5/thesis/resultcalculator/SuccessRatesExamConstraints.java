package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class SuccessRatesExamConstraints {

	private int trueExamConstraints;
	private int falseExamConstraints;
	private double successRateExamConstraints;

	private int trueRightCellCombinationsForcedCount;
	private int falseRightCellCombinationsForcedCount;
	private double successRateRightCellCombinationsForced;

	private int trueMaxSumOfProductionsCount;
	private int falseMaxSumOfProductionsCount;
	private double successRateMaxSumOfProductions;

	private int trueMaxSumOfVarsInPyramidCount;
	private int falseMaxSumOfVarsInPyramidCount;
	private double successRateMaxSumOfVarsInPyramid;

	public SuccessRatesExamConstraints(Map<String, List<ResultSample>> allResultSamples) {
		int countGeneratedGrammars = 0;
		for ( Map.Entry<String, List<ResultSample>> entry : allResultSamples.entrySet() ) {
			for ( ResultSample resultSample : entry.getValue() ) {
				countGeneratedGrammars++;
				ResultSampleExamConstraints tempResultSampleExamConstraints =
						resultSample.getResultSampleExamConstraints();
				boolean isRightCellCombinationsForced = tempResultSampleExamConstraints.isRightCellCombinationsForced();
				boolean isMaxSumOfProductionsCount = tempResultSampleExamConstraints.isMaxSumOfProductionsCount();
				boolean isMaxSumOfVarsInPyramidCount = tempResultSampleExamConstraints.isMaxSumOfVarsInPyramidCount();

				if ( isRightCellCombinationsForced ) {
					trueRightCellCombinationsForcedCount++;
				}
				else {
					falseRightCellCombinationsForcedCount++;
				}
				if ( isMaxSumOfProductionsCount ) {
					trueMaxSumOfProductionsCount++;
				}
				else {
					falseMaxSumOfProductionsCount++;
				}
				if ( isMaxSumOfVarsInPyramidCount ) {
					trueMaxSumOfVarsInPyramidCount++;
				}
				else {
					falseMaxSumOfVarsInPyramidCount++;
				}
				if ( isRightCellCombinationsForced && isMaxSumOfProductionsCount && isMaxSumOfVarsInPyramidCount ) {
					trueExamConstraints++;
				}
				else {
					falseExamConstraints++;
				}
			}
			successRateExamConstraints = (double) trueExamConstraints / countGeneratedGrammars;
			successRateRightCellCombinationsForced = (double) trueRightCellCombinationsForcedCount / countGeneratedGrammars;
			successRateMaxSumOfProductions = (double) trueMaxSumOfProductionsCount / countGeneratedGrammars;
			successRateMaxSumOfVarsInPyramid = (double) trueMaxSumOfVarsInPyramidCount / countGeneratedGrammars;
		}
	}

	public int getTrueExamConstraints() {
		return trueExamConstraints;
	}

	public int getFalseExamConstraints() {
		return falseExamConstraints;
	}

	public double getSuccessRateExamConstraints() {
		return successRateExamConstraints;
	}

	public int getTrueRightCellCombinationsForcedCount() {
		return trueRightCellCombinationsForcedCount;
	}

	public int getFalseRightCellCombinationsForcedCount() {
		return falseRightCellCombinationsForcedCount;
	}

	public double getSuccessRateRightCellCombinationsForced() {
		return successRateRightCellCombinationsForced;
	}

	public int getTrueMaxSumOfProductionsCount() {
		return trueMaxSumOfProductionsCount;
	}

	public int getFalseMaxSumOfProductionsCount() {
		return falseMaxSumOfProductionsCount;
	}

	public double getSuccessRateMaxSumOfProductions() {
		return successRateMaxSumOfProductions;
	}

	public int getTrueMaxSumOfVarsInPyramidCount() {
		return trueMaxSumOfVarsInPyramidCount;
	}

	public int getFalseMaxSumOfVarsInPyramidCount() {
		return falseMaxSumOfVarsInPyramidCount;
	}

	public double getSuccessRateMaxSumOfVarsInPyramid() {
		return successRateMaxSumOfVarsInPyramid;
	}

	@Override
	public String toString() {
		return "\nSuccessRatesExamConstraints{" +
				"\n		trueExamConstraints=" + trueExamConstraints +
				"\n		falseExamConstraints=" + falseExamConstraints +
				"\n			-->	SUCCESSRATEExamConstraints=" + successRateExamConstraints +
				"\n		trueRightCellCombinationsForcedCount=" + trueRightCellCombinationsForcedCount +
				"\n		falseRightCellCombinationsForcedCount=" + falseRightCellCombinationsForcedCount +
				"\n			-->	SUCCESSRATERightCellCombinationsForced=" + successRateRightCellCombinationsForced +
				"\n		trueMaxSumOfProductionsCount=" + trueMaxSumOfProductionsCount +
				"\n		falseMaxSumOfProductionsCount=" + falseMaxSumOfProductionsCount +
				"\n			-->	SUCCESSRATEMaxSumOfProductions=" + successRateMaxSumOfProductions +
				"\n		trueMaxSumOfVarsInPyramidCount=" + trueMaxSumOfVarsInPyramidCount +
				"\n		falseMaxSumOfVarsInPyramidCount=" + falseMaxSumOfVarsInPyramidCount +
				"\n			-->	SUCCESSRATEMaxSumOfVarsInPyramid=" + successRateMaxSumOfVarsInPyramid +
				"\n}";
	}
}
