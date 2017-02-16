package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;

/**
 * Created by Andreas Braun on 17.01.2017.
 * countGeneratedGrammars = countGeneratedGrammarsPerWord * countDifferentWords
 * word[1] has sampleGrammars from 1 to 10 and its corresponding sampleSetVs
 * word[2] has sampleGrammars from 2 to 20 ...
 */
public class Result {
	private int countGeneratedGrammarsPerWord;
	private int countDifferentWords;
	private GrammarGeneratorSettings grammarGeneratorSettings;
	private long totalTime;
	private String generatorType;
	private RepresentativeResultSamples RepresentativeResultSamples;
	private SuccessRates SuccessRates;

	public Result(
			int countGeneratedGrammarsPerWord,
			int countDifferentWords,
			GrammarGeneratorSettings grammarGeneratorSettings,
			long totalTime,
			String generatorType,
			Map<String, List<ResultSample>> allResultSamples
	) {
		this.countGeneratedGrammarsPerWord = countGeneratedGrammarsPerWord;
		this.countDifferentWords = countDifferentWords;
		this.grammarGeneratorSettings = grammarGeneratorSettings;
		this.generatorType = generatorType;
		this.totalTime = totalTime;

		RepresentativeResultSamples = new RepresentativeResultSamples( allResultSamples );
		this.SuccessRates = new SuccessRates( allResultSamples );
	}



	@Override
	public String toString() {
		long minutes = TimeUnit.MILLISECONDS.toMinutes( totalTime );
		long tempTime = totalTime - minutes * 60 * 1000;
		long seconds = TimeUnit.MILLISECONDS.toSeconds( tempTime );
		return "Result{" +
				"\n		countGeneratedGrammars= " + countGeneratedGrammarsPerWord * countDifferentWords +
				"\n		countGeneratedGrammarsPerWord= " + countGeneratedGrammarsPerWord +
				"\n		countDifferentWords= " + countDifferentWords +
				"\n		totalTime= " + totalTime + "ms" +
				"\n		totalTime= " + minutes + "min " + seconds + "sec" +
				"\n		generatorType= " + this.generatorType +
				grammarGeneratorSettings +
				SuccessRates.toString() +
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

	public RepresentativeResultSamples getRepresentativeResultSamples() {
		return RepresentativeResultSamples;
	}

	public SuccessRates getSuccessRates() {
		return SuccessRates;
	}

	public GrammarGeneratorSettings getGeneratorGrammarDiceRollSettings() {
		return grammarGeneratorSettings;
	}
}
