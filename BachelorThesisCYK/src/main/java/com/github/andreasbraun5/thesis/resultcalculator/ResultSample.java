package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarPropertiesExamConstraints;
import com.github.andreasbraun5.thesis.grammar.GrammarPropertiesGrammarRestrictions;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableK;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckMaxNumberOfVarsPerCellResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckMaxSumOfVarsInPyramidResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckRightCellCombinationsForcedResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckSumOfProductionsResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.util.SetVMatrix;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSample {

	private Grammar grammar;
	private String word;
	private SetVMatrix<VariableK> setVMatrix;

	// This stays here
	private boolean isValid;
	private boolean isWordProducible;

	private ResultSampleExamConstraints resultSampleExamConstraints = new ResultSampleExamConstraints();
	private ResultSampleGrammarRestrictions resultSampleGrammarRestrictions = new ResultSampleGrammarRestrictions();

	public ResultSample(
			Grammar grammar,
			String word,
			SetVMatrix<VariableK> setVMatrix,
			GrammarGeneratorSettings grammarGeneratorSettings
	) {
		GrammarPropertiesGrammarRestrictions tempGrammarRestrictions = grammarGeneratorSettings.getGrammarProperties().
				grammarPropertiesGrammarRestrictions;
		GrammarPropertiesExamConstraints tempExamConstraints = grammarGeneratorSettings.getGrammarProperties().
				grammarPropertiesExamConstraints;
		this.grammar = grammar;
		this.word = word;
		this.setVMatrix = setVMatrix;
		this.isWordProducible = GrammarValidityChecker.
				checkProducibilityCYK( setVMatrix, grammar, grammarGeneratorSettings.getGrammarProperties() );


		// CheckMaxNumberOfVarsPerCellResultWrapper
		CheckMaxNumberOfVarsPerCellResultWrapper maxNumberOfVarsPerCellResultWrapper = GrammarValidityChecker.
				checkMaxNumberOfVarsPerCell( setVMatrix, tempGrammarRestrictions.getMaxNumberOfVarsPerCell() );
		this.resultSampleGrammarRestrictions.setMaxNumberOfVarsPerCellCount(
				maxNumberOfVarsPerCellResultWrapper.isMaxNumberOfVarsPerCell() );
		this.resultSampleGrammarRestrictions.setMaxNumberOfVarsPerCellCount(
				maxNumberOfVarsPerCellResultWrapper.getMaxNumberOfVarsPerCell() );

		// CheckMaxSumOfVarsInPyramidResultWrapper
		CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramidResultWrapper = GrammarValidityChecker.
				checkMaxSumOfVarsInPyramid( setVMatrix, tempExamConstraints.getMaxSumOfVarsInPyramid() );
		this.resultSampleExamConstraints.setMaxSumOfVarsInPyramidCount(
				checkMaxSumOfVarsInPyramidResultWrapper.isMaxSumOfVarsInPyramid() );
		this.resultSampleExamConstraints.setMaxSumOfVarsInPyramid(
				checkMaxSumOfVarsInPyramidResultWrapper.getMaxSumOfVarsInPyramid() );

		// CheckRightCellCombinationsForcedResultWrapper
		CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationsForcedResultWrapper =
				GrammarValidityChecker.checkRightCellCombinationForced(
						setVMatrix,
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
				"\nsetV=" + SetVMatrix.buildEmptySetVMatrixWrapper( word.length(), Variable.class )
				.setSetV( setVMatrix.getSimpleMatrix() )
				.getStringToPrintAsLowerTriangularMatrix() +
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

	public SetVMatrix<VariableK> getSetV() {
		return setVMatrix;
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
