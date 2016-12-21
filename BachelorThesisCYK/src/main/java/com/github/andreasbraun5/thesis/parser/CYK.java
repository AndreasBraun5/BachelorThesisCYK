package com.github.andreasbraun5.thesis.parser;

import com.github.andreasbraun5.thesis.grammar.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class CYK {

    public boolean cykAlgorithmSimple(StringBuilder word, Grammar grammar) {
        int wordlength = word.length();
        List<Production> productions = grammar.getProductions();
        int countOfProductions = productions.size();
        Set<Variable>[][] setV = new HashSet[wordlength][wordlength];
        for (int i = 0; i < wordlength; i++) {
            for (int j = 0; j < wordlength; j++) {
                setV[i][j] = new HashSet<>();
            }
        }
        // Check whether the terminal is on the right side of the production, then add its left variable to v_ii
        for (int i = 0; i < wordlength - 1; i++) { // n=0 therefore n-1, false
            RuleElement tempTerminal = new Terminal(String.valueOf(word.charAt(i)));
            for (int j = 0; j < countOfProductions; j++) {
                Production tempProduction = productions.get(j);
                if (tempProduction.isTerminalAtRightSide(tempTerminal)) {
                    setV[i][i].add(tempProduction.getLeftHandSide());
                }
            }
        }
        // Be careful with boundaries. In the TI script the loop counters begin with 1 instead of 0, like here.
        // Here (l-1) is used as l. The same goes for (i-1) as i. false
        for (int l = 0; l < wordlength; l++) { // l=0 therefore l=0 to n-2, false
            for (int i = 0; i < wordlength - l; i++) { // i=0 therefore i=0 to n-l-1, false
                // setV[i][i+l-1] = EmptySet; This is already done via initialisation (set.size = 0).
                for (int k = i; k < i + l; k++) {
                    // Check for combinations of substrings that concatenate to the word.
                    // This is done through taking the
                    setV[i][i + l].add(new Variable("XX "));
                    //setV[i][i+l];
                }
            }
        }
        CYK.printSetV(setV, wordlength);
        return false;
    }

    public Tree cykAlgorithmAdvanced(StringBuilder word, Grammar grammar) {

        return new Tree();
    }

    public static void printSetV(Set<Variable>[][] setV, int wordlength) {
        System.out.println();
        System.out.println("setV:");
        for (int i = 0; i < wordlength; i++) {
            for (int j = 0; j < wordlength; j++) {
                System.out.print(setV[i][j].toString());
            }
            System.out.println();
        }
    }
}
