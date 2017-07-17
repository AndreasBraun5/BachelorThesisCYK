package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.util.Word;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
public class SuccessRatesPyramidConstraints {

	private int countGeneratedGrammars = 0;

	private int trueExamConstraints = 0;
	private int falseExamConstraints = 0;
	private double successRateExamConstraints = 0.0;

	private int trueRightCellCombinationsForcedCount = 0;
	private int falseRightCellCombinationsForcedCount = 0;
	private double successRateRightCellCombinationsForced = 0.0;

    private int trueMaxNumberOfVarsPerCellCount = 0;
    private int falseMaxNumberOfVarsPerCellCount = 0;
    private double successRateMaxNumberOfVarsPerCell = 0.0;

	private int trueMaxSumOfVarsInPyramidCount = 0;
	private int falseMaxSumOfVarsInPyramidCount = 0;
	private double successRateMaxSumOfVarsInPyramid = 0.0;

	public SuccessRatesPyramidConstraints updateSuccessRatesExamConstraints(Map<Word, List<ResultSample>> chunkResultSamples) {
		for ( Map.Entry<Word, List<ResultSample>> entry : chunkResultSamples.entrySet() ) {
			for ( ResultSample resultSample : entry.getValue() ) {
				countGeneratedGrammars++;
				ResultSamplePyramidConstraints tempResultSamplePyramidConstraints =
						resultSample.getResultSamplePyramidConstraints();
				boolean isRightCellCombinationsForced = tempResultSamplePyramidConstraints.isRightCellCombinationsForced();
                boolean isMaxNumberOfVarsPerCellCount = tempResultSamplePyramidConstraints.isMaxNumberOfVarsPerCell();
				boolean isMaxSumOfVarsInPyramidCount = tempResultSamplePyramidConstraints.isMaxSumOfVarsInPyramid();
				if ( isRightCellCombinationsForced ) {
					trueRightCellCombinationsForcedCount++;
				}
				else {
					falseRightCellCombinationsForcedCount++;
				}
                if ( isMaxNumberOfVarsPerCellCount ) {
                    trueMaxNumberOfVarsPerCellCount++;
                }
                else {
                    falseMaxNumberOfVarsPerCellCount++;
                }
				if ( isMaxSumOfVarsInPyramidCount ) {
					trueMaxSumOfVarsInPyramidCount++;
				}
				else {
					falseMaxSumOfVarsInPyramidCount++;
				}
				if ( isRightCellCombinationsForced && isMaxNumberOfVarsPerCellCount && isMaxSumOfVarsInPyramidCount ) {
					trueExamConstraints++;
				}
				else {
					falseExamConstraints++;
				}
			}
			successRateExamConstraints = (double) trueExamConstraints / countGeneratedGrammars;
			successRateRightCellCombinationsForced = (double) trueRightCellCombinationsForcedCount / countGeneratedGrammars;
            successRateMaxNumberOfVarsPerCell = (double) trueMaxNumberOfVarsPerCellCount / countGeneratedGrammars;
            successRateMaxSumOfVarsInPyramid = (double) trueMaxSumOfVarsInPyramidCount / countGeneratedGrammars;
		}
		return this;
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
                "\n		trueMaxNumberOfVarsPerCellCount=" + trueMaxNumberOfVarsPerCellCount +
                "\n		falseMaxNumberOfVarsPerCellCount=" + falseMaxNumberOfVarsPerCellCount +
                "\n			-->	SUCCESSRATEMaxNumberOfVarsPerCell=" + successRateMaxNumberOfVarsPerCell +
                "\n		trueMaxSumOfVarsInPyramidCount=" + trueMaxSumOfVarsInPyramidCount +
                "\n		falseMaxSumOfVarsInPyramidCount=" + falseMaxSumOfVarsInPyramidCount +
                "\n			-->	SUCCESSRATEMaxSumOfVarsInPyramid=" + successRateMaxSumOfVarsInPyramid +
                "\n}";
    }
}
