package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarRuntimeException;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public interface GeneratorGrammar {
    Grammar generateGrammar(GrammarProperties grammarProperties);
}
