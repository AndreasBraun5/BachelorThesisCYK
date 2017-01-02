package com.github.andreasbraun5.thesis.grammar.test;

import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import org.junit.Test;

/**
 * Created by Andreas Braun on 02.01.2017.
 */
public class ProductionTest {

    @Test
    public void productionToStringTest() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test Production: toString");

        Production production11 = new Production(new Variable("A"), new Terminal("a"), new Variable("CA"), new Variable("CC"));
        Production production22 = new Production(new Variable("B"), new Terminal("b"));
        Terminal[] terminals = {new Terminal("a"), new Terminal("b")};
        Production production33 = new Production(new Variable("C"), terminals);
        System.out.print("Production11: " + production11);
        System.out.print("Production22: " + production22);
        System.out.print("Production33: " + production33);
    }

    @Test
    public void productionIsElementAtRightHandSide() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test Production: isElementAtRightHandSide");

        Production production11 = new Production(new Variable("A"), new Terminal("a"), new Variable("CA"), new Variable("CC"));
        System.out.print("Production11: " + production11);
        boolean temp = production11.isElementAtRightHandSide(new Variable("CA"));
        System.out.println("Variable CA isElementAtRightHandSide: " + temp);
    }

}
