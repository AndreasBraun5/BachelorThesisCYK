package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.exception.TestGrammarRuntimeException;
import com.github.andreasbraun5.thesis.generator.WordGeneratorDiceRoll;
import com.github.andreasbraun5.thesis.generator.GrammarGenerator;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Each instance of ResultCalculator should use the same countOfGrammarsToGeneratePerWord that the results are comparable.
 * countSamplesToKeep = 100 grammars and its setVs are kept for further inspection.
 */
public class ResultCalculator {

    private int countOfGrammarsToGeneratePerWord = 50;
    private int countDifferentWords = 50;
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
        if (countDifferentWords < 5) {
            throw new TestGrammarRuntimeException("countDifferentWords must be at least 5.");
        }
        this.countDifferentWords = countDifferentWords;
        return this;
    }

    public Result buildResultWithGenerator(GrammarGenerator grammarGenerator, WorkLog workLog) {
        Result result = Result.buildResult(grammarGenerator.getGrammarGeneratorSettings().name);
        int generatedGrammars = 0;
        boolean init = false;
        long chunkTimeInterval;
        Map<Word, List<ResultSample>> chunkResults;
        while (generatedGrammars <= countOfGrammarsToGeneratePerWord * countDifferentWords) {
            long startTime = System.currentTimeMillis();
            chunkResults = createChunkResults(grammarGenerator, workLog);
            long endTime = System.currentTimeMillis();
            chunkTimeInterval = endTime - startTime;
            System.out.println("\nInterval Tic at time: " + System.currentTimeMillis());
            if (!init) {
                result.initResult(
                        countOfGrammarsToGeneratePerWord,
                        countDifferentWords,
                        grammarGenerator.getGrammarGeneratorSettings(),
                        grammarGenerator.getGeneratorType(),
                        chunkResults
                );
                init = true;
            } else {
                result.addChunk(chunkTimeInterval, chunkResults);
            }
            generatedGrammars += CHUNK_SIZE;
        }
        System.out.println("\nFinal Tic at time: " + System.currentTimeMillis());
        return result;
    }

    /**
     * Adds the next chunk of the result to the already existing result.
     */
    private Map<Word, List<ResultSample>> createChunkResults(
            GrammarGenerator grammarGenerator,
            WorkLog workLog) {
        GrammarProperties tempGrammarProperties = grammarGenerator.getGrammarGeneratorSettings().grammarProperties;
        // allResultSamples.size() not always equals countDifferentWords because of duplicate words.
        Map<Word, List<ResultSample>> chunkResultSamples = new HashMap<>();
        int countSamplesGenerated = 0;
        for (int i = 0; i < countDifferentWords && countSamplesGenerated < CHUNK_SIZE; i++) {
            // Generate a random word that is used to decide whether the Grammar is true or false. Generate more words
            // Make sure that 100 different words are stored into the map.
            GrammarWordMatrixWrapper grammarWordMatrixWrapper = GrammarWordMatrixWrapper.buildGrammarWordMatrixWrapper().
                    setWord(WordGeneratorDiceRoll.generateWord(tempGrammarProperties));
            Word tempWord = grammarWordMatrixWrapper.getWord();
            while (chunkResultSamples.containsKey(tempWord)) {
                tempWord = WordGeneratorDiceRoll.generateWord(tempGrammarProperties);
            }
            chunkResultSamples.put(tempWord, new ArrayList<>());
            for (int j = 0; j < countOfGrammarsToGeneratePerWord && countSamplesGenerated < CHUNK_SIZE; j++) {
                // Regarding the specified testMethod of grammarGenerator the correct grammar is generated.
                GrammarWordMatrixWrapper.buildGrammarWordMatrixWrapper().setWord(tempWord);
                grammarWordMatrixWrapper = grammarGenerator.generateGrammarWordMatrixWrapper(grammarWordMatrixWrapper, workLog);
                chunkResultSamples.get(tempWord).add(
                        new ResultSample(grammarWordMatrixWrapper, grammarGenerator.getGrammarGeneratorSettings())
                );
                countSamplesGenerated++;
            }
        }
        return chunkResultSamples;
    }
}