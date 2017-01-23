package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammar.Variable;
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
		TestGrammar testGrammar1 = new TestGrammar( countGeneratedGrammarsPerWord, countDifferentWords );
		TestGrammarResult test1DiceRollResult = testGrammar1.testGeneratorGrammar(
				generatorGrammarDiceRollSettings, TestMethod.DICE );
		List<String> sampleWords = test1DiceRollResult.getTestGrammarSamples().getSampleWords();
		List<Grammar> sampleGrammars = test1DiceRollResult.getTestGrammarSamples().getSampleGrammars();
		List<Set<Variable>[][]> sampleSetVs = test1DiceRollResult.getTestGrammarSamples().getSampleSetVs();
		List<Boolean> sampleBooleanOverall = test1DiceRollResult.getTestGrammarSamples().getSampleBooleanOverall();
		List<Boolean> sampleBooleanProducibility = test1DiceRollResult.getTestGrammarSamples()
				.getSampleBooleanProducibility();
		List<Boolean> SampleBooleanRestrictions = test1DiceRollResult.getTestGrammarSamples()
				.getSampleBooleanRestrictions();
		int curIndex = 0;
		for ( int i = 0; i <= sampleWords.size(); i++ ) {
			for ( int j = 0; j <= sampleGrammars.size(); j++ ) {
				Assert.assertEquals(
						"productions.length was not equal to .size",
						sampleBooleanProducibility.get( curIndex ),
						GrammarValidityChecker.checkProducibilityCYK(
								sampleSetVs.get( curIndex ),
								sampleGrammars.get( curIndex ),
								grammarProperties
						)
				);
			}
			curIndex++;
		}
		System.out.println( "Executed successfully." );
	}
}

