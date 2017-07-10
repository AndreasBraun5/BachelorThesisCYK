package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public abstract class GrammarGenerator {

    protected final String generatorType;
    protected GrammarGeneratorSettings grammarGeneratorSettings;

    public GrammarGenerator(String generatorType, GrammarGeneratorSettings grammarGeneratorSettings) {
        this.generatorType = generatorType;
        this.grammarGeneratorSettings = grammarGeneratorSettings;
    }

    /**
     * Its goal is to generate a grammar. GrammarPyramidWrapper allows to additionally give more parameters, which are needed
     * for the specific generator method.
     * Here the specific implementation of each algorithm is written.
     */
    public abstract GrammarPyramidWrapper generateGrammarPyramidWrapper(GrammarPyramidWrapper grammarPyramidWrapper, WorkLog workLog);

    public String getGeneratorType() {
        return generatorType;
    }

    public GrammarGeneratorSettings getGrammarGeneratorSettings() {
        return grammarGeneratorSettings;
    }

    public void setGrammarGeneratorSettings(GrammarGeneratorSettings grammarGeneratorSettings) {
        this.grammarGeneratorSettings = grammarGeneratorSettings;
    }
}
