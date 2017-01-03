package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammar;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Set<Variable> variables = new HashSet<>();
        variables.add(new Variable("A"));
        variables.add(new Variable("B"));
        variables.add(new Variable("C"));
        String word = "01110100";
        int maxNumberOfVarsPerCell = 3;
        Grammar grammar = GeneratorGrammar.findGrammar(word, variables, maxNumberOfVarsPerCell);
        CYK.algorithmSimple(word, grammar);

    }

}