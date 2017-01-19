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
		// TODO: left here. think about which grammars should be stored. map(word, grammars),
		// looping to generate the TestGrammarResult instance

		// right now 100 grammars for the first word are stored.
		int countSamplesToKeep = 100;
		int countSamplesStored = 0;
		List<Grammar> sampleGrammars = new ArrayList<>();
		List<Set<Variable>[][]> sampleSetVs = new ArrayList<>();
		List<Grammar> grammars = new ArrayList<>();
		List<Set<Variable>[][]> setVs = new ArrayList<>();
		List<Boolean> booleanProducibility = new ArrayList<>();
		List<Boolean> booleanRestrictions = new ArrayList<>();
		List<Boolean> booleanOverall = new ArrayList<>();
		for ( int i = 0; i < countDifferentWords; i++ ) {
			// Generate a random word that is used to decide whether the Grammar is true or false.
			GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
			String word = generatorWordDiceRoll.generateWord( tempGrammarProperties )
					.toString();
			for ( int j = 0; j <= countOfGrammarsToGeneratePerWord; j++ ) {
				grammars.add( generatorGrammarDiceRoll.generateGrammar() );
				setVs.add( CYK.calculateSetV( grammars.get( i ), word ) );
				if ( countSamplesStored < countSamplesToKeep ) {
					sampleGrammars.add( grammars.get( i ) );
					sampleSetVs.add( setVs.get( i ) );
					countSamplesStored++;
				}
				boolean producibility = GrammarValidityChecker.checkProducibilityCYK(
						setVs.get( i ),
						grammars.get( i ),
						tempGrammarProperties
				);
				// TODO: up till now only maxVarPerCell is checked
				boolean restrictions = GrammarValidityChecker.checkGrammarRestrictions(
						tempGrammarProperties, setVs.get( i ) );
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
				sampleGrammars,
				sampleSetVs,
				booleanOverall,
				booleanProducibility,
				booleanRestrictions
		);
	}
}
