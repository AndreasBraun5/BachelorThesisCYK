package com.github.andreasbraun5.thesis.antlr;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import org.junit.Test;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
public class GrammarCompilerTest {

    @Test
    public void test() {
        String grammarString = "start:S;\n" +
                "rules: {\n" +
                "    A -> C C\n" +
                "    B -> C B\n" +
                "    B -> a\n" +
                "};";
        Grammar grammar = GrammarCompiler.parseGrammar(grammarString);
        System.out.println(grammar);
    }

}
