package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.Set;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarPropertiesExamConstraints;
import com.github.andreasbraun5.thesis.grammar.GrammarPropertiesGrammarRestrictions;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultSample {

	private Grammar grammar;
	private String word;
	private Set<VariableKWrapper>[][] setV;

	// TODO stays here
	private boolean validity;
	private boolean isWordProducible;

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
		this.resultSampleExamConstraints.setRightCellCombinationsForced(
				GrammarValidityChecker.checkRightCellCombinationForced(
						setV,
						tempExamConstraints.getMinRightCellCombinationsForced()
				) );
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

		// TODO: Up till now isSizeOfWordCount is always true...
		this.resultSampleGrammarRestrictions.setSizeOfWordCount( true );

		this.resultSampleGrammarRestrictions.setRestrictions(
				resultSampleGrammarRestrictions.isMaxNumberOfVarsPerCellCount() &&
						resultSampleGrammarRestrictions.isSizeOfWordCount() );

		this.validity = resultSampleExamConstraints.isExamConstraints() &&
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
				"\nvalidity=" + validity +
				"\nisWordProducible=" + isWordProducible +
				"\nmaxVarsPerCellSetV=" + resultSampleExamConstraints.getMaxVarsPerCell() +
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

	public boolean isValidity() {
		return validity;
	}

	public boolean isWordProducible() {
		return isWordProducible;
	}
}
