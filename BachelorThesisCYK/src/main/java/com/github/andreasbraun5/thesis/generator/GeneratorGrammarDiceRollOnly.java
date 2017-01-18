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
 */
public class GeneratorGrammarDiceRollOnly implements GeneratorGrammar {

	private GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings;
	private final Random random;

	// Needed for testing purposes, fixed seed possible.
	public GeneratorGrammarDiceRollOnly(
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings,
			Random random) {
		this.generatorGrammarDiceRollSettings = generatorGrammarDiceRollSettings;
		this.random = random;
	}

	public GeneratorGrammarDiceRollOnly(
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings) {
		this( generatorGrammarDiceRollSettings, new Random() );
	}

	/**
	 * Generate a random grammar via dice rolling.
	 */
	@Override
	public Grammar generateGrammar() {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected any more.
		Grammar grammar = new Grammar( generatorGrammarDiceRollSettings.grammarProperties.variableStart );
		grammar = distributeTerminals( grammar );
		grammar = distributeCompoundVariables( grammar );
		return grammar;
	}

	private Grammar distributeTerminals(Grammar grammar) {
		return distributeDiceRollRightHandSideElements(
				grammar,
				generatorGrammarDiceRollSettings.grammarProperties.terminals,
				generatorGrammarDiceRollSettings.getMinValueTerminalsAreAddedTo(),
				generatorGrammarDiceRollSettings.getMaxValueTerminalsAreAddedTo()
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
				generatorGrammarDiceRollSettings.getMaxValueCompoundVariablesAreAddedTo()
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
			int maxCountElementDistributedTo) {
		for ( RightHandSideElement tempRhse : rightHandSideElements ) {
			// countOfLeftSideRhseWillBeAdded = [minCountElementDistributedTo, maxCountElementDistributedTo]
			int countOfLeftSideRhseWillBeAdded = random.nextInt( maxCountElementDistributedTo ) + minCountElementDistributedTo;
			// TODO: try biased dice rolling, like here as example
			// Removing Variables from tempVariables until countOfVarsTerminalWillBeAdded vars are left.
			List<Variable> tempVariables = new ArrayList<>( generatorGrammarDiceRollSettings.grammarProperties.variables );
			for ( int i = tempVariables.size(); i > countOfLeftSideRhseWillBeAdded; i-- ) {
				tempVariables.remove( random.nextInt( tempVariables.size() ) );
			}
			for ( Variable var : tempVariables ) {
				grammar.addProduction( new Production( var, tempRhse ) );
			}
		}
		return grammar;
	}
}