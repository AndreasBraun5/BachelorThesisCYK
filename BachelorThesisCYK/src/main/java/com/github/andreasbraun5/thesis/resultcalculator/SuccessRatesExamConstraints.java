package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class SuccessRatesExamConstraints {

	private int countGeneratedGrammars = 0;

	private int trueExamConstraints = 0;
	private int falseExamConstraints = 0;
	private double successRateExamConstraints = 0.0;

	private int trueRightCellCombinationsForcedCount = 0;
	private int falseRightCellCombinationsForcedCount = 0;
	private double successRateRightCellCombinationsForced = 0.0;

	private int trueMaxSumOfProductionsCount = 0;
	private int falseMaxSumOfProductionsCount = 0;
	private double successRateMaxSumOfProductions = 0.0;

	private int trueMaxSumOfVarsInPyramidCount = 0;
	private int falseMaxSumOfVarsInPyramidCount = 0;
	private double successRateMaxSumOfVarsInPyramid = 0.0;

	public SuccessRatesExamConstraints updateSuccessRatesExamConstraints(Map<String, List<ResultSample>> chunkResultSamples) {
		for ( Map.Entry<String, List<ResultSample>> entry : chunkResultSamples.entrySet() ) {
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
		return this;
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
