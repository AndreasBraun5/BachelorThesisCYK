package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.GrammarWordMatrixWrapper;
import org.junit.Test;

import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.main.Main;

/**
 * Created by Andreas Braun on 11.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarGeneratorDiceRollTopDownMartensTest {

	@Test
	public void distributeTerminals() throws Exception {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "GrammarGeneratorDiceRollTopDownMartensTest: generateGrammarWordMatrixWrapper" );
		GrammarProperties grammarProperties = Main.generateGrammarPropertiesForTesting();
		System.out.println( grammarProperties );

		GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings =
				new GrammarGeneratorSettingsDiceRoll( grammarProperties );
		generatorGrammarDiceRollSettings.setMaxValueTerminalsAreAddedTo( 1 );
		generatorGrammarDiceRollSettings.setMinValueTerminalsAreAddedTo( 1 );
		generatorGrammarDiceRollSettings.setMaxValueCompoundVariablesAreAddedTo( 2 );

		GrammarGeneratorDiceRollTopDownMartens generatorDiceRollTopDownMartens =
				new GrammarGeneratorDiceRollTopDownMartens( generatorGrammarDiceRollSettings );
		GrammarWordMatrixWrapper grammarWordMatrixWrapper = GrammarWordMatrixWrapper.buildGrammarWordMatrixWrapper();
		grammarWordMatrixWrapper.setWord( WordGeneratorDiceRoll.generateWordAsTerminalList( grammarProperties ) );
		generatorDiceRollTopDownMartens.generateGrammarWrapper(grammarWordMatrixWrapper);
	}

}