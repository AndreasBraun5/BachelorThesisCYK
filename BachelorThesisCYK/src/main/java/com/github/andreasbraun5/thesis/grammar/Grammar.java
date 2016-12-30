package com.github.andreasbraun5.thesis.grammar;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
/*
In the class Grammar one entry of productions represents not only one production exclusively, more specific it contains
all summed up productions of one variable. A-->a and A-->B and A--> AB is represented analogue to the script of TI1 as
one "line" of productions like "A --> a | A | AB".
 */
public class Grammar {

    // TODO: it may be possible to have more Productions for one Variable here
    private Map<Variable, List<Production>> productions = new HashMap<>();

    public void addProduction(Production...  production) {
        for (Production aProduction : production) {
            List<Production> prods = productions.get(aProduction.getLeftHandSideElement());
            if (prods == null) {
                prods = new ArrayList<>();
                productions.put(aProduction.getLeftHandSideElement(), prods);
            }
            prods.add(aProduction);
        }
    }

    public void replaceProductions(Variable variable, List<Production> productions) {
        //assert production.getLeftHandSideElement() == variable;
        this.productions.put(variable, productions);
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "" + productions +
                '}';
    }

    public Map<Variable, List<Production>> getProductions() {
        return productions;
    }
}
