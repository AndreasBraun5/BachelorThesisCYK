package com.github.andreasbraun5.thesis.grammar;

import com.github.andreasbraun5.thesis.exception.GrammarRuntimeException;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 * A Grammar must at least have its variableStart defined.
 * In the class Grammar one entry of productionsMap represents not only one production exclusively, more specific
 * it contains all summed up productions of one variable. Key=A, Value: A-->a and A-->B and A--> AB,
 * represents, analogue to the script of TI1, one "line" of a productionsMap like "A --> a | A | AB".
 */
public class Grammar {
    // As stated above. Key=A, Value: A-->a and A-->B and A--> AB
    private Map<Variable, List<Production>> productionsMap = new HashMap<>();
    // Implemented like this, because there can be only one variableStart.
    private final VariableStart variableStart;

    public Grammar(VariableStart variableStart) {
        this.variableStart = variableStart;
    }

    /**
     * You must make sure beforehand that only reasonable productions are added. No duplicates can be added and no
     * already existing production.
     */
    public void addProduction(Production... production) throws GrammarRuntimeException {
        int countProductionsToAdd = production.length;
        // Making production that are added unique.
        Set<Production> tempSet = new HashSet<>(Arrays.asList(production));
        // Considering duplicates in the production.
        if (tempSet.size() != countProductionsToAdd) {
            throw new GrammarRuntimeException("AddProduction: Duplicate production found in the production array.");
        }
        addProduction(tempSet);
    }

    /**
     * You must make sure beforehand that only reasonable productions are added. No duplicates can be added and no
     * already existing production.
     */
    public void addProduction(Set<Production> productions) throws GrammarRuntimeException {
        for (Production prod : productions) {
            Variable var = prod.getLeftHandSideElement();
            Set<Production> prodsSet = new HashSet<>();
            // add all existing productions with key var to prods.
            if (productionsMap.containsKey(var)) {
                prodsSet.addAll(this.productionsMap.get(var));
            }
            // Add the new production to prodsList.
            int countBefore = prodsSet.size();
            prodsSet.add(prod);
            int countAfter = prodsSet.size();
            if (countBefore != countAfter-1) {
                throw new GrammarRuntimeException("AddProduction: No duplicate production can be added.");
            }
            List<Production> prodList = new ArrayList<>();
            prodList.addAll(prodsSet);
            // Exchange/replace the updated productionList in the map.
            productionsMap.put(var, prodList);
        }
    }


    public void removeAllProductions() {
        this.productionsMap.clear();
    }

    public Map<Variable, List<Production>> getProductionsMap() {
        return productionsMap;
    }

    public VariableStart getVariableStart() {
        return variableStart;
    }

    public List<Production> getProductionsAsList() {
        List<Production> productionsList = new ArrayList<>();
        for (Map.Entry<Variable, List<Production>> pair : productionsMap.entrySet()) {
            List<Production> temp = pair.getValue();
            productionsList.addAll(temp);
        }
        return productionsList;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Grammar{" +
                "\nvariableStart: " + variableStart +
                "\nProductions\n");
        for (Map.Entry<Variable, List<Production>> entry : productionsMap.entrySet()) {
            str.append(entry.getKey());
            str.append(entry.getValue());
            str.append("\n");
        }
        str.append("}");
        return str.toString();
    }
}
