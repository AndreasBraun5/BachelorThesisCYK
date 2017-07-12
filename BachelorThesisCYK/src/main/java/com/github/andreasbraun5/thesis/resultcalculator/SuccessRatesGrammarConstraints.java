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
public class SuccessRatesGrammarConstraints {

	private int countGeneratedGrammars = 0;

	private int trueGrammarRestrictionsCount = 0;
	private int falseGrammarRestrictionsCount = 0;
	private double successRateGrammarRestrictions = 0.0;

	private int trueMaxNumberOfVarsPerCellCount = 0;
	private int falseMaxNumberOfVarsPerCellCount = 0;
	private double successRateMaxNumberOfVarsPerCell = 0.0;

	public SuccessRatesGrammarConstraints updateSuccessRatesGrammarRestrictions(Map<Word, List<ResultSample>> chunkResultSamples) {
		for ( Map.Entry<Word, List<ResultSample>> entry : chunkResultSamples.entrySet() ) {
			for ( ResultSample resultSample : entry.getValue() ) {
				countGeneratedGrammars++;
				ResultSampleGrammarRestrictions tempResultSampleGrammarRestrictions =
						resultSample.getResultSampleGrammarRestrictions();
				boolean isMaxNumberOfVarsPerCellCount = tempResultSampleGrammarRestrictions.isMaxNumberOfVarsPerCell();
				if ( isMaxNumberOfVarsPerCellCount ) {
					trueMaxNumberOfVarsPerCellCount++;
				}
				else {
					falseMaxNumberOfVarsPerCellCount++;
				}
				if ( isMaxNumberOfVarsPerCellCount ) {
					trueGrammarRestrictionsCount++;
				}
				else {
					falseGrammarRestrictionsCount++;
				}
			}
			successRateGrammarRestrictions = (double) trueGrammarRestrictionsCount / countGeneratedGrammars;
			successRateMaxNumberOfVarsPerCell = (double) trueMaxNumberOfVarsPerCellCount / countGeneratedGrammars;
		}
		return this;
	}

    @Override
    public String toString() {
        return "\nSuccessRatesGrammarConstraints{" +
                "\n		trueGrammarRestrictionsCount=" + trueGrammarRestrictionsCount +
                "\n		falseGrammarRestrictionsCount=" + falseGrammarRestrictionsCount +
                "\n			-->	SUCCESSRATEGrammarRestrictions=" + successRateGrammarRestrictions +
                "\n		trueMaxNumberOfVarsPerCellCount=" + trueMaxNumberOfVarsPerCellCount +
                "\n		falseMaxNumberOfVarsPerCellCount=" + falseMaxNumberOfVarsPerCellCount +
                "\n			-->	SUCCESSRATEMaxNumberOfVarsPerCell=" + successRateMaxNumberOfVarsPerCell +
                "\n}";
    }
}
