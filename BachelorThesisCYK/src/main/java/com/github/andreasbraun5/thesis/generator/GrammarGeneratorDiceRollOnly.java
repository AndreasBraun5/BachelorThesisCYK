package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;

import java.util.ArrayList;
import java.util.HashSet;
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
        workLog.log("#########################################################################################################");
        workLog.log("START of Logging of GrammarGeneratorDiceRollOnly.");
        // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
        grammarPyramidWrapper.setGrammar(new Grammar(grammarGeneratorSettings.grammarProperties.variableStart));
        workLog.log("Used word:");
        workLog.log(grammarPyramidWrapper.getPyramid().getWord().toString());
        grammarPyramidWrapper = A_DistributeTerminals.distributeTerminals(
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.terminals),
                grammarPyramidWrapper,
                grammarGeneratorSettings,
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables)
        );
        workLog.log("After distributing the terminals:");
        workLog.log(grammarPyramidWrapper.getGrammar().toString());
        // Generating all possible compound variables
        Set<VariableCompound> varComp = new HashSet<>();
        for (Variable var1 : grammarGeneratorSettings.grammarProperties.variables) {
            for (Variable var2 : grammarGeneratorSettings.grammarProperties.variables) {
                varComp.add(new VariableCompound(var1, var2));
            }
        }
        grammarPyramidWrapper = B_DistributeVariables.distributeCompoundVariables(
                new ArrayList<>(varComp),
                grammarPyramidWrapper,
                grammarGeneratorSettings,
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables)
        );
        workLog.log("After distributing all possible compound variables:");
        workLog.log(grammarPyramidWrapper.getGrammar().toString());
        grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
        workLog.log("After removing useless productions:");
        grammarPyramidWrapper = GrammarGeneratorUtil.removeUselessProductions(grammarPyramidWrapper);
        workLog.log(grammarPyramidWrapper.getGrammar().toString());
        workLog.log("END of Logging of GrammarGeneratorDiceRollOnly.");
        workLog.log("#########################################################################################################");
        return grammarPyramidWrapper;
    }
}
