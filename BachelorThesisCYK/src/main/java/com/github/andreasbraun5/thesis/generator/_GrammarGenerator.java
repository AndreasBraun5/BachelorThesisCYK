package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.sun.glass.ui.Window;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public abstract class _GrammarGenerator {

    protected final String generatorType;
    protected _GrammarGeneratorSettings grammarGeneratorSettings;

    public _GrammarGenerator(String generatorType, _GrammarGeneratorSettings grammarGeneratorSettings) {
        this.generatorType = generatorType;
        this.grammarGeneratorSettings = grammarGeneratorSettings;
    }

    /**
     *  Its goal is to generate a grammar. GrammarWordMatrixWrapper allows to additionally give more parameters, which are needed
     *  for the specific generator method.
     *  Here the specific implementation of each algorithm is written.
     */
    public abstract GrammarWordMatrixWrapper generateGrammarWordMatrixWrapper(GrammarWordMatrixWrapper grammarWordMatrixWrapper, WorkLog workLog);

    public String getGeneratorType() {
        return generatorType;
    }

    public _GrammarGeneratorSettings getGrammarGeneratorSettings() {
        return grammarGeneratorSettings;
    }

    public void setGrammarGeneratorSettings(_GrammarGeneratorSettings grammarGeneratorSettings) {
        this.grammarGeneratorSettings = grammarGeneratorSettings;
    }
}
