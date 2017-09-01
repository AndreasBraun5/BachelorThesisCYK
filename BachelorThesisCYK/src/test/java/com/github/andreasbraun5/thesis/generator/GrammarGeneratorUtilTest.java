package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.valididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by AndreasBraun on 07.07.2017.
 */
public class GrammarGeneratorUtilTest {
    @Test
    public void usefulProductionsPerCellForVarComp() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarGeneratorUtilTest: contributingProductionsPerCellForVarComp");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        Set<VariableCompound> variableCompounds = new HashSet<>();
        variableCompounds.add(new VariableCompound(new Variable("N"), new Variable("B")));
        variableCompounds.add(new VariableCompound(new Variable("N"), new Variable("C")));
        variableCompounds.add(new VariableCompound(new Variable("N"), new Variable("N")));
        variableCompounds.add(new VariableCompound(new Variable("A"), new Variable("A")));
        Set<Production> prodList = grammar.getProductionsAsSet();
        Set<Production> usefulProductions = GrammarGeneratorUtil.contributingProductionsPerCellForVarComp(
                variableCompounds,  prodList);
        System.out.println("Useful productions are: S-->NB, S'-->NB and C-->AA");
        Assert.assertTrue(usefulProductions.size() == 3);
        Assert.assertTrue(usefulProductions.contains(new Production(new VariableStart("S"),
                new VariableCompound(new Variable("N"), new Variable("B")))));
        Assert.assertTrue(usefulProductions.contains(new Production(new Variable("S'"),
                new VariableCompound(new Variable("N"), new Variable("B")))));
        Assert.assertTrue(usefulProductions.contains(new Production(new Variable("C"),
                new VariableCompound(new Variable("A"), new Variable("A")))));
        System.out.println("All 3 useful productions have been found.");
        System.out.println(usefulProductions);

    }

    @Test
    public void usefulProductionsPerCellForTerminals() throws Exception {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarGeneratorUtilTest: contributingProductionsPerCellForTerminals");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        Set<Production> usefulProductions = GrammarGeneratorUtil.contributingProductionsPerCellForTerminals(
                word, grammar.getProductionsAsSet());
        grammar.addProductions(new Production(new Variable("E"), new Terminal("3")));
        System.out.println(grammar);
        System.out.println("Not useful production is: E --> 3");
        Assert.assertTrue(usefulProductions.contains(new Production(new Variable("N"),
                new Terminal("0"))));
        Assert.assertTrue(usefulProductions.contains(new Production(new Variable("A"),
                new Terminal("0"))));
        Assert.assertTrue(usefulProductions.contains(new Production(new Variable("E"),
                new Terminal("1"))));
        Assert.assertTrue(usefulProductions.contains(new Production(new Variable("B"),
                new Terminal("1"))));
        Assert.assertTrue(usefulProductions.size() == 4);
        System.out.println(usefulProductions);
    }

    @Test
    public void calculatePossibleCellPairs() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarGeneratorUtilTest: calculatePossibleCellPairs");
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        Pyramid pyramid = new Pyramid(setVarKMatrix.getSetV(), word);
        Set<Tuple<CellK, CellK>> cellPairs = Util.
                calculatePossibleCellPairs(4, 2, pyramid);
        System.out.println(cellPairs);
        Assert.assertTrue(cellPairs.contains(new Tuple<>(pyramid.getCellK(3, 2), pyramid.getCellK(0, 6))));
        Assert.assertTrue(cellPairs.contains(new Tuple<>(pyramid.getCellK(2, 2), pyramid.getCellK(1, 5))));
        Assert.assertTrue(cellPairs.contains(new Tuple<>(pyramid.getCellK(1, 2), pyramid.getCellK(2, 4))));
        Assert.assertTrue(cellPairs.contains(new Tuple<>(pyramid.getCellK(0, 2), pyramid.getCellK(3, 3))));
        System.out.println("All four needed cell pairs are included.");
    }

    @Test
    public void removeUselessProductions() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("GrammarGeneratorUtilTest: deleteUnnecessaryProductions");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        Production useless1 = new Production(new Variable("E"), new Terminal("5"));
        grammar.addProductions(useless1);
        Production useless2 = new Production(new Variable("B"), new VariableCompound(new Variable("K"), new Variable("D_")));
        grammar.addProductions(useless2);
        GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder().grammar(grammar).build();
        grammarPyramidWrapper.setPyramid(new Pyramid(setVarKMatrix.getSetV(), word));
        System.out.println(grammar);
        System.out.println(word);
        System.out.println("Useless productions are" + useless1 + useless2 + "\n");
        GrammarGeneratorUtil.deleteUnnecessaryProductions(grammarPyramidWrapper);
        System.out.println(grammar);
        Assert.assertTrue(
                "There should be only 15 productions left.",
                GrammarValidityChecker.checkSumOfProductions(grammar, 15).isSumOfProductions()
        );
        Assert.assertFalse(
                "There should be more than 14 productions left.",
                GrammarValidityChecker.checkSumOfProductions(grammar, 14).isSumOfProductions()
        );
        System.out.println("UtilTest: deleteUnnecessaryProductions was successful");
    }
}

