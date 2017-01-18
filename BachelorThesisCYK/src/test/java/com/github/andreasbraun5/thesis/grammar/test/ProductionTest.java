package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andreas Braun on 02.01.2017.
 */
public class ProductionTest {

    @Test
    public void productionToStringTest() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("TestGrammar Production: toString");

        Production production11 = new Production(new Variable("A"), new Terminal("a"));
        Production production22 = new Production(new Variable("B"), new Terminal("B"));
        Production production33 = new Production(new Variable("C"), new Terminal("a"));
        System.out.print("1." + production11);
        System.out.print("2." + production22);
        System.out.print("3." + production33);
    }

    @Test
    public void productionIsElementAtRightHandSide() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("TestGrammar Production: isElementAtRightHandSide");

        Production production11 = new Production(new Variable("A"), new Terminal("a"));
        System.out.print(production11);
        boolean temp = production11.isElementAtRightHandSide(new Terminal("a"));
        Assert.assertEquals(true, temp);
        System.out.println("Terminal a isElementAtRightHandSide: " + temp);
    }
}
