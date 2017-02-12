package com.github.andreasbraun5.thesis.grammar;

import java.util.List;

import com.github.andreasbraun5.thesis.exception.GrammarWrapperRuntimeException;

/**
 * Created by Andreas Braun on 11.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarWrapper {
	private Grammar grammar;
	private List<Terminal> word;

	public static GrammarWrapper buildGrammarWrapper() {
		return new GrammarWrapper();
	}

	public Grammar getGrammar() {
		if ( grammar == null ) {
			throw new GrammarWrapperRuntimeException( "GrammarWrapper: Grammar is not defined." );
		}
		return grammar;
	}

	public GrammarWrapper setGrammar(Grammar grammar) {
		this.grammar = grammar;
		return this;
	}

	public List<Terminal> getWord() {
		if ( word == null ) { // TODO Note: empty word not possible then if here: || word.size() == 0){
			throw new GrammarWrapperRuntimeException( "GrammarWrapper: Word is not defined." );
		}
		return word;
	}

	public GrammarWrapper setWord(List<Terminal> word) {
		this.word = word;
		return this;
	}
}
