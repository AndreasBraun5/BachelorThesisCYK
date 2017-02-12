package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class SuccessRatesGrammarRestrictions {

	private int trueGrammarRestrictionsCount;
	private int falseGrammarRestrictionsCount;
	private double successRateGrammarRestrictions;

	private int trueSizeOfWordCount;
	private int falseSizeOfWordCount;
	private double successRateSizeOfWord;

	private int trueMaxNumberOfVarsPerCellCount;
	private int falseMaxNumberOfVarsPerCellCount;
	private double successRateMaxNumberOfVarsPerCell;

	public SuccessRatesGrammarRestrictions(Map<String, List<ResultSample>> allResultSamples) {
		int countGeneratedGrammars = 0;
		for ( Map.Entry<String, List<ResultSample>> entry : allResultSamples.entrySet() ) {
			for ( ResultSample resultSample : entry.getValue() ) {
				countGeneratedGrammars++;
				ResultSampleGrammarRestrictions tempResultSampleGrammarRestrictions =
						resultSample.getResultSampleGrammarRestrictions();
				boolean isSizeOfWordCount = tempResultSampleGrammarRestrictions.isSizeOfWordCount();
				boolean isMaxNumberOfVarsPerCellCount = tempResultSampleGrammarRestrictions.isMaxNumberOfVarsPerCellCount();
				if ( isSizeOfWordCount ) {
					trueSizeOfWordCount++;
				}
				else {
					falseSizeOfWordCount++;
				}
				if ( isMaxNumberOfVarsPerCellCount ) {
					trueMaxNumberOfVarsPerCellCount++;
				}
				else {
					falseMaxNumberOfVarsPerCellCount++;
				}
				if ( isMaxNumberOfVarsPerCellCount && isSizeOfWordCount ) {
					trueGrammarRestrictionsCount++;
				}
				else {
					falseGrammarRestrictionsCount++;
				}
			}
			successRateGrammarRestrictions = (double) trueGrammarRestrictionsCount / countGeneratedGrammars;
			successRateSizeOfWord = (double) trueSizeOfWordCount / countGeneratedGrammars;
			successRateMaxNumberOfVarsPerCell = (double) trueMaxNumberOfVarsPerCellCount / countGeneratedGrammars;
		}
	}

	public int getTrueGrammarRestrictionsCount() {
		return trueGrammarRestrictionsCount;
	}

	public int getFalseGrammarRestrictionsCount() {
		return falseGrammarRestrictionsCount;
	}

	public double getSuccessRateGrammarRestrictions() {
		return successRateGrammarRestrictions;
	}

	public int getTrueSizeOfWordCount() {
		return trueSizeOfWordCount;
	}

	public int getFalseSizeOfWordCount() {
		return falseSizeOfWordCount;
	}

	public double getSuccessRateSizeOfWord() {
		return successRateSizeOfWord;
	}

	public int getTrueMaxNumberOfVarsPerCellCount() {
		return trueMaxNumberOfVarsPerCellCount;
	}

	public int getFalseMaxNumberOfVarsPerCellCount() {
		return falseMaxNumberOfVarsPerCellCount;
	}

	public double getSuccessRateMaxNumberOfVarsPerCell() {
		return successRateMaxNumberOfVarsPerCell;
	}

	@Override
	public String toString() {
		return "\nSuccessRatesGrammarRestrictions{" +
				"\n		trueGrammarRestrictionsCount=" + trueGrammarRestrictionsCount +
				"\n		falseGrammarRestrictionsCount=" + falseGrammarRestrictionsCount +
				"\n			-->	SUCCESSRATEGrammarRestrictions=" + successRateGrammarRestrictions +
				"\n		trueSizeOfWordCount=" + trueSizeOfWordCount +
				"\n		falseSizeOfWordCount=" + falseSizeOfWordCount +
				"\n			-->	SUCCESSRATESizeOfWord=" + successRateSizeOfWord +
				"\n		trueMaxNumberOfVarsPerCellCount=" + trueMaxNumberOfVarsPerCellCount +
				"\n		falseMaxNumberOfVarsPerCellCount=" + falseMaxNumberOfVarsPerCellCount +
				"\n			-->	SUCCESSRATEMaxNumberOfVarsPerCell=" + successRateMaxNumberOfVarsPerCell +
				"\n}";
	}
}
