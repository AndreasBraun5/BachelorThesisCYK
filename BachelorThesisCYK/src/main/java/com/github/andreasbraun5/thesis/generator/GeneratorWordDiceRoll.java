package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.WordException;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GeneratorWordDiceRoll implements GeneratorWord {

    public StringBuilder generateWord(GrammarProperties grammarProperties) throws WordException {
        Set<Terminal> tempTerminals = grammarProperties.terminals;
        List<Terminal> terminals = new ArrayList<>(tempTerminals);
        int sizeOfWord = grammarProperties.sizeOfWord;
        return generateWord(terminals, sizeOfWord);
    }

    public StringBuilder generateWord(List<Terminal> terminals, int sizeOfWord)
            throws WordException {
        StringBuilder randomWord = new StringBuilder("");
        // Generate random word out the alphabet with the given size
        Random random = new Random();
        int min = 0 ; // lower limit is inclusive
        int max = terminals.size(); // upper limit would be exclusive without the +1
        int randomNumber;
        for(int i = 0; i < sizeOfWord; i++) {
            randomNumber = random.nextInt(max - min) + min;
            randomWord.append(terminals.get(randomNumber));
        }
        if(randomWord.length() > sizeOfWord) throw new WordException("randomWord.length of the " +
                "generated word is bigger than the specified sizeOfWord of the grammar");
        return randomWord;
    }

}
