package com.github.andreasbraun5.thesis.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;

/**
 * Created by Andreas Braun on 24.01.2017.
 * https://github.com/AndreasBraun5/
 */
public abstract class GeneratorGrammarDiceRoll {

	protected GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings;
	protected final Random random;

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

	public Grammar generateGrammar() {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( generatorGrammarDiceRollSettings.grammarProperties.variableStart );
		grammar = distributeTerminals( grammar );
		grammar = distributeCompoundVariables( grammar );
		return grammar;
	}

	protected abstract Grammar distributeTerminals(Grammar grammar);

	protected abstract Grammar distributeCompoundVariables(Grammar grammar);

	/**
	 * @param minCountElementDistributedTo: If you want to distribute the terminals to at least one rightHandSide then
	 * this value is 1.
	 */
	protected Grammar distributeDiceRollRightHandSideElements(
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


}
