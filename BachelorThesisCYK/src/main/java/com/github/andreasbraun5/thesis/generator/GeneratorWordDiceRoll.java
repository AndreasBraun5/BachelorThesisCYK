package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;

import java.util.Random;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GeneratorWordDiceRoll implements GeneratorWord {
    public StringBuilder generateWord(GrammarProperties grammarProperties) throws GrammarException {
        StringBuilder randomWord = new StringBuilder("");
        //String[] possibleTerminals= grammarProperties.getPossibleTerminals();

        // Generate random word out the alphabet with the given size
        Random random = new Random();
        int min = 0 ; // lower limit is inclusive
        int max = grammarProperties.terminals.size(); // upper limit would be exclusive without the +1
        int randomNumber;
        for(int i = 0; i < grammarProperties.sizeOfWord; i++) {
            randomNumber = random.nextInt(max - min) + min;
            randomWord.append(grammarProperties.terminals.get(randomNumber));
        }
        if(randomWord.length() > grammarProperties.sizeOfWord) throw new GrammarException("randomWord.length of the " +
                "generated word is bigger than the specified sizeOfWord of the grammar");
        return randomWord;
    }
}
