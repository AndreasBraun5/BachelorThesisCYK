package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.grammar.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GrammarTest {

    private String toSetup;

    @Before
    public void setup() {

    }

    @After
    public void teardown() {

    }

    @Test
    public void grammarToString() throws GrammarException {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test Grammar: toString");
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
    @Test(expected = GrammarException.class)
    public void addProduction() throws  GrammarException{
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test Grammar: addProduction");
        Grammar grammar = new Grammar(new VariableStart("S"));
        System.out.println("Count of productions that should be added: " + 5);
        System.out.println("An GrammarException is to be excepted. Because of duplicate production A-->a");
        grammar.addProduction(
                new Production(new Variable("A"), new Terminal("a")),
                new Production(new Variable("A"), new Terminal("a")),
                new Production(new Variable("A"), new Terminal("A")),
                new Production(new Variable("B"), new Terminal("B")),
                new Production(new Variable("C"), new Terminal("a")));
    }

    /**
     *  No duplicate productions are added. No already existing production can be added.
     */
    @Test(expected = GrammarException.class)
    public void addProduction2() throws  GrammarException{
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test Grammar: addProduction2");
        Grammar grammar = new Grammar(new VariableStart("S"));
        Production production11 = new Production(new Variable("A"), new Terminal("a"));
        Production production13 = new Production(new Variable("A"), new Terminal("A"));
        Production production22 = new Production(new Variable("B"), new Terminal("B"));
        Production production33 = new Production(new Variable("C"), new Terminal("a"));
        Production[] productions = {production11, production13, production22, production33};
        grammar.addProduction(productions);
        System.out.println(Arrays.toString(productions));
        System.out.println("When adding the production: ");
        System.out.println(production11);
        System.out.println("An GrammarException is to be excepted.");
        grammar.addProduction(production11);
    }

    /**
     *  Checking if a production actually is added.
     */
    @Test
    public void addProduction3() throws  GrammarException{
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test Grammar: addProduction3");
        Grammar grammar = new Grammar(new VariableStart("S"));
        Production production11 = new Production(new Variable("A"), new Terminal("a"));
        Production production13 = new Production(new Variable("A"), new Terminal("A"));
        Production production22 = new Production(new Variable("B"), new Terminal("B"));
        Production production33 = new Production(new Variable("C"), new Terminal("a"));
        Production[] productions = {production11, production13, production22, production33};
        System.out.println("Count of productions that should be added: " + productions.length);
        System.out.println(Arrays.toString(productions));
        grammar.addProduction(productions);
        Assert.assertEquals("productions.length was not equal to .size", productions.length, grammar.getProductionsAsList().size());
        System.out.println("Count of productions that acually have been added: " + grammar.getProductionsAsList().size());
    }
}
