package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarPropertiesGrammarRestrictions {

	private int sizeOfWord = 10; // optional, default = 3
	private int maxNumberOfVarsPerCell = 3; // optional, default = 3

	public static GrammarPropertiesGrammarRestrictions buildGrammarRestrictionsWrapper(){
		return new GrammarPropertiesGrammarRestrictions();
	}

	public GrammarPropertiesGrammarRestrictions setSizeOfWord(int sizeOfWord) {
		this.sizeOfWord = sizeOfWord;
		return this;
	}

	public GrammarPropertiesGrammarRestrictions setMaxNumberOfVarsPerCell(int maxNumberOfVarsPerCell) {
		this.maxNumberOfVarsPerCell = maxNumberOfVarsPerCell;
		return this;
	}

	public int getSizeOfWord() {
		return sizeOfWord;
	}

	public int getMaxNumberOfVarsPerCell() {
		return maxNumberOfVarsPerCell;
	}

	@Override
	public String toString() {
		return "\nGrammarPropertiesGrammarRestrictions{" +
				"\nsizeOfWord=" + sizeOfWord +
				"\nmaxNumberOfVarsPerCell=" + maxNumberOfVarsPerCell +
				"\n}";
	}
}
