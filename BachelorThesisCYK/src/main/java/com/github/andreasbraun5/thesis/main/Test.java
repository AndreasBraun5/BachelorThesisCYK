package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generator.GeneratorWord;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarIntegrityChecker;

/**
 * Created by Andreas Braun on 15.01.2017.
 */
public class Test {

	public int countOfGrammarsToGenerate;
	public GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings;

	public Test(int countOfGrammarsToGenerate, GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings) {
		this.countOfGrammarsToGenerate = countOfGrammarsToGenerate;
		this.generatorGrammarDiceRollSettings = generatorGrammarDiceRollSettings;
	}

	public void testGeneratorGrammarDiceRollOnly() {

		GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings = new GeneratorGrammarDiceRollSettings(
				this.generatorGrammarDiceRollSettings.grammarProperties );
		// TODO settings must be specified
		GeneratorGrammarDiceRollOnly generatorGrammarDiceRollOnly = new GeneratorGrammarDiceRollOnly(
				generatorGrammarDiceRollSettings
		);

		// generating word used to check the integrity.
		GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
		String word = generatorWordDiceRoll.generateWord( generatorGrammarDiceRollSettings.grammarProperties).toString();

		// Using loop to generate Grammars
		Grammar[] grammars = new Grammar[countOfGrammarsToGenerate];
		int trueCount = 0;
		int falseCount = 0;
		// 500000 Grammars take around 3 minutes
		for ( int i = 0; i < countOfGrammarsToGenerate; i++ ) {
			grammars[i] = generatorGrammarDiceRollOnly.generateGrammar();
			boolean temp = GrammarIntegrityChecker.checkIntegrity( grammars[i], word );
			if ( temp ) {
				trueCount++;
			}
			else {
				falseCount++;
			}
		}
		System.out.println( "True: " + trueCount + " and False: " + falseCount );
	}
}
