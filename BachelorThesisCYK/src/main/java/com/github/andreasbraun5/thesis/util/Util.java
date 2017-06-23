package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.main.ThesisDirectory;
import com.github.andreasbraun5.thesis.pyramid.CellElement;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.resultcalculator.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andreas Braun on 05.01.2017.
 * https://github.com/AndreasBraun5/
 */
public abstract class Util {
    /**
     * Removing duplicates from a collection.
     */
    public static <T extends RightHandSideElement> List<T> withoutDuplicates(Collection<T> ruleElements) {
        List<T> ret = new ArrayList<>();
        ret.addAll(new HashSet<>(ruleElements));
        return ret;
    }

    /**
     *
     */
    public static <T extends Object> Set<T>[][] getInitialisedHashSetArray(int wordLength, Class<T> clazz) {
        @SuppressWarnings("unchecked")
        Set<T>[][] setVTemp = new Set[wordLength][wordLength];
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                setVTemp[i][j] = new HashSet<>();
            }
        }
        return setVTemp;
    }

	/*
    /**
	 * Counting the stored elements in each entry of the setV matrix and looking for the max count.
	 */
    // TODO ???: duplicate maxVarPerCell
    /*
    public static int getMaxVarPerCellForSetV(SetVarKMatrix<VariableK> setVMatrix) {
		Set<Variable>[][] tempSetV = setVMatrix.getSimpleSetDoubleArray();
		int numberOfVarsPerCell = 0;
		int temp = tempSetV.length;
		for ( int i = 0; i < temp; i++ ) {
			for ( int j = 0; j < temp; j++ ) {
				if ( tempSetV[i][j].size() > numberOfVarsPerCell ) {
					numberOfVarsPerCell = tempSetV[i][j].size();
				}
			}
		}
		return numberOfVarsPerCell;
	}
	*/

    /**
     * Storing the result output in a text file.
     */
    public static void writeResultToTxtFile(
            Result... result) {
        for (int i = 0; i < result.length; i++) {
            Util.writeResultToTxtFile(result[i], result[i].name);
        }
    }

    /**
     * Storing the result output in a text file.
     */
    public static void writeResultToTxtFile(
            Result result, String name) {
        File test = new File(ThesisDirectory.EXAMPLES.path, name + ".txt");
        PrintWriter out = null;
        try {
            out = new PrintWriter(test);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.println(result);
        out.println(result.getRepresentativeResultSamples().toString());
        out.close();

    }

    // Its structure is very similar to stepIIAdvanced and calculateSetVAdvanced.
    public static Grammar removeUselessProductions(
            Grammar grammar,
            Pyramid pyramid,
            Word word) {
        List<Terminal> wordAsTerminalsList = word.getTerminals();
        SetVarKMatrix setVarKMatrix = pyramid.getAsVarKMatrix();
        Set<VariableK>[][] setV = setVarKMatrix.getSetV();
        int wordLength = setVarKMatrix.getSetV().length;
        Map<Variable, List<Production>> productions = grammar.getProductionsMap();
        Set<Production> onlyUsefulProductions = new HashSet<>();
        // Similar to stepIIAdvanced
        // Look at each terminal of the word
        for (int i = 1; i <= wordLength; i++) {
            RightHandSideElement tempTerminal = wordAsTerminalsList.get(i - 1);
            // Get all productions that have the same leftHandSide variable. This is done for all unique variables.
            // So all production in general are taken into account.
            for (Map.Entry<Variable, List<Production>> entry : grammar.getProductionsMap().entrySet()) {
                VariableK var = new VariableK(entry.getKey(), i);
                List<Production> prods = entry.getValue();
                // Check if there is one rightHandSideElement that equals the observed terminal.
                for (Production prod : prods) {
                    if (prod.isElementAtRightHandSide(tempTerminal)) {
                        setV[i - 1][i - 1].add(var);
                        // This here was added.
                        onlyUsefulProductions.add(prod);
                    }
                }
            }
        }
        // Similar to calculateSetVAdvanced
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
                                    // This here is changed.
                                    onlyUsefulProductions.add(tempProduction);
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
        grammar.removeAllProductions();
        Production[] productionsArray = new Production[onlyUsefulProductions.size()];
        onlyUsefulProductions.toArray(productionsArray);
        grammar.addProduction(productionsArray);
        return grammar;
    }

    /**
     * Set<VariableK> --> Set<Variable>
     */
    public static Set<Variable> varKSetToVarSet(Set<VariableK> varKWrapper) {
        Set<Variable> setVVariable = new HashSet<>();
        for (CellElement cellElement : varKWrapper) {
            setVVariable.add(cellElement.getVariable());
        }
        return setVVariable;
    }

    public static <I, O extends I> List<O> filter(List<I> in, Class<O> clazz) {
        return in.stream().filter(i -> clazz.equals(i.getClass())).map(i -> (O) i).collect(Collectors.toList());
    }

    /**
     * helper method used by printSetVAsLowerTriangularMatrix
     */
    public static String uniformStringMaker(String str, int length) {
        StringBuilder builder = new StringBuilder(str);
        for (int i = str.length(); i < length; ++i) {
            builder.append(" ");
        }
        return builder.toString();
    }
}

