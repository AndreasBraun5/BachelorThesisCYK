package com.github.andreasbraun5.thesis.grammar;

import com.github.andreasbraun5.thesis.exception.GrammarRuntimeException;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 * A Grammar must at least have its variableStart defined.
 * In the class Grammar one entry of productionsMap represents not only one production exclusively, more specific
 * it contains all summed up productions of one variable. Key=A, Value: A-->a and A-->B and A--> AB,
 * represents, analogue to the script of TI1, one "line" of a productionsMap like "A --> a | A | AB".
 */
@EqualsAndHashCode
public class Grammar {
    // As stated above. Key=A, Value: A-->a and A-->B and A--> AB
    private final Map<Variable, Set<Production>> productionsMap;
    // Implemented like this, because there can be only one variableStart.
    private final VariableStart variableStart;

    public Grammar(VariableStart variableStart) {
        this(variableStart, new HashMap<>());
    }

    public Grammar(Grammar grammar) {
        this(grammar.variableStart, new HashMap<>(grammar.productionsMap));
    }

    public Grammar(VariableStart variableStart, Map<Variable, Set<Production>> productionsMap) {
        this.variableStart = variableStart;
        this.productionsMap = productionsMap;
    }

    public static Grammar empty(VariableStart variableStart) {
        return new Grammar(variableStart);
    }

    /**
     * You must make sure beforehand that only reasonable productions are added. No duplicates can be added and no
     * already existing production.
     */
    public Grammar addProductions(Production... production) throws GrammarRuntimeException {
        int countProductionsToAdd = production.length;
        // Making production that are added unique.
        Set<Production> tempSet = new HashSet<>(Arrays.asList(production));
        // Considering duplicates in the production.
        if (tempSet.size() != countProductionsToAdd) {
            throw new GrammarRuntimeException("AddProduction: Duplicate production found in the production array.");
        }
        addProductions(tempSet);
        return this;
    }

    /**
     * You must make sure beforehand that only reasonable productions are added. No duplicates can be added and no
     * already existing production.
     */
    public Grammar addProductions(Set<Production> productions) throws GrammarRuntimeException {
        for (Production prod : productions) {
            Variable var = prod.getLeftHandSideElement();
            Set<Production> prodsSet = new HashSet<>();
            // add all existing productions with key var to prods.
            if (productionsMap.containsKey(var)) {
                prodsSet.addAll(this.productionsMap.get(var));
            }
            // Add the new production to prodsList.
            prodsSet.add(prod);
            // Exchange/replace the updated productionList in the map.
            productionsMap.put(var, prodsSet);
        }
        return this;
    }

    public void removeAllProductions() {
        this.productionsMap.clear();
    }

    public Map<Variable, Set<Production>> getProductionsMap() {
        return productionsMap;
    }

    public VariableStart getVariableStart() {
        return variableStart;
    }

    public List<Production> getProductionsAsList() {
        List<Production> productionsList = new ArrayList<>();
        for (Map.Entry<Variable, Set<Production>> pair : productionsMap.entrySet()) {
            Set<Production> temp = pair.getValue();
            productionsList.addAll(temp);
        }
        return productionsList;
    }

    public Set<Production> getProductionsAsSet() {
        return new HashSet<>(getProductionsAsList());
    }

    public void removeProduction(Production production) {
        productionsMap.get(production.getLeftHandSideElement()).remove(production);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(
                "start:" + variableStart + ";\n");
        str.append("rules:{\n");
        for (Map.Entry<Variable, Set<Production>> entry : productionsMap.entrySet()) {
            entry.getValue().forEach(production -> str.append(production).append("\n"));
        }
        str.append("};");
        return str.toString();
    }


}
