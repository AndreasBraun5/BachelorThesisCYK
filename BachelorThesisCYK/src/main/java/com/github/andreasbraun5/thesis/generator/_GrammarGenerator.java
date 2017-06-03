package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.GrammarWordMatrixWrapper;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public abstract class _GrammarGenerator {

    protected String generatorType;
    protected _GrammarGeneratorSettings grammarGeneratorSettings;

    public _GrammarGenerator(_GrammarGeneratorSettings grammarGeneratorSettings) {
        this.grammarGeneratorSettings = grammarGeneratorSettings;
    }

    /**
     *  Its goal is to generate a grammar. GrammarWordMatrixWrapper allows to additionally give more parameters, which are needed
     *  for the specific generator method.
     *  Here the specific implementation of each algorithm is written.
     */
    public abstract GrammarWordMatrixWrapper generateGrammarWordMatrixWrapper(GrammarWordMatrixWrapper grammarWordMatrixWrapper);

    public String getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(String generatorType) {
        this.generatorType = generatorType;
    }


    public _GrammarGeneratorSettings getGrammarGeneratorSettings() {
        return grammarGeneratorSettings;
    }

    public void setGrammarGeneratorSettings(_GrammarGeneratorSettings grammarGeneratorSettings) {
        this.grammarGeneratorSettings = grammarGeneratorSettings;
    }
}
