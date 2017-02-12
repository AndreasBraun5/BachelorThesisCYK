package com.github.andreasbraun5.thesis.resultcalculator;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSampleGrammarRestrictions {

	private boolean isRestrictions;
	private boolean isSizeOfWordCount;
	private boolean isMaxNumberOfVarsPerCellCount;

	public static ResultSampleGrammarRestrictions buildResultSampleGrammarRestrictions(){
		return new ResultSampleGrammarRestrictions();
	}

	public boolean isGrammarRestrictions() {
		return isRestrictions;
	}

	public ResultSampleGrammarRestrictions setRestrictions(boolean restrictions) {
		isRestrictions = restrictions;
		return this;
	}

	public boolean isSizeOfWordCount() {
		return isSizeOfWordCount;
	}

	public ResultSampleGrammarRestrictions setSizeOfWordCount(boolean sizeOfWordCount) {
		isSizeOfWordCount = sizeOfWordCount;
		return this;
	}

	public boolean isMaxNumberOfVarsPerCellCount() {
		return isMaxNumberOfVarsPerCellCount;
	}

	public ResultSampleGrammarRestrictions setMaxNumberOfVarsPerCellCount(boolean maxNumberOfVarsPerCellCount) {
		isMaxNumberOfVarsPerCellCount = maxNumberOfVarsPerCellCount;
		return this;
	}

	@Override
	public String toString() {
		return "\nResultSampleGrammarRestrictions{" +
				"\n		isGrammarRestrictions=" + isRestrictions +
				"\n		isSizeOfWordCount=" + isSizeOfWordCount +
				"\n		isMaxNumberOfVarsPerCellCount=" + isMaxNumberOfVarsPerCellCount +
				"\n}";
	}
}
