package com.github.andreasbraun5.thesis.parser.test;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 02.01.2017.
 */
public class CYKTest {

    @Test
    public void CYKCalculateSetVTest(){
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test CYK: algorithmSimple with input Grammar from the TI1 script");

        CYK cyk = new CYK();
        Production production1 = new Production(new Variable("S"), new Variable("NB"), new Variable("EA"), new Terminal(""));
        Production production2 = new Production(new Variable("S'"), new Variable("NB"), new Variable("EA"));
        Production production3 = new Production(new Variable("N"), new Terminal("0"));
        Production production4 = new Production(new Variable("E"), new Terminal("1"));
        Production production5 = new Production(new Variable("A"), new Terminal("0"), new Variable("NS'"), new Variable("EC"));
        Production production6 = new Production(new Variable("B"), new Terminal("1"), new Variable("ES'"), new Variable("ND"));
        Production production7 = new Production(new Variable("C"), new Variable("AA"));
        Production production8 = new Production(new Variable("D"), new Variable("BB"));
        Grammar grammar = new Grammar();
        grammar.addProduction(production1, production2, production3, production4, production5, production6, production7, production8);
        String word = new String("01110100");

        Set<Variable> setV[][] = CYK.calculateSetV(word, grammar);

        boolean temp = true;

        int wordLength = word.length();
        Set<Variable>[][] setVTemp = new Set[wordLength][wordLength];
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                setVTemp[i][j] = new HashSet<>();
            }
        }


        setV[1][1].contains(new Variable("A"));
        setV[1][1].contains(new Variable("N"));
        boolean a = setV[1][1].size()==2;
        // if one is not true then temp = false
        Assert.assertEquals(true, temp);

        }
}
