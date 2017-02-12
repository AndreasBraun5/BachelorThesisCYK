package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
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
	 */ // TODO delete: not needed any more.
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
	 * // TODO Test: checkRightCellCombinationForced
	 */
	public static RightCellCombinationsForcedWrapper checkRightCellCombinationForced(
			Set<VariableKWrapper>[][] setV, int minCountRightCellCombinationsForced, Grammar grammar) {

		Set<Variable>[][] tempSetV = Util.getVarsFromSetDoubleArray( setV );

		int wordLength = tempSetV[0].length;
		Set<Variable>[][] markedRightCellCombinationForced = Util.getInitialisedHashSetArray( wordLength );

		Map<Variable, List<Production>> prodMap = grammar.getProductionsMap();
		int rightCellCombinationsForced = 0;
		// Keep in mind that the setV matrix is a upper right matrix. But descriptions of how the algorithm works
		// is done, as if the setV pyramid points downwards (reflection on the diagonal).
		// Regarding one cell, its upper left cell and its upper right cell are looked at.
		// setV[i][j] = down
		// setV[i + 1][j] = upper right
		// setV[i][j - 1] = upper left
		for ( int i = 0; i < wordLength; i++ ) { // row
			// here it is restricted that the upper two rows aren't checked. Trivial cases, which would fulfill the
			// restrictions each time.
			for ( int j = wordLength - 1; j > i + 2; j-- ) { // column
				Set<VariableCompound> tempVarCompSet = new HashSet<>();
				// if one., the left or right cell is empty, then rightCellCombinationsForced wont't be incremented.
				if ( tempSetV[i][j - 1].size() != 0 && tempSetV[i + 1][j].size() != 0 ) {
					// make all tuples of left and right --> tempVariablesCompound = tuples of type
					// ({varLeft}, {varRight})
					for ( Variable varLeft : tempSetV[i][j - 1] ) {
						for ( Variable varRight : tempSetV[i + 1][j] ) {
							tempVarCompSet.add( new VariableCompound( varLeft, varRight ) );
						}
					}
					// for each var in down check if no combination is rhse of var in its prodMap
					boolean isRightCellCombinationForced = true;
					for ( Variable varDown : tempSetV[i][j] ) {
						// As long as the opposite isn't found isRightCellCombinationForced is true
						isRightCellCombinationForced = true;
						// Get its map
						List<Production> varDownProdList = prodMap.get( varDown );
						// Check for each tempVarComp if it is element of the rhse of the observed variable <-> prodmap
						for ( VariableCompound tempVarComp : tempVarCompSet ) {
							// regarding an empty cell down
							if ( varDownProdList == null ) {
								isRightCellCombinationForced = false;
								break;
							}
							for ( Production prod : varDownProdList ) {
								if ( prod.getRightHandSideElement().equals( tempVarComp ) ) {
									isRightCellCombinationForced = false;
								}
								if ( !isRightCellCombinationForced ) {
									break;
								}
							}
							if ( !isRightCellCombinationForced ) {
								break;
							}
						}
						if ( isRightCellCombinationForced ) {
							rightCellCombinationsForced++;
							markedRightCellCombinationForced[i][j].add( new Variable( varDown.toString() ) );
						}
					}
				}
			}
		}
		return RightCellCombinationsForcedWrapper.buildRightCellCombinationsForcedWrapper().
				setCountRightCellCombinationForced( rightCellCombinationsForced ).
				setRightCellCombinationForced( rightCellCombinationsForced >= minCountRightCellCombinationsForced ).
				setMarkedRightCellCombinationForced( markedRightCellCombinationForced );
	}

	// TODO Test: checksumOfProductions
	public static boolean checkSumOfProductions(Grammar grammar, int maxSumOfProductions) {
		return grammar.getProductionsAsList().size() <= maxSumOfProductions;
	}

	/**
	 * checkMaxSumOfVarsInPyramid is tested only on the setV simple.
	 */
	// TODO Test: checkMaxSumOfVarsInPyramid
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
