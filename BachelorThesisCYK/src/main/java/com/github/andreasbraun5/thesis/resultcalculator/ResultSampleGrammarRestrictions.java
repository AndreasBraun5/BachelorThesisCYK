package com.github.andreasbraun5.thesis.resultcalculator;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSampleGrammarRestrictions {

    public boolean isGrammarRestrictions;
    public boolean isSizeOfWordCount;
    public boolean isMaxNumberOfVarsPerCellCount;
    public int maxNumberOfVarsPerCellCount;

	@Override
	public String toString() {
		return "\nResultSampleGrammarRestrictions{" +
				"\n		isGrammarRestrictions=" + isGrammarRestrictions +
				"\n		isSizeOfWordCount=" + isSizeOfWordCount +
				"\n		isMaxNumberOfVarsPerCellCount=" + isMaxNumberOfVarsPerCellCount +
				"\n}";
	}
}
