package com.github.andreasbraun5.thesis.parser;

import com.github.andreasbraun5.thesis.grammar.*;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class CYK {
    /*
        - Epsilon rule is implemented
     */

    public static Set<Variable>[][] calculateSetV(String word, Grammar grammar) {
        List<Terminal> list = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            list.add(new Terminal(String.valueOf(word.charAt(i))));
        }
        return calculateSetV(list, grammar);
    }

    public static boolean algorithmSimple(String word, Grammar grammar) {
        List<Terminal> list = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            list.add(new Terminal(String.valueOf(word.charAt(i))));
        }
        return algorithmSimple(list, grammar);
    }

    /*
    Each variable that has the terminal at position i of the word as its rightHandSideElement,
    will be added to setV[i][i]
     */
    public static void stepII(Set<Variable>[][] setV, List<Terminal> word,
                              Grammar grammar) {
        int wordLength = word.size();
        for (int i = 0; i < wordLength; i++) {
            RightHandSideElement tempTerminal = word.get(i);
            for (Map.Entry<Variable, List<Production>> entry : grammar.getProductions().entrySet()) {
                Variable var = entry.getKey();
                List<Production> prods = entry.getValue();
                for (Production prod : prods) {
                    if (prod.isElementAtRightHandSide(tempTerminal)) {
                        setV[i][i].add(var);
                    }
                }
            }
        }
    }

    public static boolean algorithmSimple(List<Terminal> word, Grammar grammar){
        Set<Variable>[][] setV = calculateSetV(word, grammar);
        int wordLength = word.size();
        // TODO: Here it is, that our starting variable always is S. This needs to be changed.
        System.out.println("The result is: " + setV[0][wordLength-1]);

        return setV[0][wordLength-1].contains(new Variable("S"));
    }

    public static Set<Variable>[][] calculateSetV(List<Terminal> word, Grammar grammar) {
        int wordLength = word.size();
        Map<Variable, List<Production>> productions = grammar.getProductions();
        // TODO why problem here? Generics stuff?
        Set<Variable>[][] setV = new Set[wordLength][wordLength];
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                setV[i][j] = new HashSet<>();
            }
        }
        // Check whether the terminal is on the right side of the production, then add its left variable to v_ii
        stepII(setV, word, grammar);

        for (int l = 0; l <= wordLength - 1; l++) {
            for (int i = 0; i < wordLength - l; i++) {
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
                    // for all productions check if there is any of the combined Variables, which equals a RightHandSideElement
                    for (List<Production> tempProductions : productions.values()) {
                        for(Production tempProduction : tempProductions) {
                            for (Variable yz : tempSetYZ) {
                                System.out.println("tempSetYZ" + tempSetYZ);
                                System.out.println("tempXY: " + yz);
                                System.out.println("tempProduction: " + tempProduction);
                                if (tempProduction.isElementAtRightHandSide(yz)) {
                                    tempSetX.add(tempProduction.getLeftHandSideElement());
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
        return setV;
    }

    public static Tree algorithmAdvanced(StringBuilder word, Grammar grammar) {

        return new Tree();
    }

    public static void printSetV(Set<Variable>[][] setV) {
        System.out.println();
        System.out.println("setV:");
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

    public static String uniformStringMaker(String str, int length) {
        StringBuilder builder = new StringBuilder(str);
        for(int i = str.length(); i < length; ++i) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
