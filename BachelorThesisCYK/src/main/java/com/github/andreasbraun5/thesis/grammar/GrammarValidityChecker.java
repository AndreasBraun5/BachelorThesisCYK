package com.github.andreasbraun5.thesis.grammar;

import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public class GrammarValidityChecker {

	// TODO: up till now only maxVarPerCell is checked
	public static boolean checkGrammarRestrictions(GrammarProperties grammarProperties, Set<Variable>[][] setV) {
		return checkMaxNumberOfVarsPerCell(
				grammarProperties.maxNumberOfVarsPerCell,
				setV
		); //&& checkRightCellCombinationForced( setV );
	}

	public static boolean checkProducibilityCYK(
			Set<Variable>[][] setV,
			Grammar grammar,
			GrammarProperties grammarProperties) {
		return CYK.algorithmSimple( setV, grammar, grammarProperties );
	}
	// TODO: duplicate maxVarPerCell
	private static boolean checkMaxNumberOfVarsPerCell(int maxNumberOfVarsPerCell, Set<Variable>[][] setV) {
		if ( maxNumberOfVarsPerCell == 0 ) {
			throw new GrammarPropertiesRuntimeException( "maxNumberOfVarsPerCell is zero." );
		}
		int numberOfVarsPerCell = 0;
		int wordLength = setV[0].length;
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				if ( setV[i][j].size() > numberOfVarsPerCell ) {
					numberOfVarsPerCell = setV[i][j].size();
				}
			}
		}
		return maxNumberOfVarsPerCell >= numberOfVarsPerCell;
	}

	private static boolean checkRightCellCombinationForced(Set<Variable>[][] setV) {

		return false;
	}
}
