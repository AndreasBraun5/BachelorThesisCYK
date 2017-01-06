package com.github.andreasbraun5.thesis.parser;

import com.github.andreasbraun5.thesis.grammar.*;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
/*
    TODO Later: Implement interface parser
 */
public class CYK {
    /*
       ###############################################################
       - Epsilon rule is implemented
       ###############################################################
     */
    /**
     *  Implementation of the simple Algorithm described in the script TI1. Overloaded method for simple usage.
     */
    public static boolean algorithmSimple(Grammar grammar, String word) {
        List<Terminal> list = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            list.add(new Terminal(String.valueOf(word.charAt(i))));
        }
        return algorithmSimple(grammar, list);
    }
    public static boolean algorithmSimple(Grammar grammar, List<Terminal> word){
        Set<Variable>[][] setV = calculateSetV(grammar, word);
        int wordLength = word.size();
        return setV[0][wordLength-1].contains(grammar.getVariableStart());
    }

    /**
     *  Each variable that has the terminal at position i of the word as its rightHandSideElement,
     *  will be added to setV[i][i]
     */
    public static void stepII(Set<Variable>[][] setV, List<Terminal> word, Grammar grammar) {
        int wordLength = word.size();
        // Look at each terminal of the word
        for (int i = 0; i < wordLength; i++) {
            RightHandSideElement tempTerminal = word.get(i);
            // Get all productions that have the same leftHandSide variable. This is done for all unique variables.
            // So all production in general are taken into account.
            for (Map.Entry<Variable, List<Production>> entry : grammar.getProductionsMap().entrySet()) {
                Variable var = entry.getKey();
                List<Production> prods = entry.getValue();
                // Check if there is one rightHandSideElement that equals the observed terminal.
                for (Production prod : prods) {
                    if (prod.isElementAtRightHandSide(tempTerminal)) {
                        setV[i][i].add(var);
                    }
                }
            }
        }
    }

    /**
     *  Calculating the set needed for the cyk algorithm. Overloaded method for simple usage.
     */
    public static Set<Variable>[][] calculateSetV(Grammar grammar, String word) {
        List<Terminal> list = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            list.add(new Terminal(String.valueOf(word.charAt(i))));
        }
        return calculateSetV(grammar, list);
    }

    public static Set<Variable>[][] calculateSetV(Grammar grammar, List<Terminal> word) {
        int wordLength = word.size();
        Map<Variable, List<Production>> productions = grammar.getProductionsMap();
        // TODO Note: Why problem here? Generics stuff?
        Set<Variable>[][] setV = new Set[wordLength][wordLength];
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                setV[i][j] = new HashSet<>(); // this generates a set with size = 0
            }
        }
        // Check whether the terminal is on the right side of the production, then add its left variable to v_ii
        stepII(setV, word, grammar);
        // l loop of the described algorithm
        for (int l = 0; l <= wordLength - 1; l++) {
            // i loop of the described algorithm
            for (int i = 0; i < wordLength - l; i++) {
                // k loop of the described algorithm
                for (int k = i; k < i + l; k++) {
                    // tempSetX contains the newly to be added variables, regarding the "X-->YZ" rule.
                    // If the substring X can be concatenated with the substring Y and substring Z, whereas Y and Z
                    // must be element of its specified subsets, then add the element X to setV[i][i+l]
                    Set<Variable> tempSetX = new HashSet<>();
                    Set<Variable> tempSetY = setV[i][k];
                    Set<Variable> tempSetZ = setV[k+1][i+l];
                    Set<Variable> tempSetYZ = new HashSet<>();
                    // All possible concatenations of the variables yz are constructed. And so its substrings, which
                    // they are able to generate
                    // TODO Discuss: Compound variables allowed?
                    for(Variable y : tempSetY) {
                        for(Variable z : tempSetZ) {
                            Variable tempVariable = new Variable(y.getName(), z.getName());
                            tempSetYZ.add(tempVariable);
                        }
                    }
                    //System.out.println();
                    //System.out.println();
                    //System.out.println(productions);
                    //System.out.println("tempSetYZ" + tempSetYZ);
                    // Looking at all productions of the grammar, it is checked if there is one rightHandSideElement that
                    // equals any of the concatenated variables tempSetYZ. If yes, the LeftHandSideElement or more
                    // specific the variable of the production is added to the tempSetX. All according to the "X-->YZ" rule.
                    for (List<Production> tempProductions : productions.values()) {
                        for(Production tempProduction : tempProductions) {
                            for (Variable yz : tempSetYZ) {
                                //System.out.println("tempSetYZ" + tempSetYZ);
                                //System.out.println("tempXY: " + yz);
                                //System.out.println("tempProduction: " + tempProduction);
                                if (tempProduction.isElementAtRightHandSide(yz)) {
                                    tempSetX.add(tempProduction.getLeftHandSideElement());
                                }
                                //System.out.println("tempSetX step: " + tempSetX);
                            }
                            //System.out.println("tempSetX final: " + tempSetX);
                        }
                    }
                    setV[i][i+l].addAll(tempSetX);
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

    /**
     * Method for printing the set matrix
     */
    public static void printSetV(Set<Variable>[][] setV, String setName) {
        System.out.println();
        System.out.println(setName);
        int wordlength = setV.length;
        int maxLen = 0;
        for (int i = 0; i < wordlength; i++) {
            for (int j = 0; j < wordlength; j++) {
                maxLen = Math.max(maxLen, setV[j][i].toString().length());
            }
        }
        for (int i = 0; i < wordlength; i++) {
            for (int j = 0; j < wordlength; j++) {
                System.out.print(uniformStringMaker(setV[j][i].toString(), maxLen));
            }
            System.out.println();
        }
    }

    /**
     * helper method used by printSetV
     */
    public static String uniformStringMaker(String str, int length) {
        StringBuilder builder = new StringBuilder(str);
        for(int i = str.length(); i < length; ++i) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
