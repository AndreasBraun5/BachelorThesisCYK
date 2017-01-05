package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GrammarTest {

    @Test
    public void grammarToString() {
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

    @Test
    /**
     *  No duplicate productions are added.
     */
    public void addProduction() {
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
        System.out.println(productions);
        List<Production> productionsAdded = new ArrayList<>();
        grammar.getProductions().keySet();

        Iterator it = grammar.getProductions().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            // it.remove(); // avoids a ConcurrentModificationException, TODO: what is this?
        }

        for(Map<Variable, Production> productions1 : grammar.getProductions()) {

        }

        boolean temp = grammar.getProductions().size() == 4;
        System.out.println(grammar);
        System.out.println("Count of productions that were added: " + grammar.g);
        Assert.assertEquals(true, temp);
    }

    @Test
    /**
     *
     */
    public void replaceProduction() {

    }
}
