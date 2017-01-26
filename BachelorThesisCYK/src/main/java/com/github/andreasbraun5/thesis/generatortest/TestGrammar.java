package com.github.andreasbraun5.thesis.generatortest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.TestGrammarRuntimeException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammar;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Each instance of TestGrammar should use the same countOfGrammarsToGeneratePerWord that the results are comparable.
 * countSamplesToKeep = 100 grammars and its setVs are kept for further inspection.
 */
public class TestGrammar {

	private int countOfGrammarsToGeneratePerWord;
	private int countDifferentWords;

	public TestGrammar(int countDifferentWords, int countOfGrammarsToGeneratePerWord) {
		this.countOfGrammarsToGeneratePerWord = countOfGrammarsToGeneratePerWord;
		if ( countDifferentWords < 5 ) {
			throw new TestGrammarRuntimeException( "countDifferentWords must be at least 5." );
		}
		this.countDifferentWords = countDifferentWords;
	}

	public TestGrammarResult testGeneratorGrammar(
			//GeneratorGrammarSettings generatorGrammarSettings,
			GeneratorGrammar generatorGrammar) {
		GrammarProperties tempGrammarProperties = generatorGrammar.getGeneratorGrammarSettings().getGrammarProperties();
		//GrammarProperties tempGrammarProperties = generatorGrammarSettings.grammarProperties;
		long startTime = System.currentTimeMillis();
		//TODO Discuss: Readability over shortness?
		String tempWord;
		Grammar grammar;
		Set<Variable>[][] tempSetV;
		boolean tempValidity;
		boolean tempIsWordProducible;
		boolean tempFulfillsRestriction;
		Integer tempMaxVarsPerCellSetV;
		// testGrammarSamples.size() not always equals countDifferentWords because of duplicate words.
		Map<String, List<TestGrammarSample>> testGrammarSamples = new HashMap<>();
		for ( int i = 0; i < countDifferentWords; i++ ) {
			// Generate a random word that is used to decide whether the Grammar is true or false. Generate more words
			// Ensuring that 100 different words are stored into the map.
			tempWord = GeneratorWordDiceRoll.generateWord( tempGrammarProperties ).toString();
			while ( testGrammarSamples.containsKey( tempWord ) ) {
				tempWord = GeneratorWordDiceRoll.generateWord( tempGrammarProperties ).toString();
			}
			testGrammarSamples.put( tempWord, new ArrayList<>() );
			for ( int j = 0; j < countOfGrammarsToGeneratePerWord; j++ ) {
				// Regarding the specified testMethod enum, the correct grammar is being added to the grammar list.
				grammar = generatorGrammar.generateGrammar();
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
				tempValidity = tempIsWordProducible && tempFulfillsRestriction;
				tempMaxVarsPerCellSetV = Util.getMaxVarPerCellForSetV( tempSetV );
				testGrammarSamples.get( tempWord ).add( new TestGrammarSample(
						grammar,
						tempWord,
						tempSetV,
						tempValidity,
						tempIsWordProducible,
						tempFulfillsRestriction,
						tempMaxVarsPerCellSetV
				) );
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		TestGrammarRepresentativeExamples testGrammarRepresentativeExamples = new TestGrammarRepresentativeExamples(
				testGrammarSamples );
		System.out.println( "\nTic at time: " + System.currentTimeMillis() );
		return new TestGrammarResult(
				countOfGrammarsToGeneratePerWord,
				countDifferentWords,
				generatorGrammar.getGeneratorGrammarSettings(),
				totalTime,
				generatorGrammar.getGeneratorType(),
				testGrammarRepresentativeExamples,
				testGrammarSamples
		);
	}
}