package com.github.andreasbraun5.thesis.grammar;

import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public class GrammarIntegrityChecker {

    public static boolean checkIntegrity(Grammar grammar, String word){
        boolean temp = CYK.algorithmSimple(grammar, word);
        return CYK.algorithmSimple(grammar, word);
    }
}
