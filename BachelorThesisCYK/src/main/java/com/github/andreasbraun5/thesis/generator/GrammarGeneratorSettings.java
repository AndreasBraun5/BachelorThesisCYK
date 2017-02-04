package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.GrammarProperties;

/**
 * Created by Andreas Braun on 15.01.2017.
 * Each GrammarGeneratorSettings subclass should have at least GrammarProperties as its minimum settings.
 * All more variables are additional settings needed for the specific generator.
 */
public interface GrammarGeneratorSettings {

	GrammarProperties getGrammarProperties();

}
