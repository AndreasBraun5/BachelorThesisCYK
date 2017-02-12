package com.github.andreasbraun5.thesis.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarWrapper;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 11.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarGeneratorDiceRollTopDownMartens extends GrammarGeneratorDiceRoll<GrammarGeneratorSettingsDiceRoll> {

	public GrammarGeneratorDiceRollTopDownMartens(
			GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings,
			Random random) {
		super( generatorGrammarDiceRollSettings, random );
		this.generatorType = "DICEROLLTOPDOWNMARTENS";
	}

	public GrammarGeneratorDiceRollTopDownMartens(GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings) {
		super( generatorGrammarDiceRollSettings );
		this.generatorType = "DICEROLLTOPDOWNMARTENS";
	}

	@Override
	protected GrammarWrapper distributeTerminals(GrammarWrapper grammarWrapper) {
		return distributeDiceRollRightHandSideElements(
				grammarWrapper,
				generatorGrammarSettings.grammarProperties.terminals,
				generatorGrammarSettings.getMinValueTerminalsAreAddedTo(),
				generatorGrammarSettings.getMaxValueTerminalsAreAddedTo(),
				new ArrayList<>( generatorGrammarSettings.grammarProperties.variables )
		);
	}

	@Override
	protected GrammarWrapper distributeCompoundVariables(GrammarWrapper grammarWrapper) {
		Grammar grammar = grammarWrapper.getGrammar();
		GrammarProperties grammarProperties = generatorGrammarSettings.getGrammarProperties();
		List<Terminal> word = grammarWrapper.getWord();
		int wordSize = word.size();
		@SuppressWarnings("unchecked")
		Set<VariableKWrapper>[][] setVAdvanced = new Set[wordSize][wordSize];
		for ( int i = 0; i < wordSize; i++ ) {
			for ( int j = 0; j < wordSize; j++ ) {
				setVAdvanced[i][j] = new HashSet<>(); // this generates a set with size = 0
			}
		}
		// The stepII now needs to be done first. Usage of CYK.calculateSetVAdvanced equivalent to CYK.stepIIAdvanced.
		// But like this stepIIAdvanced can stay private.
		setVAdvanced = CYK.calculateSetVAdvanced( grammar, word );
		// Keep in mind that the setV matrix is a upper right matrix. But the description of how the algorithm works
		// is done, as if the setV pyramid points downwards (reflection on the diagonal)
		// Regarding one cell, its upper left cell and its upper right cell are looked at.
		// setV[i][j] = down
		// setV[i + 1][j] = upper right
		// setV[i][j - 1] = upper left
		// Visited indexes are as following: 01->12->23->34; 02->13->24; 03->14; 04; with wordSize = 5;
		Set<VariableCompound> tempVarCompSet = new HashSet<>();
		for ( int k = 0;
				k < wordSize &&
						setVAdvanced[0][grammarProperties.grammarPropertiesGrammarRestrictions.getSizeOfWord() - 1]
								.isEmpty();
				k++ ) { // row
			int i = k;
			for ( int j = i + 1;
					j < wordSize &&
							setVAdvanced[0][grammarProperties.grammarPropertiesGrammarRestrictions.getSizeOfWord() - 1]
									.isEmpty();
					j++ ) { // column
				tempVarCompSet.clear();
				// if one, the left or right cell is empty, then break.
				if ( setVAdvanced[i][j - 1].size() != 0 && setVAdvanced[i + 1][j].size() != 0 ) {
					// make all tuples of left and right --> tempVariablesCompound = tuples of type
					// ({varLeft}, {varRight}) --> all possible compoundVariables
					for ( VariableKWrapper varLeft : setVAdvanced[i][j - 1] ) {
						for ( VariableKWrapper varRight : setVAdvanced[i + 1][j] ) {
							tempVarCompSet.add( new VariableCompound(
									varLeft.getVariable(),
									varRight.getVariable()
							) );
						}
					}
					// TODO Martin: how to prevent forgetting to write grammarWrapper =
					for ( VariableCompound varcomp : tempVarCompSet ) {
						Set<VariableCompound> varCompSet = new HashSet<>();
						varCompSet.add( varcomp );
						// Because of dice rolling and a large amount of generated grammars, the actually to be added
						// variable will just be ignored.
						try {
							grammarWrapper = distributeDiceRollRightHandSideElements(
									grammarWrapper,
									varCompSet,
									generatorGrammarSettings.getMinValueCompoundVariablesAreAddedTo(),
									generatorGrammarSettings.getMaxValueCompoundVariablesAreAddedTo(),
									new ArrayList<>( generatorGrammarSettings.grammarProperties.variables )
							);
						}
						catch (GrammarRuntimeException ignored) {
						}
						// Each time something is changed recalculate the setVAdvanced. The entire set is recalculated.
						setVAdvanced = CYK.calculateSetVAdvanced( grammarWrapper.getGrammar(), word );
						/*
						Set<Variable>[][] setVSimple = Util.getVarsFromSetDoubleArray( setVAdvanced );
						System.out.print( grammarWrapper.getGrammar() );
						System.out.println( Util.getSetVVariableKAsStringForPrintingAsLowerTriangularMatrix(
								setVAdvanced,
								"setVKBefore"
						) );
						System.out.println( Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
								setVSimple,
								"setVKBefore"
						) );
						int öalskfdj = 0;
						*/
					}
				}
				i++;
			}
		}
		return grammarWrapper.setGrammar( grammar );
	}
}
