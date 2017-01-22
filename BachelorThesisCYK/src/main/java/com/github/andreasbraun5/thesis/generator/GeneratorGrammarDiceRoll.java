package com.github.andreasbraun5.thesis.generator;

import java.util.*;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;

/**
 * Created by Andreas Braun on 21.12.2016.
 * - consider terminals (terminal set) and alphabet as synonyms
 * - each of the terminals must be included in the grammar at least in one production
 * - One kind of bias is the setting of the minValue* and maxValue* variables.
 * - Another kind of bias is that one variable gets more rightHandSideElements than the others, this happens with a probability.
 */
public class GeneratorGrammarDiceRoll {

	private GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings;
	private final Random random;

	// Needed for testing purposes, fixed seed possible.
	public GeneratorGrammarDiceRoll(
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings,
			Random random) {
		this.generatorGrammarDiceRollSettings = generatorGrammarDiceRollSettings;
		this.random = random;
	}

	public GeneratorGrammarDiceRoll(
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings) {
		this( generatorGrammarDiceRollSettings, new Random() );
	}

	/**
	 * Generate a random grammar via dice rolling and bias.
	 */
	public Grammar generateGrammar() {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( generatorGrammarDiceRollSettings.grammarProperties.variableStart );
		grammar = distributeTerminals( grammar );
		grammar = distributeCompoundVariables( grammar );
		return grammar;
	}

	public Grammar generateGrammarBias() {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( generatorGrammarDiceRollSettings.grammarProperties.variableStart );
		grammar = distributeTerminalsBias( grammar );
		grammar = distributeCompoundVariablesBias( grammar );
		return grammar;
	}

	private Grammar distributeTerminalsBias(Grammar grammar) {
		return distributeDiceRollRightHandSideElementsBias(
				grammar,
				generatorGrammarDiceRollSettings.grammarProperties.terminals,
				generatorGrammarDiceRollSettings.getMinValueTerminalsAreAddedTo(),
				generatorGrammarDiceRollSettings.getMaxValueTerminalsAreAddedTo(),
				generatorGrammarDiceRollSettings.getFavouritism()
		);
	}

	private Grammar distributeCompoundVariablesBias(Grammar grammar) {
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

	private Grammar distributeTerminals(Grammar grammar) {
		return distributeDiceRollRightHandSideElements(
				grammar,
				generatorGrammarDiceRollSettings.grammarProperties.terminals,
				generatorGrammarDiceRollSettings.getMinValueTerminalsAreAddedTo(),
				generatorGrammarDiceRollSettings.getMaxValueTerminalsAreAddedTo(),
				new ArrayList<>( generatorGrammarDiceRollSettings.grammarProperties.variables )
		);
	}

	private Grammar distributeCompoundVariables(Grammar grammar) {
		Set<VariableCompound> varTupel = new HashSet<>();
		for ( Variable var1 : generatorGrammarDiceRollSettings.grammarProperties.variables ) {
			for ( Variable var2 : generatorGrammarDiceRollSettings.grammarProperties.variables ) {
				varTupel.add( new VariableCompound( var1, var2 ) );
			}
		}
		return distributeDiceRollRightHandSideElements(
				grammar,
				varTupel,
				generatorGrammarDiceRollSettings.getMinValueCompoundVariablesAreAddedTo(),
				generatorGrammarDiceRollSettings.getMaxValueCompoundVariablesAreAddedTo(),
				new ArrayList<>( generatorGrammarDiceRollSettings.grammarProperties.variables )
		);
	}

	/**
	 * @param minCountElementDistributedTo: If you want to distribute the terminals to at least one rightHandSide then
	 * this value is 1.
	 */
	private Grammar distributeDiceRollRightHandSideElements(
			Grammar grammar,
			Set<? extends RightHandSideElement> rightHandSideElements,
			int minCountElementDistributedTo,
			int maxCountElementDistributedTo,
			List<Variable> variablesWeighted) {
		for ( RightHandSideElement tempRhse : rightHandSideElements ) {
			// countOfLeftSideRhseWillBeAdded is element of the interval [minCountElementDistributedTo, maxCountElementDistributedTo]
			int countOfLeftSideRhseWillBeAdded = random.nextInt( maxCountElementDistributedTo ) + minCountElementDistributedTo;
			//Removing Variables from variablesWeighted until countOfVarsTerminalWillBeAdded vars are left.
			List<Variable> tempVar = new ArrayList<>( variablesWeighted );
			for ( int i = tempVar.size(); i > countOfLeftSideRhseWillBeAdded; i-- ) {
				tempVar.remove( random.nextInt( tempVar.size() ) );
			}
			for ( Variable var : tempVar ) {
				grammar.addProduction( new Production( var, tempRhse ) );
			}
		}
		return grammar;
	}


	private Grammar distributeDiceRollRightHandSideElementsBias(
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