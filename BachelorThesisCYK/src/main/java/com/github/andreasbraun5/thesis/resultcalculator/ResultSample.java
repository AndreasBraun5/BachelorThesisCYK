package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammarproperties.*;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.*;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Word;

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
        this.resultSampleGrammarRestrictions.isMaxNumberOfVarsPerCellCount =
                maxNumberOfVarsPerCellResultWrapper.isMaxNumberOfVarsPerCell();
        this.resultSampleGrammarRestrictions.maxNumberOfVarsPerCellCount =
                maxNumberOfVarsPerCellResultWrapper.getMaxNumberOfVarsPerCell();

        // CheckMaxSumOfVarsInPyramidResultWrapper
        CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramidResultWrapper = GrammarValidityChecker.
                checkMaxSumOfVarsInPyramid(pyramid, tempExamConstraints.maxSumOfVarsInPyramid);
        this.resultSampleExamConstraints.isMaxSumOfVarsInPyramidCount =
                checkMaxSumOfVarsInPyramidResultWrapper.isMaxSumOfVarsInPyramid();
        this.resultSampleExamConstraints.maxSumOfVarsInPyramid =
                checkMaxSumOfVarsInPyramidResultWrapper.getMaxSumOfVarsInPyramid();

        // CheckRightCellCombinationsForcedResultWrapper
        CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
                GrammarValidityChecker.checkRightCellCombinationForced(
                        pyramid,
                        tempExamConstraints.minRightCellCombinationsForced,
                        grammar
                );
        this.resultSampleExamConstraints.isRightCellCombinationsForced =
                checkRightCellCombinationsForcedResultWrapper.isRightCellCombinationForced();
        this.resultSampleExamConstraints.countRightCellCombinationsForced =
                checkRightCellCombinationsForcedResultWrapper.getCountRightCellCombinationForced();
        this.resultSampleExamConstraints.markedRightCellCombinationForced =
                checkRightCellCombinationsForcedResultWrapper.getMarkedRightCellCombinationForced();

        // CheckSumOfProductionsResultWrapper
        CheckSumOfProductionsResultWrapper checkSumOfProductionsResultWrapper = GrammarValidityChecker.
                checkSumOfProductions(grammar, tempExamConstraints.maxSumOfProductions);
        this.resultSampleExamConstraints.isMaxSumOfProductionsCount =
                checkSumOfProductionsResultWrapper.isSumOfProductions();
        this.resultSampleExamConstraints.maxSumOfProductions =
                checkSumOfProductionsResultWrapper.getMaxSumOfProductions();

        this.resultSampleExamConstraints.isExamConstraints =
                resultSampleExamConstraints.isMaxSumOfProductionsCount &&
                        resultSampleExamConstraints.isMaxSumOfVarsInPyramidCount &&
                        resultSampleExamConstraints.isRightCellCombinationsForced
        ;
        // TODO Note: Up till now isSizeOfWordCount is always true...
        this.resultSampleGrammarRestrictions.isSizeOfWordCount = true;
        this.resultSampleGrammarRestrictions.isGrammarRestrictions =
                resultSampleGrammarRestrictions.isMaxNumberOfVarsPerCellCount &&
                        resultSampleGrammarRestrictions.isSizeOfWordCount;
        this.isValid = resultSampleExamConstraints.isExamConstraints &&
                resultSampleGrammarRestrictions.isGrammarRestrictions &&
                isWordProducible;


    }

    @Override
    public String toString() {
        return "ResultSample{" +
                "\ngrammar=" + grammar +
                "\nword='" + word + '\'' +
                "\nsetV=" + pyramid +
                "\nmarkedRightCellCombinationForced=" + resultSampleExamConstraints.markedRightCellCombinationForced +
                "\nmaxVarsPerCellSetV=" + resultSampleGrammarRestrictions.maxNumberOfVarsPerCellCount +
                "\nmaxSumOfVarsInPyramid=" + resultSampleExamConstraints.maxSumOfVarsInPyramid +
                "\ncountRightCellCombinationForced=" + resultSampleExamConstraints.countRightCellCombinationsForced +
                "\nmaxSumOfProductions=" + resultSampleExamConstraints.maxSumOfProductions +
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
