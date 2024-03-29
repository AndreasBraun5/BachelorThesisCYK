package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.util.Word;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andreas Braun on 17.01.2017.
 * countGeneratedGrammars = countGeneratedGrammarsPerWord * countDifferentWords
 * word[1] has sampleGrammars from 1 to 10 and its corresponding sampleSetVs
 * word[2] has sampleGrammars from 2 to 20 ...
 */
@Getter
public class Result {
    public final String name;
	private int countGeneratedGrammarsPerWord;
	private int countDifferentWords;
	private GrammarGeneratorSettings grammarGeneratorSettings;
	private long totalTime = 0;
	private String generatorType;
	private ExampleResultSamples exampleResultSamples;
	private SuccessRates successRates = new SuccessRates();

	public static Result buildResult(String name) {
		return new Result(name);
	}

	private Result(String name){
	    this.name = name;
    }

	public Result initResult(
			int countGeneratedGrammarsPerWord,
			int countDifferentWords,
			GrammarGeneratorSettings grammarGeneratorSettings,
			String generatorType,
			Map<Word, List<ResultSample>> chunkResultSamples) {
		this.countGeneratedGrammarsPerWord = countGeneratedGrammarsPerWord;
		this.countDifferentWords = countDifferentWords;
		this.grammarGeneratorSettings = grammarGeneratorSettings;
		this.generatorType = generatorType;
		exampleResultSamples = new ExampleResultSamples( chunkResultSamples );
		this.successRates = successRates.updateSuccessRates( chunkResultSamples );
		return this;
	}

	public Result addChunk(
			long chunkTime,
			Map<Word, List<ResultSample>> chunkResultSamples) {
		this.totalTime += chunkTime;
		this.successRates = successRates.updateSuccessRates( chunkResultSamples );
		return this;
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
				successRates.toString() +
				"\n}";
	}
}
