package com.github.andreasbraun5.thesis.test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Andreas Braun on 17.01.2017.
 */
public class TestResult {
	private int generatedGrammars;
	private int trueGrammars;
	private int falseGrammars;
	private double successRate;
	private long totalTime;

	public TestResult(int generatedGrammars, int trueGrammars, int falseGrammars, double successRate, long totalTime) {
		this.generatedGrammars = generatedGrammars;
		this.trueGrammars = trueGrammars;
		this.falseGrammars = falseGrammars;
		this.successRate = successRate;
		this.totalTime = totalTime;
	}

	@Override
	public String toString() {
		long minutes = TimeUnit.MILLISECONDS.toMinutes( totalTime );
		long tempTime = totalTime - minutes * 60 * 1000;
		long seconds = TimeUnit.MILLISECONDS.toSeconds( tempTime );
		return "TestResult{" +
				"\ngeneratedGrammars= " + generatedGrammars +
				"\ntrueGrammars= " + trueGrammars +
				"\nfalseGrammars= " + falseGrammars +
				"\nsuccessRate= " + successRate +
				"\ntotalTime= " + totalTime + "ms" +
				"\ntotalTime= " + minutes + "min " + seconds + "sec" +
				"\n}";
	}
}
