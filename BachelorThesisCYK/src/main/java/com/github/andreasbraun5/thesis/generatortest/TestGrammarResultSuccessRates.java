package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 18.01.2017.
 */
public class TestGrammarResultSuccessRates {

	private int trueCount;
	private int falseCount;
	private double successRate;
	private int trueProducibilityCount;
	private int falseProducibilityCount;
	private double successRateProducibility;
	private int trueRestrictionsCount;
	private int falseRestrictionsCount;
	private double successRateRestrictions;

	public TestGrammarResultSuccessRates(Map<String, List<TestGrammarSample>> testGrammarSamples) {
		int countGeneratedGrammars = 0;
		for ( Map.Entry<String, List<TestGrammarSample>> entry : testGrammarSamples.entrySet() ) {
			for ( TestGrammarSample testGrammarSample : entry.getValue() ) {
				countGeneratedGrammars++;
				if ( testGrammarSample.isWordProducible() ) {
					trueProducibilityCount++;
				}
				else {
					falseProducibilityCount++;
				}
				if ( testGrammarSample.isFulfillsRestriction() ) {
					trueRestrictionsCount++;
				}
				else {
					falseRestrictionsCount++;
				}
				if ( testGrammarSample.isValidity() ) {
					trueCount++;
				}
				else {
					falseCount++;
				}
			}
		}
		successRate = (double) trueCount / countGeneratedGrammars;
		successRateProducibility = (double) trueProducibilityCount / countGeneratedGrammars;
		successRateRestrictions = (double) trueRestrictionsCount / countGeneratedGrammars;
	}

	@Override
	public String toString() {
		return "\nTestGrammarResultSuccessRates{" +
				"\ntrueCount=" + trueCount +
				"\nfalseCount=" + falseCount +
				"\n			-->	SUCCESSRATE=" + successRate +
				"\ntrueProducibilityCount=" + trueProducibilityCount +
				"\nfalseProducibilityCount=" + falseProducibilityCount +
				"\n			-->	SUCCESSRATEPRODUCIBILITY=" + successRateProducibility +
				"\ntrueRestrictionsCount=" + trueRestrictionsCount +
				"\nfalseRestrictionsCount=" + falseRestrictionsCount +
				"\n			-->	SUCCESSRATERESTRICTIONS=" + successRateRestrictions +
				"\n}";
	}
}
