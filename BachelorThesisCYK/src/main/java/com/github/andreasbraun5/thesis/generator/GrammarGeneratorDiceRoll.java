package com.github.andreasbraun5.thesis.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammar.GrammarWordMatrixWrapper;

/**
 * Created by Andreas Braun on 24.01.2017.
 * https://github.com/AndreasBraun5/
 */
public abstract class GrammarGeneratorDiceRoll<T extends GrammarGeneratorSettingsDiceRoll> extends GrammarGenerator<T> {

	protected final Random random;

	// Needed for testing purposes, fixed seed possible.
	public GrammarGeneratorDiceRoll(
			T generatorGrammarDiceRollSettings,
			Random random) {
		super( generatorGrammarDiceRollSettings );
		this.random = random;
		this.grammarGeneratorSettings = generatorGrammarDiceRollSettings;
	}

	public GrammarGeneratorDiceRoll(T generatorGrammarDiceRollSettings) {
		this( generatorGrammarDiceRollSettings, new Random() );
	}

	@Override
	protected abstract GrammarWordMatrixWrapper distributeTerminals(GrammarWordMatrixWrapper grammarWordMatrixWrapper);

	@Override
	protected abstract GrammarWordMatrixWrapper distributeCompoundVariables(GrammarWordMatrixWrapper grammarWordMatrixWrapper);

	/**
	 * @param minCountElementDistributedTo: If you want to distribute the terminals to at least one rightHandSide then
	 * this value is 1.
	 * Dice roll for every rhse how often it is added and to which vars in the grammar it is added.
	 */
	protected GrammarWordMatrixWrapper distributeDiceRollRightHandSideElements(
			GrammarWordMatrixWrapper grammarWordMatrixWrapper,
			Set<? extends RightHandSideElement> rightHandSideElements,
			int minCountElementDistributedTo,
			int maxCountElementDistributedTo,
			List<Variable> variablesWeighted) {
		Grammar grammar = grammarWordMatrixWrapper.getGrammar();
		for ( RightHandSideElement tempRhse : rightHandSideElements ) {
			// countOfLeftSideRhseWillBeAdded is element of the interval [minCountElementDistributedTo, maxCountElementDistributedTo]
			int countOfLeftSideRhseWillBeAdded = random.nextInt( maxCountElementDistributedTo ) + minCountElementDistributedTo;
			//Removing Variables from variablesWeighted until countOfVarsTerminalWillBeAdded vars are left.
			List<Variable> tempVar = new ArrayList<>( variablesWeighted );
			for ( int i = tempVar.size(); i > countOfLeftSideRhseWillBeAdded; i-- ) {
				tempVar.remove( random.nextInt( tempVar.size() ) );
			}
			//Adding the element to the leftover variables
			for ( Variable var : tempVar ) {
				grammar.addProduction( new Production( var, tempRhse ) );
			}
		}
		grammarWordMatrixWrapper.setGrammar( grammar );
		return grammarWordMatrixWrapper;
	}


}
