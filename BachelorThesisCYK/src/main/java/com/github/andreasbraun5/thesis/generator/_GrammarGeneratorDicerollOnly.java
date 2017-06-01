package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarWordWrapper;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public class _GrammarGeneratorDiceRollOnly extends _GrammarGenerator {


    public _GrammarGeneratorDiceRollOnly(_GrammarGeneratorSettings grammarGeneratorSettings) {
        super(grammarGeneratorSettings);
        this.generatorType = "DICEROLLONLY";
    }

    @Override
    /**
     *  Its goal is to generate a grammar. GrammarWordWrapper allows to additionally give more parameters, which are needed
     *  for the specific generator method.
     *  Here the specific implementation of each algorithm is written.
     */
    public GrammarWordWrapper generateGrammarWordWrapper(GrammarWordWrapper grammarWordWrapper) {
        // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
        // TODO: simplify
        Grammar grammar = new Grammar( grammarGeneratorSettings.grammarProperties.variableStart );
        grammarWordWrapper.setGrammar( grammar );
        grammarWordWrapper = _GrammarGeneratorUtil.distributeTerminals(
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.terminals),
                grammarWordWrapper,
                grammarGeneratorSettings,
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables )
        );
        Set<VariableCompound> varComp = new HashSet<>();
        for (Variable var1 : grammarGeneratorSettings.grammarProperties.variables) {
            for (Variable var2 : grammarGeneratorSettings.grammarProperties.variables) {
                varComp.add(new VariableCompound(var1, var2));
            }
        }
        grammarWordWrapper = _GrammarGeneratorUtil.distributeCompoundVariables(
                new ArrayList<>(varComp),
                grammarWordWrapper,
                grammarGeneratorSettings,
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables )
        );
        return grammarWordWrapper;
    }
}
