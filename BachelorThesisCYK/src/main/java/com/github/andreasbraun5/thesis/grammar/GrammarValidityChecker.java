package com.github.andreasbraun5.thesis.grammar;

import java.util.HashSet;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public class GrammarValidityChecker {

	public static boolean checkGrammarRestrictions(GrammarProperties grammarProperties, Set<Variable>[][] setV) {
		if (grammarProperties.maxNumberOfVarsPerCell == 0) {
			throw new GrammarPropertiesRuntimeException( "maxNumberOfVarsPerCell is zero." );
		}
		int numberOfVarsPerCell = 0;
		int wordLength = grammarProperties.sizeOfWord;
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				if(setV[i][j].size() > numberOfVarsPerCell){
					numberOfVarsPerCell = setV[i][j].size();
				}
			}
		}
		return grammarProperties.maxNumberOfVarsPerCell >= numberOfVarsPerCell;
	}

	public static boolean checkProducibilityCYK(
			Set<Variable>[][] setV,
			Grammar grammar,
			GrammarProperties grammarProperties) {
		return CYK.algorithmSimple( setV, grammar, grammarProperties );
	}
}
