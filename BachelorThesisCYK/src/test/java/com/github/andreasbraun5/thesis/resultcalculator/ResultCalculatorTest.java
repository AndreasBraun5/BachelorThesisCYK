package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettingsDiceRoll;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollOnly;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.main.Main;

/**
 * Created by Andreas Braun on 19.01.2017.
 */
public class ResultCalculatorTest {

	@Test
	public void testGeneratorGrammarDiceRollOnly() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculatorTest buildResultFromGenerator:" );
		GrammarProperties grammarProperties = Main.generateGrammarPropertiesForTesting();
		GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings =
				new GrammarGeneratorSettingsDiceRoll( grammarProperties );
		grammarProperties.sizeOfWord = 10; // All TestResults will be based on this sizeOfWord.
		grammarProperties.maxNumberOfVarsPerCell = 2;
		int countGeneratedGrammarsPerWord = 20;
		int countDifferentWords = 10;
		// this boundary is relevant so that the JVM doesn't run out of memory while calculating one Result.
		if ( ( countGeneratedGrammarsPerWord * countDifferentWords ) > 70000 ) {
			throw new GrammarSettingRuntimeException( "Too many grammars would be generated. [ N !< 70000 ]" );
		}
		ResultCalculator resultCalculator1 = new ResultCalculator( countDifferentWords, countGeneratedGrammarsPerWord);
		Result test1DiceRollResult = resultCalculator1.buildResultFromGenerator(
				new GrammarGeneratorDiceRollOnly( generatorGrammarDiceRollSettings )
		);
		List<ResultSample> testGrammarRepresentativeExamples = test1DiceRollResult.getRepresentativeResultSamples()
				.getTestGrammarRepresentativeExamples();
		int curIndex = 0;
		// TODO: correct the test
		for ( ResultSample resultSample : testGrammarRepresentativeExamples ) {
			Assert.assertEquals(
					"productions.length was not equal to .size",
					resultSample.isValidity(),
					GrammarValidityChecker.checkProducibilityCYK(
							resultSample.getSetV(),
							resultSample.getGrammar(),
							grammarProperties
					)
			);
		}
		curIndex++;
		System.out.println( "Executed successfully." );
	}
}


