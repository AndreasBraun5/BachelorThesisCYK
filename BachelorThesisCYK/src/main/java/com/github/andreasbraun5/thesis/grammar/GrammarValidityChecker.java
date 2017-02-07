package com.github.andreasbraun5.thesis.grammar;

import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public class GrammarValidityChecker {

	// TODO: up till now only maxVarPerCell is checked. Other structural not exam relevant restrictions will be checked here.
	public static boolean checkGrammarRestrictions(GrammarProperties grammarProperties, Set<VariableKWrapper>[][] setV) {
		return checkMaxNumberOfVarsPerCell(
				grammarProperties.maxNumberOfVarsPerCell,
				setV
		);
	}

	/**
	 * True if the starting symbol is contained at the bottom of the pyramid.
	 */
	public static boolean checkProducibilityCYK(
			Set<VariableKWrapper>[][] setV,
			Grammar grammar,
			GrammarProperties grammarProperties) {
		Set<Variable>[][] tempSetV = Util.getVarsFromSetDoubleArray( setV );
		return CYK.algorithmAdvanced( tempSetV, grammar, grammarProperties );
	}

	/**
	 * True if numberOfVarsPerCell is smaller than maxNumberOfVarsPerCell.
	 */
	private static boolean checkMaxNumberOfVarsPerCell(int maxNumberOfVarsPerCell, Set<VariableKWrapper>[][] setV) {
		Set<Variable>[][] tempSetV = Util.getVarsFromSetDoubleArray( setV );
		if ( maxNumberOfVarsPerCell == 0 ) {
			throw new GrammarPropertiesRuntimeException( "maxNumberOfVarsPerCell is zero." );
		}
		int numberOfVarsPerCell = 0;
		int wordLength = tempSetV[0].length;
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				if ( tempSetV[i][j].size() > numberOfVarsPerCell ) {
					numberOfVarsPerCell = tempSetV[i][j].size();
				}
			}
		}
		return numberOfVarsPerCell <= maxNumberOfVarsPerCell;
	}

	/**
	 * True if more than one "rightCellCombination" is forced. Exam relevant restriction.
	 * The upper two rows of the pyramid aren't checked.
	 * Starting from from the upper right index of the matrix setV[0][wL-1] towards the diagonal.
	 */ // TODO: write Tests for!!
	public static boolean checkRightCellCombinationForced(Set<VariableKWrapper>[][] setV) {
		Set<Variable>[][] tempSetV = Util.getVarsFromSetDoubleArray( setV );
		int rightCellCombinationsForced = 0;
		int wordLength = tempSetV[0].length;
		for ( int i = 0; i < wordLength; i++ ) { // row
			// here it is restricted that the upper two rows aren't checked.
			for ( int j = wordLength - 1; j > i + 2; j-- ) { // column
				//Util.printSetVAsUpperTriangularMatrix( setV, "setV" );
				for ( Variable var : tempSetV[i][j] ) {
					/*
					Util.printSetVAsUpperTriangularMatrix( setV, "setV" );
					System.out.println( "setV[i][j]: " + setV[i][j] );
					System.out.println( "var: " + var );
					System.out.println( "   i=" + i + "   j=" + j );
					System.out.println( "setV[i+1][j]: " + setV[i + 1][j] );
					System.out.println( "setV[i][j-1]: " + setV[i][j - 1] );
					boolean temp = !setV[i + 1][j].contains( var ) || !setV[i][j - 1].contains( var );
					System.out.println( temp );
					*/
					// setV[i][j] = top
					// setV[i + 1][j] = right
					// setV[i][j - 1] = left
					// if both
					if ( !tempSetV[i + 1][j].contains( var ) || !tempSetV[i][j - 1].contains( var ) ) {
						rightCellCombinationsForced++;
					}
				}
			}
		}
		return rightCellCombinationsForced >= 1;
	}
}
