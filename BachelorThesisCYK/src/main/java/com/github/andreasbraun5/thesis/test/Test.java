package com.github.andreasbraun5.thesis.test;

import java.util.Set;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnlySettings;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 15.01.2017.
 * Each instance of Test should use the same countOfGrammarsToGenerate that the results are comparable.
 */
public class Test {

	private int countOfGrammarsToGenerate;

	public Test(int countOfGrammarsToGenerate) {
		this.countOfGrammarsToGenerate = countOfGrammarsToGenerate;
	}

	public TestResult testGeneratorGrammarDiceRollOnly(GeneratorGrammarDiceRollOnlySettings generatorGrammarDiceRollOnlySettings) {

		GrammarProperties tempGrammarProperties = generatorGrammarDiceRollOnlySettings.grammarProperties;
		long startTime = System.currentTimeMillis();
		//	Initialising the specific test with its settings.
		GeneratorGrammarDiceRollOnly generatorGrammarDiceRollOnly =
				new GeneratorGrammarDiceRollOnly( generatorGrammarDiceRollOnlySettings );

		// Generate a random word that is used to decide whether the Grammar is true or false.
		GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
		String word = generatorWordDiceRoll.generateWord( tempGrammarProperties )
				.toString();

		// looping to generate the grammars
		Grammar[] grammars = new Grammar[countOfGrammarsToGenerate];
		int trueProduciblityCount = 0;
		int falseProduciblityCount = 0;
		int trueRestrictionsCount = 0;
		int falseRestricitonsCount = 0;
		int trueCount = 0;
		int falseCount = 0;
		for ( int i = 0; i < countOfGrammarsToGenerate; i++ ) {
			grammars[i] = generatorGrammarDiceRollOnly.generateGrammar();
			Set<Variable>[][] setV = CYK.calculateSetV( grammars[i], word );
			boolean producibility = GrammarValidityChecker.checkProducibilityCYK(
					setV,
					grammars[i],
					tempGrammarProperties
			);
			/**
			 * up till now only maxVarPerCell is checked
			 */
			boolean restrictions = GrammarValidityChecker.checkGrammarRestrictions( tempGrammarProperties, setV );
			if ( producibility ) {
				trueProduciblityCount++;
			}
			else {
				falseProduciblityCount++;
			}
			if ( restrictions ) {
				trueRestrictionsCount++;
			}
			else {
				falseRestricitonsCount++;
			}
			if ( restrictions && producibility ) {
				trueCount++;
			}
			else {
				falseCount++;
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		return new TestResult(
				countOfGrammarsToGenerate,
				totalTime,
				trueCount,
				falseCount,
				trueProduciblityCount,
				falseProduciblityCount,
				trueRestrictionsCount,
				falseRestricitonsCount
		);
	}
}
