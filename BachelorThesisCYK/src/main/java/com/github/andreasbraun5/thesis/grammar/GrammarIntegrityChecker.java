package com.github.andreasbraun5.thesis.grammar;

import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public class GrammarIntegrityChecker {

	/**
	 * 	Is intended to check the the constructed grammars to all restrictions that are wanted.
	 * 	GrammarProperties need to be checked too.
	 * 	// TODO not implemented fully yet, another parameter would be grammarProperties
	 */
	public static boolean checkIntegrity(Grammar grammar, String word) {
		boolean temp = CYK.algorithmSimple( grammar, word );
		return CYK.algorithmSimple( grammar, word );
	}
}
