package com.github.andreasbraun5.thesis.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.GrammarWordMatrixWrapper;
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
	protected GrammarWordMatrixWrapper distributeTerminals(GrammarWordMatrixWrapper grammarWordMatrixWrapper) {
		return distributeDiceRollRightHandSideElements(
                grammarWordMatrixWrapper,
				grammarGeneratorSettings.grammarProperties.terminals,
				grammarGeneratorSettings.getMinValueTerminalsAreAddedTo(),
				grammarGeneratorSettings.getMaxValueTerminalsAreAddedTo(),
				new ArrayList<>( grammarGeneratorSettings.grammarProperties.variables )
		);
	}

	@Override
	protected GrammarWordMatrixWrapper distributeCompoundVariables(GrammarWordMatrixWrapper grammarWordMatrixWrapper) {
		Set<VariableCompound> varTupel = new HashSet<>();
		for ( Variable var1 : grammarGeneratorSettings.grammarProperties.variables ) {
			for ( Variable var2 : grammarGeneratorSettings.grammarProperties.variables ) {
				varTupel.add( new VariableCompound( var1, var2 ) );
			}
		}
		return super.distributeDiceRollRightHandSideElements(
                grammarWordMatrixWrapper,
				varTupel,
				grammarGeneratorSettings.getMinValueCompoundVariablesAreAddedTo(),
				grammarGeneratorSettings.getMaxValueCompoundVariablesAreAddedTo(),
				new ArrayList<>( grammarGeneratorSettings.grammarProperties.variables )
		);
	}


}