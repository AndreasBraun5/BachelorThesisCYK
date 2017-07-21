package com.github.andreasbraun5.thesis.exercise;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.util.Word;
import lombok.*;

/**
 * Created by AndreasBraun on 20.07.2017.
 */
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Exercise {
    private Grammar grammar;
    private Word word;
}
