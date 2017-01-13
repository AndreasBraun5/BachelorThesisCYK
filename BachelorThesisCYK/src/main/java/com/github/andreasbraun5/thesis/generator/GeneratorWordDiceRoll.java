package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarPropertieRuntimeException;
import com.github.andreasbraun5.thesis.exception.WordRuntimeException;
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

    /**
     *  Excludes the case where the word could be the empty word.
     */
    public StringBuilder generateWord(GrammarProperties grammarProperties) {
        if (grammarProperties.sizeOfWord == 0) {
            throw new GrammarPropertieRuntimeException("The sizeOfWord is not defined.");
        }
        return generateWord(grammarProperties.terminals, grammarProperties.sizeOfWord);
    }

    /**
     *  Not all terminals must be included.
     */
    public StringBuilder generateWord(Set<Terminal> terminals, int sizeOfWord) {
        StringBuilder randomWord = new StringBuilder("");
        List<Terminal> tempTerminals = new ArrayList<>();
        tempTerminals.addAll(terminals);
        // Generate random word out the alphabet with the given size
        Random random = new Random();
        int min = 0 ; // lower limit is inclusive
        int max = tempTerminals.size(); // upper limit would be exclusive without the +1
        int randomNumber;
        for(int i = 0; i < sizeOfWord; i++) {
            randomNumber = random.nextInt(max - min) + min;
            randomWord.append(tempTerminals.get(randomNumber));
        }
        if(randomWord.length() > sizeOfWord) throw new WordRuntimeException("randomWord.length of the " +
                "generated word is bigger than the specified sizeOfWord of the grammar");
        return randomWord;
    }

}
