package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generator.GeneratorType;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.main.Main;

/**
 * Created by Andreas Braun on 19.01.2017.
 */
public class TestGrammarTest {

	@Test
	public void testGeneratorGrammarDiceRollOnly() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "TestGrammarTest testGeneratorGrammar:" );
		GrammarProperties grammarProperties = Main.generateGrammarPropertiesForTesting();
		GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings =
				new GeneratorGrammarDiceRollSettings( grammarProperties );
		grammarProperties.sizeOfWord = 10; // All TestResults will be based on this sizeOfWord.
		grammarProperties.maxNumberOfVarsPerCell = 2;
		int countGeneratedGrammarsPerWord = 20;
		int countDifferentWords = 10;
		// this boundary is relevant so that the JVM doesn't run out of memory while calculating one TestGrammarResult.
		if ( ( countGeneratedGrammarsPerWord * countDifferentWords ) > 70000 ) {
			throw new GrammarSettingRuntimeException( "Too many grammars would be generated. [ N !< 70000 ]" );
		}
		TestGrammar testGrammar1 = new TestGrammar( countDifferentWords, countGeneratedGrammarsPerWord);
		TestGrammarResult test1DiceRollResult = testGrammar1.testGeneratorGrammar(
				new GeneratorGrammarDiceRollOnly( generatorGrammarDiceRollSettings, GeneratorType.DICEROLLONLY )
		);
		List<TestGrammarSample> testGrammarRepresentativeExamples = test1DiceRollResult.getTestGrammarRepresentativeExamples()
				.getTestGrammarRepresentativeExamples();
		int curIndex = 0;
		for ( TestGrammarSample testGrammarSample : testGrammarRepresentativeExamples ) {
			Assert.assertEquals(
					"productions.length was not equal to .size",
					testGrammarSample.isValidity(),
					GrammarValidityChecker.checkProducibilityCYK(
							testGrammarSample.getSetV(),
							testGrammarSample.getGrammar(),
							grammarProperties
					)
			);
		}
		curIndex++;
		System.out.println( "Executed successfully." );
	}
}


