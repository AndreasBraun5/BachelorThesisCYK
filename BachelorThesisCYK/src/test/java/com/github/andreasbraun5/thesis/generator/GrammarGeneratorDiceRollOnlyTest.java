package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 06.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarGeneratorDiceRollOnlyTest {

    @Test
    public void generateGrammar() {
        // TODO: This test is kinda senseless
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator GrammarGeneratorDiceRollOnly: generateGrammarPyramidWrapper");
        Set<Variable> variables = new HashSet<>();
        variables.add(new Variable("A"));
        variables.add(new Variable("B"));
        variables.add(new Variable("C"));
        Set<Terminal> terminals = new HashSet<>();
        terminals.add(new Terminal("a"));
        terminals.add(new Terminal("b"));
        terminals.add(new Terminal("c"));
        terminals.add(new Terminal("d"));
        terminals.add(new Terminal("e"));
        terminals.add(new Terminal("f"));
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"));
        grammarProperties.addTerminals(terminals).addVariables(variables);

        grammarProperties.variableStart = new VariableStart("S");
        GrammarGeneratorSettings grammarGeneratorSettings = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollOnlyTest");
        GrammarGeneratorDiceRollOnly generatorGrammarDiceRollOnly = new GrammarGeneratorDiceRollOnly(
                grammarGeneratorSettings);
        GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.buildGrammarPyramidWrapper();
        // TODO: non deterministic behaviour;
        grammarPyramidWrapper.setPyramid(
                new Pyramid(WordGeneratorDiceRoll.generateWord(grammarGeneratorSettings.grammarProperties)));
        grammarPyramidWrapper = generatorGrammarDiceRollOnly.generateGrammarPyramidWrapper(
                grammarPyramidWrapper,
                WorkLog.createFromWriter(null)
        );
        System.out.println(grammarPyramidWrapper.getGrammar());
        GrammarProperties grammarProperties2 = GrammarProperties.generatePartOfGrammarPropertiesFromGrammar(
                grammarPyramidWrapper.getGrammar()
        );
        // TODO: non deterministic behaviour;
        Assert.assertEquals(
                "terminals size is not the same",
                grammarProperties.terminals.size(),
                grammarProperties2.terminals.size()
        );
        Assert.assertEquals(
                "variables size is not the same",
                grammarProperties.variables.size(),
                grammarProperties2.variables.size()
        );
        Assert.assertEquals(
                "variable start is not the same",
                grammarProperties.variableStart,
                grammarProperties.variableStart
        );
    }
}
