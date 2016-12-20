package com.github.andreasbraun5.thesis.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class Production {

    private Variable leftHandSide;
    private final List<RuleElement> rightHandSide = new ArrayList<>();

    public Production(Variable leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    public Variable getLeftHandSide() {
        return leftHandSide;
    }

    public List<RuleElement> getRightHandSide() {
        return rightHandSide;
    }

    @Override
    public String toString() {
        return "Production{" +
                "leftHandSide=" + leftHandSide +
                ", rightHandSide=" + rightHandSide +
                '}';
    }
}
