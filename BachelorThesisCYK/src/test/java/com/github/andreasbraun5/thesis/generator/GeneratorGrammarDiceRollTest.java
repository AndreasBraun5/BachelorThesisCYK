package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.exception.WordException;
import com.github.andreasbraun5.thesis.grammar.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 06.01.2017.
 */
public class GeneratorGrammarDiceRollTest {

    @Test
    public void generateGrammar() throws GrammarException {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test GeneratorGrammarDiceRoll: generateGrammar");
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
        grammarProperties.addTerminals(terminals);
        grammarProperties.addVariables(variables);
        grammarProperties.variableStart = new VariableStart("S");
        grammarProperties.maxNumberOfVarsPerCell = 3;
        System.out.println(grammarProperties);
        GeneratorGrammarDiceRoll generatorGrammarDiceRoll = new GeneratorGrammarDiceRoll();
        Grammar grammar = generatorGrammarDiceRoll.generateGrammar(grammarProperties);
        System.out.println(grammar);
        GrammarProperties grammarProperties2 = new GrammarProperties(new VariableStart("S"));
        // generatePartOfGrammarPropertiesFromGrammar now needed TODO: Left here
    }
}
