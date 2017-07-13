package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by AndreasBraun on 05.07.2017.
 */
@Getter
@Setter
@Builder
public class GrammarPyramidWrapper {
    private Grammar grammar;
    private Pyramid pyramid;
}
