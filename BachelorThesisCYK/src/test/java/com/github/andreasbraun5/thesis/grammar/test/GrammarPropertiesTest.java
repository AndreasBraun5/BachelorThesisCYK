package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import org.junit.Test;

/**
 * Created by Andreas Braun on 02.01.2017.
 */
public class GrammarPropertiesTest {

    @Test
    public void grammarPropertiesToStringTest() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test GrammarProperties: toString");
        GrammarProperties grammarProperties = new GrammarProperties();
        grammarProperties.addVariables(new Variable("A"), new Variable("B"));
        grammarProperties.addTerminals(new Terminal("a"), new Terminal("b"));
        grammarProperties.sizeOfWord = 10;
        System.out.println(grammarProperties);
    }

}
