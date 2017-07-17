package com.github.andreasbraun5.thesis.grammarproperties;

import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import org.junit.Test;

/**
 * Created by AndreasBraun on 28.06.2017.
 */
public class GrammarPropertiesTest {

    @Test
    public void toStringg() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator GrammarProperties: toString");
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"));
        grammarProperties.addVariables(new Variable("A"), new Variable("B"));
        grammarProperties.addTerminals(new Terminal("a"), new Terminal("b"));
        grammarProperties.examConstraints.sizeOfWord = 10;
        System.out.println(grammarProperties);
    }
}
