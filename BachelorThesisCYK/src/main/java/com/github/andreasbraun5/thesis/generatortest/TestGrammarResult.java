package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;

/**
 * Created by Andreas Braun on 17.01.2017.
 * countGeneratedGrammars = countGeneratedGrammarsPerWord * countDifferentWords
 * word[1] has sampleGrammars from 1 to 10 and its corresponding sampleSetVs
 * word[2] has sampleGrammars from 2 to 20 ...
 */
public class TestGrammarResult {
	private int countGeneratedGrammarsPerWord;
	private int countDifferentWords;
	private GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings;
	private long totalTime;
	private String testMethod;
	private TestGrammarSamples testGrammarSamples;
	private TestGrammarResultSuccessRates testGrammarResultSuccessRates;

	public TestGrammarResult(
			int countGeneratedGrammarsPerWord,
			int countDifferentWords,
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings,
			long totalTime,
			String testMethod,
			TestGrammarSamples testGrammarSamples,
			List<Boolean> booleanOverall,
			List<Boolean> booleanProducibility,
			List<Boolean> booleanRestrictions
	) {
		this.countGeneratedGrammarsPerWord = countGeneratedGrammarsPerWord;
		this.countDifferentWords = countDifferentWords;
		this.generatorGrammarDiceRollSettings = generatorGrammarDiceRollSettings;
		this.testMethod = testMethod;
		this.totalTime = totalTime;
		this.testGrammarSamples = testGrammarSamples;
		this.testGrammarResultSuccessRates = new TestGrammarResultSuccessRates(
				booleanOverall,
				booleanProducibility,
				booleanRestrictions
		);
	}

	@Override
	public String toString() {
		long minutes = TimeUnit.MILLISECONDS.toMinutes( totalTime );
		long tempTime = totalTime - minutes * 60 * 1000;
		long seconds = TimeUnit.MILLISECONDS.toSeconds( tempTime );
		return "\n\nTestGrammarResult{" +
				"\ncountGeneratedGrammars= " + countGeneratedGrammarsPerWord * countDifferentWords +
				"\ncountGeneratedGrammarsPerWord= " + countGeneratedGrammarsPerWord +
				"\ncountDifferentWords= " + countDifferentWords +
				"\ntotalTime= " + totalTime + "ms" +
				"\ntotalTime= " + minutes + "min " + seconds + "sec" +
				"\ntestMethod= " + this.testMethod +
				testGrammarResultSuccessRates.toString() +
				"\n}";
	}

	public int getCountGeneratedGrammarsPerWord() {
		return countGeneratedGrammarsPerWord;
	}

	public int getCountDifferentWords() {
		return countDifferentWords;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public TestGrammarSamples getTestGrammarSamples() {
		return testGrammarSamples;
	}

	public TestGrammarResultSuccessRates getTestGrammarResultSuccessRates() {
		return testGrammarResultSuccessRates;
	}

	public GeneratorGrammarDiceRollSettings getGeneratorGrammarDiceRollSettings() {
		return generatorGrammarDiceRollSettings;
	}
}
