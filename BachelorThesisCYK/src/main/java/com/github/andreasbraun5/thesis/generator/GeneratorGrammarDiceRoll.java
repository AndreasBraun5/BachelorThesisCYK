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
public abstract class GeneratorGrammarDiceRoll<T extends GeneratorGrammarDiceRollSettings> extends GeneratorGrammar<T> {

	protected final Random random;

	// Needed for testing purposes, fixed seed possible.
	public GeneratorGrammarDiceRoll(
			T generatorGrammarDiceRollSettings,
			Random random, GeneratorType generatorType) {
		super( generatorGrammarDiceRollSettings, generatorType );
		this.random = random;
		this.generatorGrammarSettings = generatorGrammarDiceRollSettings;
	}

	public GeneratorGrammarDiceRoll(T generatorGrammarDiceRollSettings, GeneratorType generatorType) {
		this( generatorGrammarDiceRollSettings, new Random(), generatorType );
	}

	@Override
	public Grammar generateGrammar() {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( generatorGrammarSettings.grammarProperties.variableStart );
		grammar = distributeTerminals( grammar );
		grammar = distributeCompoundVariables( grammar );
		return grammar;
	}

	@Override
	protected abstract Grammar distributeTerminals(Grammar grammar);

	@Override
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
