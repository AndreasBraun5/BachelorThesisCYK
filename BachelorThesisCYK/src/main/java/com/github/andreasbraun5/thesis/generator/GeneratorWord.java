package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public interface GeneratorWord {
    public StringBuilder generateWord(GrammarProperties grammarProperties) throws GrammarException;
}
