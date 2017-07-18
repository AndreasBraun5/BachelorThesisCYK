package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.main.ThesisDirectory;
import com.github.andreasbraun5.thesis.pyramid.CellK;
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

    public static <T extends Object> List<T>[][] getInitialisedArrayList(int wordLength, Class<T> clazz) {
        @SuppressWarnings("unchecked")
        List<T>[][] setVTemp = new ArrayList[wordLength][wordLength];
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                setVTemp[i][j] = new ArrayList<>();
            }
        }
        return setVTemp;
    }

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
        out.println(result.getExampleResultSamples().toString());
        out.close();

    }

    /**
     * Set<VariableK> --> Set<Variable>
     */
    public static Set<Variable> varKSetToVarSet(Set<VariableK> varKWrapper) {
        Set<Variable> variableSet = new HashSet<>();
        varKWrapper.forEach(variableK -> variableSet.add((Variable) variableK.getLhse()));
        return variableSet;
    }

    public static <I, O extends I> List<O> filter(List<I> in, Class<O> clazz) {
        return in.stream().filter(i -> clazz.equals(i.getClass())).map(i -> (O) i).collect(Collectors.toList());
    }

    /**
     * helper method used by printSetVAsLowerTriangularMatrix
     */
    public static String padWithSpaces(String str, int length) {
        StringBuilder builder = new StringBuilder(str);
        for (int i = str.length(); i < length; ++i) {
            builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Calculates all possible compoundVariables for the given set of CellTuples.
     */
    public static Set<VariableCompound> calculateVariablesCompoundForCellPair(Tuple<CellK, CellK> cellPairThatCanForce) {
        Set<VariableK> xSet = new HashSet<>(cellPairThatCanForce.x.getCellElements());
        Set<VariableK> ySet = new HashSet<>(cellPairThatCanForce.y.getCellElements());
        Set<VariableCompound> varComp = new HashSet<>();
        for (VariableK varX : xSet) {
            for (VariableK varY : ySet) {
                varComp.add(new VariableCompound((Variable) varX.getLhse(), (Variable) varY.getLhse()));
            }
        }
        return varComp;
    }

    /**
     * Set<VariableK>[][] --> Set<Variable>[][]
     */
    public static Set<Variable>[][] getSimpleSetDoubleArray(Set<VariableK>[][] setV) {
        int length = setV.length;
        Set<Variable>[][] setVVariable = Util.getInitialisedHashSetArray(length, Variable.class);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                setVVariable[i][j] = Util.varKSetToVarSet(setV[i][j]);
            }
        }
        return setVVariable;
    }
}

