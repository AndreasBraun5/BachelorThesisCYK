package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.grammarproperties.*;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.*;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
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
            GrammarWordMatrixWrapper grammarWordMatrixWrapper,
            GrammarGeneratorSettings grammarGeneratorSettings
    ) {
        GrammarPropertiesGrammarRestrictions tempGrammarRestrictions = grammarGeneratorSettings.grammarProperties.
                grammarPropertiesGrammarRestrictions;
        GrammarPropertiesExamConstraints tempExamConstraints = grammarGeneratorSettings.grammarProperties.
                grammarPropertiesExamConstraints;
        this.grammar = grammarWordMatrixWrapper.getGrammar();
        this.word = grammarWordMatrixWrapper.getWord();
        this.pyramid = grammarWordMatrixWrapper.getVarKMatrix().getAsPyramid();
        this.isWordProducible = GrammarValidityChecker.
                checkProducibilityCYK(pyramid, grammar, grammarGeneratorSettings.grammarProperties);


        {
            GrammarConstraintValues constraintValues = new GrammarConstraintValues();
            Map<String, Boolean> checkResults = new HashMap<>();

            for (BooleanConstraintCheck check : BooleanConstraintCheck.getAllConstraints(true)) {
                check.check(grammarWordMatrixWrapper, constraintValues, checkResults);
            }
        }


        // CheckMaxNumberOfVarsPerCellResultWrapper
        CheckMaxNumberOfVarsPerCellResultWrapper maxNumberOfVarsPerCellResultWrapper = GrammarValidityChecker.
                checkMaxNumberOfVarsPerCell(pyramid, tempGrammarRestrictions.getMaxNumberOfVarsPerCell());
        this.resultSampleGrammarRestrictions.setMaxNumberOfVarsPerCellCount(
                maxNumberOfVarsPerCellResultWrapper.isMaxNumberOfVarsPerCell());
        this.resultSampleGrammarRestrictions.setMaxNumberOfVarsPerCellCount(
                maxNumberOfVarsPerCellResultWrapper.getMaxNumberOfVarsPerCell());

        // CheckMaxSumOfVarsInPyramidResultWrapper
        CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramidResultWrapper = GrammarValidityChecker.
                checkMaxSumOfVarsInPyramid(pyramid, tempExamConstraints.getMaxSumOfVarsInPyramid());
        this.resultSampleExamConstraints.setMaxSumOfVarsInPyramidCount(
                checkMaxSumOfVarsInPyramidResultWrapper.isMaxSumOfVarsInPyramid());
        this.resultSampleExamConstraints.setMaxSumOfVarsInPyramid(
                checkMaxSumOfVarsInPyramidResultWrapper.getMaxSumOfVarsInPyramid());

        // CheckRightCellCombinationsForcedResultWrapper
        CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
                GrammarValidityChecker.checkRightCellCombinationForced(
                        pyramid,
                        tempExamConstraints.getMinRightCellCombinationsForced(),
                        grammar
                );
        this.resultSampleExamConstraints.setRightCellCombinationsForced(checkRightCellCombinationsForcedResultWrapper.
                isRightCellCombinationForced());
        this.resultSampleExamConstraints.setCountRightCellCombinationsForced(
                checkRightCellCombinationsForcedResultWrapper
                        .getCountRightCellCombinationForced());
        this.resultSampleExamConstraints.setMarkedRightCellCombinationForced(
                checkRightCellCombinationsForcedResultWrapper
                        .getMarkedRightCellCombinationForced()
        );

        // CheckSumOfProductionsResultWrapper
        CheckSumOfProductionsResultWrapper checkSumOfProductionsResultWrapper = GrammarValidityChecker.
                checkSumOfProductions(grammar, tempExamConstraints.getMaxSumOfProductions());
        this.resultSampleExamConstraints.setMaxSumOfProductionsCount(
                checkSumOfProductionsResultWrapper.isSumOfProductions());
        this.resultSampleExamConstraints.setMaxSumOfProductions(
                checkSumOfProductionsResultWrapper.getMaxSumOfProductions());

        this.resultSampleExamConstraints.setExamConstraints(
                resultSampleExamConstraints.isMaxSumOfProductionsCount() &&
                        resultSampleExamConstraints.isMaxSumOfVarsInPyramidCount() &&
                        resultSampleExamConstraints.isRightCellCombinationsForced());
        // TODO Note: Up till now isSizeOfWordCount is always true...
        this.resultSampleGrammarRestrictions.setSizeOfWordCount(true);
        this.resultSampleGrammarRestrictions.setRestrictions(
                resultSampleGrammarRestrictions.isMaxNumberOfVarsPerCellCount() &&
                        resultSampleGrammarRestrictions.isSizeOfWordCount());
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
                "\nmaxVarsPerCellSetV=" + resultSampleGrammarRestrictions.getMaxNumberOfVarsPerCellCount() +
                "\nmaxSumOfVarsInPyramid=" + resultSampleExamConstraints.getMaxSumOfVarsInPyramid() +
                "\ncountRightCellCombinationForced=" + resultSampleExamConstraints.getCountRightCellCombinationsForced() +
                "\nmaxSumOfProductions=" + resultSampleExamConstraints.getMaxSumOfProductions() +
                "\n\nisValid=" + isValid +
                "\nisWordProducible=" + isWordProducible +
                resultSampleExamConstraints.toString() +
                resultSampleGrammarRestrictions.toString() +
                "\n}";
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public Word getWord() {
        return word;
    }

    public Pyramid getPyramid() {
        return pyramid;
    }

    public ResultSampleExamConstraints getResultSampleExamConstraints() {
        return resultSampleExamConstraints;
    }

    public ResultSampleGrammarRestrictions getResultSampleGrammarRestrictions() {
        return resultSampleGrammarRestrictions;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isWordProducible() {
        return isWordProducible;
    }
}
