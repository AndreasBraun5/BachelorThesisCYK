package com.github.andreasbraun5.thesis.grammarproperties;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarConstraints {

    // Grammar constraints
	public int sizeOfWord = 10; // optional, default = 3
    public int maxNumberOfVarsPerCell = 3; // optional, default = 3

	@Override
	public String toString() {
		return "\nGrammarConstraints{" +
				"\n		sizeOfWord=" + sizeOfWord +
				"\n		maxNumberOfVarsPerCell=" + maxNumberOfVarsPerCell +
				"\n}";
	}
}
