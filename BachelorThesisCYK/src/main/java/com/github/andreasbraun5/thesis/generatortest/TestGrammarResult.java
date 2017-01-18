package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;

/**
 * Created by Andreas Braun on 17.01.2017.
 */
public class TestGrammarResult {
	private int countGeneratedGrammars;
	private long totalTime;
	private List<Grammar> grammars;
	private List<Set<Variable>[][]> setVs;
	private List<Boolean> booleanOverall;
	private List<Boolean> booleanProducibility;
	private List<Boolean> booleanRestrictions;
	private TestGrammarResultSuccessRates testGrammarResultSuccessRates;

	public TestGrammarResult(
			int countGeneratedGrammars,
			long totalTime,
			List<Grammar> grammars,
			List<Set<Variable>[][]> setVs,
			List<Boolean> booleanOverall,
			List<Boolean> booleanProducibility,
			List<Boolean> booleanRestrictions
	) {
		this.countGeneratedGrammars = countGeneratedGrammars;
		this.totalTime = totalTime;
		this.grammars = grammars;
		this.setVs = setVs;
		this.booleanOverall = booleanOverall;
		this.booleanProducibility = booleanProducibility;
		this.booleanRestrictions = booleanRestrictions;
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
				"\ncountGeneratedGrammars= " + countGeneratedGrammars +
				"\ntotalTime= " + totalTime + "ms" +
				"\ntotalTime= " + minutes + "min " + seconds + "sec" +
				testGrammarResultSuccessRates.toString() +
				"\n}";
	}
}
