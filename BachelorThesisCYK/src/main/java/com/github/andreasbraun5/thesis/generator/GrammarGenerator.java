package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarWrapper;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 * An GrammarGenerator must have at least GrammarGeneratorSettings to work with. Its only public method is
 * generateGrammarWrapper which gets its needed input from the Settings that are already stored in the object and therefore
 * does't need any more input.
 * distributeTerminals and distributeCompoundVariables are used by generateGrammarWrapper and should not be accessed from other
 * classes.
 */
public abstract class GrammarGenerator<T extends GrammarGeneratorSettings> {

	protected String generatorType;
	protected T grammarGeneratorSettings;

	// Needed for testing purposes, fixed seed possible.
	public GrammarGenerator(T grammarGeneratorSettings) {
		this.grammarGeneratorSettings = grammarGeneratorSettings;
	}

	/**
	 *  Its goal is to generate a grammar. GrammarWrapper allows to additionally give more parameters, which are needed
	 *  for the specific generator method.
	 */
	public GrammarWrapper generateGrammarWrapper(GrammarWrapper grammarWrapper) {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( grammarGeneratorSettings.getGrammarProperties().variableStart );
		grammarWrapper.setGrammar( grammar );
		grammarWrapper = distributeTerminals( grammarWrapper );
		grammarWrapper = distributeCompoundVariables( grammarWrapper );
		return grammarWrapper;
	}

	protected abstract GrammarWrapper distributeTerminals(GrammarWrapper grammarWrapper);

	protected abstract GrammarWrapper distributeCompoundVariables(GrammarWrapper grammarWrapper);

	public String getGeneratorType() {
		return generatorType;
	}

	public T getGrammarGeneratorSettings() {
		return grammarGeneratorSettings;
	}
}