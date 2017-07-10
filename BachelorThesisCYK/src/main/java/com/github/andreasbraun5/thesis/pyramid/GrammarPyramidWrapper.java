package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.grammar.Grammar;

/**
 * Created by AndreasBraun on 05.07.2017.
 */
public class GrammarPyramidWrapper {

    private Grammar grammar;
    private Pyramid pyramid;

    private GrammarPyramidWrapper() {
    }

    public GrammarPyramidWrapper setGrammar(Grammar grammar) {
        this.grammar = grammar;
        return this;
    }

    public GrammarPyramidWrapper setPyramid(Pyramid pyramid) {
        this.pyramid = pyramid;
        return this;
    }

    public static GrammarPyramidWrapper buildGrammarPyramidWrapper() {
        return  new GrammarPyramidWrapper();
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public Pyramid getPyramid() {
        return pyramid;
    }
}
