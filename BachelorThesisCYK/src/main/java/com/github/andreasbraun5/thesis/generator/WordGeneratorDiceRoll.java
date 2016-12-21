package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.main.GrammarProperties;

import java.util.Random;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class WordGeneratorDiceRoll implements WordGenerator {
    public StringBuilder generateWord(GrammarProperties grammarProperties){
        StringBuilder randomWord = new StringBuilder("");
        //String[] possibleTerminals= grammarProperties.getPossibleTerminals();

        // Generate word out the alphabet with the given size
        Random random = new Random();
        int min = 0 ; // lower limit is inclusive
        int max = grammarProperties.terminals.size(); // upper limit would be exclusive without the +1
        int randomNumber;
        for(int i = 0; i < grammarProperties.sizeOfWord; i++) {
            randomNumber = random.nextInt(max - min) + min;
            randomWord.append(grammarProperties.terminals.get(randomNumber));
        }
        assert randomWord.length() <= grammarProperties.sizeOfWord : "randomWord.length is bigger than sizeOfWord";
        return randomWord;
    }
}
