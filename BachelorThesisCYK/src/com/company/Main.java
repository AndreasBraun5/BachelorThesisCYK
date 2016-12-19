package com.company;

import java.util.Random;

public class Main {

    // Define absolute parameters here
    private static final String[] allPossibleVariables = {"A", "B", "C", "D", "E"};
    private static final String[] allPossibleTerminals = {"a", "b", "c", "d", "e"};
    private static final int maxNumberOfVars = 2; // limited by allPossibleVariables.length
    private static final int maxNumberOfVarsPerCell = 3;
    private static final int maxSizeOfAlphabet = 2; // limited by allPossibleTerminals.length
    private static final int maxSizeOfWord = 10;

    public static void main(String[] args) {
        double succesRate;
        int numberOfVars = 2;
        int sizeOfWord = 6;
        testParameterConfiguration(numberOfVars, sizeOfWord);

        // Call functions here
        String word = generateRandomWord(sizeOfWord);
        System.out.println(word);
        // calling cykFindGrammar()
        printPrettyToConsole();
    }

    private static String generateRandomWord(int lengthOfWord) {
        StringBuffer randomWordBuf = new StringBuffer("");
        String[] possibleVars = new String[maxNumberOfVars];
        System.arraycopy(allPossibleTerminals, 0, possibleVars, 0, maxNumberOfVars);

        // mapping the random number, which is between 0 and 1, to one of the possible terminals
        Random random = new Random();
        int min = 0 ; // lower limit is inclusive
        int max = possibleVars.length; // upper limit would be exclusive without the +1
        int randomNumber;
        // TODO check boundary
        for(int i = 0; i <= lengthOfWord; i++) {
            randomNumber = random.nextInt(max - min) + min;
            // TODO adding cases for larger number of alphabet
            assert randomNumber <= possibleVars.length :"randomNumber is bigger than possibleVars.length";
            switch (randomNumber) {
                case 0: randomWordBuf.append(possibleVars[0]); break;
                case 1: randomWordBuf.append(possibleVars[1]); break;
                case 2: randomWordBuf.append(possibleVars[2]); break;
                case 3: randomWordBuf.append(possibleVars[3]); break;
                case 4: randomWordBuf.append(possibleVars[4]); break;
            }
        }
        // Generate word out the alphabet with the given size
        assert randomWordBuf.length() <= maxSizeOfWord : "randomWord.length is bigger than maxSizeOfWord";
        return new String(randomWordBuf);
    }

    private static boolean cykFindGrammar(String alphabet, int lengthOfWord) {
        // Generate word out the alphabet with the given size
        return false;
    }

    private static void printPrettyToConsole() {
        StringBuffer buf = new StringBuffer();
        buf.append("Hello CYK!");
        System.out.println(buf);
    }

    private static void testParameterConfiguration(int numberOfVars, int sizeOfWord) {
        assert maxNumberOfVars <= allPossibleVariables.length : "maxNumberOfVars is bigger than the allPossibleVariables.length";
        assert maxSizeOfAlphabet <=  allPossibleTerminals.length : "maxSizeOfAlphabet is bigger than the allPossibleTerminals.length";
        assert numberOfVars <= maxNumberOfVars : "numberOfVars is bigger than maxNumberOfVars";
        assert sizeOfWord <= maxSizeOfWord : "sizeOfWord is bigger than maxSizeOfWord";
    }
}