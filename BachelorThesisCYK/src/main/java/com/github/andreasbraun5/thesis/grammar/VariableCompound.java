package com.github.andreasbraun5.thesis.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas Braun on 08.01.2017.
 */
public class VariableCompound implements RightHandSideElement {

    protected List<Variable> variables;

    public VariableCompound(Variable varLeft, Variable varRight) {
        variables = new ArrayList<>();
        variables.add(varLeft);
        variables.add(varRight);
    }

    public List<Variable> getVariables() {
        return variables;
    }

    @Override
    public String getName() {
        return variables.get(0).toString() + variables.get(1).toString();
    }

    @Override
    public String toString() {
        return variables.get(0).toString() + variables.get(1).toString();
    }
}
