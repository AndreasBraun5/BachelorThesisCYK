package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.parser.CYK;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public class _GrammarGeneratorDiceRollOnly extends _GrammarGenerator {

    public _GrammarGeneratorDiceRollOnly(_GrammarGeneratorSettings grammarGeneratorSettings) {
        super("DICEROLLONLY", grammarGeneratorSettings);
    }

    @Override
    /**
     *  Its goal is to generate a grammar. GrammarWordMatrixWrapper allows to additionally give more parameters, which are needed
     *  for the specific generator method.
     *  Here the specific implementation of each algorithm is written.
     */
    public GrammarWordMatrixWrapper generateGrammarWordMatrixWrapper(
            GrammarWordMatrixWrapper grammarWordMatrixWrapper,
            WorkLog workLog
            ) {
        workLog.log("#########################################################################################################");
        workLog.log("START of Logging of GrammarGeneratorDiceRollOnly.");
        // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
        // TODO: simplify
        Grammar grammar = new Grammar(grammarGeneratorSettings.grammarProperties.variableStart);
        grammarWordMatrixWrapper.setGrammar(grammar);
        workLog.log("Used word:");
        workLog.log(grammarWordMatrixWrapper.getWord().toString());
        grammarWordMatrixWrapper = _GrammarGeneratorUtil.distributeTerminals(
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.terminals),
                grammarWordMatrixWrapper,
                grammarGeneratorSettings,
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables)
        );
        workLog.log("After distributing the terminals:");
        workLog.log(grammarWordMatrixWrapper.getGrammar().toString());
        // Generating all possible compound variables
        Set<VariableCompound> varComp = new HashSet<>();
        for (Variable var1 : grammarGeneratorSettings.grammarProperties.variables) {
            for (Variable var2 : grammarGeneratorSettings.grammarProperties.variables) {
                varComp.add(new VariableCompound(var1, var2));
            }
        }
        grammarWordMatrixWrapper = _GrammarGeneratorUtil.distributeCompoundVariables(
                new ArrayList<>(varComp),
                grammarWordMatrixWrapper,
                grammarGeneratorSettings,
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables)
        );
        workLog.log("After distributing all possible compound variables:");
        workLog.log(grammarWordMatrixWrapper.getGrammar().toString());
        grammarWordMatrixWrapper.setSetV(CYK.calculateSetVAdvanced(grammar, grammarWordMatrixWrapper.getWord()));
        workLog.log("After removing useless productions:");
        grammarWordMatrixWrapper = _GrammarGeneratorUtil.removeUselessProductions(grammarWordMatrixWrapper);
        workLog.log(grammarWordMatrixWrapper.getGrammar().toString());
        workLog.log("END of Logging of GrammarGeneratorDiceRollOnly.");
        workLog.log("#########################################################################################################");
        return grammarWordMatrixWrapper;
    }
}
