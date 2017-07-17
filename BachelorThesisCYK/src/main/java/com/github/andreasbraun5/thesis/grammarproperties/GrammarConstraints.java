package com.github.andreasbraun5.thesis.grammarproperties;

/**
 * Created by Andreas Braun on 08.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarConstraints {

    // Grammar constraints
    public int maxSumOfProductions = 10; // optional, default = 10


    @Override
	public String toString() {
		return "\nGrammarConstraints{" +
				"\n		maxSumOfProductions=" + maxSumOfProductions +
				"\n}";
	}
}
