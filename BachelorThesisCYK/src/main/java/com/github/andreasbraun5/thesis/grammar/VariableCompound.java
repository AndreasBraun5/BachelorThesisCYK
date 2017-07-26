package com.github.andreasbraun5.thesis.grammar;

import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andreas Braun on 08.01.2017.
 * https://github.com/AndreasBraun5/
 */
@EqualsAndHashCode
public class VariableCompound implements RightHandSideElement {

    private final Variable left;
    private final Variable right;

    public VariableCompound(Variable left, Variable right) {
        this.left = left;
        this.right = right;
    }

    public static VariableCompound of(Variable left, Variable right) {
        return new VariableCompound(left, right);
    }

    public List<Variable> getVariables() {
        return Arrays.asList(this.left, this.right);
    }

    @Override
    public String getTerminalName() {
        return left.toString() + right.toString();
    }

    @Override
    public String toString() {
        return left.toString() + " " + right.toString();
    }

}
