package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 18.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class SuccessRates {

	private int trueCount;
	private int falseCount;
	private double successRate;

	private int trueProducibilityCount;
	private int falseProducibilityCount;
	private double successRateProducibility;

	private SuccessRatesGrammarRestrictions successRatesGrammarRestrictions;
	private SuccessRatesExamConstraints successRatesExamConstraints;

	public SuccessRates(Map<String, List<ResultSample>> allResultSamples) {
		int countGeneratedGrammars = 0;
		for ( Map.Entry<String, List<ResultSample>> entry : allResultSamples.entrySet() ) {
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
		successRatesExamConstraints = new SuccessRatesExamConstraints( allResultSamples );
		successRatesGrammarRestrictions = new SuccessRatesGrammarRestrictions( allResultSamples );
	}

	@Override
	public String toString() {
		return "\nSuccessRates{" +

				"\nSUCCESSRATEOVERVIEW:" +
				"\n			-->	SUCCESSRATE=" + successRate +
				"\n			-->	SUCCESSRATEPRODUCIBILITY=" + successRateProducibility +
				"\n			-->	SUCCESSRATEGRAMMARRESTRICTIONS=" +
				successRatesGrammarRestrictions.getSuccessRateGrammarRestrictions() +
				"\n			-->	SUCCESSRATEEXAMCONSTRAINTS=" +
				successRatesExamConstraints.getSuccessRateExamConstraints() +
				"\n" +
				"\n			-->	SUCCESSRATE=" + successRate +
				"\ntrueCount=" + trueCount +
				"\nfalseCount=" + falseCount +
				"\n			-->	SUCCESSRATEPRODUCIBILITY=" + successRateProducibility +
				"\ntrueProducibilityCount=" + trueProducibilityCount +
				"\nfalseProducibilityCount=" + falseProducibilityCount +
				successRatesGrammarRestrictions.toString() +
				successRatesExamConstraints.toString() +
				"\n}";
	}
}
