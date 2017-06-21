package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Andreas Braun on 02.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class WordGeneratorDiceRollTest {

	@Test
	public void generateWordTest() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator WordGeneratorDiceRoll: generateWordAsString" );
		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ) );
		grammarProperties.addVariables( new Variable( "A" ), new Variable( "B" ) );
		grammarProperties.addTerminals(
				new Terminal( "a" ),
				new Terminal( "b" ),
				new Terminal( "c" ),
				new Terminal( "d" )
		);
		System.out.println( grammarProperties );
		grammarProperties.grammarPropertiesGrammarRestrictions.setSizeOfWord( 10 );
		for ( int i = 0; i <= 10; i++ ) {
			String word1 = WordGeneratorDiceRoll.generateWordAsString( grammarProperties ); // GrammarRuntimeException
			System.out.println( "generated word: " + word1 );

			Assert.assertEquals(
					"grammarProperties.sizeOfWord not the same as of word1",
					grammarProperties.grammarPropertiesGrammarRestrictions.getSizeOfWord(),
					word1.length()
			);
		}

	}

}
