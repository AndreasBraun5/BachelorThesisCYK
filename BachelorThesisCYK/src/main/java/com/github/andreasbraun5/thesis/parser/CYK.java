package com.github.andreasbraun5.thesis.parser;

import com.github.andreasbraun5.thesis.grammar.*;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class CYK {

    public static boolean cykAlgorithmSimple(String word, Grammar grammar) {
        List<Terminal> list = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            list.add(new Terminal(String.valueOf(word.charAt(i))));
        }
        return cykAlgorithmSimple(list, grammar);
    }

    public static void stepII(Set<Variable>[][] setV, List<Terminal> word, int wordlength,
                              Grammar grammar) {
        for (int i = 0; i < wordlength; i++) {
            RuleElement tempTerminal = word.get(i);
            for (Map.Entry<Variable, List<Production>> entry : grammar.productions.entrySet()) {
                Variable var = entry.getKey();
                List<Production> prods = entry.getValue();
                for(Production prod : prods) {
                    if(prod.isRuleElementAtRightSide(tempTerminal)) {
                        setV[i][i].add(var);
                    }
                }
            }
        }
    }

    //TODO: epsilon rule not implemented?!?
    public static boolean cykAlgorithmSimple(List<Terminal> word, Grammar grammar) {
        int wordlength = word.size();
        Map<Variable, List<Production>> productions = grammar.productions;
        Set<Variable>[][] setV = new Set[wordlength][wordlength];
        for (int i = 0; i < wordlength; i++) {
            for (int j = 0; j < wordlength; j++) {
                setV[i][j] = new HashSet<>();
            }
        }
        // Check whether the terminal is on the right side of the production, then add its left variable to v_ii
        stepII(setV, word, wordlength, grammar);

        for (int l = 0; l <= wordlength - 1; l++) {
            for (int i = 0; i < wordlength - l; i++) {
                // setV[i][i+l] = EmptySet; This is already done via initialisation (set.size = 0).
                for (int k = i; k < i + l; k++) {
                    Set<Variable> tempSetX = new HashSet<>();
                    Set<Variable> tempSetY = setV[i][k];
                    Set<Variable> tempSetZ = setV[k+1][i+l];
                    Set<Variable> tempSetYZ = new HashSet<>();

                    for(Variable y : tempSetY) {
                        for(Variable z : tempSetZ) {
                            Variable tempVariable = new Variable(y, z);
                            tempSetYZ.add(tempVariable);
                        }
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println(productions);
                    System.out.println("tempSetYZ" + tempSetYZ);
                    // for all productions check if there is any of the combined Variables, which equals a RuleElement
                    for (List<Production> tempProductions : productions.values()) {
                        for(Production tempProduction : tempProductions) {
                            for (Variable yz : tempSetYZ) {
                                System.out.println("tempSetYZ" + tempSetYZ);
                                System.out.println("tempXY: " + yz);
                                System.out.println("tempProduction: " + tempProduction);
                                if (tempProduction.isRuleElementAtRightSide(yz)) {
                                    tempSetX.add(tempProduction.getLeftHandSide());
                                }
                                System.out.println("temppSetX step: " + tempSetX);
                            }
                            System.out.println("temppSetX final: " + tempSetX);
                        }
                    }
                    setV[i][i+l].addAll(tempSetX);
                }
            }
        }
        CYK.printSetV(setV, wordlength);
        // TODO: Here it is, that our starting variable always is S. This needs to be changed.
        System.out.println("The result is: " + setV[0][wordlength-1]);
        return setV[0][wordlength-1].contains(new Variable("S"));
    }

    public Tree cykAlgorithmAdvanced(StringBuilder word, Grammar grammar) {

        return new Tree();
    }

    public static void printSetV(Set<Variable>[][] setV, int wordlength) {
        System.out.println();
        System.out.println("setV:");
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

    public static String uniformStringMaker(String str, int length) {
        StringBuilder builder = new StringBuilder(str);
        for(int i = str.length(); i < length; ++i) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
