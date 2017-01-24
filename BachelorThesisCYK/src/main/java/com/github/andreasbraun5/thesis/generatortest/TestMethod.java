package com.github.andreasbraun5.thesis.generatortest;

import java.util.List;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnly;
import com.github.andreasbraun5.thesis.grammar.Grammar;

/**
 * Created by Andreas Braun on 15.01.2017.
 */
public enum TestMethod {
	DICE {
		@Override
		public List<Grammar> generateGrammar(
				GeneratorGrammarDiceRollOnly generatorGrammarDiceRollOnly,
				List<Grammar> grammars) {
			grammars.add( generatorGrammarDiceRollOnly.generateGrammar() );
			return grammars;
			// TODO: why not? return grammars.add( generatorGrammarDiceRollOnly.generateGrammar() );
		}
	},

	DICEANDBIAS {
		@Override
		public List<Grammar> generateGrammar(
				GeneratorGrammarDiceRollOnly generatorGrammarDiceRollBias,
				List<Grammar> grammars) {
			grammars.add( generatorGrammarDiceRollBias.generateGrammar() );
			return grammars;
		}
	};

	public abstract List<Grammar> generateGrammar(
			GeneratorGrammarDiceRollOnly generatorGrammarDiceRollOnly,
			List<Grammar> grammars);
}
