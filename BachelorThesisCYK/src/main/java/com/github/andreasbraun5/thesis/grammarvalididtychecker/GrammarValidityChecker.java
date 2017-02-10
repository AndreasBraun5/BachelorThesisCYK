package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 05.01.2017.
 * https://github.com/AndreasBraun5/
 * <p>
 * All validity tests of GrammarValidityChecker are based onto the simple setV = setV<Variable>.
 */
public class GrammarValidityChecker {

	/**
	 * general restrictions fulfilled, regarding the grammar. Not exam relevant.
	 */
	public static boolean checkGrammarRestrictions(
			GrammarProperties grammarProperties,
			Set<VariableKWrapper>[][] setV) {
		return checkMaxNumberOfVarsPerCell(
				setV,
				grammarProperties.grammarPropertiesGrammarRestrictions.getMaxNumberOfVarsPerCell()
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
	public static boolean checkMaxNumberOfVarsPerCell(Set<VariableKWrapper>[][] setV, int maxNumberOfVarsPerCell) {
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
	/*
	public static GrammarPropertiesExamConstraints checkExamConstraints(
			Set<VariableKWrapper>[][] setV,
			Grammar grammar,
			GrammarProperties grammarProperties
	) {
		return new GrammarPropertiesExamConstraints(
				checkRightCellCombinationForced( setV, grammarProperties.
						grammarPropertiesExamConstraints.getMinRightCellCombinationsForced() ),
				checkSumOfProductions( grammar, grammarProperties.
						grammarPropertiesExamConstraints.getMaxSumOfProductions() ),
				checkMaxSumOfVarsInPyramid( setV, grammarProperties.
						grammarPropertiesExamConstraints.getMaxSumOfVarsInPyramid() )
		);
	}*/

	/**
	 * True if more than one "rightCellCombination" is forced. Exam relevant restriction.
	 * The upper two rows of the pyramid aren't checked.
	 * Starting from from the upper right index of the matrix setV[0][wL-1] towards the diagonal.
	 */ // TODO: write Tests for!!
	public static boolean checkRightCellCombinationForced(
			Set<VariableKWrapper>[][] setV, int minCountRightCellCombinationsForced) {
		Set<Variable>[][] tempSetV = Util.getVarsFromSetDoubleArray( setV );
		int rightCellCombinationsForced = 0;
		int wordLength = tempSetV[0].length;
		for ( int i = 0; i < wordLength; i++ ) { // row
			// here it is restricted that the upper two rows aren't checked.
			for ( int j = wordLength - 1; j > i + 2; j-- ) { // column
				//Util.printSetVAsUpperTriangularMatrix( setV, "setV" );
				for ( Variable var : tempSetV[i][j] ) {
					if ( !tempSetV[i + 1][j].contains( var ) || !tempSetV[i][j - 1].contains( var ) ) {
						rightCellCombinationsForced++;
					}
				}
			}
		}
		return rightCellCombinationsForced >= minCountRightCellCombinationsForced;
	}

	// TODO: Test checksumOfProductions
	public static boolean checkSumOfProductions(Grammar grammar, int maxSumOfProductions) {
		return grammar.getProductionsAsList().size() <= maxSumOfProductions;
	}

	/**
	 * checkMaxSumOfVarsInPyramid is tested only on the setV simple.
	 */ // TODO: Test checkMaxSumOfVarsInPyramid
	public static boolean checkMaxSumOfVarsInPyramid(Set<VariableKWrapper>[][] setV, int maxSumOfVarsInPyramid) {
		Set<Variable>[][] tempSetV = Util.getVarsFromSetDoubleArray( setV );
		// put all vars of the matrix into one list and use its length.
		List<Variable> tempVars = new ArrayList<>();
		for ( int i = 0; i < tempSetV.length; i++ ) {
			for ( int j = 0; j < tempSetV.length; j++ ) {
				tempVars.addAll( tempSetV[i][j] );
			}
		}
		return tempVars.size() <= maxSumOfVarsInPyramid;
	}
}
