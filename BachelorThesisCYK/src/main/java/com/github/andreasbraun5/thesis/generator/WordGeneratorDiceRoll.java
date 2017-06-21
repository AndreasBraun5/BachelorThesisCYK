package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarPropertiesRuntimeException;
import com.github.andreasbraun5.thesis.exception.WordRuntimeException;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 */
public class WordGeneratorDiceRoll {

	/**
	 * Excludes the case where the word could be the empty word.
	 */
	public static String generateWordAsString(GrammarProperties grammarProperties) {
		if ( grammarProperties.grammarPropertiesGrammarRestrictions.getSizeOfWord() == 0 ) {
			throw new GrammarPropertiesRuntimeException( "The sizeOfWord is not defined." );
		}
		return generateWordAsString( grammarProperties.terminals, grammarProperties.grammarPropertiesGrammarRestrictions.
				getSizeOfWord() ).toString();
	}

	public static List<Terminal> generateWordAsTerminalList(GrammarProperties grammarProperties) {
		if ( grammarProperties.grammarPropertiesGrammarRestrictions.getSizeOfWord() == 0 ) {
			throw new GrammarPropertiesRuntimeException( "The sizeOfWord is not defined." );
		}
		return Util.stringToTerminalList(
				generateWordAsString( grammarProperties.terminals, grammarProperties.grammarPropertiesGrammarRestrictions.
						getSizeOfWord() ).toString() );
	}

	/**
	 * Not all terminals must be included in the word.
	 */ // TODO Note: Include all terminals?
	public static StringBuilder generateWordAsString(Set<Terminal> terminals, int sizeOfWord) {
		StringBuilder randomWord = new StringBuilder( "" );
		List<Terminal> tempTerminals = new ArrayList<>();
		tempTerminals.addAll( terminals );
		// Generate random word out the alphabet with the given size
		Random random = new Random();
		int randomNumber;
		int min = 0; //
		int max = tempTerminals.size();
		for ( int i = 0; i < sizeOfWord; i++ ) {
			randomNumber = random.nextInt( max ) + min;
			randomWord.append( tempTerminals.get( randomNumber ) );
		}
		// dice roll the word as long until all terminals are used.
		if ( randomWord.length() > sizeOfWord ) {
			throw new WordRuntimeException( "randomWord.length of the " +
													"generated word is bigger than the specified sizeOfWord of the grammar" );
		}
		return randomWord;
	}

}
