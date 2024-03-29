package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.exception.WordRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 */
public class WordGeneratorDiceRoll {

    /**
     * Excludes the case where the word could be the empty word.
     */
    public static Word generateWord(GrammarProperties grammarProperties) {
        if (grammarProperties.examConstraints.sizeOfWord == 0) {
            throw new GrammarPropertiesRuntimeException("The sizeOfWord is not defined.");
        }
        return generateWord(grammarProperties.terminals, grammarProperties.examConstraints.sizeOfWord);
    }

    /**
     * Not all terminals must be included in the word.
     */
    private static Word generateWord(Set<Terminal> terminals, int sizeOfWord) {
        List<Terminal> randomWord = new ArrayList<>();
        List<Terminal> tempTerminals = new ArrayList<>();
        tempTerminals.addAll(terminals);
        // Generate random word out the alphabet with the given size
        Random random = new Random();
        int randomNumber;
        int min = 0;
        int max = tempTerminals.size();
        for (int i = 0; i < sizeOfWord; i++) {
            randomNumber = random.nextInt(max) + min;
            randomWord.add(tempTerminals.get(randomNumber));
        }
        if (randomWord.size() > sizeOfWord) {
            throw new WordRuntimeException("randomWord.length of the " +
                    "generated word is bigger than the specified sizeOfWord of the grammar");
        }
        return new Word(randomWord);
    }

}
