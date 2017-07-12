package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Andreas Braun on 13.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class UtilTest {
    @Test
    public void calculateVariablesCompound() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("Util calculateVariablesCompoundTest:");
        Word word = TiScriptExercise.SCRIPT_EXAMPLE_WORD;
        SetVarKMatrix setVarKMatrix = TiScriptExercise.SCRIPT_SET_VARK;
        Pyramid pyramid = new Pyramid(setVarKMatrix.getSetV(), word);
        Tuple<CellK, CellK> cellPair = new Tuple<>(pyramid.getCellK(0,0), pyramid.getCellK(0,1));
        System.out.println(cellPair);
        Set<VariableCompound> varComps = Util.calculateVariablesCompound(cellPair);
        Assert.assertTrue(varComps.contains(new VariableCompound(new Variable("N"), new Variable("E"))));
        Assert.assertTrue(varComps.contains(new VariableCompound(new Variable("N"), new Variable("B"))));
        Assert.assertTrue(varComps.contains(new VariableCompound(new Variable("A"), new Variable("E"))));
        Assert.assertTrue(varComps.contains(new VariableCompound(new Variable("A"), new Variable("B"))));
        System.out.println("All possible four combinations of tuples are contained.");
    }


}