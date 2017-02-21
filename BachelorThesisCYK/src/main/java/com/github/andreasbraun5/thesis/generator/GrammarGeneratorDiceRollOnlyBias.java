package com.github.andreasbraun5.thesis.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarWrapper;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;

/**
 * Created by Andreas Braun on 24.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarGeneratorDiceRollOnlyBias extends GrammarGeneratorDiceRoll<GrammarGeneratorSettingsDiceRoll> {

	public GrammarGeneratorDiceRollOnlyBias(
			GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings, Random random) {
		super( generatorGrammarDiceRollSettings, random );
		this.generatorType = "DICEROLLONLYBIAS";
	}

	public GrammarGeneratorDiceRollOnlyBias(GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings) {
		super( generatorGrammarDiceRollSettings );
		this.generatorType = "DICEROLLONLYBIAS";
	}

	@Override
	protected GrammarWrapper distributeTerminals(GrammarWrapper grammarWrapper) {
		return distributeDiceRollRightHandSideElementsBias(
				grammarWrapper,
				this.grammarGeneratorSettings.grammarProperties.terminals,
				this.grammarGeneratorSettings.getMinValueTerminalsAreAddedTo(),
				this.grammarGeneratorSettings.getMaxValueTerminalsAreAddedTo(),
				this.grammarGeneratorSettings.getFavouritism()
		);
	}

	@Override
	protected GrammarWrapper distributeCompoundVariables(GrammarWrapper grammarWrapper) {
		Set<VariableCompound> varTupel = new HashSet<>();
		for ( Variable var1 : this.grammarGeneratorSettings.grammarProperties.variables ) {
			for ( Variable var2 : this.grammarGeneratorSettings.grammarProperties.variables ) {
				varTupel.add( new VariableCompound( var1, var2 ) );
			}
		}
		return distributeDiceRollRightHandSideElementsBias(
				grammarWrapper,
				varTupel,
				this.grammarGeneratorSettings.getMinValueCompoundVariablesAreAddedTo(),
				this.grammarGeneratorSettings.getMaxValueCompoundVariablesAreAddedTo(),
				this.grammarGeneratorSettings.getFavouritism()
		);
	}

	private GrammarWrapper distributeDiceRollRightHandSideElementsBias(
			GrammarWrapper grammarWrapper,
			Set<? extends RightHandSideElement> rightHandSideElements,
			int minCountElementDistributedTo,
			int maxCountElementDistributedTo,
			int favouritism[]) {
		List<Variable> tempVariables2 = new ArrayList<>( this.grammarGeneratorSettings.grammarProperties.variables );
		Map<Variable, Integer> favouritismToVariable = new HashMap<>();
		{
			// Mapping the favouritism randomly to the variables. Pick one random variable and add the first favouritism to it.
			int indexVar;
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
				grammarWrapper,
				rightHandSideElements,
				minCountElementDistributedTo,
				maxCountElementDistributedTo,
				tempVariables2
		);
	}

	@Override
	protected GrammarWrapper distributeDiceRollRightHandSideElements(
			GrammarWrapper grammarWrapper,
			Set<? extends RightHandSideElement> rightHandSideElements,
			int minCountElementDistributedTo,
			int maxCountElementDistributedTo,
			List<Variable> variablesWeighted) {
		Grammar grammar = grammarWrapper.getGrammar();
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
				// The difference to its superclass method is that, the GrammarRunTimeException is being ignored here.
				// This effect doesn't occur in the superclass method.
				// Because of dice rolling and a large amount of generated grammars, the actually to be added
				// variable will just be ignored.
				try {
					grammar.addProduction( new Production( var, tempRhse ) );
				} // TODO Martin: this ok?
				catch (GrammarRuntimeException ignored) {
				}
			}
		}
		grammarWrapper.setGrammar( grammar );
		return grammarWrapper;
	}
}

