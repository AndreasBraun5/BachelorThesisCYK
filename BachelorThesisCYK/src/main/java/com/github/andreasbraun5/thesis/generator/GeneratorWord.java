package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarPropertieRuntimeException;
import com.github.andreasbraun5.thesis.exception.WordRuntimeException;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;

import java.util.Set;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public interface GeneratorWord {
    StringBuilder generateWord(GrammarProperties grammarProperties);
    StringBuilder generateWord(Set<Terminal> terminals, int sizeOfWord);

}
