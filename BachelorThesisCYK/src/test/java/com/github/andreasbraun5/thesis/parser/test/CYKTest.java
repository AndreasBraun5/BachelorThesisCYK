package com.github.andreasbraun5.thesis.parser.test;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.generator.WordGeneratorDiceRoll;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.CellElement;
import com.github.andreasbraun5.thesis.util.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Andreas Braun on 02.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class CYKTest {
    @Test
    public void calculateSubSetForCell() throws Exception {
        // TODO: Write Test here
    }

    /**
     * Checking the trivial case. A word like "a^sizeOfWord" and a grammar with S-->a|SS. The grammar must be
     * be able to generate each of the words.
     */
    @Test
    public void CYKAlwaysTrue() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator CYK: AlwaysTrue");
        // Define GrammarProperties
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"))
                .addTerminals(new Terminal("a"));
        grammarProperties.grammarPropertiesGrammarRestrictions.setMaxNumberOfVarsPerCell(3);
        grammarProperties.grammarPropertiesGrammarRestrictions.setSizeOfWord(10);
        Word word = WordGeneratorDiceRoll.generateWord(grammarProperties);
        // Generate Grammar
        Grammar grammar = new Grammar(new VariableStart("S"));
        grammar.addProduction(new Production(new VariableStart("S"), new Terminal("a")),
                new Production(new VariableStart("S"),
                        new VariableCompound(new VariableStart("S"), new VariableStart("S")))
        );
        GrammarWordMatrixWrapper grammarWordMatrixWrapper = new GrammarWordMatrixWrapper().setGrammar(grammar);
        grammarWordMatrixWrapper.setWord(word);
        System.out.println(grammar);
        // Check for integrity
        SetVarKMatrix SetVarKMatrix = CYK.calculateSetVAdvanced(grammarWordMatrixWrapper);
        System.out.println(SetVarKMatrix.getStringToPrintAsLowerTriangularMatrixSimple());
        Assert.assertEquals("The grammar and the word aren't compatible, but should be.",
                true, CYK.algorithmAdvanced(grammarWordMatrixWrapper, word)
        );
    }

    @Test
    public void CYKCalculateSetVariableKTestWithSS12() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator CYK: algorithmAdvanced with input Grammar from the SS12");
        Grammar grammar = SS12Exercise.SS12_GRAMMAR;
        Word word = SS12Exercise.SS12_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = SS12Exercise.SS12_SET_VARK;
        GrammarWordMatrixWrapper grammarWordMatrixWrapper =
                new GrammarWordMatrixWrapper().setGrammar(grammar);
        grammarWordMatrixWrapper.setWord(word);
        SetVarKMatrix setVarKMatrixCalculated = CYK.calculateSetVAdvanced(grammarWordMatrixWrapper);
        System.out.println(setVarKMatrixCalculated.getStringToPrintAsLowerTriangularMatrix());
        int wordLength = word.getWordLength();
        SetVarKMatrix setVarKMatrixSolution = setVarKMatrix;
        System.out.print(setVarKMatrixSolution.getStringToPrintAsLowerTriangularMatrix());
        Assert.assertTrue(checkSetVCalculated(
                setVarKMatrixSolution.getSetV(),
                setVarKMatrixCalculated.getSetV(),
                wordLength)
        );
        System.out.println("\nSetV from script is the same as the calculated SetV.");
    }

    @Test
    public void CYKCalculateSetVVariableKTestWithSS13() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator CYK: algorithmAdvanced with input Grammar from the SS13");
        Grammar grammar = SS13Exercise.SS13_GRAMMAR;
        Word word = SS13Exercise.SS13_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = SS13Exercise.SS13_SET_VARK;
        GrammarWordMatrixWrapper grammarWordMatrixWrapper =
                new GrammarWordMatrixWrapper().setGrammar(grammar);
        grammarWordMatrixWrapper.setWord(word);
        SetVarKMatrix setVarKMatrixCalculated = CYK.calculateSetVAdvanced(grammarWordMatrixWrapper);
        System.out.println(setVarKMatrixCalculated.getStringToPrintAsLowerTriangularMatrix());
        int wordLength = word.getWordLength();
        SetVarKMatrix setVarKMatrixSolution = setVarKMatrix;
        System.out.print(setVarKMatrixSolution.getStringToPrintAsLowerTriangularMatrix());
        Assert.assertTrue(checkSetVCalculated(
                setVarKMatrixSolution.getSetV(),
                setVarKMatrixCalculated.getSetV(),
                wordLength)
        );
        System.out.println("\nSetV from script is the same as the calculated SetV.");
    }


    @Test
    public void CYKCalculateSetVVariableKTestWithScript() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ResultCalculator CYK: algorithmAdvanced with input Grammar from the TI1 script");
        Grammar grammar = TiScriptExercise.SCRIPT_GRAMMAR;
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        GrammarWordMatrixWrapper grammarWordMatrixWrapper =
                new GrammarWordMatrixWrapper().setGrammar(grammar);
        grammarWordMatrixWrapper.setWord(word);
        SetVarKMatrix setVarKMatrixCalculated = CYK.calculateSetVAdvanced(grammarWordMatrixWrapper);
        System.out.println(setVarKMatrixCalculated.getStringToPrintAsLowerTriangularMatrix());
        int wordLength = word.getWordLength();
        SetVarKMatrix setVarKMatrixSolution = setVarKMatrix;
        System.out.print(setVarKMatrixSolution.getStringToPrintAsLowerTriangularMatrix());
        Assert.assertTrue(checkSetVCalculated(
                setVarKMatrixSolution.getSetV(),
                setVarKMatrixCalculated.getSetV(),
                wordLength)
        );
        System.out.println("\nSetV from script is the same as the calculated SetV.");
    }

    /**
     * Checking the two sets for being identical.
     */
    private static <T extends CellElement> boolean checkSetVCalculated(
            Set<T>[][] setVTemp,
            Set<T>[][] setV,
            int wordLength) {
        boolean temp = true;
        for (int i = 0; i < wordLength; i++) {
            for (int j = 0; j < wordLength; j++) {
                // TODO Martin: Why not possible? Set<T>[][] setVTemp2 = new HashSet<T>(). setVTemp.addAll(setVTemp)
                if (!setVTemp[i][j].containsAll(setV[i][j]) ||
                        !setV[i][j].containsAll(setV[i][j]) ||
                        setVTemp[i][j].size() != setV[i][j].size()) {
                    temp = false;
                }
            }
        }
        return temp;
    }

}
