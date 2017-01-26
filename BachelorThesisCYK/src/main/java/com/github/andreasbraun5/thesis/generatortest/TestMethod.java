package com.github.andreasbraun5.thesis.generatortest;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;

/**
 * Created by Andreas Braun on 15.01.2017.
 */
public enum TestMethod {
	DICE {
		@Override
		public Grammar generateGrammar(GeneratorGrammarDiceRoll generatorGrammarDiceRollOnly) {
			return generatorGrammarDiceRollOnly.generateGrammar();
		}
	},

	DICEANDBIAS {
		@Override
		public Grammar generateGrammar(GeneratorGrammarDiceRoll generatorGrammarDiceRollBias) {
			return generatorGrammarDiceRollBias.generateGrammar();
		}
	};

	public abstract Grammar generateGrammar(GeneratorGrammarDiceRoll generatorGrammarDiceRol);
}
