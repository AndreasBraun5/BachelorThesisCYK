package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.grammar.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GrammarTest {

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

    @Test(expected = GrammarException.class)
    /**
     *  No duplicate productions are added. No duplicate production can be given as argument.
     */
    public void addProduction() throws  GrammarException{
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test Grammar: addProduction");
        Grammar grammar = new Grammar(new VariableStart("S"));
        Production production11 = new Production(new Variable("A"), new Terminal("a"));
        Production production12 = new Production(new Variable("A"), new Terminal("a"));
        Production production13 = new Production(new Variable("A"), new Terminal("A"));
        Production production22 = new Production(new Variable("B"), new Terminal("B"));
        Production production33 = new Production(new Variable("C"), new Terminal("a"));
        Production[] productions = {production11, production12, production13, production22, production33};
        System.out.println("Count of productions that should be added: " + productions.length);
        System.out.println(Arrays.toString(productions));
        System.out.println("An GrammarException is to be excepted. Because of duplicate production A-->a");
        grammar.addProduction(productions);
    }

    @Test(expected = GrammarException.class)
    /**
     *  No duplicate productions are added. No already existing production can be added.
     */
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

    @Test
    /**
     *  Checking if a production actually is added.
     */
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
        Assert.assertEquals(true, productions.length == grammar.getProductionsAsList().size());
        System.out.println("Count of productions that acually have been added: " + grammar.getProductionsAsList().size());
    }
}
