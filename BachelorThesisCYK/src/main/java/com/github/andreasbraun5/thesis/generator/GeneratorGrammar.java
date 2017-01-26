package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;

/**
 * Created by Andreas Braun on 25.01.2017.
 * https://github.com/AndreasBraun5/
 * An GeneratorGrammar must have at least GeneratorGrammarSettings to work with. Its only public method is
 * generateGrammar which gets its needed input from the Settings that are already stored in the object and therefore
 * does't need any more input.
 * distributeTerminals and distributeCompoundVariables are used by generateGrammar and should not be accessed from other
 * classes.
 */
public abstract class GeneratorGrammar <T extends  GeneratorGrammarSettings> {

	protected GeneratorType generatorType;
	protected T generatorGrammarSettings;

	// Needed for testing purposes, fixed seed possible.
	public GeneratorGrammar(T generatorGrammarSettings, GeneratorType generatorType) {
		this.generatorGrammarSettings = generatorGrammarSettings;
		this.generatorType = generatorType;
	}

	public Grammar generateGrammar() {
		// Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
		Grammar grammar = new Grammar( generatorGrammarSettings.getGrammarProperties().variableStart );
		grammar = distributeTerminals( grammar );
		grammar = distributeCompoundVariables( grammar );
		return grammar;
	}

	protected abstract Grammar distributeTerminals(Grammar grammar);

	protected abstract Grammar distributeCompoundVariables(Grammar grammar);

	public GeneratorType getGeneratorType() {
		return generatorType;
	}

	public T getGeneratorGrammarSettings() {
		return generatorGrammarSettings;
	}
}
