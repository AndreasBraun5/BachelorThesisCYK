package com.github.andreasbraun5.thesis.cyk;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    /*
    Vorwärtsproblem zuerst lösen.
    Ableitbare Eigentschaften nicht mit übergeben.
     */

    /*
    1) Generate a string over the alphabet
    2) Row1: Distribute the alphabet symbols over the variables. Every alphabet symbol needs at least one var.
        --> numberOfVars <= sizeOfAlphabet <= maxSizeOfAlphabet
        (make configurable!) Default: at least one, at most 2.
        --> numberOfVars >= sizeOfAlphabet/2
    3) Row2: Per cell, compute combinations of vars. Distribute again over right hand sides of vars such that the
        0-2 constraint (maxNumberOfVarsPerCell) is satisfied. Note: Here it hurts less to also have an empty cell.
    4) Row3 till last row: Similar procedure, but we have to take more cell combinations into account
    5) One goal is to determine and increase the success ratio
    6) Printing of the matrices must be possible
     */
    public static void main(String[] args) {

        Grammar grammar = new Grammar();
        {
            Variable A = new Variable("A");

        }

        // define parameters here
        String[] allPossibleVariables = {"A", "B", "C", "D", "E"};
        String[] allPossibleTerminals = {"a", "b", "c", "d", "e"};
        int maxNumberOfVars = 2;
        int maxNumberOfVarsPerCell = 3;
        int maxSizeOfAlphabet = 2;
        int maxSizeOfWord = 1;
        int sizeOfAlphabet = 2;
        int numberOfVars = 2;
        int sizeOfWord = 10;

        // grammar properties are tested in the constructor
        GrammarProperties grammarProperties = new GrammarProperties(allPossibleVariables, allPossibleTerminals,
                maxNumberOfVars, maxNumberOfVarsPerCell, maxSizeOfAlphabet, maxSizeOfWord, sizeOfAlphabet, numberOfVars, sizeOfWord);


        // Call functions here
        StringBuffer word = generateRandomWord(grammarProperties);
        System.out.println(word);
        cykFindGrammar(word, grammarProperties);
        printPrettyToConsole(word, grammarProperties);
    }

    private static StringBuffer generateRandomWord(GrammarProperties grammarProperties) {
        StringBuffer randomWordBuf = new StringBuffer("");
        String[] possibleTerminals= grammarProperties.getPossibleTerminals();

        // Generate word out the alphabet with the given size
        Random random = new Random();
        int min = 0 ; // lower limit is inclusive
        int max = possibleTerminals.length; // upper limit would be exclusive without the +1
        int randomNumber;
        for(int i = 0; i <= grammarProperties.getSizeOfWord(); i++) {
            randomNumber = random.nextInt(max - min) + min;
            assert randomNumber <= possibleTerminals.length :"randomNumber is bigger than possibleVars.length";
            randomWordBuf.append(possibleTerminals[randomNumber]);
        }
        assert randomWordBuf.length() <= grammarProperties.getMaxSizeOfWord() : "randomWord.length is bigger than maxSizeOfWord";
        return randomWordBuf;
    }

    private static boolean cykFindGrammar(StringBuffer word, GrammarProperties grammarProperties) {
        // The first dimension of grammarbuf corresponds to one specific variable. The First list corresponds to A, The second List to B, ...
        // The second dimension of grammarbuf corresponds to the possible productions of this one variable
        // Each entry of Stringbuffer corresponds to one specific production. The epsilon production can be added via an empty Stringbuffer "";
        int numberOfVars = grammarProperties.getNumberOfVars();
        ArrayList<ArrayList<StringBuffer>> grammarbuf = new ArrayList<>(numberOfVars);
        for(int i = 0; i < numberOfVars; i++) {
            grammarbuf.add(i, new ArrayList<>(0)); // Wouldn´t be needed because the empty constructor does initialise an Arraylist with size 10 by default
        }

        // Distribute the alphabet symbols over the possible variables. sizeOfAlphabet/2 <= numberOfVariables <= sizeOfAlphabet
        // Up till now there is the restriction of sizeOfAlphabet == maxSizeOfAlphabet, so each variable needs to have exactly one letter
        for(int i = 0; i <= numberOfVars; i++){
            //grammarbuf.get(i).get()
        }

        // Update the setVij
        // i is the depth of the pyramid, equals the rownumber of the matrix, ?equals j+1?
        // j is the length of the word
        int j = word.length();
        int i = j++;
        String[][] setV = new String[i][j];

        // variable for the v_ij set
        // variable for the pyramid
        return false;
    }

    private static void printPrettyToConsole(StringBuffer word, GrammarProperties grammarProperties) {
        StringBuffer buf = new StringBuffer();
        buf.append("Hello CYK!");
        System.out.println(buf);
    }
}