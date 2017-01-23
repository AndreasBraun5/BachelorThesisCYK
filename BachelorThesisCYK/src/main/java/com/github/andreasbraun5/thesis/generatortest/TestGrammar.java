package com.github.andreasbraun5.thesis.generatortest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.TestGrammarRuntimeException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRoll;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
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

	public TestGrammar(int countOfGrammarsToGeneratePerWord, int countDifferentWords) {
		this.countOfGrammarsToGeneratePerWord = countOfGrammarsToGeneratePerWord;
		this.countDifferentWords = countDifferentWords;
	}

	public TestGrammarResult testGeneratorGrammar(
			GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings,
			TestMethod testMethod) {
		GrammarProperties tempGrammarProperties = generatorGrammarDiceRollSettings.grammarProperties;
		long startTime = System.currentTimeMillis();
		//	Initialising the specific generatorTest with its settings.
		GeneratorGrammarDiceRoll generatorGrammarDiceRoll =
				new GeneratorGrammarDiceRoll( generatorGrammarDiceRollSettings );
		List<Grammar> grammars = new ArrayList<>();
		List<String> wordsToGenerateSetVs = new ArrayList<>();
		List<Set<Variable>[][]> setVs = new ArrayList<>();
		List<Boolean> booleanProducibility = new ArrayList<>();
		List<Boolean> booleanRestrictions = new ArrayList<>();
		List<Boolean> booleanOverall = new ArrayList<>();
		for ( int i = 0; i < countDifferentWords; i++ ) {
			// Generate a random word that is used to decide whether the Grammar is true or false. Generate more words
			// so that one possible "not ordinary" word does't bias the result too much.
			wordsToGenerateSetVs.add( GeneratorWordDiceRoll.generateWord( tempGrammarProperties )
											  .toString() );
			for ( int j = 0; j < countOfGrammarsToGeneratePerWord; j++ ) {
				// The generated word and the grammar share the same grammarProperties --> no parameters needed for generateGrammar
				// Depending on how the grammar is generated the respecting method will be called.
				if ( testMethod == TestMethod.DICE ) {
					grammars.add( generatorGrammarDiceRoll.generateGrammar() );
				}
				else if ( testMethod == TestMethod.DICEANDBIAS ) {
					grammars.add( generatorGrammarDiceRoll.generateGrammarBias() );
				}
				else {
					throw new TestGrammarRuntimeException( "Incompatible TestMethod found." );
				}
				int curGrammarIndex = grammars.size() - 1;
				setVs.add( CYK.calculateSetV(
						grammars.get( curGrammarIndex ),
						Util.stringToTerminalList( wordsToGenerateSetVs.get( i ) )
				) );
				boolean producibility = GrammarValidityChecker.checkProducibilityCYK(
						setVs.get( curGrammarIndex ),
						grammars.get( curGrammarIndex ),
						tempGrammarProperties
				);
				// TODO: up till now only maxVarPerCell is checked
				boolean restrictions = GrammarValidityChecker.checkGrammarRestrictions(
						tempGrammarProperties, setVs.get( curGrammarIndex ) );
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
		// TODO Discuss: How to make sure the parameters are given in the right order?
		TestGrammarSamples testGrammarSamples = TestGrammarSamples.calculateTestGrammarResult(
				countOfGrammarsToGeneratePerWord,
				grammars,
				wordsToGenerateSetVs,
				setVs,
				booleanOverall,
				booleanProducibility,
				booleanRestrictions
		);
		System.out.println( "\nTic at time: " + System.currentTimeMillis() );
		return new TestGrammarResult(
				countOfGrammarsToGeneratePerWord,
				countDifferentWords,
				generatorGrammarDiceRollSettings,
				totalTime,
				TestMethod.DICEANDBIAS.toString(),
				testGrammarSamples,
				booleanOverall,
				booleanProducibility,
				booleanRestrictions
		);
	}
}