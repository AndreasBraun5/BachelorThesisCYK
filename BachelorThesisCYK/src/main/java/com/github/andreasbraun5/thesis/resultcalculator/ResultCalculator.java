package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.TestGrammarRuntimeException;
import com.github.andreasbraun5.thesis.generator.GrammarGenerator;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Each instance of ResultCalculator should use the same countOfGrammarsToGeneratePerWord that the results are comparable.
 * countSamplesToKeep = 100 grammars and its setVs are kept for further inspection.
 */
public class ResultCalculator {

	private int countOfGrammarsToGeneratePerWord;
	private int countDifferentWords;

	public ResultCalculator(int countDifferentWords, int countOfGrammarsToGeneratePerWord) {
		this.countOfGrammarsToGeneratePerWord = countOfGrammarsToGeneratePerWord;
		if ( countDifferentWords < 5 ) {
			throw new TestGrammarRuntimeException( "countDifferentWords must be at least 5." );
		}
		this.countDifferentWords = countDifferentWords;
	}

	public Result buildResultFromGenerator(
			//GrammarGeneratorSettings generatorGrammarSettings,
			GrammarGenerator grammarGenerator) {
		GrammarProperties tempGrammarProperties = grammarGenerator.getGeneratorGrammarSettings().getGrammarProperties();
		//GrammarProperties tempGrammarProperties = generatorGrammarSettings.grammarProperties;
		long startTime = System.currentTimeMillis();
		//TODO Discuss: Readability over shortness?
		// You could directly add the resultSample to the allResultSamples
		String tempWord;
		Grammar grammar;
		Set<Variable>[][] tempSetV;
		boolean tempValidity;
		boolean tempIsWordProducible;
		boolean tempFulfillsRestriction;
		boolean tempRightCellCombinationForced;
		Integer tempMaxVarsPerCellSetV;
		// allResultSamples.size() not always equals countDifferentWords because of duplicate words.
		Map<String, List<ResultSample>> allResultSamples = new HashMap<>();
		for ( int i = 0; i < countDifferentWords; i++ ) {
			// Generate a random word that is used to decide whether the Grammar is true or false. Generate more words
			// Make sure that 100 different words are stored into the map.
			tempWord = GeneratorWordDiceRoll.generateWord( tempGrammarProperties );
			while ( allResultSamples.containsKey( tempWord ) ) {
				tempWord = GeneratorWordDiceRoll.generateWord( tempGrammarProperties );
			}
			allResultSamples.put( tempWord, new ArrayList<>() );
			for ( int j = 0; j < countOfGrammarsToGeneratePerWord; j++ ) {
				// Regarding the specified testMethod enum, the correct grammar is being added to the grammar list.
				grammar = grammarGenerator.generateGrammar();
				tempSetV = CYK.calculateSetV( grammar, Util.stringToTerminalList( tempWord ) );
				tempIsWordProducible = GrammarValidityChecker.checkProducibilityCYK(
						tempSetV,
						grammar,
						tempGrammarProperties
				);
				tempFulfillsRestriction = GrammarValidityChecker.checkGrammarRestrictions(
						tempGrammarProperties,
						tempSetV
				);
				tempRightCellCombinationForced = GrammarValidityChecker.checkRightCellCombinationForced( tempSetV );
				tempValidity = tempIsWordProducible && tempFulfillsRestriction && tempRightCellCombinationForced;
				tempMaxVarsPerCellSetV = Util.getMaxVarPerCellForSetV( tempSetV );
				allResultSamples.get( tempWord ).add( new ResultSample(
						grammar,
						tempWord,
						tempSetV,
						tempValidity,
						tempIsWordProducible,
						tempFulfillsRestriction,
						tempRightCellCombinationForced,
						tempMaxVarsPerCellSetV
				) );
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		RepresentativeResultSamples RepresentativeResultSamples = new RepresentativeResultSamples(
				allResultSamples );
		System.out.println( "\nTic at time: " + System.currentTimeMillis() );
		return new Result(
				countOfGrammarsToGeneratePerWord,
				countDifferentWords,
				grammarGenerator.getGeneratorGrammarSettings(),
				totalTime,
				grammarGenerator.getGeneratorType(),
				RepresentativeResultSamples,
				allResultSamples
		);
	}
}