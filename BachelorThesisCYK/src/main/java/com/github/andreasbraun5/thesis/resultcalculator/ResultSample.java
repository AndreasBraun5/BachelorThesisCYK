package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammarproperties.ExamConstraints;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarConstraints;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.*;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Word;
import lombok.Getter;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
public class ResultSample {

    private Grammar grammar;
    private Word word;
    private Pyramid pyramid;

    // This stays here
    private boolean isValid;
    private boolean isWordProducible;

    private ResultSampleExamConstraints resultSampleExamConstraints = new ResultSampleExamConstraints();
    private ResultSampleGrammarRestrictions resultSampleGrammarRestrictions = new ResultSampleGrammarRestrictions();

    public ResultSample(
            GrammarPyramidWrapper grammarPyramidWrapper,
            GrammarGeneratorSettings grammarGeneratorSettings
    ) {
        GrammarConstraints tempGrammarRestrictions = grammarGeneratorSettings.grammarProperties.
                grammarConstraints;
        ExamConstraints tempExamConstraints = grammarGeneratorSettings.grammarProperties.
                examConstraints;
        this.grammar = grammarPyramidWrapper.getGrammar();
        this.word = grammarPyramidWrapper.getPyramid().getWord();
        this.pyramid = grammarPyramidWrapper.getPyramid();
        this.isWordProducible = GrammarValidityChecker.
                checkProducibilityCYK(pyramid, grammar, grammarGeneratorSettings.grammarProperties);

        // CheckMaxNumberOfVarsPerCellResultWrapper
        CheckMaxNumberOfVarsPerCellResultWrapper maxNumberOfVarsPerCellResultWrapper = GrammarValidityChecker.
                checkMaxNumberOfVarsPerCell(pyramid, tempGrammarRestrictions.maxNumberOfVarsPerCell);
        this.resultSampleGrammarRestrictions.setMaxNumberOfVarsPerCell(
                maxNumberOfVarsPerCellResultWrapper.isMaxNumberOfVarsPerCell());
        this.resultSampleGrammarRestrictions.maxNumberOfVarsPerCellCount =
                maxNumberOfVarsPerCellResultWrapper.getMaxNumberOfVarsPerCellCount();

        // CheckMaxSumOfVarsInPyramidResultWrapper
        CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramidResultWrapper = GrammarValidityChecker.
                checkMaxSumOfVarsInPyramid(pyramid, tempExamConstraints.maxSumOfVarsInPyramid);
        this.resultSampleExamConstraints.setMaxSumOfVarsInPyramidCount(
                checkMaxSumOfVarsInPyramidResultWrapper.getMaxSumOfVarsInPyramidCount());
        this.resultSampleExamConstraints.setMaxSumOfVarsInPyramid(
                checkMaxSumOfVarsInPyramidResultWrapper.isMaxSumOfVarsInPyramid());

        // CheckRightCellCombinationsForcedResultWrapper
        CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
                GrammarValidityChecker.checkRightCellCombinationForcedSimpleCells(
                        pyramid,
                        tempExamConstraints.minRightCellCombinationsForced,
                        grammar
                );
        this.resultSampleExamConstraints.setRightCellCombinationsForced(
                checkRightCellCombinationsForcedResultWrapper.isRightCellCombinationForced());
        this.resultSampleExamConstraints.setRightCellCombinationsForcedCount(
                checkRightCellCombinationsForcedResultWrapper.getRightCellCombinationForcedCount());
        this.resultSampleExamConstraints.setMarkedRightCellCombinationForced(
                checkRightCellCombinationsForcedResultWrapper.getMarkedRightCellCombinationForced());

        // CheckSumOfProductionsResultWrapper
        CheckSumOfProductionsResultWrapper checkSumOfProductionsResultWrapper = GrammarValidityChecker.
                checkSumOfProductions(grammar, tempExamConstraints.maxSumOfProductions);
        this.resultSampleExamConstraints.setMaxSumOfProductionsCount(
                checkSumOfProductionsResultWrapper.getMaxSumOfProductionsCount());
        this.resultSampleExamConstraints.setMaxSumOfProductions(
                checkSumOfProductionsResultWrapper.isSumOfProductions());

        this.resultSampleExamConstraints.setExamConstraints(
                resultSampleExamConstraints.isMaxSumOfProductions() &&
                        resultSampleExamConstraints.isMaxSumOfVarsInPyramid() &&
                        resultSampleExamConstraints.isRightCellCombinationsForced()
        );
        this.resultSampleGrammarRestrictions.setGrammarRestrictions(
                resultSampleGrammarRestrictions.isGrammarRestrictions());
        this.isValid = resultSampleExamConstraints.isExamConstraints() &&
                resultSampleGrammarRestrictions.isGrammarRestrictions() &&
                isWordProducible;


    }

    @Override
    public String toString() {
        return "ResultSample{" +
                "\ngrammar=" + grammar +
                "\nword='" + word + '\'' +
                "\nsetV=" + pyramid +
                "\nmarkedRightCellCombinationForced=" + resultSampleExamConstraints.getMarkedRightCellCombinationForced() +
                "\nmaxVarsPerCellSetV=" + resultSampleGrammarRestrictions.maxNumberOfVarsPerCellCount +
                "\nmaxSumOfVarsInPyramid=" + resultSampleExamConstraints.isMaxSumOfVarsInPyramid() +
                "\nrightCellCombinationForcedCount=" + resultSampleExamConstraints.getRightCellCombinationsForcedCount() +
                "\nmaxSumOfProductionsCount=" + resultSampleExamConstraints.getMaxSumOfProductionsCount() +
                "\n\nisValid=" + isValid +
                "\nisWordProducible=" + isWordProducible +
                resultSampleExamConstraints.toString() +
                resultSampleGrammarRestrictions.toString() +
                "\n}";
    }
}
