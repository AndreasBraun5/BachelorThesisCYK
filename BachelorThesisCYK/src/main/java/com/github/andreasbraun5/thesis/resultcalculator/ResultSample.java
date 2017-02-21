package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarPropertiesExamConstraints;
import com.github.andreasbraun5.thesis.grammar.GrammarPropertiesGrammarRestrictions;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableK;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.RightCellCombinationsForcedWrapper;
import com.github.andreasbraun5.thesis.util.SetVMatrix;
import com.github.andreasbraun5.thesis.util.Util;

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

	//TODO Martin: Remove new here and use builder pattern
	private ResultSampleExamConstraints resultSampleExamConstraints = new ResultSampleExamConstraints();
	private ResultSampleGrammarRestrictions resultSampleGrammarRestrictions = new ResultSampleGrammarRestrictions();

	public ResultSample(
			Grammar grammar,
			String word,
			SetVMatrix<VariableK> setVMatrix,
			GrammarGeneratorSettings grammarGeneratorSettings
	) {
		this.grammar = grammar;
		this.word = word;
		this.setVMatrix = setVMatrix;
		this.isWordProducible = GrammarValidityChecker.
				checkProducibilityCYK( setVMatrix, grammar, grammarGeneratorSettings.getGrammarProperties() );

		GrammarPropertiesExamConstraints tempExamConstraints = grammarGeneratorSettings.getGrammarProperties().
				grammarPropertiesExamConstraints;
		this.resultSampleExamConstraints.setMaxVarsPerCell( Util.getMaxVarPerCellForSetV( setVMatrix ) );
		this.resultSampleExamConstraints.setMaxSumOfProductionsCount(
				GrammarValidityChecker.checkSumOfProductions(
						grammar,
						tempExamConstraints.getMaxSumOfProductions()
				) );
		this.resultSampleExamConstraints.setMaxSumOfVarsInPyramidCount(
				GrammarValidityChecker.checkMaxSumOfVarsInPyramid(
						setVMatrix,
						tempExamConstraints.getMaxSumOfVarsInPyramid()
				) );
		RightCellCombinationsForcedWrapper rightCellCombinationsForcedWrapper =
				GrammarValidityChecker.checkRightCellCombinationForced(
						setVMatrix,
						tempExamConstraints.getMinRightCellCombinationsForced(),
						grammar
				);
		this.resultSampleExamConstraints.setRightCellCombinationsForced( rightCellCombinationsForcedWrapper.
				isRightCellCombinationForced() );
		this.resultSampleExamConstraints.setCountRightCellCombinationsForced( rightCellCombinationsForcedWrapper.
				getCountRightCellCombinationForced() );
		this.resultSampleExamConstraints.setMarkedRightCellCombinationForced( rightCellCombinationsForcedWrapper.
				getMarkedRightCellCombinationForced()
		);
		this.resultSampleExamConstraints.setExamConstraints(
				resultSampleExamConstraints.isMaxSumOfProductionsCount() &&
						resultSampleExamConstraints.isMaxSumOfVarsInPyramidCount() &&
						resultSampleExamConstraints.isRightCellCombinationsForced()
		);
		GrammarPropertiesGrammarRestrictions tempGrammarRestrictions = grammarGeneratorSettings.getGrammarProperties().
				grammarPropertiesGrammarRestrictions;
		this.resultSampleGrammarRestrictions.setMaxNumberOfVarsPerCellCount(
				GrammarValidityChecker.checkMaxNumberOfVarsPerCell(
						setVMatrix,
						tempGrammarRestrictions.getMaxNumberOfVarsPerCell()
				) );

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
				"\nisValid=" + isValid +
				"\nisWordProducible=" + isWordProducible +
				"\nmaxVarsPerCellSetV=" + resultSampleExamConstraints.getMaxVarsPerCell() +
				"\ncountRightCellCombinationsForced=" + resultSampleExamConstraints.getCountRightCellCombinationsForced() +
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
