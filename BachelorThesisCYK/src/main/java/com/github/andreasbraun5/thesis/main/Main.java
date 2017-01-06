package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.exception.WordException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRoll;;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws WordException, GrammarException {
        // Defining variables and terminals that are used
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

        // Generating a word, that will be used for generating a grammar.
        GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
        String word = generatorWordDiceRoll.generateWord(terminals, 10).toString();
        System.out.println(word);

        // Defining the GrammarProperties
        // numberOfVars from 2 to 5
        // sizeOfAlphabet from 2 to 4
        // maxNumberOfVarsPerCell = 3
        GrammarProperties grammarProperties = new GrammarProperties();
        grammarProperties.generatePartOfGrammarPropertiesFromWord(word);
        grammarProperties.addTerminals(terminals);
        grammarProperties.addVariables(variables);
        grammarProperties.variableStart = new VariableStart("S"); // well it would already be as the default
        grammarProperties.maxNumberOfVarsPerCell = 3;

        // Generating a grammar.
        GeneratorGrammarDiceRoll generatorGrammarDiceRoll = new GeneratorGrammarDiceRoll();
        Grammar grammar = generatorGrammarDiceRoll.generateGrammar(grammarProperties);

        // Checking if the word is in the found grammar
        GrammarIntegrityChecker.checkIntegrity(grammar, grammarProperties, word);
    }

}