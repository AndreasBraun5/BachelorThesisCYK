package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSample {

	private Grammar grammar;
	private String word;
	private Set<VariableKWrapper>[][] setV;
	private boolean validity;
	private boolean isWordProducible;
	private boolean fulfillsRestriction;
	private boolean rightCellCombinationForced;
	private Integer maxVarsPerCellSetV;

	public ResultSample(
			Grammar grammar,
			String word,
			Set<VariableKWrapper>[][] setV,
			boolean validity,
			boolean isWordProducible,
			boolean fulfillsRestriction,
			boolean rightCellCombinationForced,
			int maxVarsPerCellSetV) {
		this.grammar = grammar;
		this.word = word;
		this.setV = setV;
		this.validity = validity;
		this.isWordProducible = isWordProducible;
		this.fulfillsRestriction = fulfillsRestriction;
		this.rightCellCombinationForced = rightCellCombinationForced;
		this.maxVarsPerCellSetV = maxVarsPerCellSetV;
	}

	@Override
	public String toString() {
		return "ResultSample{" +
				"\ngrammar=" + grammar +
				"\nword='" + word + '\'' +
				"\nsetV=" + Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				Util.getVarsFromSetDoubleArray( setV ), "setV" ) +
				"\nvalidity=" + validity +
				"\nisWordProducible=" + isWordProducible +
				"\nfulfillsRestriction=" + fulfillsRestriction +
				"\nrightCellCombinationsForced=" + rightCellCombinationForced +
				"\nmaxVarsPerCellSetV=" + maxVarsPerCellSetV +
				"\n}";
	}

	public Grammar getGrammar() {
		return grammar;
	}

	public String getWord() {
		return word;
	}

	public Set<VariableKWrapper>[][] getSetV() {
		return setV;
	}

	public boolean isValidity() {
		return validity;
	}

	public boolean isWordProducible() {
		return isWordProducible;
	}

	public boolean isFulfillsRestriction() {
		return fulfillsRestriction;
	}

	public Integer getMaxVarsPerCellSetV() {
		return maxVarsPerCellSetV;
	}

	public boolean isRightCellCombinationForced() {
		return rightCellCombinationForced;
	}
}
