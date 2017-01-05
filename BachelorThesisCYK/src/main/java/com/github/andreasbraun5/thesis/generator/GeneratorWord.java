package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.WordException;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;

import java.util.List;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public interface GeneratorWord {
    StringBuilder generateWord(GrammarProperties grammarProperties) throws WordException;
    StringBuilder generateWord(List<Terminal> terminals, int sizeOfWord) throws WordException;

}
