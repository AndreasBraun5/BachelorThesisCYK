package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by AndreasBraun on 16.07.2017.
 */
public class D_ChooseXYDependingOnIFromRowSet {

    /**
     * Implements linear dependency between i. It weights the tuples regarding with their i.
     * Example: If there is (AB,4) and (AB,1) then it is merged to (AB,1). Tuples that already have been considered are
     * given an lower chance of being used again.
     */
    public static VariableCompound chooseXYDependingOnIFromRowSet(Set<Tuple<VariableCompound, Integer>> rowSet) {
        List<VariableCompound> weightedVarComps = new ArrayList<>();
        Set<Tuple<VariableCompound, Integer>> rowSetCompressed = new HashSet<>();
        // Get all uniqueVarComps in rowSet
        Set<VariableCompound> uniqueVarComps = new HashSet<>();
        rowSet.forEach(varCompIntTuple -> uniqueVarComps.add(varCompIntTuple.x));
        // Filter for each unique varComp and determine its first occurrence described via the Integer.
        // Merge corresponding to (AB,4) + (AB,1) --> (AB,1)
        for (VariableCompound varComp : uniqueVarComps) {
            Set<Tuple<VariableCompound, Integer>> sameVarComp =
                    rowSet.stream().filter(varCompIntTuple -> varCompIntTuple.x == varComp).collect(Collectors.toSet());
            int maxI = 0;
            for (Tuple<VariableCompound, Integer> tuple : sameVarComp) {
                maxI = Math.min(tuple.y, maxI);
            }
            rowSetCompressed.add(new Tuple<>(varComp, maxI));
        }
        // Implement the "linear" dependency to the integer i. The higher the i out of all tuples the lower is the
        // chance for an tuple with lower i to be chosen.
        for (Tuple<VariableCompound, Integer> tuple : rowSetCompressed) {
            for (int i = 0; i <= tuple.y; i++) {
                weightedVarComps.add(tuple.x);
            }
        }
        int k = new Random().nextInt(weightedVarComps.size());
        return weightedVarComps.get(k);
    }
}
