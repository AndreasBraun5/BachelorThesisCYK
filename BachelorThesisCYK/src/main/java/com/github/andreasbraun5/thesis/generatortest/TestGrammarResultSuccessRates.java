package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;

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


	public TestGrammarResultSuccessRates(
			List<Boolean> booleanOverall,
			List<Boolean> booleanProducibility,
			List<Boolean> booleanRestrictions) {
		for ( int i = 0; i < booleanOverall.size(); i++ ) {
			if ( booleanProducibility.get( i ) ) {
				trueProducibilityCount++;
			}
			else {
				falseProducibilityCount++;
			}
			if ( booleanRestrictions.get( i ) ) {
				trueRestrictionsCount++;
			}
			else {
				falseRestrictionsCount++;
			}
			if ( booleanOverall.get( i ) ) {
				trueCount++;
			}
			else {
				falseCount++;
			}
			int countGeneratedGrammars = booleanOverall.size();
			successRate = (double) trueCount / countGeneratedGrammars;
			successRateProducibility = (double) trueProducibilityCount / countGeneratedGrammars;
			successRateRestrictions = (double) trueRestrictionsCount / countGeneratedGrammars;
		}
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

	public int getTrueCount() {
		return trueCount;
	}

	public int getFalseCount() {
		return falseCount;
	}

	public double getSuccessRate() {
		return successRate;
	}

	public int getTrueProducibilityCount() {
		return trueProducibilityCount;
	}

	public int getFalseProducibilityCount() {
		return falseProducibilityCount;
	}

	public double getSuccessRateProducibility() {
		return successRateProducibility;
	}

	public int getTrueRestrictionsCount() {
		return trueRestrictionsCount;
	}

	public int getFalseRestrictionsCount() {
		return falseRestrictionsCount;
	}

	public double getSuccessRateRestrictions() {
		return successRateRestrictions;
	}

}
