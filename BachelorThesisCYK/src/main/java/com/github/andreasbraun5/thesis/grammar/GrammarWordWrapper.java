package com.github.andreasbraun5.thesis.grammar;

import java.util.List;

import com.github.andreasbraun5.thesis.exception.GrammarWrapperRuntimeException;

/**
 * Created by Andreas Braun on 11.02.2017.
 * https://github.com/AndreasBraun5/
 * With this not only the grammar alone, but also the word can influence the filling of the pyramid.
 */
public class GrammarWordWrapper {
	private Grammar grammar;
	private List<Terminal> word;

	public static GrammarWordWrapper buildGrammarWrapper() {
		return new GrammarWordWrapper();
	}

	public Grammar getGrammar() {
		if ( grammar == null ) {
			throw new GrammarWrapperRuntimeException( "GrammarWordWrapper: Grammar is not defined." );
		}
		return grammar;
	}

	public GrammarWordWrapper setGrammar(Grammar grammar) {
		this.grammar = grammar;
		return this;
	}

	public List<Terminal> getWord() {
		// TODO Note: empty word not possible then if here: || word.size() == 0){
		if ( word == null ) {
			throw new GrammarWrapperRuntimeException( "GrammarWordWrapper: Word is not defined." );
		}
		return word;
	}

	public GrammarWordWrapper setWord(List<Terminal> word) {
		this.word = word;
		return this;
	}
}
