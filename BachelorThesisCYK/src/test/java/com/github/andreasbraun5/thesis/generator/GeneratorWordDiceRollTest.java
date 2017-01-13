package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Andreas Braun on 02.01.2017.
 */
public class GeneratorWordDiceRollTest {

    @Test
    public void generateWordTest() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test GeneratorWordDiceRoll: generateWord");
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"));
        grammarProperties.addVariables(new Variable("A"), new Variable("B"));
        grammarProperties.addTerminals(new Terminal("a"), new Terminal("b"));
        grammarProperties.sizeOfWord = 10;
        GeneratorWordDiceRoll diceRoll = new GeneratorWordDiceRoll();
        StringBuilder word1 = diceRoll.generateWord(grammarProperties); // GrammarRuntimeException
        System.out.println(grammarProperties);
        System.out.println("generated word: " + word1);
        Assert.assertEquals("grammarProperties.sizeOfWord not the same as of word1", grammarProperties.sizeOfWord, word1.length());
    }

}
