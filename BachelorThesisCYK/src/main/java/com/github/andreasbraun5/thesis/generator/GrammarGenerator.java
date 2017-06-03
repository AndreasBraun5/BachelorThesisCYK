package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarWordMatrixWrapper;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 * An GrammarGenerator must have at least GrammarGeneratorSettings to work with. Its only public method is
 * generateGrammarWordMatrixWrapper which gets its needed input from the Settings that are already stored in the object and therefore
 * does't need any more input.
 * distributeTerminals and distributeCompoundVariables are used by generateGrammarWordMatrixWrapper and should not be accessed from other
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
	 *  Its goal is to generate a grammar. GrammarWordMatrixWrapper allows to additionally give more parameters, which are needed
	 *  for the specific generator method.
	 */
	public GrammarWordMatrixWrapper generateGrammarWrapper(GrammarWordMatrixWrapper grammarWordMatrixWrapper) {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( grammarGeneratorSettings.getGrammarProperties().variableStart );
		grammarWordMatrixWrapper.setGrammar( grammar );
		grammarWordMatrixWrapper = distributeTerminals(grammarWordMatrixWrapper);
		grammarWordMatrixWrapper = distributeCompoundVariables(grammarWordMatrixWrapper);
		return grammarWordMatrixWrapper;
	}

	protected abstract GrammarWordMatrixWrapper distributeTerminals(GrammarWordMatrixWrapper grammarWordMatrixWrapper);

	protected abstract GrammarWordMatrixWrapper distributeCompoundVariables(GrammarWordMatrixWrapper grammarWordMatrixWrapper);

	public String getGeneratorType() {
		return generatorType;
	}

	public T getGrammarGeneratorSettings() {
		return grammarGeneratorSettings;
	}
}
