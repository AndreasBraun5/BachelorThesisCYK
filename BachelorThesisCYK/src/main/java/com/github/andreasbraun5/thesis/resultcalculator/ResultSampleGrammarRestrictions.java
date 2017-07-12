package com.github.andreasbraun5.thesis.resultcalculator;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
@Setter
class ResultSampleGrammarRestrictions {

    private boolean grammarRestrictions;
    private boolean maxNumberOfVarsPerCell;
    int maxNumberOfVarsPerCellCount;

	@Override
	public String toString() {
		return "\nResultSampleGrammarRestrictions{" +
				"\n		grammarRestrictions=" + grammarRestrictions +
				"\n		maxNumberOfVarsPerCell=" + maxNumberOfVarsPerCell +
				"\n}";
	}
}
