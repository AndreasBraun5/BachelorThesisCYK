package com.github.andreasbraun5.thesis.parser.test;

import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 02.01.2017.
 */
public class CYKTest {

    /**
     *  Checking the trivial case. A word like "a^sizeOfWord" and a grammar with S-->a|SS. The grammar must be
     *  be able to generate each of the words.
     */
    @Test
    public void CYKAlwaysTrue() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test CYK: AlwaysTrue");
        // Define GrammarProperties
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"))
                .addTerminals(new Terminal("a"));
        grammarProperties.maxNumberOfVarsPerCell = 3;
        grammarProperties.sizeOfWord = 10;
        // Generate word
        GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
        String word = generatorWordDiceRoll.generateWord(grammarProperties).toString();
        // Generate Grammar
        Grammar grammar = new Grammar(new VariableStart("S"));
        grammar.addProduction(new Production(new VariableStart("S"), new Terminal("a")),
                new Production(new VariableStart("S"), new VariableCompound(new VariableStart("S"), new VariableStart("S"))));
        System.out.println(grammar);
        // Check for integrity
        CYK.printSetV(CYK.calculateSetV(grammar,word), "setV");
        Assert.assertEquals("The grammar and the word aren't compatible, but should be.", true, GrammarIntegrityChecker.checkIntegrity(grammar, word));
    }

    @Test
    public void CYKCalculateSetVTestWithScript() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Test CYK: algorithmSimple with input Grammar from the TI1 script");

        Grammar grammar = new Grammar(new VariableStart("S"));
        Production productions[] = new Production[15];
        productions[0] = new Production(new VariableStart("S"), new VariableCompound(new Variable("N"), new Variable("B")));
        productions[1] = new Production(new VariableStart("S"), new VariableCompound(new Variable("E"), new Variable("A")));
        productions[2] = new Production(new VariableStart("S"), new Terminal(""));
        productions[3] = new Production(new Variable("S'"), new VariableCompound(new Variable("N"), new Variable("B")));
        productions[4] = new Production(new Variable("S'"), new VariableCompound(new Variable("E"), new Variable("A")));
        productions[5] = new Production(new Variable("N"), new Terminal("0"));
        productions[6] = new Production(new Variable("E"), new Terminal("1"));
        productions[7] = new Production(new Variable("A"), new Terminal("0"));
        productions[8] = new Production(new Variable("A"), new VariableCompound(new Variable("N"), new Variable("S'")));
        productions[9] = new Production(new Variable("A"), new VariableCompound(new Variable("E"), new Variable("C")));
        productions[10] = new Production(new Variable("B"), new Terminal("1"));
        productions[11] = new Production(new Variable("B"), new VariableCompound(new Variable("E"), new Variable("S'")));
        productions[12] = new Production(new Variable("B"), new VariableCompound(new Variable("N"), new Variable("D")));
        productions[13] = new Production(new Variable("C"), new VariableCompound(new Variable("A"), new Variable("A")));
        productions[14] = new Production(new Variable("D"), new VariableCompound(new Variable("B"), new Variable("B")));
        grammar.addProduction(productions);
        String word = "01110100";

        Set<Variable> setV[][] = CYK.calculateSetV(grammar, word);
        CYK.printSetV(setV, "setV calculated:");

        int wordLength = word.length();
        Set<Variable>[][] setVTemp = new Set[wordLength][wordLength];
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                setVTemp[i][j] = new HashSet<>();
            }
        }
        // reconstructing example matrix from scriptTI1
        setVTemp[0][0].add(new Variable("A"));
        setVTemp[0][0].add(new Variable("N"));
        setVTemp[0][1].add(new VariableStart("S"));
        setVTemp[0][1].add(new Variable("S'"));
        setVTemp[0][2].add(new Variable("B"));
        setVTemp[0][3].add(new Variable("D"));
        setVTemp[0][4].add(new Variable("B"));
        setVTemp[0][5].add(new Variable("D"));
        setVTemp[0][6].add(new Variable("B"));
        setVTemp[0][7].add(new VariableStart("S"));
        setVTemp[0][7].add(new Variable("S'"));

        setVTemp[1][1].add(new Variable("E"));
        setVTemp[1][1].add(new Variable("B"));
        setVTemp[1][2].add(new Variable("D"));
        setVTemp[1][4].add(new Variable("D"));
        setVTemp[1][6].add(new Variable("D"));
        setVTemp[1][7].add(new Variable("B"));

        setVTemp[2][2].add(new Variable("E"));
        setVTemp[2][2].add(new Variable("B"));
        setVTemp[2][3].add(new Variable("D"));
        setVTemp[2][4].add(new Variable("B"));
        setVTemp[2][5].add(new Variable("D"));
        setVTemp[2][6].add(new Variable("B"));
        setVTemp[2][7].add(new VariableStart("S"));
        setVTemp[2][7].add(new Variable("S'"));

        setVTemp[3][3].add(new Variable("E"));
        setVTemp[3][3].add(new Variable("B"));
        setVTemp[3][4].add(new VariableStart("S"));
        setVTemp[3][4].add(new Variable("S'"));
        setVTemp[3][5].add(new Variable("B"));
        setVTemp[3][6].add(new VariableStart("S"));
        setVTemp[3][6].add(new Variable("S'"));
        setVTemp[3][7].add(new Variable("A"));

        setVTemp[4][4].add(new Variable("A"));
        setVTemp[4][4].add(new Variable("N"));
        setVTemp[4][5].add(new VariableStart("S"));
        setVTemp[4][5].add(new Variable("S'"));
        setVTemp[4][6].add(new Variable("A"));
        setVTemp[4][7].add(new Variable("C"));

        setVTemp[5][5].add(new Variable("E"));
        setVTemp[5][5].add(new Variable("B"));
        setVTemp[5][6].add(new VariableStart("S"));
        setVTemp[5][6].add(new Variable("S'"));
        setVTemp[5][7].add(new Variable("A"));

        setVTemp[6][6].add(new Variable("A"));
        setVTemp[6][6].add(new Variable("N"));
        setVTemp[6][7].add(new Variable("C"));

        setVTemp[7][7].add(new Variable("A"));
        setVTemp[7][7].add(new Variable("N"));
        CYK.printSetV(setVTemp, "setVSolution");

        boolean temp = true;
        // optimizing possible, if one time temp == false, then stop executing the loops
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                if (!(setVTemp[i][j].containsAll(setV[i][j])) &&
                        setVTemp[i][j].size() == setV[i][j].size()) {
                    temp = false;
                }
            }
        }
        Assert.assertEquals(true, temp);
        System.out.println("\nSetV from script is the same as the calculated SetV: " + temp);
    }
}
