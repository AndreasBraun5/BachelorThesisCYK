package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.exception.GrammarRuntimeException;
import com.github.andreasbraun5.thesis.grammar.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 */
public class GrammarTest {

    @Test
    public void grammarToString() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator Grammar: toString");
        Production production11 = new Production(new Variable("A"), new Terminal("a"));
        Production production13 = new Production(new Variable("A"), new Terminal("A"));
        Production production22 = new Production(new Variable("B"), new Terminal("B"));
        Production production33 = new Production(new Variable("C"), new Terminal("a"));
        // defining dummy startVariable here
        Grammar grammar1 = new Grammar(new VariableStart("S"));
        Production[] productions = {production11, production13, production22, production33};
        grammar1.addProduction(productions);
        System.out.println(grammar1);
    }

    /**
     *  No duplicate productions are added. No duplicate production can be given as argument.
     */
    @Test(expected = GrammarRuntimeException.class)
    public void addProductionAsArrayNoDuplicates() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator Grammar: addProductionAsArrayNoDuplicates");
        Grammar grammar = new Grammar(new VariableStart("S"));
        System.out.println("Count of productions that should be added: " + 5);
        System.out.println("An GrammarRuntimeException is to be excepted. Because of duplicate production A-->a");
        grammar.addProduction(
                new Production(new Variable("A"), new Terminal("a")),
                new Production(new Variable("A"), new Terminal("a")),
                new Production(new Variable("A"), new Terminal("b")),
                new Production(new Variable("B"), new Terminal("c")),
                new Production(new Variable("C"), new Terminal("a")));
    }

    /**
     *  No duplicate productions are added. No already existing production can be added.
     */
    @Test(expected = GrammarRuntimeException.class)
    public void addProductionAsSetNoDuplicates() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator Grammar: addProductionAsSetNoDuplicates");
        Grammar grammar = new Grammar(new VariableStart("S"));
        grammar.addProduction(new Production(new Variable("A"), new Terminal("a")));
        Set<Production> productionSet = new HashSet<>();
        productionSet.add(new Production(new Variable("A"), new Terminal("a")));
        productionSet.add(new Production(new Variable("A"), new Terminal("b")));
        productionSet.add(new Production(new Variable("B"), new Terminal("c")));
        productionSet.add(new Production(new Variable("C"), new Terminal("a")));
        System.out.println("Production A-->a already exist in the grammar.");
        System.out.println(grammar);
        System.out.println("Production to be added:");
        System.out.println(productionSet);
        System.out.println("GrammarRuntimeException is expected.");
        grammar.addProduction(productionSet);
    }

    /**
     *  Checking if a production actually is added.
     */
    @Test
    public void addProductionActuallyAddsProductions() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator Grammar: addProductionActuallyAddsProductions");
        Grammar grammar = new Grammar(new VariableStart("S"));
        Production production11 = new Production(new Variable("A"), new Terminal("a"));
        Production production13 = new Production(new Variable("A"), new Terminal("A"));
        Production production22 = new Production(new Variable("B"), new Terminal("B"));
        Production production33 = new Production(new Variable("C"), new Terminal("a"));
        Production[] productions = {production11, production13, production22, production33};
        System.out.println("Count of productions that should be added: " + productions.length);
        System.out.println(Arrays.toString(productions));
        System.out.println("Grammar before adding:");
        System.out.println(grammar);
        grammar.addProduction(productions);
        System.out.println("Grammar after adding:");
        System.out.println(grammar);
        Assert.assertEquals("productions.length was not equal to .size", productions.length, grammar.getProductionsAsList().size());
        System.out.println("Count of productions that actually have been added: " + grammar.getProductionsAsList().size());
    }
}
