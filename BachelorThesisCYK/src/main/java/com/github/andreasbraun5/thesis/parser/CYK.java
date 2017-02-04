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
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 */
public class CYK {
	/*
	   ###############################################################
       - Epsilon rule is implemented
       ###############################################################
     */

	/**
	 * Implementation of the simple Algorithm described in the script TI1. Overloaded method for simpler usage.
	 */
	// TODO: the simple algorithm is a special case of the advanced algorithm. Use a util method to "convert" the
	// Set<VariableKWrapper>[][] to a Set<Variable>[][] when needed.
	public static boolean algorithmSimple(Grammar grammar, List<Terminal> word) {
		Set<Variable>[][] setV = calculateSetV( grammar, word );
		int wordLength = word.size();
		return setV[0][wordLength - 1].contains( grammar.getVariableStart() );
	}

	public static boolean algorithmSimple(
			Set<Variable>[][] setV,
			Grammar grammar,
			GrammarProperties grammarProperties) {
		return setV[0][grammarProperties.sizeOfWord - 1].contains( grammar.getVariableStart() );
	}

	/**
	 * Each variable that has the terminal at position i of the word as its rightHandSideElement,
	 * will be added to setV[i][i]
	 */
	public static void stepII(Set<Variable>[][] setV, List<Terminal> word, Grammar grammar) {
		int wordLength = word.size();
		// Look at each terminal of the word
		for ( int i = 0; i < wordLength; i++ ) {
			RightHandSideElement tempTerminal = word.get( i );
			// Get all productions that have the same leftHandSide variable. This is done for all unique variables.
			// So all production in general are taken into account.
			for ( Map.Entry<Variable, List<Production>> entry : grammar.getProductionsMap().entrySet() ) {
				Variable var = entry.getKey();
				List<Production> prods = entry.getValue();
				// Check if there is one rightHandSideElement that equals the observed terminal.
				for ( Production prod : prods ) {
					if ( prod.isElementAtRightHandSide( tempTerminal ) ) {
						setV[i][i].add( var );
					}
				}
			}
		}
	}

	/**
	 * Calculating the set needed for the cyk algorithm.
	 */
	public static Set<Variable>[][] calculateSetV(Grammar grammar, List<Terminal> word) {
		int wordLength = word.size();
		Map<Variable, List<Production>> productions = grammar.getProductionsMap();
		@SuppressWarnings("unchecked")
		Set<Variable>[][] setV = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setV[i][j] = new HashSet<>(); // this generates a set with size = 0
			}
		}
		// Check whether the terminal is on the right side of the production, then add its left variable to v_ii
		stepII( setV, word, grammar );
		// l loop of the described algorithm
		for ( int l = 0; l <= wordLength - 1; l++ ) {
			// i loop of the described algorithm
			for ( int i = 0; i < wordLength - l; i++ ) {
				// k loop of the described algorithm
				for ( int k = i; k < i + l; k++ ) {
					// tempSetX contains the newly to be added variables, regarding the "X-->YZ" rule.
					// If the substring X can be concatenated with the substring Y and substring Z, whereas Y and Z
					// must be element of its specified subsets, then add the element X to setV[i][i+l]
					Set<Variable> tempSetX = new HashSet<>();
					Set<Variable> tempSetY = setV[i][k];
					Set<Variable> tempSetZ = setV[k + 1][i + l];
					Set<VariableCompound> tempSetYZ = new HashSet<>();
					// All possible concatenations of the variables yz are constructed. And so its substrings, which
					// they are able to generate
					for ( Variable y : tempSetY ) {
						for ( Variable z : tempSetZ ) {
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
					setV[i][i + l].addAll( tempSetX );
				}
			}
		}
		return setV;
	}

	/**
	 * not yet implemented algorithm
	 */
	public static Tree algorithmAdvanced(StringBuilder word, Grammar grammar) {
		return new Tree();
	}


}
