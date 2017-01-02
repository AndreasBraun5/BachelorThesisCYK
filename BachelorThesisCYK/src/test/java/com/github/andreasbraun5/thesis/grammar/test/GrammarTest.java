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

        Production production11 = new Production(new Variable("A"), new Terminal("a"), new Variable("CA"), new Variable("CC"));
        Production production22 = new Production(new Variable("B"), new Terminal("b"));
        Terminal[] terminals = {new Terminal("a"), new Terminal("b")};
        Production production33 = new Production(new Variable("C"), terminals);

        Grammar grammar1 = new Grammar();
        Production[] productions = {production11, production22, production33};
        grammar1.addProduction(productions);
        Production production44 = new Production(new Variable("C"), new Terminal("a"), new Terminal("b"), new Variable("CA") );
        grammar1.addProduction(production44);
        System.out.println(grammar1);
    }

}
