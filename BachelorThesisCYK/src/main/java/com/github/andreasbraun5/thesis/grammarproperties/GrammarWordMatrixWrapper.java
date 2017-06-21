package com.github.andreasbraun5.thesis.grammarproperties;

import com.github.andreasbraun5.thesis.exception.GrammarWrapperRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;

import java.util.List;

/**
 * Created by Andreas Braun on 11.02.2017.
 * https://github.com/AndreasBraun5/
 * With this not only the grammar alone, but also the word can influence the filling of the pyramid.
 */
public class GrammarWordMatrixWrapper {
	private Grammar grammar;
	private List<Terminal> word;
	private SetVarKMatrix SetV;

	public static GrammarWordMatrixWrapper buildGrammarWordMatrixWrapper() {
		return new GrammarWordMatrixWrapper();
	}

	public Grammar getGrammar() {
		if ( grammar == null ) {
			throw new GrammarWrapperRuntimeException( "GrammarWordMatrixWrapper: Grammar is not defined." );
		}
		return grammar;
	}

	public GrammarWordMatrixWrapper setGrammar(Grammar grammar) {
		this.grammar = grammar;
		return this;
	}

	public List<Terminal> getWord() {
		// TODO Note: empty word not possible then if here: || word.size() == 0){
		if ( word == null ) {
			throw new GrammarWrapperRuntimeException( "GrammarWordMatrixWrapper: Word is not defined." );
		}
		return word;
	}

	public GrammarWordMatrixWrapper setWord(List<Terminal> word) {
		this.word = word;
		return this;
	}

	public SetVarKMatrix getSetV() {
		return SetV;
	}

	public void setSetV(SetVarKMatrix setV) {
		this.SetV = setV;
	}
}
