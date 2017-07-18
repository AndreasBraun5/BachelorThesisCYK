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

    private PyramidConstraintValues pyramidConstraintValues = new PyramidConstraintValues();
    private GrammarConstraintValues grammarConstraintValues = new GrammarConstraintValues();

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
        this.grammarConstraintValues.setMaxSumOfProductionsCount(
                checkSumOfProductionsResultWrapper.getMaxSumOfProductionsCount());
        this.grammarConstraintValues.setMaxSumOfProductions(
                checkSumOfProductionsResultWrapper.isSumOfProductions());

        // CheckMaxSumOfVarsInPyramidResultWrapper
        CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramidResultWrapper = GrammarValidityChecker.
                checkMaxSumOfVarsInPyramid(pyramid, tempExamConstraints.maxSumOfVarsInPyramid);
        this.pyramidConstraintValues.setMaxSumOfVarsInPyramidCount(
                checkMaxSumOfVarsInPyramidResultWrapper.getMaxSumOfVarsInPyramidCount());
        this.pyramidConstraintValues.setMaxSumOfVarsInPyramid(
                checkMaxSumOfVarsInPyramidResultWrapper.isMaxSumOfVarsInPyramid());

        // CheckRightCellCombinationsForcedResultWrapper
        CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
                GrammarValidityChecker.checkRightCellCombinationForcedSimpleCells(
                        pyramid,
                        tempExamConstraints.minRightCellCombinationsForced,
                        grammar
                );
        this.pyramidConstraintValues.setRightCellCombinationsForced(
                checkRightCellCombinationsForcedResultWrapper.isRightCellCombinationForced());
        this.pyramidConstraintValues.setRightCellCombinationsForcedCount(
                checkRightCellCombinationsForcedResultWrapper.getRightCellCombinationForcedCount());
        this.pyramidConstraintValues.setMarkedRightCellCombinationForced(
                checkRightCellCombinationsForcedResultWrapper.getMarkedRightCellCombinationForced());

        // CheckMaxNumberOfVarsPerCellResultWrapper
        CheckMaxNumberOfVarsPerCellResultWrapper maxNumberOfVarsPerCellResultWrapper = GrammarValidityChecker.
                checkMaxNumberOfVarsPerCell(pyramid, tempExamConstraints.maxNumberOfVarsPerCell);
        this.pyramidConstraintValues.setMaxNumberOfVarsPerCell(
                maxNumberOfVarsPerCellResultWrapper.isMaxNumberOfVarsPerCell());
        this.pyramidConstraintValues.maxNumberOfVarsPerCellCount =
                maxNumberOfVarsPerCellResultWrapper.getMaxNumberOfVarsPerCellCount();

        this.pyramidConstraintValues.setExamConstraints(
                pyramidConstraintValues.isMaxNumberOfVarsPerCell() &&
                        pyramidConstraintValues.isMaxSumOfVarsInPyramid() &&
                        pyramidConstraintValues.isRightCellCombinationsForced()
        );
        this.grammarConstraintValues.setGrammarRestrictions(
                grammarConstraintValues.isMaxSumOfProductions());
        this.isValid = pyramidConstraintValues.isExamConstraints() &&
                grammarConstraintValues.isGrammarRestrictions() &&
                isWordProducible;
    }

    @Override
    public String toString() {
        return "ResultSample{" +
                "\ngrammar=" + grammar +
                "\nword='" + pyramid.getWord() + '\'' +
                "\nPyramid=\n" + Pyramid.printPyramid(pyramid.getCellsSimple()) +
                "\nmarkedRightCellCombinationForced=\n" + Pyramid.printPyramid(pyramidConstraintValues.getMarkedRightCellCombinationForced()) +
                "\nmaxSumOfProductionsCount=" + grammarConstraintValues.getMaxSumOfProductionsCount() +
                "\nmaxSumOfVarsInPyramid=" + pyramidConstraintValues.isMaxSumOfVarsInPyramid() +
                "\nrightCellCombinationForcedCount=" + pyramidConstraintValues.getRightCellCombinationsForcedCount() +
                "\nmaxNumberOfVarsPerCellCount=" + pyramidConstraintValues.getMaxNumberOfVarsPerCellCount() +
                "\n\nisValid=" + isValid +
                "\nisWordProducible=" + isWordProducible +
                pyramidConstraintValues.toString() +
                grammarConstraintValues.toString() +
                "\n}";
    }
}
