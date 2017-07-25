package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;

/**
 * Created by AndreasBraun on 25.07.2017.
 */
public class GrammarGeneratorSplitAndFill extends GrammarGenerator {

    public GrammarGeneratorSplitAndFill(GrammarGeneratorSettings grammarGeneratorSettings) {
        super("SPLITANDFILL", grammarGeneratorSettings);
    }

    @Override
    public GrammarPyramidWrapper generateGrammarPyramidWrapper(GrammarPyramidWrapper grammarPyramidWrapper, WorkLog workLog) {
        return null;
    }
}
