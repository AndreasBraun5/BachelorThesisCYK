package com.github.andreasbraun5.thesis.grammar;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andreas Braun on 08.01.2017.
 * https://github.com/AndreasBraun5/
 */
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

    @Override //TODO OMG...
    public String toString() {
        return left.toString() + "," + right.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariableCompound that = (VariableCompound) o;

        if (left != null ? !left.equals(that.left) : that.left != null) return false;
        return right != null ? right.equals(that.right) : that.right == null;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

}
