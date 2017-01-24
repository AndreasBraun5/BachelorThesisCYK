package com.github.andreasbraun5.thesis.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;

/**
 * Created by Andreas Braun on 24.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class GeneratorGrammarDiceRollBias extends GeneratorGrammarDiceRoll {

	public GeneratorGrammarDiceRollBias(
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings,
			Random random) {
		super( generatorGrammarDiceRollSettings, random );
	}

	public GeneratorGrammarDiceRollBias(GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings) {
		super( generatorGrammarDiceRollSettings );
	}

	public Grammar generateGrammarBias() {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( generatorGrammarDiceRollSettings.grammarProperties.variableStart );
		grammar = distributeTerminals( grammar );
		grammar = distributeCompoundVariables( grammar );
		return grammar;
	}

	@Override
	protected Grammar distributeTerminals(Grammar grammar) {
		return distributeDiceRollRightHandSideElementsBias(
				grammar,
				generatorGrammarDiceRollSettings.grammarProperties.terminals,
				generatorGrammarDiceRollSettings.getMinValueTerminalsAreAddedTo(),
				generatorGrammarDiceRollSettings.getMaxValueTerminalsAreAddedTo(),
				generatorGrammarDiceRollSettings.getFavouritism()
		);
	}

	@Override
	protected Grammar distributeCompoundVariables(Grammar grammar) {
		Set<VariableCompound> varTupel = new HashSet<>();
		for ( Variable var1 : generatorGrammarDiceRollSettings.grammarProperties.variables ) {
			for ( Variable var2 : generatorGrammarDiceRollSettings.grammarProperties.variables ) {
				varTupel.add( new VariableCompound( var1, var2 ) );
			}
		}
		return distributeDiceRollRightHandSideElementsBias(
				grammar,
				varTupel,
				generatorGrammarDiceRollSettings.getMinValueCompoundVariablesAreAddedTo(),
				generatorGrammarDiceRollSettings.getMaxValueCompoundVariablesAreAddedTo(),
				generatorGrammarDiceRollSettings.getFavouritism()
		);
	}

	protected Grammar distributeDiceRollRightHandSideElementsBias(
			Grammar grammar,
			Set<? extends RightHandSideElement> rightHandSideElements,
			int minCountElementDistributedTo,
			int maxCountElementDistributedTo,
			int favouritism[]) {
		List<Variable> tempVariables2 = new ArrayList<>( generatorGrammarDiceRollSettings.grammarProperties.variables );
		Map<Variable, Integer> favouritismToVariable = new HashMap<>();
		{
			// Mapping the favouritism randomly to the variables. Pick one random variable and add the first favouritism to it.
			int indexVar = 0;
			for ( int i = 0; i < favouritism.length; i++ ) {
				indexVar = random.nextInt( tempVariables2.size() );
				favouritismToVariable.put(
						tempVariables2.get( ( indexVar ) ),
						favouritism[i]
				);
				tempVariables2.remove( indexVar );
			}
			tempVariables2.clear();
			// now the mapped favouritism inflates the the List<Variables>, so that the different weights are reflected.
			for ( Map.Entry<Variable, Integer> entry : favouritismToVariable.entrySet() ) {
				for ( int i = 0; i < entry.getValue(); i++ ) {
					tempVariables2.add( entry.getKey() );
				}
			}
		}
		return distributeDiceRollRightHandSideElements(
				grammar,
				rightHandSideElements,
				minCountElementDistributedTo,
				maxCountElementDistributedTo,
				tempVariables2
		);
	}
}

