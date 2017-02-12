package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.Set;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarPropertiesExamConstraints;
import com.github.andreasbraun5.thesis.grammar.GrammarPropertiesGrammarRestrictions;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.RightCellCombinationsForcedWrapper;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSample {

	private Grammar grammar;
	private String word;
	private Set<VariableKWrapper>[][] setV;

	// This stays here
	private boolean isValid;
	private boolean isWordProducible;

	//TODO Martin: Remove new here and use builder pattern
	private ResultSampleExamConstraints resultSampleExamConstraints = new ResultSampleExamConstraints();
	private ResultSampleGrammarRestrictions resultSampleGrammarRestrictions = new ResultSampleGrammarRestrictions();

	public ResultSample(
			Grammar grammar,
			String word,
			Set<VariableKWrapper>[][] setV,
			GrammarGeneratorSettings grammarGeneratorSettings
	) {
		this.grammar = grammar;
		this.word = word;
		this.setV = setV;
		this.isWordProducible = GrammarValidityChecker.
				checkProducibilityCYK( setV, grammar, grammarGeneratorSettings.getGrammarProperties() );

		GrammarPropertiesExamConstraints tempExamConstraints = grammarGeneratorSettings.getGrammarProperties().
				grammarPropertiesExamConstraints;
		this.resultSampleExamConstraints.setMaxVarsPerCell( Util.getMaxVarPerCellForSetV( setV ) );
		this.resultSampleExamConstraints.setMaxSumOfProductionsCount(
				GrammarValidityChecker.checkSumOfProductions(
						grammar,
						tempExamConstraints.getMaxSumOfProductions()
				) );
		this.resultSampleExamConstraints.setMaxSumOfVarsInPyramidCount(
				GrammarValidityChecker.checkMaxSumOfVarsInPyramid(
						setV,
						tempExamConstraints.getMaxSumOfVarsInPyramid()
				) );
		RightCellCombinationsForcedWrapper rightCellCombinationsForcedWrapper =
				GrammarValidityChecker.checkRightCellCombinationForced(
						setV,
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
						setV,
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
				"\nsetV=" + Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				Util.getVarsFromSetDoubleArray( setV ), "setV" ) +
				Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
						resultSampleExamConstraints.getMarkedRightCellCombinationForced(),
						"\nmarkedRightCellCombinationForced=" ) +
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

	public Set<VariableKWrapper>[][] getSetV() {
		return setV;
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
