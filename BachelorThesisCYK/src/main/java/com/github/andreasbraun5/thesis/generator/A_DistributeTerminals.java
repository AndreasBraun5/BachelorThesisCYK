package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;

import java.util.List;

/**
 * Created by AndreasBraun on 12.07.2017.
 */
public class A_DistributeTerminals {
    /**
     * Equals the circled A Method.
     * If you want to distribute the terminals to at least one rightHandSide then minCountElementDistributedTo is 1.
     * grammarWordMatrixWrapper only needed for its contained Grammar here.
     * variablesWeighted: allows favouritism of specific variables.
     */
    static GrammarPyramidWrapper distributeTerminals(
            List<Terminal> terminals,
            GrammarPyramidWrapper grammarPyramidWrapper,
            GrammarGeneratorSettings grammarGeneratorSettings,
            List<Variable> variablesWeighted) {
        return GrammarGeneratorUtil.distributeDiceRollRightHandSideElements(
                grammarPyramidWrapper,
                terminals,
                grammarGeneratorSettings.getMinValueTerminalsAreAddedTo(),
                grammarGeneratorSettings.getMaxValueTerminalsAreAddedTo(),
                variablesWeighted
        );
    }
}
