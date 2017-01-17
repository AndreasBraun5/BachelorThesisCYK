package com.github.andreasbraun5.thesis.grammar;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */

/**
 * Some settings for the beginning:
 * terminals.size() should be 2.
 * sizeOfWord should be between 6 and 10.
 * maxNumberOfVarsPerCell should be 3.
 */
public class GrammarProperties {

	public final Set<Variable> variables = new HashSet<>(); // obligatory attribute
	public final Set<Terminal> terminals = new HashSet<>(); // obligatory attribute

	public int sizeOfWord; // optional
	public int maxNumberOfVarsPerCell; // optional
	public VariableStart variableStart; // obligatory attribute

	public GrammarProperties(VariableStart varStart) {
		this.variableStart = varStart;
		this.variables.add( varStart );
	}

	public GrammarProperties(VariableStart varStart, Set<Variable> variables, Set<Terminal> terminals){
		this (varStart);
		this.variables.addAll( variables );
		this.terminals.addAll( terminals );
	}

	/**
	 * Generates not all obligatory attributes of the GrammarProperties. E.g. terminal and VariableStart, but
	 * variables is missing.
	 * Also sets the sizeOf the Word variable.
	 */
	public static GrammarProperties generatePartOfGrammarPropertiesFromWord(VariableStart startVariable, String word) {
		GrammarProperties grammarProperties = new GrammarProperties( startVariable );
		Set<Character> terminals = new HashSet<>();
		for ( int i = 0; i < word.length(); i++ ) {
			terminals.add( word.charAt( i ) );
		}
		for ( Character t : terminals ) {
			grammarProperties.addTerminals( new Terminal( t.toString() ) );
		}
		grammarProperties.sizeOfWord = word.length();
		return grammarProperties;
	}

	/**
	 * Generates all obligatory attributes of GrammarProperties. E.g. variables, terminals, variableStart
	 */
	public static GrammarProperties generatePartOfGrammarPropertiesFromGrammar(Grammar grammar) {
		GrammarProperties grammarProperties = new GrammarProperties( grammar.getVariableStart() );
		List<Production> productions = grammar.getProductionsAsList();
		for ( Production tempProd : productions ) {
			grammarProperties.addVariables( tempProd.getLeftHandSideElement() );
			if ( tempProd.getRightHandSideElement() instanceof Terminal ) {
				grammarProperties.addTerminals( (Terminal) tempProd.getRightHandSideElement() );
			}
		}
		return grammarProperties;
	}

	public GrammarProperties addVariables(Variable... vars) {
		Collections.addAll( this.variables, vars );
		return this;
	}

	public GrammarProperties addTerminals(Terminal... terms) {
		Collections.addAll( this.terminals, terms );
		return this;
	}

	public GrammarProperties addVariables(Set<Variable> variables) {
		this.variables.addAll( variables );
		return this;
	}

	public GrammarProperties addTerminals(Set<Terminal> terminal) {
		this.terminals.addAll( terminal );
		return this;
	}


	@Override
	public String toString() {
		return "GrammarProperties:" +
				"\nsizeOfWord= " + sizeOfWord +
				"\nvariables= " + variables +
				"\nterminals= " + terminals;
	}
}
