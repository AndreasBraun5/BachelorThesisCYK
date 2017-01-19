package com.github.andreasbraun5.thesis.generatortest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRoll;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 15.01.2017.
 * Each instance of TestGrammar should use the same countOfGrammarsToGeneratePerWord that the results are comparable.
 * countSamplesToKeep = 100 grammars and its setVs are kept for further inspection.
 */
public class TestGrammar {

	private int countOfGrammarsToGeneratePerWord;
	private int countDifferentWords;

	public TestGrammar(int countOfGrammarsToGeneratePerWord, int countDifferentWords) {
		this.countOfGrammarsToGeneratePerWord = countOfGrammarsToGeneratePerWord;
		this.countDifferentWords = countDifferentWords;
	}

	public TestGrammarResult testGeneratorGrammarDiceRollOnly(GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings) {
		GrammarProperties tempGrammarProperties = generatorGrammarDiceRollSettings.grammarProperties;
		long startTime = System.currentTimeMillis();
		//	Initialising the specific generatorTest with its settings.
		GeneratorGrammarDiceRoll generatorGrammarDiceRoll =
				new GeneratorGrammarDiceRoll( generatorGrammarDiceRollSettings );
		// looping to generate the TestGrammarResult instance
		List<String> sampleWords = new ArrayList<>();
		List<Grammar> sampleGrammars = new ArrayList<>();
		List<Set<Variable>[][]> sampleSetVs = new ArrayList<>();
		List<Grammar> grammars = new ArrayList<>();
		List<Set<Variable>[][]> setVs = new ArrayList<>();
		List<Boolean> booleanProducibility = new ArrayList<>();
		List<Boolean> booleanRestrictions = new ArrayList<>();
		List<Boolean> booleanOverall = new ArrayList<>();
		for ( int i = 0; i < countDifferentWords; i++ ) {
			// For each of the first 10 sampleWords that are generated, 10 grammars are stored = 100 grammars maximum together per TestGrammarResult
			int countSamplesToKeepPerWord = 10;
			int countSamplesStoredPerWord = 0;
			// Generate a random word that is used to decide whether the Grammar is true or false. Generate more words
			// so that one possible "not ordinary" word does't bias the result too much.
			GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
			sampleWords.add( generatorWordDiceRoll.generateWord( tempGrammarProperties )
									 .toString() );
			for ( int j = 0; j <= countOfGrammarsToGeneratePerWord; j++ ) {
				// The generated word and the grammar share the same grammarProperties --> no parameters needed for generateGrammar
				grammars.add( generatorGrammarDiceRoll.generateGrammar() );
				setVs.add( CYK.calculateSetV( grammars.get( i + j ), sampleWords.get( i ) ) );
				if ( countSamplesStoredPerWord < countSamplesToKeepPerWord ) {
					sampleGrammars.add( grammars.get( i + j ) );
					sampleSetVs.add( setVs.get( i + j ) );
					countSamplesStoredPerWord++;
				}
				boolean producibility = GrammarValidityChecker.checkProducibilityCYK(
						setVs.get( i + j ),
						grammars.get( i + j ),
						tempGrammarProperties
				);
				// TODO: up till now only maxVarPerCell is checked
				boolean restrictions = GrammarValidityChecker.checkGrammarRestrictions(
						tempGrammarProperties, setVs.get( i + j ) );
				if ( producibility ) {
					booleanProducibility.add( Boolean.TRUE );
				}
				else {
					booleanProducibility.add( Boolean.FALSE );
				}
				if ( restrictions ) {
					booleanRestrictions.add( Boolean.TRUE );
				}
				else {
					booleanRestrictions.add( Boolean.FALSE );
				}
				if ( restrictions && producibility ) {
					booleanOverall.add( Boolean.TRUE );
				}
				else {
					booleanOverall.add( Boolean.FALSE );
				}
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		return new TestGrammarResult(
				countOfGrammarsToGeneratePerWord,
				countDifferentWords,
				totalTime,
				sampleWords,
				sampleGrammars,
				sampleSetVs,
				booleanOverall,
				booleanProducibility,
				booleanRestrictions
		);
	}
}