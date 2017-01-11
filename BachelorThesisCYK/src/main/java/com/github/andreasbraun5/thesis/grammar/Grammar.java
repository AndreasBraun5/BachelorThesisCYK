package com.github.andreasbraun5.thesis.grammar;

import com.github.andreasbraun5.thesis.exception.GrammarException;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
/*
In the class Grammar one entry of productionsMap represents not only one production exclusively, more specific it contains
all summed up productionsMap of one variable. Key=A, Value: A-->a and A-->B and A--> AB. Is represented analogue to the
script of TI1 as one "line" of productionsMap like "A --> a | A | AB".
 */
/**
 *  A Grammar must at least have its variableStart defined. By default its "S".
 */
public class Grammar {

    // As stated above. Key=A, Value: A-->a and A-->B and A--> AB
    private Map<Variable, List<Production>> productionsMap = new HashMap<>();
    private VariableStart variableStart; // Implemented like this, because there can be only one variableStart.

    public Grammar(VariableStart variableStart) {
        this.variableStart = variableStart;
    }

    /**
     *  No duplicate productionsMap can be added. Duplicates are detected in the production array.
     */
    public void addProduction(Production...  production) throws GrammarException {
        int countProductionsToAdd = production.length;
        // Making production that are added unique.
        Set<Production> tempSet = new HashSet<>(Arrays.asList(production));
        // Considering duplicates in the parameters.
        if(tempSet.size() != countProductionsToAdd) {
            throw new GrammarException("AddProduction: Duplicate production found in the production list.");
        }
        addProduction(tempSet);
    }

    /**
     *  No duplicate productions can be added. Duplicates are detected if the production already exists in the grammar.
     */
    public void addProduction(Set<Production> productions) throws GrammarException{
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
            // Exchange the updated productionList in the map.
            replaceProductions(var, prodsList);
        }
        int countProductionsAfter = this.getProductionsAsList().size();
        if(countProductionsAfter != countProductionsBefore+countProductionsToAdd){
            throw new GrammarException("AddProduction: Duplicate production found in the production set.");
        }
    }

    /**
     *  The productionsMap of one variable are replaced completely.
     */
    private void replaceProductions(Variable variable, List<Production> productions) {
        this.productionsMap.put(variable, productions);
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "\nvariableStart: " + variableStart +
                "\n" + productionsMap +
                '}';
    }

    public Map<Variable, List<Production>> getProductionsMap() {
        return productionsMap;
    }
    public VariableStart getVariableStart() {
        return variableStart;
    }

    /**
     * Iterate over all keys of the map.
     * Write each production into one list.
     * Get the list size of all productionsMap and sum it up.
     */
    public List<Production> getProductionsAsList() {
        List<Production> productionsList = new ArrayList<>();
        for (Map.Entry<Variable, List<Production>> pair : productionsMap.entrySet()) {
            List<Production> temp = pair.getValue();
            productionsList.addAll(temp);
        }
        return productionsList;
    }
}
