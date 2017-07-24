package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.main.ThesisDirectory;
import com.github.andreasbraun5.thesis.pyramid.CellK;
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

    private static final Random random = new Random();

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
     * Storing the result output in a text fileAsTxt.
     */
    public static void writeResultToTxtFile(
            Result... result) {
        for (int i = 0; i < result.length; i++) {
            Util.writeResultToTxtFile(result[i], result[i].name);
        }
    }

    /**
     * Storing the result output in a text fileAsTxt.
     */
    public static void writeResultToTxtFile(
            Result result, String name) {
        File test = new File(ThesisDirectory.EXAMPLES.path, name + ".txt");
        try (PrintWriter out = new PrintWriter(test)) {
            out.println(result);
            out.println(result.getExampleResultSamples().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public static Set<Tuple<CellK, CellK>> calculatePossibleCellPairs(CellK cellK, Pyramid pyramid) {
        return calculatePossibleCellPairs(cellK.getI(), cellK.getJ(), pyramid);
    }

    public static Set<Tuple<CellK, CellK>> calculatePossibleCellPairs(int i, int j, Pyramid pyramid) {
        Set<Tuple<CellK, CellK>> cellTuples = new HashSet<>();
        CellK[][] cells = pyramid.getCellsK();
        int iLeft;      // [i-1,0]
        int jLeft;      // equals j
        int iRight;     // [0,i-1]
        int jRight;     // [i+j,j+1]
        {
            jLeft = j;
            iRight = 0;
            jRight = i + j;
            for (iLeft = i - 1; iLeft >= 0; iLeft--) {
                CellK cellLeft = cells[iLeft][jLeft];
                CellK cellRight = cells[iRight][jRight];
                //noinspection SuspiciousNameCombination
                cellTuples.add(new Tuple<>(cellLeft, cellRight));
                iRight++;
                jRight--;
            }
        }
        return cellTuples;
    }

    public static <T> Set<T> uniformRandomSubset(Set<T> set) {
        int subSetRandomSize = random.nextInt(set.size() + 1);
        List<T> tempList = new ArrayList<>(set);
        while (set.size() > subSetRandomSize) {
            tempList.remove(random.nextInt(subSetRandomSize));
        }
        return new HashSet<>(tempList);
    }

}

