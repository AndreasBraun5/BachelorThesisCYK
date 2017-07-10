package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.TiScriptExercise;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by AndreasBraun on 07.07.2017.
 */
public class GrammarGeneratorUtilTest {

    @Test
    public void removeUselessProductions() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("UtilTest: removeUselessProductions");
        Grammar grammar = new Grammar(TiScriptExercise.SCRIPT_GRAMMAR);
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        Production useless1 = new Production(new Variable("E"), new Terminal("5"));
        grammar.addProduction(useless1);
        Production useless2 = new Production(new Variable("B"), new VariableCompound(new Variable("K"), new Variable("D")));
        grammar.addProduction(useless2);
        GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.buildGrammarPyramidWrapper().
                setGrammar(grammar);
        grammarPyramidWrapper.setPyramid(new Pyramid(setVarKMatrix.getSetV(), word));
        System.out.println(grammar);
        System.out.println(word);
        System.out.println("Useless productions are" + useless1 + useless2 + "\n");
        GrammarGeneratorUtil.removeUselessProductions(grammarPyramidWrapper);
        System.out.println(grammar);
        Assert.assertTrue(
                "There should be only 14 productions left.",
                GrammarValidityChecker.checkSumOfProductions(grammar, 14).isSumOfProductions()
        );
        Assert.assertFalse(
                "There should be more than 13 productions left.",
                GrammarValidityChecker.checkSumOfProductions(grammar, 13).isSumOfProductions()
        );
        System.out.println("UtilTest: removeUselessProductions was successful");
    }
}

