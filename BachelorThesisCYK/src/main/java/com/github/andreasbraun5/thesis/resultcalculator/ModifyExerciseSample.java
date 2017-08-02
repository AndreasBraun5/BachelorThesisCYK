package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@Builder
public class ModifyExerciseSample {
    private Grammar grammar;
    private Pyramid pyramid;

    int maxNumberOfVarsPerCellCount;
    private int rightCellCombinationsForcedCount;
    private int maxSumOfVarsInPyramidCount;
    private CellSimple[][] markedRightCellCombinationForced;

    private int maxSumOfProductionsCount;

    @Override
    public String toString() {
        return grammar.toString() +
                pyramid.getWord() + "\n" +
                "\nPyramid=\n" + Pyramid.printPyramid(pyramid.getCellsSimple()) +
                "\nmarkedRightCellCombinationForced=\n" + Pyramid.printPyramid(getMarkedRightCellCombinationForced()) +
                "\nmaxNumberOfVarsPerCellCount=" + maxNumberOfVarsPerCellCount +
                "\nrightCellCombinationsForcedCount=" + rightCellCombinationsForcedCount +
                "\nmaxSumOfVarsInPyramidCount=" + maxSumOfVarsInPyramidCount +
                "\nmaxSumOfProductionsCount=" + maxSumOfProductionsCount;
    }

}