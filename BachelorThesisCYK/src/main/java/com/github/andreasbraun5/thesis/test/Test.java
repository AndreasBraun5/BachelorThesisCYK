package com.github.andreasbraun5.thesis.test;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 15.01.2017.
 * Each instance of Test should use the same countOfGrammarsToGenerate that the results are comparable.
 */
public class Test {

	public int countOfGrammarsToGenerate;

	public Test(int countOfGrammarsToGenerate) {
		this.countOfGrammarsToGenerate = countOfGrammarsToGenerate;
	}

	public TestResult testGeneratorGrammarDiceRollOnly(GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings) {

		long startTime = System.currentTimeMillis();
		//	Initialising the specific test with its settings.
		GeneratorGrammarDiceRollOnly generatorGrammarDiceRollOnly =
				new GeneratorGrammarDiceRollOnly( generatorGrammarDiceRollSettings );

		// Generate a random word that is used to decide whether the Grammar is true or false.
		GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
		String word = generatorWordDiceRoll.generateWord( generatorGrammarDiceRollSettings.grammarProperties )
				.toString();

		// looping to generate the grammars
		Grammar[] grammars = new Grammar[countOfGrammarsToGenerate];
		int trueCount = 0;
		int falseCount = 0;
		for ( int i = 0; i < countOfGrammarsToGenerate; i++ ) {
			grammars[i] = generatorGrammarDiceRollOnly.generateGrammar();
			boolean temp = CYK.algorithmSimple( grammars[i], word );
			if ( temp ) {
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
				trueCount,
				falseCount,
				(double) trueCount / countOfGrammarsToGenerate,
				totalTime
		);
	}
}
