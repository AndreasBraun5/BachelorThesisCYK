package com.github.andreasbraun5.thesis.resultcalculator;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Andreas Braun on 09.02.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
@Setter
public class GrammarConstraintValues {

    private boolean grammarRestrictions;
    private boolean maxSumOfProductions;
    private int maxSumOfProductionsCount;

    @Override
    public String toString() {
        return "\nResultSampleGrammarConstraints{" +
                "\n		grammarRestrictions=" + grammarRestrictions +
                "\n		maxSumOfProductions=" + maxSumOfProductions +
                "\n}";
    }
}
