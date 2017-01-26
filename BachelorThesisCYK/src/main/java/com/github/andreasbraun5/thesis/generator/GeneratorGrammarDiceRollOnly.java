package com.github.andreasbraun5.thesis.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;

/**
 * Created by Andreas Braun on 21.12.2016.
 * - consider terminals (terminal set) and alphabet as synonyms
 * - each of the terminals must be included in the grammar at least in one production
 * - One kind of bias is the setting of the minValue* and maxValue* variables.
 * - Another kind of bias is that one variable gets more rightHandSideElements than the others, this happens with a probability.
 */
public class GeneratorGrammarDiceRollOnly extends GeneratorGrammarDiceRoll<GeneratorGrammarDiceRollSettings> {

	// Needed for testing purposes, fixed seed possible.
	public GeneratorGrammarDiceRollOnly(
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings,
			Random random, GeneratorType generatorType) {
		super( generatorGrammarDiceRollSettings, random, generatorType );
	}

	public GeneratorGrammarDiceRollOnly(
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings, GeneratorType generatorType) {
		super( generatorGrammarDiceRollSettings, new Random(), generatorType );
	}

	/**
	 * Generate a random grammar via dice rolling and bias.
	 */
	@Override
	public Grammar generateGrammar() {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( generatorGrammarSettings.grammarProperties.variableStart );
		grammar = distributeTerminals( grammar );
		grammar = distributeCompoundVariables( grammar );
		return grammar;
	}

	@Override
	protected Grammar distributeTerminals(Grammar grammar) {
		return distributeDiceRollRightHandSideElements(
				grammar,
				generatorGrammarSettings.grammarProperties.terminals,
				generatorGrammarSettings.getMinValueTerminalsAreAddedTo(),
				generatorGrammarSettings.getMaxValueTerminalsAreAddedTo(),
				new ArrayList<>( generatorGrammarSettings.grammarProperties.variables )
		);
	}

	@Override
	protected Grammar distributeCompoundVariables(Grammar grammar) {
		Set<VariableCompound> varTupel = new HashSet<>();
		for ( Variable var1 : generatorGrammarSettings.grammarProperties.variables ) {
			for ( Variable var2 : generatorGrammarSettings.grammarProperties.variables ) {
				varTupel.add( new VariableCompound( var1, var2 ) );
			}
		}
		return super.distributeDiceRollRightHandSideElements(
				grammar,
				varTupel,
				generatorGrammarSettings.getMinValueCompoundVariablesAreAddedTo(),
				generatorGrammarSettings.getMaxValueCompoundVariablesAreAddedTo(),
				new ArrayList<>( generatorGrammarSettings.grammarProperties.variables )
		);
	}


}