package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammar;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        try {
            testHack();
        } catch (GrammarException e) {
            e.printStackTrace();
        }
        Set<Variable> variables = new HashSet<>();
        variables.add(new Variable("A"));
        variables.add(new Variable("B"));
        variables.add(new Variable("C"));
        String word = "01110100";
        int maxNumberOfVarsPerCell = 3;
        Grammar grammar = GeneratorGrammar.findGrammar(word, variables, maxNumberOfVarsPerCell);
        CYK.cykAlgorithmSimple(word, grammar);
    }

    public static void testHack() throws GrammarException{

        // Test: Printing of a word
        GrammarProperties grammarProperties = new GrammarProperties();
        grammarProperties.addVariables(new Variable("A"), new Variable("B"));
        grammarProperties.addTerminals(new Terminal("a"), new Terminal("b"));
        grammarProperties.sizeOfWord = 10;
        System.out.println(grammarProperties);


        // Test: Printing of a word
        GeneratorWordDiceRoll diceRoll = new GeneratorWordDiceRoll();
        StringBuilder word1 = diceRoll.generateWord(grammarProperties);
        System.out.println();
        System.out.println("word:");
        System.out.println(word1);

        // Test: Printing of productions
        Production production11 = new Production(new Variable("A"), new Terminal("a"), new Variable("CA"), new Variable("CC"));
        Production production22 = new Production(new Variable("B"), new Terminal("b"));
        Terminal[] terminals = {new Terminal("a"), new Terminal("b")};
        Production production33 = new Production(new Variable("C"), terminals);
        System.out.println();
        System.out.print(production11);
        System.out.print(production22);
        System.out.print(production33);

        // Test: Printing of Is
        boolean temp = production11.isElementAtRightHandSide(new Variable("CA"));
        System.out.println(temp);

        // Test: Printing of a grammar
        CYK cyk = new CYK();
        Grammar grammar1 = new Grammar();
        Production[] productions = {production11, production22, production33};
        grammar1.addProduction(productions);
        Production production44 = new Production(new Variable("C"), new Terminal("a"), new Terminal("b"), new Variable("CA") );
        grammar1.addProduction(production44);
        System.out.println();
        System.out.println(grammar1);

        // Test: Printing setV
        int size = 10;
        Variable[][] setV = new Variable[size][size];
        setV[0][0] = new Variable("A");
        setV[3][3]= new Variable("B");
        //CYK.printSetV(setV, size);

        // Testing: CYK-SimpleAlgorithm for the grammar out of the script.
        Production production1 = new Production(new Variable("S"), new Variable("NB"), new Variable("EA"), new Terminal(""));
        Production production2 = new Production(new Variable("S'"), new Variable("NB"), new Variable("EA"));
        Production production3 = new Production(new Variable("N"), new Terminal("0"));
        Production production4 = new Production(new Variable("E"), new Terminal("1"));
        Production production5 = new Production(new Variable("A"), new Terminal("0"), new Variable("NS'"), new Variable("EC"));
        Production production6 = new Production(new Variable("B"), new Terminal("1"), new Variable("ES'"), new Variable("ND"));
        Production production7 = new Production(new Variable("C"), new Variable("AA"));
        Production production8 = new Production(new Variable("D"), new Variable("BB"));
        Grammar grammar = new Grammar();
        grammar.addProduction(production1, production2, production3, production4, production5, production6, production7, production8);
        String word = new String("01110100");
        boolean isWordInGrammar = cyk.cykAlgorithmSimple(word, grammar);
        StringBuilder str = new StringBuilder("");
        System.out.println("Word: " + word + "\nGrammar: " + grammar + "\nisWordInGrammar: " + isWordInGrammar);
    }
}