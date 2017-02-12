package com.github.andreasbraun5.thesis.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.GrammarWrapper;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;

/**
 * Created by Andreas Braun on 21.12.2016.
 * - consider terminals (terminal set) and alphabet as synonyms
 * - each of the terminals must be included in the grammar at least in one production
 * - One kind of bias is the setting of the minValue* and maxValue* variables.
 * - Another kind of bias is that one variable gets more rightHandSideElements than the others, this happens with a probability.
 */
public class GrammarGeneratorDiceRollOnly extends GrammarGeneratorDiceRoll<GrammarGeneratorSettingsDiceRoll> {

	// Needed for testing purposes, fixed seed possible.
	public GrammarGeneratorDiceRollOnly(
			GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings, Random random) {
		super( generatorGrammarDiceRollSettings, random );
		this.generatorType = "DICEROLLONLY";
	}

	public GrammarGeneratorDiceRollOnly(
			GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings) {
		super( generatorGrammarDiceRollSettings, new Random() );
		this.generatorType = "DICEROLLONLY";
	}

	@Override
	protected GrammarWrapper distributeTerminals(GrammarWrapper grammarWrapper) {
		return distributeDiceRollRightHandSideElements(
				grammarWrapper,
				generatorGrammarSettings.grammarProperties.terminals,
				generatorGrammarSettings.getMinValueTerminalsAreAddedTo(),
				generatorGrammarSettings.getMaxValueTerminalsAreAddedTo(),
				new ArrayList<>( generatorGrammarSettings.grammarProperties.variables )
		);
	}

	@Override
	protected GrammarWrapper distributeCompoundVariables(GrammarWrapper grammarWrapper) {
		Set<VariableCompound> varTupel = new HashSet<>();
		for ( Variable var1 : generatorGrammarSettings.grammarProperties.variables ) {
			for ( Variable var2 : generatorGrammarSettings.grammarProperties.variables ) {
				varTupel.add( new VariableCompound( var1, var2 ) );
			}
		}
		return super.distributeDiceRollRightHandSideElements(
				grammarWrapper,
				varTupel,
				generatorGrammarSettings.getMinValueCompoundVariablesAreAddedTo(),
				generatorGrammarSettings.getMaxValueCompoundVariablesAreAddedTo(),
				new ArrayList<>( generatorGrammarSettings.grammarProperties.variables )
		);
	}


}