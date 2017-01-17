package com.github.andreasbraun5.thesis.grammar;

import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public class GrammarIntegrityChecker {

	/**
	 * 	Is intended to check the the constructed grammars to all restrictions that are wanted.
	 *
	 */
	public static boolean checkIntegrity(Grammar grammar, String word) {
		CYK.algorithmSimple( grammar, word );
		return CYK.algorithmSimple( grammar, word );
	}

	public static boolean checkGrammarRestrictions(GrammarProperties grammarProperties){

		return true;
	}

	public static boolean checkProducability(Grammar grammar, String word){
		return CYK.algorithmSimple( grammar, word );
	}
}
