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
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;

/**
 * Created by Andreas Braun on 24.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarGeneratorDiceRollBias extends GrammarGeneratorDiceRoll<GrammarGeneratorSettingsDiceRoll> {

	public GrammarGeneratorDiceRollBias(
			GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings, Random random) {
		super( generatorGrammarDiceRollSettings, random );
		this.generatorType = "DICEROLLBIAS";
	}

	public GrammarGeneratorDiceRollBias(GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings) {
		super( generatorGrammarDiceRollSettings );
		this.generatorType = "DICEROLLBIAS";
	}

	@Override
	protected Grammar distributeTerminals(Grammar grammar) {
		return distributeDiceRollRightHandSideElementsBias(
				grammar,
				this.generatorGrammarSettings.grammarProperties.terminals,
				this.generatorGrammarSettings.getMinValueTerminalsAreAddedTo(),
				this.generatorGrammarSettings.getMaxValueTerminalsAreAddedTo(),
				this.generatorGrammarSettings.getFavouritism()
		);
	}

	@Override
	protected Grammar distributeCompoundVariables(Grammar grammar) {
		Set<VariableCompound> varTupel = new HashSet<>();
		for ( Variable var1 : this.generatorGrammarSettings.grammarProperties.variables ) {
			for ( Variable var2 : this.generatorGrammarSettings.grammarProperties.variables ) {
				varTupel.add( new VariableCompound( var1, var2 ) );
			}
		}
		return distributeDiceRollRightHandSideElementsBias(
				grammar,
				varTupel,
				this.generatorGrammarSettings.getMinValueCompoundVariablesAreAddedTo(),
				this.generatorGrammarSettings.getMaxValueCompoundVariablesAreAddedTo(),
				this.generatorGrammarSettings.getFavouritism()
		);
	}

	@Override
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
			//Adding the element to the leftover variables
			for ( Variable var : tempVar ) {
				// The difference to its superclass method is that, the GrammarRunTimeException is being ignored here.
				// This effect doesn't occur in the superclass method.
				try {
					grammar.addProduction( new Production( var, tempRhse ) );
				}
				catch (GrammarRuntimeException ignored) {
				}
			}
		}
		return grammar;
	}

	protected Grammar distributeDiceRollRightHandSideElementsBias(
			Grammar grammar,
			Set<? extends RightHandSideElement> rightHandSideElements,
			int minCountElementDistributedTo,
			int maxCountElementDistributedTo,
			int favouritism[]) {
		List<Variable> tempVariables2 = new ArrayList<>( this.generatorGrammarSettings.grammarProperties.variables );
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

