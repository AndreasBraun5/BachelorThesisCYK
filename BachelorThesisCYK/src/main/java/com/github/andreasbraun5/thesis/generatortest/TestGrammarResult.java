package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarSettings;
import com.github.andreasbraun5.thesis.generator.GeneratorType;

/**
 * Created by Andreas Braun on 17.01.2017.
 * countGeneratedGrammars = countGeneratedGrammarsPerWord * countDifferentWords
 * word[1] has sampleGrammars from 1 to 10 and its corresponding sampleSetVs
 * word[2] has sampleGrammars from 2 to 20 ...
 */
public class TestGrammarResult {
	private int countGeneratedGrammarsPerWord;
	private int countDifferentWords;
	private GeneratorGrammarSettings generatorGrammarSettings;
	private long totalTime;
	private GeneratorType generatorType;
	private TestGrammarRepresentativeExamples testGrammarRepresentativeExamples;
	private TestGrammarResultSuccessRates testGrammarResultSuccessRates;

	public TestGrammarResult(
			int countGeneratedGrammarsPerWord,
			int countDifferentWords,
			GeneratorGrammarSettings generatorGrammarSettings,
			long totalTime,
			GeneratorType generatorType,
			TestGrammarRepresentativeExamples testGrammarRepresentativeExamples,
			Map<String, List<TestGrammarSample>> testGrammarSamples
	) {
		this.countGeneratedGrammarsPerWord = countGeneratedGrammarsPerWord;
		this.countDifferentWords = countDifferentWords;
		this.generatorGrammarSettings = generatorGrammarSettings;
		this.generatorType = generatorType;
		this.totalTime = totalTime;
		this.testGrammarRepresentativeExamples = testGrammarRepresentativeExamples;
		this.testGrammarResultSuccessRates = new TestGrammarResultSuccessRates( testGrammarSamples );
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
				"\ngeneratorType= " + this.generatorType +
				"\ngeneratorGrammarSettings" + generatorGrammarSettings +
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

	public TestGrammarRepresentativeExamples getTestGrammarRepresentativeExamples() {
		return testGrammarRepresentativeExamples;
	}

	public TestGrammarResultSuccessRates getTestGrammarResultSuccessRates() {
		return testGrammarResultSuccessRates;
	}

	public GeneratorGrammarSettings getGeneratorGrammarDiceRollSettings() {
		return generatorGrammarSettings;
	}
}
