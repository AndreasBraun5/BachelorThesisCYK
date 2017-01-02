package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;
import org.junit.Test;
import org.junit.runners.model.TestClass;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GrammarTest {

    @Test
    public void grammarToString() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test Grammar: toString");

        // TODO: duplicate productions won´t be added
        Production production11 = new Production(new Variable("A"), new Terminal("a"));
        Production production12 = new Production(new Variable("A"), new Terminal("a"));
        Production production13 = new Production(new Variable("A"), new Terminal("A"));
        Production production22 = new Production(new Variable("B"), new Terminal("B"));
        Production production33 = new Production(new Variable("C"), new Terminal("a"));

        Grammar grammar1 = new Grammar();
        Production[] productions = {production11, production12, production13, production22, production33};
        grammar1.addProduction(productions);
        System.out.println(grammar1);
    }

}
