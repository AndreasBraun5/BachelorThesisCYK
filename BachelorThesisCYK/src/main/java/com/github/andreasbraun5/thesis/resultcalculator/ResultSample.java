package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarPropertiesExamConstraints;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarPropertiesGrammarRestrictions;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.*;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSample {

	private Grammar grammar;
	private String word;
	private SetVarKMatrix SetVarKMatrix;

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
		this.word = grammarWordMatrixWrapper.getWord().toString();
		this.SetVarKMatrix = grammarWordMatrixWrapper.getSetV();
		this.isWordProducible = GrammarValidityChecker.
				checkProducibilityCYK(SetVarKMatrix, grammar, grammarGeneratorSettings.grammarProperties );


		// CheckMaxNumberOfVarsPerCellResultWrapper
		CheckMaxNumberOfVarsPerCellResultWrapper maxNumberOfVarsPerCellResultWrapper = GrammarValidityChecker.
				checkMaxNumberOfVarsPerCell(SetVarKMatrix, tempGrammarRestrictions.getMaxNumberOfVarsPerCell() );
		this.resultSampleGrammarRestrictions.setMaxNumberOfVarsPerCellCount(
				maxNumberOfVarsPerCellResultWrapper.isMaxNumberOfVarsPerCell() );
		this.resultSampleGrammarRestrictions.setMaxNumberOfVarsPerCellCount(
				maxNumberOfVarsPerCellResultWrapper.getMaxNumberOfVarsPerCell() );

		// CheckMaxSumOfVarsInPyramidResultWrapper
		CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramidResultWrapper = GrammarValidityChecker.
				checkMaxSumOfVarsInPyramid(SetVarKMatrix, tempExamConstraints.getMaxSumOfVarsInPyramid() );
		this.resultSampleExamConstraints.setMaxSumOfVarsInPyramidCount(
				checkMaxSumOfVarsInPyramidResultWrapper.isMaxSumOfVarsInPyramid() );
		this.resultSampleExamConstraints.setMaxSumOfVarsInPyramid(
				checkMaxSumOfVarsInPyramidResultWrapper.getMaxSumOfVarsInPyramid() );

		// CheckRightCellCombinationsForcedResultWrapper
		CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
				GrammarValidityChecker.checkRightCellCombinationForced(
                        SetVarKMatrix,
						tempExamConstraints.getMinRightCellCombinationsForced(),
						grammar
				);
		this.resultSampleExamConstraints.setRightCellCombinationsForced( checkRightCellCombinationsForcedResultWrapper.
				isRightCellCombinationForced() );
		this.resultSampleExamConstraints.setCountRightCellCombinationsForced(
				checkRightCellCombinationsForcedResultWrapper
						.getCountRightCellCombinationForced() );
		this.resultSampleExamConstraints.setMarkedRightCellCombinationForced(
				checkRightCellCombinationsForcedResultWrapper
						.getMarkedRightCellCombinationForced()
		);

		// CheckSumOfProductionsResultWrapper
		CheckSumOfProductionsResultWrapper checkSumOfProductionsResultWrapper = GrammarValidityChecker.
				checkSumOfProductions( grammar, tempExamConstraints.getMaxSumOfProductions() );
		this.resultSampleExamConstraints.setMaxSumOfProductionsCount(
				checkSumOfProductionsResultWrapper.isSumOfProductions() );
		this.resultSampleExamConstraints.setMaxSumOfProductions(
				checkSumOfProductionsResultWrapper.getMaxSumOfProductions() );

		this.resultSampleExamConstraints.setExamConstraints(
				resultSampleExamConstraints.isMaxSumOfProductionsCount() &&
						resultSampleExamConstraints.isMaxSumOfVarsInPyramidCount() &&
						resultSampleExamConstraints.isRightCellCombinationsForced() );
		// TODO Note: Up till now isSizeOfWordCount is always true...
		this.resultSampleGrammarRestrictions.setSizeOfWordCount( true );
		this.resultSampleGrammarRestrictions.setRestrictions(
				resultSampleGrammarRestrictions.isMaxNumberOfVarsPerCellCount() &&
						resultSampleGrammarRestrictions.isSizeOfWordCount() );
		this.isValid = resultSampleExamConstraints.isExamConstraints() &&
				resultSampleGrammarRestrictions.isGrammarRestrictions() &&
				isWordProducible;


	}

	@Override
	public String toString() {
		return "ResultSample{" +
				"\ngrammar=" + grammar +
				"\nword='" + word + '\'' +
				"\nsetV=" + SetVarKMatrix.getStringToPrintAsLowerTriangularMatrixSimple() +
				"\nmarkedRightCellCombinationForced=" + resultSampleExamConstraints.getMarkedRightCellCombinationForced()
				.getStringToPrintAsLowerTriangularMatrix() +
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

	public String getWord() {
		return word;
	}

	public SetVarKMatrix getSetV() {
		return SetVarKMatrix;
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
