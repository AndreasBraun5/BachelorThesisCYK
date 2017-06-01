package com.github.andreasbraun5.thesis.resultcalculator;

import java.util.List;

import com.github.andreasbraun5.thesis.generator._GrammarGeneratorDiceRollOnly;
import com.github.andreasbraun5.thesis.generator._GrammarGeneratorSettings;
import org.junit.Assert;
import org.junit.Test;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettingsDiceRoll;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.main.Main;

/**
 * Created by Andreas Braun on 19.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class ResultCalculatorTest {

	@Test
	public void testGeneratorGrammarDiceRollOnly() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculatorTest buildResultWithGenerator:" );
		GrammarProperties grammarProperties = Main.generateGrammarPropertiesForTesting();
		_GrammarGeneratorSettings generatorGrammarDiceRollSettings =
				new _GrammarGeneratorSettings( grammarProperties );
		grammarProperties.grammarPropertiesGrammarRestrictions.setSizeOfWord( 10 ); // All TestResults will be based on this sizeOfWord.
		grammarProperties.grammarPropertiesGrammarRestrictions.setMaxNumberOfVarsPerCell( 2 );

		int countGeneratedGrammarsPerWord = 20;
		int countDifferentWords = 10;
		// this boundary is relevant so that the JVM doesn't run out of memory while calculating one Result.
		if ( ( countGeneratedGrammarsPerWord * countDifferentWords ) > 70000 ) {
			throw new GrammarSettingRuntimeException( "Too many grammars would be generated. [ N !< 70000 ]" );
		}
		ResultCalculator resultCalculator1 = ResultCalculator.buildResultCalculator().
				setCountDifferentWords( countDifferentWords ).
				setCountOfGrammarsToGeneratePerWord( countGeneratedGrammarsPerWord );
		Result test1DiceRollResult = resultCalculator1.buildResultWithGenerator(
				new _GrammarGeneratorDiceRollOnly( generatorGrammarDiceRollSettings )
		);
		List<ResultSample> representativeResultSamples = test1DiceRollResult.getRepresentativeResultSamples()
				.getTestGrammarRepresentativeExamples();
		for ( ResultSample resultSample : representativeResultSamples ) {
			Assert.assertEquals(
					"ResultSample validity is not as expected.",
					resultSample.isWordProducible(),
					GrammarValidityChecker.checkProducibilityCYK(
							resultSample.getSetV(),
							resultSample.getGrammar(),
							grammarProperties
					)
			);
		}
		System.out.println( "Executed successfully." );
	}
}


