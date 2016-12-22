package com.github.andreasbraun5.thesis.grammar;

import java.time.temporal.ValueRange;
import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class Production {

    private Variable leftHandSide;
    private List<RuleElement> rightHandSide = new ArrayList<>();

    public Production(Variable leftHandSide, RuleElement ... rightHandside) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide.addAll(Arrays.asList(rightHandside).subList(0, rightHandside.length));
    }

    public Variable getLeftHandSide() {
        return leftHandSide;
    }

    public boolean isRuleElementAtRightSide(RuleElement ruleElement){
        return rightHandSide.contains(ruleElement);
    }

    public List<RuleElement> getRightHandSide() {
        return rightHandSide;
    }

    @Override
    public String toString() {
        return "Production{" +
                leftHandSide + "-->" + rightHandSide +
                "}\n";
    }

}
