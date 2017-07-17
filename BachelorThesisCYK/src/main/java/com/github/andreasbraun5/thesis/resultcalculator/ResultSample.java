package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammarproperties.ExamConstraints;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarConstraints;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.*;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import lombok.Getter;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
public class ResultSample {

    private Grammar grammar;
    private Pyramid pyramid;

    // This stays here
    private boolean isValid;
    private boolean isWordProducible;

    private ResultSamplePyramidConstraints resultSamplePyramidConstraints = new ResultSamplePyramidConstraints();
    private ResultSampleGrammarConstraints resultSampleGrammarConstraints = new ResultSampleGrammarConstraints();

    public ResultSample(
            GrammarPyramidWrapper grammarPyramidWrapper,
            GrammarGeneratorSettings grammarGeneratorSettings) {
        GrammarConstraints tempGrammarConstraints = grammarGeneratorSettings.grammarProperties.
                grammarConstraints;
        ExamConstraints tempExamConstraints = grammarGeneratorSettings.grammarProperties.
                examConstraints;
        this.grammar = grammarPyramidWrapper.getGrammar();
        this.pyramid = grammarPyramidWrapper.getPyramid();
        this.isWordProducible = GrammarValidityChecker.
                checkProducibilityCYK(pyramid, grammar, grammarGeneratorSettings.grammarProperties);

        // CheckSumOfProductionsResultWrapper
        CheckSumOfProductionsResultWrapper checkSumOfProductionsResultWrapper = GrammarValidityChecker.
                checkSumOfProductions(grammar, tempGrammarConstraints.maxSumOfProductions);
        this.resultSampleGrammarConstraints.setMaxSumOfProductionsCount(
                checkSumOfProductionsResultWrapper.getMaxSumOfProductionsCount());
        this.resultSampleGrammarConstraints.setMaxSumOfProductions(
                checkSumOfProductionsResultWrapper.isSumOfProductions());

        // CheckMaxSumOfVarsInPyramidResultWrapper
        CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramidResultWrapper = GrammarValidityChecker.
                checkMaxSumOfVarsInPyramid(pyramid, tempExamConstraints.maxSumOfVarsInPyramid);
        this.resultSamplePyramidConstraints.setMaxSumOfVarsInPyramidCount(
                checkMaxSumOfVarsInPyramidResultWrapper.getMaxSumOfVarsInPyramidCount());
        this.resultSamplePyramidConstraints.setMaxSumOfVarsInPyramid(
                checkMaxSumOfVarsInPyramidResultWrapper.isMaxSumOfVarsInPyramid());

        // CheckRightCellCombinationsForcedResultWrapper
        CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
                GrammarValidityChecker.checkRightCellCombinationForcedSimpleCells(
                        pyramid,
                        tempExamConstraints.minRightCellCombinationsForced,
                        grammar
                );
        this.resultSamplePyramidConstraints.setRightCellCombinationsForced(
                checkRightCellCombinationsForcedResultWrapper.isRightCellCombinationForced());
        this.resultSamplePyramidConstraints.setRightCellCombinationsForcedCount(
                checkRightCellCombinationsForcedResultWrapper.getRightCellCombinationForcedCount());
        this.resultSamplePyramidConstraints.setMarkedRightCellCombinationForced(
                checkRightCellCombinationsForcedResultWrapper.getMarkedRightCellCombinationForced());

        // CheckMaxNumberOfVarsPerCellResultWrapper
        CheckMaxNumberOfVarsPerCellResultWrapper maxNumberOfVarsPerCellResultWrapper = GrammarValidityChecker.
                checkMaxNumberOfVarsPerCell(pyramid, tempExamConstraints.maxNumberOfVarsPerCell);
        this.resultSamplePyramidConstraints.setMaxNumberOfVarsPerCell(
                maxNumberOfVarsPerCellResultWrapper.isMaxNumberOfVarsPerCell());
        this.resultSamplePyramidConstraints.maxNumberOfVarsPerCellCount =
                maxNumberOfVarsPerCellResultWrapper.getMaxNumberOfVarsPerCellCount();

        this.resultSamplePyramidConstraints.setExamConstraints(
                resultSamplePyramidConstraints.isMaxNumberOfVarsPerCell() &&
                        resultSamplePyramidConstraints.isMaxSumOfVarsInPyramid() &&
                        resultSamplePyramidConstraints.isRightCellCombinationsForced()
        );
        this.resultSampleGrammarConstraints.setGrammarRestrictions(
                resultSampleGrammarConstraints.isMaxSumOfProductions());
        this.isValid = resultSamplePyramidConstraints.isExamConstraints() &&
                resultSampleGrammarConstraints.isGrammarRestrictions() &&
                isWordProducible;
    }

    @Override
    public String toString() {
        return "ResultSample{" +
                "\ngrammar=" + grammar +
                "\nword='" + pyramid.getWord() + '\'' +
                "\nPyramid=\n" + Pyramid.printPyramid(pyramid.getCellsSimple()) +
                "\nmarkedRightCellCombinationForced=\n" + Pyramid.printPyramid(resultSamplePyramidConstraints.getMarkedRightCellCombinationForced()) +
                "\nmaxSumOfProductionsCount=" + resultSampleGrammarConstraints.getMaxSumOfProductionsCount() +
                "\nmaxSumOfVarsInPyramid=" + resultSamplePyramidConstraints.isMaxSumOfVarsInPyramid() +
                "\nrightCellCombinationForcedCount=" + resultSamplePyramidConstraints.getRightCellCombinationsForcedCount() +
                "\nmaxNumberOfVarsPerCellCount=" + resultSamplePyramidConstraints.getMaxNumberOfVarsPerCellCount() +
                "\n\nisValid=" + isValid +
                "\nisWordProducible=" + isWordProducible +
                resultSamplePyramidConstraints.toString() +
                resultSampleGrammarConstraints.toString() +
                "\n}";
    }
}
