package com.github.andreasbraun5.thesis.generatortest;

import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class TestGrammarSample {

	private Grammar grammar;
	private String word;
	private Set<Variable>[][] setV;
	private boolean validity;
	private boolean isWordProducible;
	private boolean fulfillsRestriction;
	private Integer maxVarsPerCellSetV;

	public TestGrammarSample(
			Grammar grammar,
			String word,
			Set<Variable>[][] setV,
			boolean validity,
			boolean isWordProducible,
			boolean fulfillsRestriction,
			int maxVarsPerCellSetV) {
		this.grammar = grammar;
		this.word = word;
		this.setV = setV;
		this.validity = validity;
		this.isWordProducible = isWordProducible;
		this.fulfillsRestriction = fulfillsRestriction;
		this.maxVarsPerCellSetV = maxVarsPerCellSetV;
	}

	@Override
	public String toString() {
		return "TestGrammarSample{" +
				"\ngrammar=" + grammar +
				"\nword='" + word + '\'' +
				"\nsetV=" + Util.getSetVAsStringForPrinting( setV, "setV" ) +
				"\nvalidity=" + validity +
				"\nisWordProducible=" + isWordProducible +
				"\nfulfillsRestriction=" + fulfillsRestriction +
				"\nmaxVarsPerCellSetV=" + maxVarsPerCellSetV +
				"\n}";
	}

	public Grammar getGrammar() {
		return grammar;
	}

	public String getWord() {
		return word;
	}

	public Set<Variable>[][] getSetV() {
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
}
