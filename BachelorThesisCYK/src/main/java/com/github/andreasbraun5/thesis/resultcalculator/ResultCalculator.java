package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.andreasbraun5.thesis.exception.TestGrammarRuntimeException;
import com.github.andreasbraun5.thesis.generator.WordGeneratorDiceRoll;
import com.github.andreasbraun5.thesis.generator._GrammarGenerator;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Each instance of ResultCalculator should use the same countOfGrammarsToGeneratePerWord that the results are comparable.
 * countSamplesToKeep = 100 grammars and its setVs are kept for further inspection.
 */
public class ResultCalculator {

	private int countOfGrammarsToGeneratePerWord;
	private int countDifferentWords;
	private final int CHUNK_SIZE = 1000;

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

	public Result buildResultWithGenerator(_GrammarGenerator grammarGenerator) {
		Result result = Result.buildResult();
		int generatedGrammars = 0;
		boolean init = false;
		long chunkTimeInterval;
		Map<String, List<ResultSample>> chunkResults;
		while ( generatedGrammars <= countOfGrammarsToGeneratePerWord * countDifferentWords ) {
			long startTime = System.currentTimeMillis();
			chunkResults = createChunkResults( grammarGenerator );
			long endTime = System.currentTimeMillis();
			chunkTimeInterval = endTime - startTime;
			System.out.println( "\nInterval Tic at time: " + System.currentTimeMillis() );
			if ( !init ) {
				result.initResult(
						countOfGrammarsToGeneratePerWord,
						countDifferentWords,
						grammarGenerator.getGrammarGeneratorSettings(),
						grammarGenerator.getGeneratorType(),
						chunkResults
				);
				init = true;
			}
			else {
				result.addChunk( chunkTimeInterval, chunkResults );
			}
			generatedGrammars += CHUNK_SIZE;
		}
		System.out.println( "\nFinal Tic at time: " + System.currentTimeMillis() );
		return result;
	}

	/**
	 * Adds the next chunk of the result to the already existing result.
	 */
	private Map<String, List<ResultSample>> createChunkResults(_GrammarGenerator grammarGenerator) {
		GrammarProperties tempGrammarProperties = grammarGenerator.getGrammarGeneratorSettings().grammarProperties;
		String tempWord;
		//Grammar grammar;
		//SetVMatrix<VariableK> tempSetV;
		// allResultSamples.size() not always equals countDifferentWords because of duplicate words.
		Map<String, List<ResultSample>> chunkResultSamples = new HashMap<>();
		int countSamplesGenerated = 0;
		for ( int i = 0; i < countDifferentWords && countSamplesGenerated < CHUNK_SIZE; i++ ) {
			// Generate a random word that is used to decide whether the Grammar is true or false. Generate more words
			// Make sure that 100 different words are stored into the map.
			tempWord = WordGeneratorDiceRoll.generateWordAsString( tempGrammarProperties );
			while ( chunkResultSamples.containsKey( tempWord ) ) {
				tempWord = WordGeneratorDiceRoll.generateWordAsString( tempGrammarProperties );
			}
			chunkResultSamples.put( tempWord, new ArrayList<>() );
			for ( int j = 0; j < countOfGrammarsToGeneratePerWord && countSamplesGenerated < CHUNK_SIZE; j++ ) {
				// Regarding the specified testMethod of grammarGenerator the correct grammar is generated.
				GrammarWordMatrixWrapper grammarWordMatrixWrapper = GrammarWordMatrixWrapper.
                                buildGrammarWordMatrixWrapper().setWord(Util.stringToTerminalList( tempWord ) );
				grammarWordMatrixWrapper = grammarGenerator.generateGrammarWordMatrixWrapper(grammarWordMatrixWrapper);
				//grammar = grammarWordMatrixWrapper.getGrammar();
				//tempSetV = grammarWordMatrixWrapper.getSetV();
				//tempSetV = CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( tempWord ) );
				//Util.removeUselessProductions( grammar, tempSetV, Util.stringToTerminalList( tempWord ) );
				chunkResultSamples.get( tempWord ).add(
						new ResultSample(
                                grammarWordMatrixWrapper.getGrammar(),
								tempWord,
                                grammarWordMatrixWrapper.getSetV(),
								grammarGenerator.getGrammarGeneratorSettings()
						) );
				countSamplesGenerated++;
			}
		}
		return chunkResultSamples;
	}
}