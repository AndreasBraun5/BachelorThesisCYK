package com.github.andreasbraun5.thesis.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class Grammar {

    private final List<Production> productions = new ArrayList<>();

    public List<Variable> getAllVariables() {
        List<Variable> ret = new ArrayList<>();
        for(Production prod : this.productions) {
            ret.add(prod.getLeftHandSide());
        }
        return ret;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public void addProduction(Production production) {
        this.productions.add(production);
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "productions=" + productions +
                '}';
    }
}
