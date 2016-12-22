package com.github.andreasbraun5.thesis.grammar;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class Grammar {

    // TODO: make private with getter
    public Map<Variable, List<Production>> productions = new HashMap<>();

    public void addProduction(Production...  production) {
        for (Production aProduction : production) {
            List<Production> prods = productions.get(aProduction.getLeftHandSide());
            if (prods == null) {
                prods = new ArrayList<>();
                productions.put(aProduction.getLeftHandSide(), prods);
            }
            prods.add(aProduction);
        }
    }

    public void replaceProductions(Variable variable, List<Production> productions) {
        //assert production.getLeftHandSide() == variable;
        this.productions.put(variable, productions);
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "" + productions +
                '}';
    }
}
