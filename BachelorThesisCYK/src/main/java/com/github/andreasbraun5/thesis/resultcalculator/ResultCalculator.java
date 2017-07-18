package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.exception.TestGrammarRuntimeException;
import com.github.andreasbraun5.thesis.generator.GrammarGenerator;
import com.github.andreasbraun5.thesis.generator.WordGeneratorDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Word;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Each instance of ResultCalculator should use the same countOfGrammarsToGeneratePerWord that the results are comparable.
 * countSamplesToKeep = 100 grammars and its setVs are kept for further inspection.
 */
@Builder
@Setter
@Getter
public class ResultCalculator {

    private int countOfGrammarsToGeneratePerWord = 50;
    private int countDifferentWords = 50;
    private final int CHUNK_SIZE = 1000;

    // TODO: can't override Setter??
    public void setCountDifferentWords(int countDifferentWords) {
        if (countDifferentWords < 5) {
            throw new TestGrammarRuntimeException("countDifferentWords must be at least 5.");
        }
        this.countDifferentWords = countDifferentWords;
        return;
    }

    public Result buildResultWithGenerator(GrammarGenerator grammarGenerator, WorkLog workLog) {
        Result result = Result.buildResult(grammarGenerator.getGrammarGeneratorSettings().name);
        BestResultSamples best = BestResultSamples.builder().
                name(grammarGenerator.getGrammarGeneratorSettings().name).build();
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
                best.tryAdd(chunkResults);
                init = true;
            } else {
                result.addChunk(chunkTimeInterval, chunkResults);
                best.tryAdd(chunkResults);
            }
            generatedGrammars += CHUNK_SIZE;
        }
        System.out.println("\nFinal Tic at time: " + System.currentTimeMillis());
        best.write();
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
            Word tempWord = WordGeneratorDiceRoll.generateWord(tempGrammarProperties);
            while (chunkResultSamples.containsKey(tempWord)) { // No duplicate words possible
                tempWord = WordGeneratorDiceRoll.generateWord(tempGrammarProperties);
            }
            chunkResultSamples.put(tempWord, new ArrayList<>());
            for (int j = 0; j < countOfGrammarsToGeneratePerWord && countSamplesGenerated < CHUNK_SIZE; j++) {
                GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder()
                        .pyramid(new Pyramid(tempWord)).build();
                // Regarding the specified testMethod of grammarGenerator the correct grammar is generated.
                grammarPyramidWrapper = grammarGenerator.generateGrammarPyramidWrapper(grammarPyramidWrapper, workLog);
                chunkResultSamples.get(tempWord).add(
                        new ResultSample(grammarPyramidWrapper, grammarGenerator.getGrammarGeneratorSettings())
                );
                countSamplesGenerated++;
            }
        }
        return chunkResultSamples;
    }
}