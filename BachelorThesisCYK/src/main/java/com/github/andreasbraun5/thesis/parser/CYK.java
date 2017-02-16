package com.github.andreasbraun5.thesis.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.util.SetVMatrix;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 */
@SuppressWarnings("Duplicates")
public class CYK {
	/*
	   ###############################################################
       - Epsilon rule is implemented
       - !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SetV is in reality an upper triangular matrix !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
       ###############################################################
     */

	/**
	 * Implementation of the simple Algorithm described in the script TI1. Overloaded method for simpler usage.
	 */
	// Think about deleting this.
	public static boolean algorithmSimple(Grammar grammar, List<Terminal> word) {
		SetVMatrix setVMatrix = calculateSetVAdvanced( grammar, word );
		int wordLength = word.size();
		return Util.varKSetToVarSet( setVMatrix.getSetV()[0][wordLength - 1] )
				.contains( grammar.getVariableStart() );
	}

	public static boolean algorithmSimple(
			Set<Variable>[][] setV,
			Grammar grammar,
			GrammarProperties grammarProperties) {
		return setV[0][grammarProperties.grammarPropertiesGrammarRestrictions.getSizeOfWord() - 1].
				contains( grammar.getVariableStart() );
	}

	public static boolean algorithmAdvanced(Grammar grammar, List<Terminal> word) {
		SetVMatrix setVMatrix = calculateSetVAdvanced( grammar, word );

		int wordLength = word.size();
		return Util.varKSetToVarSet( setVMatrix.getSetV()[0][wordLength - 1] )
				.contains( grammar.getVariableStart() );
	}
	/* // TODO Martin: Both methods have the same erasure.
	public static boolean algorithmAdvanced(
			Set<VariableKWrapper>[][] setV,
			Grammar grammar,
			GrammarProperties grammarProperties) {
		return Util.getVarsFromSet( setV[0][grammarProperties.sizeOfWord - 1] ).contains( grammar.getVariableStart() );
	}*/

	public static boolean algorithmAdvanced(
			Set<Variable>[][] setV,
			Grammar grammar,
			GrammarProperties grammarProperties) {
		return setV[0][grammarProperties.grammarPropertiesGrammarRestrictions.getSizeOfWord() - 1].
				contains( grammar.getVariableStart() );
	}

	/**
	 * Each variable that has the terminal at position i of the word as its rightHandSideElement,
	 * will be added to setV[i][i]
	 */
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SetV is in reality an upper triangular matrix !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private static Set<VariableKWrapper>[][] stepIIAdvanced(
			Set<VariableKWrapper>[][] setV, List<Terminal> word, Grammar grammar) {
		int wordLength = word.size();
		// TODO Martin: How to prevent writing CYK.stepIIAdvanced instead of setV = CYK.stepIIAdvanced?
		// Look at each terminal of the word
		for ( int i = 1; i <= wordLength; i++ ) {
			RightHandSideElement tempTerminal = word.get( i - 1 );
			// Get all productions that have the same leftHandSide variable. This is done for all unique variables.
			// So all production in general are taken into account.
			for ( Map.Entry<Variable, List<Production>> entry : grammar.getProductionsMap().entrySet() ) {
				VariableKWrapper var = new VariableKWrapper( entry.getKey(), i );
				List<Production> prods = entry.getValue();
				// Check if there is one rightHandSideElement that equals the observed terminal.
				for ( Production prod : prods ) {
					if ( prod.isElementAtRightHandSide( tempTerminal ) ) {
						setV[i - 1][i - 1].add( var );
					}
				}
			}
		}
		return setV;
	}

	/**
	 * Calculating the set needed for the cyk algorithm.
	 */
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SetV is in reality an upper triangular matrix !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static SetVMatrix calculateSetVAdvanced(Grammar grammar, List<Terminal> word) {
		int wordLength = word.size();
		Map<Variable, List<Production>> productions = grammar.getProductionsMap();
		Set<VariableKWrapper>[][] setV = Util.getInitialisedHashSetArray( wordLength );
		// Check whether the terminal is on the right side of the production, then add its left variable to v_ii
		setV = stepIIAdvanced( setV, word, grammar );
		// l loop of the described algorithm
		for ( int l = 1; l <= wordLength - 1; l++ ) {
			// i loop of the described algorithm.
			// Needs to be 1 <= i <= n-1-l, because of index starting from 0 for an array.
			for ( int i = 0; i <= wordLength - l - 1; i++ ) {
				// k loop of the described algorithm
				// Needs to be i <= k <= i+l, because of index starting from 0 for i already.
				for ( int k = i; k < i + l; k++ ) {
					// tempSetX contains the newly to be added variables, regarding the "X-->YZ" rule.
					// If the substring X can be concatenated with the substring Y and substring Z, whereas Y and Z
					// must be element of its specified subsets, then add the element X to setV[i][i+l]
					Set<Variable> tempSetX = new HashSet<>();
					Set<Variable> tempSetY = Util.varKSetToVarSet( setV[i][k] );
					Set<Variable> tempSetZ = Util.varKSetToVarSet( setV[k + 1][i + l] );
					Set<VariableCompound> tempSetYZ = new HashSet<>();
					// All possible concatenations of the variables yz are constructed. And so its substrings, which
					// they are able to generate
					for ( Variable y : tempSetY ) {
						for ( Variable z : tempSetZ ) {
							@SuppressWarnings("SuspiciousNameCombination")
							VariableCompound tempVariable = new VariableCompound( y, z );
							tempSetYZ.add( tempVariable );
						}
					}
					// Looking at all productions of the grammar, it is checked if there is one rightHandSideElement that
					// equals any of the concatenated variables tempSetYZ. If yes, the LeftHandSideElement or more
					// specific the variable of the production is added to the tempSetX. All according to the "X-->YZ" rule.
					for ( List<Production> tempProductions : productions.values() ) {
						for ( Production tempProduction : tempProductions ) {
							for ( VariableCompound yz : tempSetYZ ) {
								if ( tempProduction.isElementAtRightHandSide( yz ) ) {
									tempSetX.add( tempProduction.getLeftHandSideElement() );
								}
							}
						}
					}
					for ( Variable var : tempSetX ) {
						// ( k + 1) because of index range of k  because of i.
						setV[i][i + l].add( new VariableKWrapper( var, ( k + 1 ) ) );
					}
				}
			}
		}
		return SetVMatrix.buildEmptySetVMatrixWrapper( setV.length ).setSetV( setV );
	}
}
