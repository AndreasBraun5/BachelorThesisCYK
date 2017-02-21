package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class SuccessRatesGrammarRestrictions {

	private int countGeneratedGrammars = 0;

	private int trueGrammarRestrictionsCount = 0;
	private int falseGrammarRestrictionsCount = 0;
	private double successRateGrammarRestrictions = 0.0;

	private int trueSizeOfWordCount = 0;
	private int falseSizeOfWordCount = 0;
	private double successRateSizeOfWord = 0.0;

	private int trueMaxNumberOfVarsPerCellCount = 0;
	private int falseMaxNumberOfVarsPerCellCount = 0;
	private double successRateMaxNumberOfVarsPerCell = 0.0;

	public SuccessRatesGrammarRestrictions updateSuccessRatesGrammarRestrictions(Map<String, List<ResultSample>> chunkResultSamples) {
		for ( Map.Entry<String, List<ResultSample>> entry : chunkResultSamples.entrySet() ) {
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
		return this;
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
