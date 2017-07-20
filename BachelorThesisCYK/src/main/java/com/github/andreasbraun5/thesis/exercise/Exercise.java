package com.github.andreasbraun5.thesis.exercise;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Word;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
@Getter
@Setter
@Builder
public class Exercise {
    private final Grammar grammar;
    private final Word word;
    private final Pyramid pyramid;
}
