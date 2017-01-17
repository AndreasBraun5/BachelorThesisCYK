package com.github.andreasbraun5.thesis.test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Andreas Braun on 17.01.2017.
 */
public class TestResult {
	private int generatedGrammars;
	private long totalTime;
	private int trueCount;
	private int falseCount;
	private double successRate;
	private int trueProduciblityCount;
	private int falseProduciblityCount;
	private double successRateProducibility;
	private int trueRestrictionsCount;
	private int falseRestrictionsCount;
	private double successRateRestrictions;



	public TestResult(
			int generatedGrammars,
			long totalTime,
			int trueCount,
			int falseCount,
			int trueProduciblityCount,
			int falseProduciblityCount,
			int trueRestrictionsCount,
			int falseRestricitonsCount
	) {
		this.generatedGrammars = generatedGrammars;
		this.totalTime = totalTime;
		this.trueCount = trueCount;
		this.falseCount = falseCount;
		this.successRate = (double) trueCount / generatedGrammars;
		this.trueProduciblityCount = trueProduciblityCount;
		this.falseProduciblityCount = falseProduciblityCount;
		this.successRateProducibility = (double) trueProduciblityCount / generatedGrammars;
		this.trueRestrictionsCount = trueRestrictionsCount;
		this.falseRestrictionsCount = falseRestricitonsCount;
		this.successRateRestrictions = (double) trueRestrictionsCount / generatedGrammars;
	}

	@Override
	public String toString() {
		long minutes = TimeUnit.MILLISECONDS.toMinutes( totalTime );
		long tempTime = totalTime - minutes * 60 * 1000;
		long seconds = TimeUnit.MILLISECONDS.toSeconds( tempTime );
		return "TestResult{" +
				"\ngeneratedGrammars= " + generatedGrammars +
				"\ntotalTime= " + totalTime + "ms" +
				"\ntotalTime= " + minutes + "min " + seconds + "sec" +
				"\ntrueCount= " + trueCount +
				"\nfalseCount= " + falseCount +
				"\nsuccessRate= " + successRate +
				"\ntrueProduciblityCount= " + trueProduciblityCount +
				"\nfalseProduciblityCount= " + falseProduciblityCount +
				"\nsuccessRateProducibility= " + successRateProducibility +
				"\ntrueRestrictionsCount= " + trueRestrictionsCount +
				"\nfalseRestrictionsCount= " + falseRestrictionsCount +
				"\nsuccessRateRestrictions= " + successRateRestrictions +
				"\n}";
	}
}
