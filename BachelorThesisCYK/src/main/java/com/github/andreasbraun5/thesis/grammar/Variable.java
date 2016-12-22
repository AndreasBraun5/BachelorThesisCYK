package com.github.andreasbraun5.thesis.grammar;

import java.time.temporal.ValueRange;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class Variable implements RuleElement{

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public Variable(Variable... variable) {
        StringBuilder string = new StringBuilder("");
        for (Variable v : variable) {
            string.append(v.getName());
        }
        this.name = string.toString();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        return name.equals(variable.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
