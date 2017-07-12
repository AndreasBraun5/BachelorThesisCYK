package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;

import java.util.List;

/**
 * Created by AndreasBraun on 12.07.2017.
 */
public class B_DistributeVariables {
    /**
     * Equals the circled B Method.
     * grammarWordMatrixWrapper only needed for its contained Grammar here.
     * variablesWeighted: allows favouritism of specific variables.
     */
    static GrammarPyramidWrapper distributeCompoundVariables(
            List<VariableCompound> varComp,
            GrammarPyramidWrapper grammarPyramidWrapper,
            GrammarGeneratorSettings grammarGeneratorSettings,
            List<Variable> variablesWeighted) {
        return GrammarGeneratorUtil.distributeDiceRollRightHandSideElements(
                grammarPyramidWrapper,
                varComp,
                grammarGeneratorSettings.getMinValueCompoundVariablesAreAddedTo(),
                grammarGeneratorSettings.getMaxValueCompoundVariablesAreAddedTo(),
                variablesWeighted
        );
    }
}
