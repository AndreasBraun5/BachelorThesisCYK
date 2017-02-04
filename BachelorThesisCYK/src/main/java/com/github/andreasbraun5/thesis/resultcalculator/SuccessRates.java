package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 18.01.2017.
 */
public class SuccessRates {

	private int trueCount;
	private int falseCount;
	private double successRate;
	private int trueProducibilityCount;
	private int falseProducibilityCount;
	private double successRateProducibility;
	private int trueRestrictionsCount;
	private int falseRestrictionsCount;
	private double successRateRestrictions;
	private int trueExamConstraints;
	private int falseExamConstraints;
	private double successRateExamConstraints;

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
				if ( resultSample.isFulfillsRestriction() ) {
					trueRestrictionsCount++;
				}
				else {
					falseRestrictionsCount++;
				}
				if ( resultSample.isValidity() ) {
					trueCount++;
				}
				else {
					falseCount++;
				}
				if ( resultSample.isRightCellCombinationForced() ) {
					trueExamConstraints++;
				}
				else {
					falseExamConstraints++;
				}
			}
		}
		successRate = (double) trueCount / countGeneratedGrammars;
		successRateProducibility = (double) trueProducibilityCount / countGeneratedGrammars;
		successRateRestrictions = (double) trueRestrictionsCount / countGeneratedGrammars;
		successRateExamConstraints = (double) trueExamConstraints / countGeneratedGrammars;
	}

	@Override
	public String toString() {
		return "\nSuccessRates{" +
				"\ntrueCount=" + trueCount +
				"\nfalseCount=" + falseCount +
				"\n			-->	SUCCESSRATE=" + successRate +
				"\ntrueProducibilityCount=" + trueProducibilityCount +
				"\nfalseProducibilityCount=" + falseProducibilityCount +
				"\n			-->	SUCCESSRATEPRODUCIBILITY=" + successRateProducibility +
				"\ntrueRestrictionsCount=" + trueRestrictionsCount +
				"\nfalseRestrictionsCount=" + falseRestrictionsCount +
				"\n			-->	SUCCESSRATERESTRICTIONS=" + successRateRestrictions +
				"\ntrueExamConstraints=" + trueExamConstraints +
				"\nfalseExamConstraints=" + falseExamConstraints +
				"\n			-->	SUCCESSRATEEXAMCONSTRAINTS=" + successRateExamConstraints +
				"\n}";
	}
}
