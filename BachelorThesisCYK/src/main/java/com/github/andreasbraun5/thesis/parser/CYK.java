package com.github.andreasbraun5.thesis.parser;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.pyramid.CellElement;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static boolean algorithmAdvanced(GrammarWordMatrixWrapper grammarWordMatrixWrapper, Word word) {
        SetVarKMatrix SetVarKMatrix = calculateSetVAdvanced(grammarWordMatrixWrapper);
        int wordLength = word.getWordLength();
        return Util.varKSetToVarSet(SetVarKMatrix.getSetV()[0][wordLength - 1])
                .contains(grammarWordMatrixWrapper.getGrammar().getVariableStart());
    }

    public static boolean algorithmAdvanced(Pyramid pyramid, Grammar grammar, GrammarProperties grammarProperties) {
        int wordlength = grammarProperties.grammarPropertiesGrammarRestrictions.getSizeOfWord();
        return pyramid.getCell(wordlength-1, 0).getCellElements().contains(grammar.getVariableStart());
    }

    /**
     * Each variable that has the terminal at position i of the word as its rightHandSideElement,
     * will be added to setV[i][i]
     */
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SetV is in reality an upper triangular matrix !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private static Set<VariableK>[][] stepIIAdvanced(
            Set<VariableK>[][] setV, List<Terminal> word, Grammar grammar) {
        int wordLength = word.size();
        // TODO Martin: How to prevent writing CYK.stepIIAdvanced instead of setV = CYK.stepIIAdvanced?
        // Look at each terminal of the word
        for (int i = 1; i <= wordLength; i++) {
            RightHandSideElement tempTerminal = word.get(i - 1);
            // Get all productions that have the same leftHandSide variable. This is done for all unique variables.
            // So all production in general are taken into account.
            for (Map.Entry<Variable, List<Production>> entry : grammar.getProductionsMap().entrySet()) {
                VariableK var = new VariableK(entry.getKey(), i);
                List<Production> prods = entry.getValue();
                // Check if there is one rightHandSideElement that equals the observed terminal.
                for (Production prod : prods) {
                    if (prod.isElementAtRightHandSide(tempTerminal)) {
                        setV[i - 1][i - 1].add(var);
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
    public static SetVarKMatrix calculateSetVAdvanced(GrammarWordMatrixWrapper grammarWordMatrixWrapper) {
        Grammar grammar = grammarWordMatrixWrapper.getGrammar();
        Word word = grammarWordMatrixWrapper.getWord();
        List<Terminal> wordAsTerminalList = word.getTerminals();
        int wordLength = wordAsTerminalList.size();
        Map<Variable, List<Production>> productions = grammar.getProductionsMap();
        Set<VariableK>[][] setV = Util.getInitialisedHashSetArray(wordLength, VariableK.class);
        // Check whether the terminal is on the right side of the production, then add its left variable to v_ii
        setV = stepIIAdvanced(setV, wordAsTerminalList, grammar);
        // l loop of the described algorithm
        for (int l = 1; l <= wordLength - 1; l++) {
            // i loop of the described algorithm.
            // Needs to be 1 <= i <= n-1-l, because of index starting from 0 for an array.
            for (int i = 0; i <= wordLength - l - 1; i++) {
                // k loop of the described algorithm
                // Needs to be i <= k <= i+l, because of index starting from 0 for i already.
                for (int k = i; k < i + l; k++) {
                    // tempSetX contains the newly to be added variables, regarding the "X-->YZ" rule.
                    // If the substring X can be concatenated with the substring Y and substring Z, whereas Y and Z
                    // must be element of its specified subsets, then add the element X to setV[i][i+l]
                    Set<Variable> tempSetX = new HashSet<>();
                    Set<Variable> tempSetY = Util.varKSetToVarSet(setV[i][k]);
                    Set<Variable> tempSetZ = Util.varKSetToVarSet(setV[k + 1][i + l]);
                    Set<VariableCompound> tempSetYZ = new HashSet<>();
                    // All possible concatenations of the variables yz are constructed. And so its substrings, which
                    // they are able to generate
                    for (Variable y : tempSetY) {
                        for (Variable z : tempSetZ) {
                            @SuppressWarnings("SuspiciousNameCombination")
                            VariableCompound tempVariable = new VariableCompound(y, z);
                            tempSetYZ.add(tempVariable);
                        }
                    }
                    // Looking at all productions of the grammar, it is checked if there is one rightHandSideElement that
                    // equals any of the concatenated variables tempSetYZ. If yes, the LeftHandSideElement or more
                    // specific the variable of the production is added to the tempSetX. All according to the "X-->YZ" rule.
                    for (List<Production> tempProductions : productions.values()) {
                        for (Production tempProduction : tempProductions) {
                            for (VariableCompound yz : tempSetYZ) {
                                if (tempProduction.isElementAtRightHandSide(yz)) {
                                    tempSetX.add(tempProduction.getLeftHandSideElement());
                                }
                            }
                        }
                    }
                    for (Variable var : tempSetX) {
                        // ( k + 1) because of index range of k  because of i.
                        setV[i][i + l].add(new VariableK(var, (k + 1)));
                    }
                }
            }
        }
        return new SetVarKMatrix(setV.length, word).setSetV(setV);
    }

    // TODO: completly false?
    public static Set<VariableK>[][] calculateSubSetForCell(
            GrammarWordMatrixWrapper grammarWordMatrixWrapper,
            Set<VariableK>[][] setV,
            int l,
            int i,
            int k) {
        // tempSetX contains the newly to be added variables, regarding the "X-->YZ" rule.
        // If the substring X can be concatenated with the substring Y and substring Z, whereas Y and Z
        // must be element of its specified subsets, then add the element X to setV[i][i+l]
        Set<Variable> tempSetX = new HashSet<>();
        Set<Variable> tempSetY = Util.varKSetToVarSet(setV[i][k]);
        Set<Variable> tempSetZ = Util.varKSetToVarSet(setV[k + 1][i + l]);
        Set<VariableCompound> tempSetYZ = new HashSet<>();
        // All possible concatenations of the variables yz are constructed. And so its substrings, which
        // they are able to generate
        for (Variable y : tempSetY) {
            for (Variable z : tempSetZ) {
                @SuppressWarnings("SuspiciousNameCombination")
                VariableCompound tempVariable = new VariableCompound(y, z);
                tempSetYZ.add(tempVariable);
            }
        }
        // Looking at all productions of the grammar, it is checked if there is one rightHandSideElement that
        // equals any of the concatenated variables tempSetYZ. If yes, the LeftHandSideElement or more
        // specific the variable of the production is added to the tempSetX. All according to the "X-->YZ" rule.
        for (List<Production> tempProductions : grammarWordMatrixWrapper.getGrammar().getProductionsMap().values()) {
            for (Production tempProduction : tempProductions) {
                for (VariableCompound yz : tempSetYZ) {
                    if (tempProduction.isElementAtRightHandSide(yz)) {
                        tempSetX.add(tempProduction.getLeftHandSideElement());
                    }
                }
            }
        }
        for (Variable var : tempSetX) {
            // ( k + 1) because of index range of k  because of i.
            setV[i][i + l].add(new VariableK(var, (k + 1)));
        }
        return setV;
    }

    public static Set<Variable> calculateSubSetForCell(
            GrammarWordMatrixWrapper grammarWordMatrixWrapper,
            Pyramid pyramid,
            int i,
            int j) {
        List<Production> productions = grammarWordMatrixWrapper.getGrammar().getProductionsAsList();
        Set<Variable> V = new HashSet<>();
        Set<Variable> X = new HashSet<>();
        Set<Variable> Y = new HashSet<>();
        Set<Variable> Z = new HashSet<>();
        Set<VariableCompound> YZ = new HashSet<>();
        for (int k = i - 1; i >= 0; i--) {
            for (CellElement ce : pyramid.getCells().get(k).get(j).getCellElements()) {
                Y.add((ce.getVariable()));
            }
            for (CellElement ce : pyramid.getCells().get(i - k - 1).get(k + j + 1).getCellElements()) {
                Z.add((ce.getVariable()));
            }
            for (Variable varY : Y) {
                for (Variable varZ : Z) {
                    YZ.add(new VariableCompound(varY, varZ));
                }
            }
            for (VariableCompound varYZ : YZ) {
                for (Production prod : productions) {
                    if (prod.getRightHandSideElement().equals(varYZ)) {
                        X.add(prod.getLeftHandSideElement());
                    }
                }
            }
            V.addAll(X);
            X.clear();
            Y.clear();
            Z.clear();
        }
        return V;
    }
}
