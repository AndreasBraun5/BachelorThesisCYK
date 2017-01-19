package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;

/**
 * Created by Andreas Braun on 17.01.2017.
 * countGeneratedGrammars = countGeneratedGrammarsPerWord * countDifferentWords
 * word[1] has sampleGrammars from 1 to 10 and its corresponding sampleSetVs
 * word[2] has sampleGrammars from 2 to 20 ...
 */
public class TestGrammarResult {
	private int countGeneratedGrammarsPerWord;
	private int countDifferentWords;
	private long totalTime;
	private List<String> sampleWords;
	private List<Grammar> sampleGrammars;
	private List<Set<Variable>[][]> sampleSetVs;
	private TestGrammarResultSuccessRates testGrammarResultSuccessRates;

	public TestGrammarResult(
			int countGeneratedGrammarsPerWord,
			int countDifferentWords,
			long totalTime,
			List<String> sampleWords,
			List<Grammar> sampleGrammars,
			List<Set<Variable>[][]> sampleSetVs,
			List<Boolean> booleanOverall,
			List<Boolean> booleanProducibility,
			List<Boolean> booleanRestrictions
	) {
		this.countGeneratedGrammarsPerWord = countGeneratedGrammarsPerWord;
		this.countDifferentWords = countDifferentWords;
		this.sampleWords = sampleWords;
		this.totalTime = totalTime;
		this.sampleGrammars = sampleGrammars;
		this.sampleSetVs = sampleSetVs;
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
		return "TestGrammarResult{" +
				"\ncountGeneratedGrammarsPerWord= " + countGeneratedGrammarsPerWord +
				"\ncountDifferentWords= " + countDifferentWords +
				"\ntotalTime= " + totalTime + "ms" +
				"\ntotalTime= " + minutes + "min " + seconds + "sec" +
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

	public List<String> getSampleWords() {
		return sampleWords;
	}

	public List<Grammar> getSampleGrammars() {
		return sampleGrammars;
	}

	public List<Set<Variable>[][]> getSampleSetVs() {
		return sampleSetVs;
	}

	public TestGrammarResultSuccessRates getTestGrammarResultSuccessRates() {
		return testGrammarResultSuccessRates;
	}

}
