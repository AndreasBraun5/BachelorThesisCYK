package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.util.Word;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 18.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class SuccessRates {

	private int countGeneratedGrammars = 0;

	private int trueCount = 0;
	private int falseCount = 0;
	private double successRate = 0.0;

	private int trueProducibilityCount = 0;
	private int falseProducibilityCount = 0;
	private double successRateProducibility = 0.0;

	private SuccessRatesGrammarRestrictions successRatesGrammarRestrictions = new SuccessRatesGrammarRestrictions();
	private SuccessRatesExamConstraints successRatesExamConstraints = new SuccessRatesExamConstraints();

	public SuccessRates updateSuccessRates(Map<Word, List<ResultSample>> chunkResultSamples) {
		for ( Map.Entry<Word, List<ResultSample>> entry : chunkResultSamples.entrySet() ) {
			for ( ResultSample resultSample : entry.getValue() ) {
				countGeneratedGrammars++;
				if ( resultSample.isWordProducible() ) {
					trueProducibilityCount++;
				}
				else {
					falseProducibilityCount++;
				}
				if ( resultSample.isValid() ) {
					trueCount++;
				}
				else {
					falseCount++;
				}
			}
		}
		successRate = (double) trueCount / countGeneratedGrammars;
		successRateProducibility = (double) trueProducibilityCount / countGeneratedGrammars;
		successRatesExamConstraints.updateSuccessRatesExamConstraints( chunkResultSamples );
		successRatesGrammarRestrictions.updateSuccessRatesGrammarRestrictions( chunkResultSamples );
		return this;
	}

	@Override
	public String toString() {
		return "\nSuccessRates{" +

				"\nSUCCESSRATEOVERVIEW{" +
				"\n			-->	SUCCESSRATE=" + successRate +
				"\n			-->	SUCCESSRATEPRODUCIBILITY=" + successRateProducibility +
				"\n			-->	SUCCESSRATEGRAMMARRESTRICTIONS=" +
				successRatesGrammarRestrictions.getSuccessRateGrammarRestrictions() +
				"\n			-->	SUCCESSRATEEXAMCONSTRAINTS=" +
				successRatesExamConstraints.getSuccessRateExamConstraints() +
				"\n" +
				"\n			-->	SUCCESSRATE=" + successRate +
				"\n		trueCount=" + trueCount +
				"\n		falseCount=" + falseCount +
				"\n			-->	SUCCESSRATEPRODUCIBILITY=" + successRateProducibility +
				"\n		trueProducibilityCount=" + trueProducibilityCount +
				"\n		falseProducibilityCount=" + falseProducibilityCount +
				"\n}" +
				successRatesGrammarRestrictions.toString() +
				successRatesExamConstraints.toString() +
				"\n}";
	}
}
