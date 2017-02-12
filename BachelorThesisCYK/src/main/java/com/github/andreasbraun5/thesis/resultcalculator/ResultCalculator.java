package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.TestGrammarRuntimeException;
import com.github.andreasbraun5.thesis.generator.GrammarGenerator;
import com.github.andreasbraun5.thesis.generator.WordGeneratorDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarWrapper;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Each instance of ResultCalculator should use the same countOfGrammarsToGeneratePerWord that the results are comparable.
 * countSamplesToKeep = 100 grammars and its setVs are kept for further inspection.
 */
public class ResultCalculator {

	private int countOfGrammarsToGeneratePerWord;
	private int countDifferentWords;

	/*public ResultCalculator(int countDifferentWords, int countOfGrammarsToGeneratePerWord) {
		this.countOfGrammarsToGeneratePerWord = countOfGrammarsToGeneratePerWord;
		if ( countDifferentWords < 5 ) {
			throw new TestGrammarRuntimeException( "countDifferentWords must be at least 5." );
		}
		this.countDifferentWords = countDifferentWords;
	}*/

	public static ResultCalculator buildResultCalculator() {
		return new ResultCalculator();
	}

	public ResultCalculator setCountOfGrammarsToGeneratePerWord(int countOfGrammarsToGeneratePerWord) {
		this.countOfGrammarsToGeneratePerWord = countOfGrammarsToGeneratePerWord;
		return this;
	}

	public ResultCalculator setCountDifferentWords(int countDifferentWords) {
		if ( countDifferentWords < 5 ) {
			throw new TestGrammarRuntimeException( "countDifferentWords must be at least 5." );
		}
		this.countDifferentWords = countDifferentWords;
		return this;
	}

	public Result buildResultFromGenerator(GrammarGenerator grammarGenerator) {
		GrammarProperties tempGrammarProperties = grammarGenerator.getGrammarGeneratorSettings().getGrammarProperties();
		long startTime = System.currentTimeMillis();
		String tempWord;
		Grammar grammar;
		Set<VariableKWrapper>[][] tempSetV;
		// allResultSamples.size() not always equals countDifferentWords because of duplicate words.
		Map<String, List<ResultSample>> allResultSamples = new HashMap<>();
		for ( int i = 0; i < countDifferentWords; i++ ) {
			// Generate a random word that is used to decide whether the Grammar is true or false. Generate more words
			// Make sure that 100 different words are stored into the map.
			tempWord = WordGeneratorDiceRoll.generateWordAsString( tempGrammarProperties );
			while ( allResultSamples.containsKey( tempWord ) ) {
				tempWord = WordGeneratorDiceRoll.generateWordAsString( tempGrammarProperties );
			}
			allResultSamples.put( tempWord, new ArrayList<>() );
			for ( int j = 0; j < countOfGrammarsToGeneratePerWord; j++ ) {
				// Regarding the specified testMethod of grammarGenerator the correct grammar is generated.
				GrammarWrapper grammarWrapper = GrammarWrapper.buildGrammarWrapper().setWord(
						Util.stringToTerminalList( tempWord ) );
				grammarWrapper = grammarGenerator.generateGrammarWrapper(grammarWrapper);
				grammar = grammarWrapper.getGrammar();
				tempSetV = CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( tempWord ) );
				Util.removeUselessProductions( grammar, tempSetV, Util.stringToTerminalList( tempWord ) );
				allResultSamples.get( tempWord ).add(
						new ResultSample(
								grammar,
								tempWord,
								tempSetV,
								grammarGenerator.getGrammarGeneratorSettings()
						) );
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;

		System.out.println( "\nTic at time: " + System.currentTimeMillis() );
		return new Result(
				countOfGrammarsToGeneratePerWord,
				countDifferentWords,
				grammarGenerator.getGrammarGeneratorSettings(),
				totalTime,
				grammarGenerator.getGeneratorType(),
				allResultSamples
		);
	}
}