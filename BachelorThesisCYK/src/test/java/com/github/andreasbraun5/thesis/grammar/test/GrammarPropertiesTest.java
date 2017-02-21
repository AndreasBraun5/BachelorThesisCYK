package com.github.andreasbraun5.thesis.grammar.test;

import org.junit.Test;

import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;

/**
 * Created by Andreas Braun on 02.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarPropertiesTest {

	@Test
	public void grammarPropertiesToStringTest() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator GrammarProperties: toString" );
		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ) );
		grammarProperties.addVariables( new Variable( "A" ), new Variable( "B" ) );
		grammarProperties.addTerminals( new Terminal( "a" ), new Terminal( "b" ) );
		grammarProperties.grammarPropertiesGrammarRestrictions.setSizeOfWord( 10 );
		System.out.println( grammarProperties );
	}

}
