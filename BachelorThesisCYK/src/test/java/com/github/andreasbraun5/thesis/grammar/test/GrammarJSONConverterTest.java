package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.json.GrammarJSONConverter;
import com.github.andreasbraun5.thesis.util.SS12Exercise;
import org.junit.Test;

/**
 * Created by AndreasBraun on 13.07.2017.
 */
public class GrammarJSONConverterTest {

    @Test
    public void test() {
        Grammar grammar = SS12Exercise.SS12_GRAMMAR;
        GrammarJSONConverter conv = new GrammarJSONConverter();
        String json = conv.toString(grammar);
        System.out.println(json);
//        conv.fromString(json);
    }

}
