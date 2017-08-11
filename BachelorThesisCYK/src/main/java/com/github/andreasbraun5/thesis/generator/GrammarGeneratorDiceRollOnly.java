package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public class GrammarGeneratorDiceRollOnly extends GrammarGenerator {

    public GrammarGeneratorDiceRollOnly(GrammarGeneratorSettings grammarGeneratorSettings) {
        super("DICEROLLONLY", grammarGeneratorSettings);
    }

    @Override
    public GrammarPyramidWrapper generateGrammarPyramidWrapper(
            GrammarPyramidWrapper grammarPyramidWrapper, WorkLog workLog) {
        {   // Start of logging
            workLog.log("#########################################################################################################");
            workLog.log("START of Logging of GrammarGeneratorDiceRollOnly.");
            // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
            grammarPyramidWrapper.setGrammar(new Grammar(grammarGeneratorSettings.grammarProperties.variableStart));
            workLog.log("Used word:");
            workLog.log(grammarPyramidWrapper.getPyramid().getWord().toString());
        }
        Set<Terminal> terminals = grammarGeneratorSettings.grammarProperties.terminals;
        Set<Variable> variables = grammarGeneratorSettings.grammarProperties.variables;
        {   // Line 2: Distributing terminals
            grammarPyramidWrapper = A_DistributeTerminals.distributeTerminals(
                    new ArrayList<>(terminals),
                    grammarPyramidWrapper,
                    grammarGeneratorSettings,
                    new ArrayList<>(variables)
            );
            workLog.log("Grammar after distributing the terminals:");
            workLog.log(grammarPyramidWrapper.getGrammar().toString());
        }
        {   // Line 3: Distributing compound variables
            // Generating all possible compound variables
            Set<VariableCompound> varComp = GrammarGeneratorUtil.
                    calculateAllVariablesCompoundForGrammar(variables);
            grammarPyramidWrapper = B_DistributeVariables.distributeCompoundVariables(
                    new ArrayList<>(varComp),
                    grammarPyramidWrapper,
                    grammarGeneratorSettings,
                    new ArrayList<>(variables)
            );
            workLog.log("Grammar after distributing all possible compound variables:");
            workLog.log(variables.toString());
            workLog.log(grammarPyramidWrapper.getGrammar().toString());
        }
        return CYK.calculateSetVAdvanced(grammarPyramidWrapper);
    }
}
