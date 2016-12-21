package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.WordGeneratorDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;

public class Main {

    /*
    Vorwärtsproblem zuerst lösen.
    Ableitbare Eigentschaften nicht mit übergeben.
     */

    /*
    1) Generate a string over the alphabet
    2) Row1: Distribute the alphabet symbols over the variables. Every alphabet symbol needs at least one var.
        --> numberOfVars <= sizeOfAlphabet
        (make configurable!) Default: at least one, at most 2.
        --> sizeOfAlphabet/2 <= numberOfVars <=  sizeOfAlphabet
    3) Row2: Per cell, compute combinations of vars. Distribute again over right hand sides of vars such that the
        0-2 constraint (maxNumberOfVarsPerCell) is satisfied. Note: Here it hurts less to also have an empty cell.
    4) Row3 till last row: Similar procedure, but we have to take more cell combinations into account
    5) One goal is to determine and increase the success ratio
    6) Printing of the matrices must be possible
     */
    public static void main(String[] args) {

        // int maxNumberOfVarsPerCell = 3;

        // Test: Printing of a word
        GrammarProperties grammarProperties = new GrammarProperties();
        grammarProperties.addVariables(new Variable("A"), new Variable("B"));
        grammarProperties.addTerminals(new Terminal("a"), new Terminal("b"));
        grammarProperties.sizeOfWord = 10;
        System.out.println(grammarProperties);


        // Test: Printing of a word
        WordGeneratorDiceRoll diceRoll = new WordGeneratorDiceRoll();
        StringBuilder word = diceRoll.generateWord(grammarProperties);
        System.out.println();
        System.out.println("word:");
        System.out.println(word);

        // Test: Printing of productions
        Production production1 = new Production(new Variable("A"), new Terminal("a"));
        Production production2 = new Production(new Variable("B"), new Terminal("b"));
        Terminal[] terminals = {new Terminal("a"), new Terminal("b")};
        Production production3 = new Production(new Variable("C"), terminals);
        System.out.println();
        System.out.print(production1);
        System.out.print(production2);
        System.out.print(production3);

        // Test: Printing of a grammar
        CYK cyk = new CYK();
        Grammar grammar = new Grammar();
        Production[] productions = {production1, production2, production3};
        grammar.addProduction(productions);
        System.out.println();
        System.out.println(grammar);

        // Test: Printing setV
        int size = 10;
        Variable[][] setV = new Variable[size][size];
        setV[0][0] = new Variable("A");
        setV[3][3]= new Variable("B");
        //CYK.printSetV(setV, size);
        cyk.cykAlgorithmSimple(word, grammar);

    }
}