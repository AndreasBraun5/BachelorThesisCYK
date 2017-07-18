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

    private int trueMaxSumOfProductionsCount = 0;
    private int falseMaxSumOfProductionsCount = 0;
    private double successRateMaxSumOfProductions = 0.0;

	public SuccessRatesGrammarConstraints updateSuccessRatesGrammarRestrictions(Map<Word, List<ResultSample>> chunkResultSamples) {
		for ( Map.Entry<Word, List<ResultSample>> entry : chunkResultSamples.entrySet() ) {
			for ( ResultSample resultSample : entry.getValue() ) {
				countGeneratedGrammars++;
				GrammarConstraintValues tempGrammarConstraintValues =
						resultSample.getGrammarConstraintValues();
                boolean isMaxSumOfProductionsCount = tempGrammarConstraintValues.isMaxSumOfProductions();
                if ( isMaxSumOfProductionsCount ) {
                    trueMaxSumOfProductionsCount++;
                }
                else {
                    falseMaxSumOfProductionsCount++;
                }
				if ( isMaxSumOfProductionsCount ) {
					trueGrammarRestrictionsCount++;
				}
				else {
					falseGrammarRestrictionsCount++;
				}
			}
			successRateGrammarRestrictions = (double) trueGrammarRestrictionsCount / countGeneratedGrammars;
            successRateMaxSumOfProductions = (double) trueMaxSumOfProductionsCount / countGeneratedGrammars;
		}
		return this;
	}

    @Override
    public String toString() {
        return "\nSuccessRatesGrammarConstraints{" +
                "\n		trueGrammarRestrictionsCount=" + trueGrammarRestrictionsCount +
                "\n		falseGrammarRestrictionsCount=" + falseGrammarRestrictionsCount +
                "\n			-->	SUCCESSRATEGrammarRestrictions=" + successRateGrammarRestrictions +
                "\n		trueMaxSumOfProductionsCount=" + trueMaxSumOfProductionsCount +
                "\n		falseMaxSumOfProductionsCount=" + falseMaxSumOfProductionsCount +
                "\n			-->	SUCCESSRATEMaxSumOfProductions=" + successRateMaxSumOfProductions +
                "\n}";
    }
}
