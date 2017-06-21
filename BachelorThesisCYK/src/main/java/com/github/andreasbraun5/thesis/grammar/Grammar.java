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
     * No duplicate productionsMap can be added.
     */
    public void addProduction(Production... production) throws GrammarRuntimeException {
        int countProductionsToAdd = production.length;
        // Making production that are added unique.
        Set<Production> tempSet = new HashSet<>(Arrays.asList(production));
        // Considering duplicates in the parameters.
        if (tempSet.size() != countProductionsToAdd) {
            throw new GrammarRuntimeException("AddProduction: Duplicate production found in the production list.");
        }
        addProduction(tempSet);
    }

    /**
     * No duplicate productions can be added.
     */
    public void addProduction(Set<Production> productions) throws GrammarRuntimeException {
        int countProductionsBefore = this.getProductionsAsList().size();
        int countProductionsToAdd = productions.size();
        for (Production prod : productions) {
            Variable var = prod.getLeftHandSideElement();
            // add all "old" productions to prods.
            List<Production> prodsList = new ArrayList<>();
            if (productionsMap.containsKey(var)) {
                prodsList.addAll(this.productionsMap.get(var));
            }
            // Add the new production to prodsList.
            prodsList.add(prod);
            // Remove duplicates.
            Set<Production> prodSet = new HashSet<>();
            prodSet.addAll(prodsList);
            // ProdsList contains now no duplicates.
            prodsList.clear();
            prodsList.addAll(prodSet);
            // Exchange/replace the updated productionList in the map.
            productionsMap.put(var, prodsList);
        }
        int countProductionsAfter = this.getProductionsAsList().size();
        if (countProductionsAfter != countProductionsBefore + countProductionsToAdd) {
            throw new GrammarRuntimeException("AddProduction: Duplicate production found in the production set.");
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
