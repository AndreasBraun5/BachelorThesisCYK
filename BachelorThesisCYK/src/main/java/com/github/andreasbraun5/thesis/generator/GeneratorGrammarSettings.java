package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.GrammarProperties;

/**
 * Created by Andreas Braun on 15.01.2017.
 * Each GeneratorGrammarSettings class should have at least GrammarProperties as its minimum requirement.
 * All more variables are additional settings needed for the specific generator.
 */
public abstract class GeneratorGrammarSettings {
	public final GrammarProperties grammarProperties;
	GeneratorGrammarSettings(GrammarProperties grammarProperties) {
		this.grammarProperties = grammarProperties;
	}
}
